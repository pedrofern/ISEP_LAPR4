/*
 * Copyright (c) 2005 Einar Pehrson <einar@pehrson.nu>.
 *
 * This file is part of
 * CleanSheets - a spreadsheet application for the Java platform.
 *
 * CleanSheets is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * CleanSheets is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CleanSheets; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package csheets;

import com.itextpdf.text.pdf.parser.Line;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.SwingUtilities;

import csheets.core.*;
import csheets.core.formula.compiler.FormulaCompiler;
import csheets.core.formula.lang.Language;
import csheets.ext.Extension;
import csheets.ext.ExtensionManager;
import csheets.io.Codec;
import csheets.io.CodecFactory;
import csheets.io.NamedProperties;
import csheets.ui.ctrl.UIController;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.LinkedList;
import lapr4.red.s1.core.n1150943.contacts.application.BootEventVerifier;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import lapr4.blue.s2.ipc.n1140822.fileShare.ShareConfiguration;
import lapr4.green.s2.core.n1150657.extensions.ui.ExtensionLoadFrame;
import lapr4.red.s1.core.n1150385.enabledisableextensions.ExtensionEvent;
import lapr4.red.s3.ipc.n1150451.multipleRealtimeWorkbookSearch.comms.CommUDPClient;
import lapr4.red.s3.ipc.n1150451.multipleRealtimeWorkbookSearch.comms.FilePathDTO;

/**
 * CleanSheets - the main class of the application. The class manages workbooks,
 * performs I/O operations and provides support for notifying listeners when
 * workbooks are created, loaded or saved. The CleanSheet will be a observer
 * now, that will be updated when the loading is completed.
 *
 * @author Einar Pehrson
 */
public class CleanSheets implements Observer {

    public static final String OWN_NAME = System.getProperty("user.name");

    /**
     * The filename for the localization resources
     */
    private static final String DEFAULT_RESOURCE_FILENAME = "MessagesBundle";

    /**
     * The resource bundle for localization
     */
    private static ResourceBundle messages = null;

    /**
     * The filename of the default properties, loaded from the directory of the
     * class
     */
    private static final String DEFAULT_PROPERTIES_FILENAME = "res/defaults.xml";

    /**
     * The filename of the user properties, loaded from the user's current
     * working directory
     */
    private static final String USER_PROPERTIES_FILENAME = "csheets.xml";

    /**
     * The open workbooks
     */
    private Map<Workbook, File> workbooks = new HashMap<Workbook, File>();

    /**
     * The application's properties
     */
    private NamedProperties props;

    private UIController uiController;

    /**
     * The listeners registered to receive events
     */
    private List<SpreadsheetAppListener> listeners
            = new ArrayList<SpreadsheetAppListener>();

    /**
     * Gives access to the localization strings
     *
     * @param stringID string id
     * @return return
     */
    public static String getString(String stringID) {
        if (messages == null) {
            messages = ResourceBundle.getBundle(DEFAULT_RESOURCE_FILENAME, Locale.getDefault());
        }
        return messages.getString(stringID);
    }

    private ExtensionLoadFrame extensionLoadFrame;

    public static boolean flag = false;

