/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr4.green.s3.lang.n1150657.XML.Export.Action;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.event.ActionEvent;
import lapr4.green.s3.lang.n1150657.XML.Export.UI.ExportSelectedSpreadsheetUI;

public class ExportSelectedSpreadsheetActionUI extends BaseAction {

    /**
     * The user interface controller
     */
    protected UIController uiController;

    /**
     * Creates a new action.
     *
     * @param uiController the user interface controller
     */
    public ExportSelectedSpreadsheetActionUI(UIController uiController) {
        this.uiController = uiController;
    }

    @Override
    protected String getName() {
        return "Selected spreadsheet";
    }
    
    /**
     * A simple action that presents a confirmation dialog. If the user confirms
     * then the spreadsheet is exported to xml
     *
     * @param event the event that was fired
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        ExportSelectedSpreadsheetUI exportSelectedSpreadsheetUI = new ExportSelectedSpreadsheetUI(uiController);
    }
    
}
