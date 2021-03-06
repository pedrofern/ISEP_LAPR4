/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr4.red.s3.ipc.n1150690.ImportExportDatabase.ExportDatabase.application;

import csheets.ui.ctrl.UIController;
import java.sql.SQLException;
import lapr4.green.s1.ipc.n1150800.importexportTXT.CellRange;
import lapr4.red.s1.core.n1150451.exportPDF.WorkbookHandler;
import lapr4.red.s3.ipc.n1150690.ImportExportDatabase.ExportDatabase.domain.ThreadExport;

/**
 * A controller for export a range of cells to database.
 *
 * @author Sofia Silva [1150690@isep.ipp.pt]
 */
public class ExportToDatabaseController {

    /**
     * The UI Controller
     */
    private UIController uiController;

    /**
     * The range of cells selected by the user
     */
    private CellRange range;

    /**
     * The table name chosen by the user.
     */
    private String tableName;

    /**
     * The database url.
     */
    private String db_url;
    
    /**
     * The database driver.
     */
    private String driver;

    /**
     * Creates a new export to database controller.
     *
     * @param uiController the user interface controller
     * @param range the range of cells
     * @param tableName the table name
     * @param db_url
     * @param driver
     */
    public ExportToDatabaseController(UIController uiController, CellRange range, String tableName, String db_url, String driver) {
        this.uiController = uiController;
        this.range = range;
        this.tableName = tableName;
        this.db_url = db_url;
        this.driver = driver;
    }

    /**
     * Exports a range of cells to the database.
     */
    public void export() throws Exception {
        ThreadExport thread = null;
        try{
            thread = new ThreadExport(range, new WorkbookHandler(uiController.getActiveWorkbook()), tableName, uiController.getActiveSpreadsheet(), db_url, driver);
            thread.start();
        }catch (Exception ex){
            thread.interrupt();
            throw new Exception(ex.getMessage()); 
        }
    }

}