    /**
     * Creates the CleanSheets application.
     */
    public CleanSheets() {
        if (flag) {
            runWithouLoadingOption();
        } else {
            //The Load of the extensions Frame
            extensionLoadFrame = new ExtensionLoadFrame(this);
            //It will wait for the notify.
            //The nofity will happen after the loading of the extensions
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //do nothing
                }

                // Load resources
                messages = ResourceBundle.getBundle(DEFAULT_RESOURCE_FILENAME, Locale.getDefault());

                // Loads compilers
                FormulaCompiler.getInstance();

                // Loads language
                Language.getInstance();

                // Loads default properties
                Properties defaultProps = new Properties();
                InputStream defaultStream = CleanSheets.class.getResourceAsStream(DEFAULT_PROPERTIES_FILENAME);
                if (defaultStream != null) {
                    try {
                        defaultProps.loadFromXML(defaultStream);
                    } catch (IOException e) {
                        System.err.println("Could not load default application properties.");
                    } finally {
                        try {
                            if (defaultStream != null) {
                                defaultStream.close();
                            }
                        } catch (IOException e) {
                        }
                    }
                }

                // Loads user properties
                File propsFile = new File(USER_PROPERTIES_FILENAME);
                props = new NamedProperties(propsFile, defaultProps);
                ShareConfiguration.downloadFolder = props.getProperty("share.download.folder");
                ShareConfiguration.sharedFolder = props.getProperty("share.shared.folder");
                ShareConfiguration.setProperties(props);
                BootEventVerifier bev = new BootEventVerifier();
                try {
                    bev.verify(props);
                } catch (DataConcurrencyException e) {
                    e.printStackTrace();
                } catch (DataIntegrityViolationException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void runWithouLoadingOption() {
        // Load resources
        messages = ResourceBundle.getBundle(DEFAULT_RESOURCE_FILENAME, Locale.getDefault());

        // Loads compilers
        FormulaCompiler.getInstance();

        // Loads language
        Language.getInstance();

        // Loads extensions
        ExtensionManager.getInstance();

        // Loads default properties
        Properties defaultProps = new Properties();
        InputStream defaultStream = CleanSheets.class.getResourceAsStream(DEFAULT_PROPERTIES_FILENAME);
        if (defaultStream != null) {
            try {
                defaultProps.loadFromXML(defaultStream);
            } catch (IOException e) {
                System.err.println("Could not load default application properties.");
            } finally {
                try {
                    if (defaultStream != null) {
                        defaultStream.close();
                    }
                } catch (IOException e) {
                }
            }
        }

        // Loads user properties
        File propsFile = new File(USER_PROPERTIES_FILENAME);
        props = new NamedProperties(propsFile, defaultProps);
        ShareConfiguration.downloadFolder = props.getProperty("share.download.folder");
        ShareConfiguration.sharedFolder = props.getProperty("share.shared.folder");
        ShareConfiguration.setProperties(props);
        BootEventVerifier bev = new BootEventVerifier();
        try {
            bev.verify(props);
        } catch (DataConcurrencyException e) {
            e.printStackTrace();
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }

    public static void setFlag(boolean newflag) {
        flag = newflag;
    }

    public static boolean getFlag() {
        return flag;
    }

    /**
     * Starts CleanSheets from the command-line.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
       // CleanSheets.setFlag(true); //if the beggining of the app is without the loading
        CleanSheets app = new CleanSheets();

        // Configures look and feel
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.JDialog.setDefaultLookAndFeelDecorated(true);

        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
            // Other options for look and feel:
            //"javax.swing.plaf.nimbus.NimbusLookAndFeel");
            //javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        /* try {
			javax.swing.UIManager.setLookAndFeel("className");
		} catch (Exception e) {} */

        // Creates user interface
        new csheets.ui.Frame.Creator(app).createAndWait();
        app.create();
    }

    /**
     * It will update the observer, notifying it so the rest of the app
     * continue.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            notify();
        }
    }

    public void setUIController(UIController uiController) {
        this.uiController = uiController;
    }

    /**
     * Returns the current user properties.
     *
     * @return the current user properties
     */
    public Properties getUserProperties() {
        return props;
    }

    /**
     * Exits the application.
     */
    public void exit() {
        // Stores properties
        if (props.size() > 0) {
            try {
                props.storeToXML("CleanSheets User Properties ("
                        + DateFormat.getDateTimeInstance().format(new Date()) + ")");
            } catch (IOException e) {
                System.err.println("An error occurred while saving properties.");
            }
        }

        // Terminates the virtual machine
        System.exit(0);
    }

    /**
     * Creates a new workbook.
     */
    public void create() {
        Workbook workbook = new Workbook(3, uiController);
        workbooks.put(workbook, null);
        fireSpreadsheetAppEvent(workbook, null, SpreadsheetAppEvent.Type.CREATED);
    }

    /**
     * Loads a workbook from the given file.
     *
     * @param file the file in which the workbook is stored
     * @throws IOException if the file could not be loaded correctly
     * @throws java.lang.ClassNotFoundException exception
     */
    public void load(File file) throws IOException, ClassNotFoundException {
        Codec codec = new CodecFactory().getCodec(file);
        if (codec != null) {
            FileInputStream stream = null;
            Workbook workbook;
            try {
                // Reads workbook data
                stream = new FileInputStream(file);

                workbook = codec.read(stream);
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException e) {
                }
            }

            workbooks.put(workbook, file);
            fireSpreadsheetAppEvent(workbook, file, SpreadsheetAppEvent.Type.LOADED);
        } else {
            throw new IOException("Codec could not be found");
        }
    }

    /**
     * Unloads the given workbook.
     *
     * @param workbook the workbook to unload
     */
    public void unload(Workbook workbook) {
        File file = workbooks.remove(workbook);
        fireSpreadsheetAppEvent(workbook, file, SpreadsheetAppEvent.Type.UNLOADED);
    }

    /**
     * Saves the given workbook to the file from which it was loaded, or to
     * which it was most recently saved.
     *
     * @param workbook the workbook to save
     * @throws IOException if the file could not be saved correctly
     */
    public void save(Workbook workbook) throws IOException {
        File file = workbooks.get(workbook);
        if (file != null) {
            saveAs(workbook, file);
            CommUDPClient c = new CommUDPClient(new FilePathDTO(file.getName(), file.getAbsolutePath()), 15310);
            c.start();
        } else {
            throw new FileNotFoundException("No file assigned to the workbook.");
        }
    }

    /**
     * Saves the given workbook to the given file.
     *
     * @param workbook the workbook to save
     * @param file the file to which the workbook should be saved
     * @throws IOException if the file could not be saved correctly
     */
    public void saveAs(Workbook workbook, File file) throws IOException {
        Codec codec = new CodecFactory().getCodec(file);
        if (codec != null) {
            FileOutputStream stream = null;
            try {
                // Reads workbook data
                stream = new FileOutputStream(file);

                codec.write(workbook, stream);
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException e) {
                }
            }
           try{
                writePreviewWorkbookMetadata(file);
           }
           catch (Exception ex)
           {
               
           }
             
            workbooks.put(workbook, file);
            fireSpreadsheetAppEvent(workbook, file, SpreadsheetAppEvent.Type.SAVED);
        }
    }

