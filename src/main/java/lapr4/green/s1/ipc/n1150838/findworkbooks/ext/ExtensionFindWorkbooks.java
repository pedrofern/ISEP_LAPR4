/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr4.green.s1.ipc.n1150838.findworkbooks.ext;

import csheets.ext.Extension;
import csheets.ui.ctrl.UIController;
import csheets.ui.ext.UIExtension;
import lapr4.green.s1.ipc.n1150838.findworkbooks.ui.UIFindWorkbooksExtension;

/**
 *
 * @author nunopinto
 */
public class ExtensionFindWorkbooks extends Extension {
    
    /** The name of the extension */
    public static final String NAME = "Find Workbook";
    
    /**
     * The description of the extension
     */
    public static final String DESCRIPTION = "It represents the finding workbooks extension.";
    
    /**
     * The first version of the dependency trees extension.
     */
    public static final int VERSION = 1;
    public ExtensionFindWorkbooks() {
        super(NAME,VERSION,DESCRIPTION);
    }
    
    	/**
	 * Returns the user interface extension of this extension (an instance of the class {@link  csheets.ext.simple.ui.UIExtensionExample}).
	 * In this extension example we are only extending the user interface.
	 * @param uiController the user interface controller
	 * @return a user interface extension, or null if none is provided
	 */
        @Override
	public UIExtension getUIExtension(UIController uiController) {
		return new UIFindWorkbooksExtension(this, uiController);
	}
        
        @Override
        public String metadata() {
        return String.format("This is %s with version %d\n"
                + "This extension has the follow description: %s\n"
                + "This extension was made by Nuno Pinto in Sprint 1 and it is in the package %s",
                getName(), getVersion(), getDescription(),getClass().getName());
        }
        
}