    /**
     * Returns the workbooks that are open.
     *
     * @return the workbooks that are open
     */
    public Workbook[] getWorkbooks() {
        Collection<Workbook> workbookSet = workbooks.keySet();
        return workbookSet.toArray(new Workbook[workbookSet.size()]);
    }

    /**
     * Returns the file in which the given workbook is stored.
     *
     * @param workbook workbook
     * @return the file in which the given workbook is stored, or null if it
     * isn't
     */
    public File getFile(Workbook workbook) {
        return workbooks.get(workbook);
    }

    /**
     * Returns whether a file has been specified for the given workbook, either
     * when it was loaded or when it was last saved.
     *
     * @param workbook workbook
     * @return whether the given workbook belongs to a file
     */
    public boolean isWorkbookStored(Workbook workbook) {
        return workbooks.get(workbook) != null;
    }

    /**
     * Returns the workbook that is stored in the given file, if it is already
     * open.
     *
     * @param file the file to look for
     * @return the workbook that is stored in the given file, or null if the
     * file isn't open
     */
    public Workbook getWorkbook(File file) {
        for (Map.Entry<Workbook, File> entry : workbooks.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(file)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns whether the given file is open, and a workbook thereby loaded
     * from it or saved to it.
     *
     * @param file the file to look for
     * @return whether the given file is open
     */
    public boolean isFileOpen(File file) {
        return workbooks.containsValue(file);
    }

    /**
     * Registers the given listener on the spreadsheet application.
     *
     * @param listener the listener to be added
     */
    public void addSpreadsheetAppListener(SpreadsheetAppListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the given listener from the spreadsheet application.
     *
     * @param listener the listener to be removed
     */
    public void removeSpreadsheetAppListener(SpreadsheetAppListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that a spreadsheet application event
     * occurred.
     *
     * @param workbook the workbook that was affected
     * @param file the file that was affected
     * @param type type
     */
    private void fireSpreadsheetAppEvent(Workbook workbook, File file,
            SpreadsheetAppEvent.Type type) {
        SpreadsheetAppEvent event
                = new SpreadsheetAppEvent(this, workbook, file, type);
        if (SwingUtilities.isEventDispatchThread()) {
            for (SpreadsheetAppListener listener : listeners) {
                switch (event.getType()) {
                    case CREATED:
                        listener.workbookCreated(event);
                        break;
                    case LOADED:
                        listener.workbookLoaded(event);
                        break;
                    case UNLOADED:
                        listener.workbookUnloaded(event);
                        break;
                    case SAVED:
                        listener.workbookSaved(event);
                        break;
                }
            }
        } else {
            SwingUtilities.invokeLater(
                    new EventDispatcher(event,
                            listeners.toArray(new SpreadsheetAppListener[listeners.size()])
                    )
            );

        }
    }

    /**
     * A utility for dispatching events on the AWT event dispatching thread.
     *
     * @author Einar Pehrson
     */
    public static class EventDispatcher implements Runnable {

        /**
         * The event to fire
         */
        private SpreadsheetAppEvent event;

        /**
         * The listeners to which the event should be dispatched
         */
        private SpreadsheetAppListener[] listeners;

        /**
         * Creates a new event dispatcher.
         *
         * @param event the event to fire
         * @param listeners the listeners to which the event should be
         * dispatched
         */
        public EventDispatcher(SpreadsheetAppEvent event,
                SpreadsheetAppListener[] listeners) {
            this.event = event;
            this.listeners = listeners;
        }

        /**
         * Dispatches the event.
         */
        public void run() {
            for (SpreadsheetAppListener listener : listeners) {
                switch (event.getType()) {
                    case CREATED:
                        listener.workbookCreated(event);
                        break;
                    case LOADED:
                        listener.workbookLoaded(event);
                        break;
                    case UNLOADED:
                        listener.workbookUnloaded(event);
                        break;
                    case SAVED:
                        listener.workbookSaved(event);
                        break;
                }
            }
        }
    }

    /**
     * Writes metadata on the workbook so it can be previewed
     *
     * @param file the workbook file
     * @throws IOException if not found
     */
    private void writePreviewWorkbookMetadata(File file) throws IOException {
        if (file != null) {
            UserDefinedFileAttributeView view = Files.getFileAttributeView(file.toPath(), UserDefinedFileAttributeView.class);
            List<Cell> listCells = new LinkedList<>();
            //add 3x3 cells from the first filled cell
            for (int i = 0; i < uiController.getActiveSpreadsheet().getColumnCount() + 1; i++) {
                for (int j = 0; j < uiController.getActiveSpreadsheet().getRowCount() + 1; j++) {
                    if (!uiController.getActiveSpreadsheet().getCell(i, j).getContent().equals("")) {
                        int rowIndex = i;
                        int colIndex = j;
                        for (int k = rowIndex; k < rowIndex + 3; k++) {
                            for (int l = colIndex; l < colIndex + 3; l++) {
                                listCells.add(uiController.getActiveSpreadsheet().getCell(k, l));
                            }
                        }
                        break;
                    }
                }

            }
            int row = 0, col = 0;
            for (int i = 0; i < listCells.size(); i++) {
                if (i < 10) {
                    //write the first cells as metadata into 3x3 matrix
                    view.write("cell" + i, Charset.defaultCharset().encode(row + "," + col + "," + listCells.get(i).getContent()));
                    row++;
                    if (row == 3) {
                        row = 0;
                        col++;
                        if (col == 3) {
                            break;
                        }
                    }
                }
            }
        }
    }
}
