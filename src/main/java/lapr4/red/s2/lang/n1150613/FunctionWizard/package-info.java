/**
 * Technical documentation regarding the user story Lang04.2 - Insert Function Intermediate Wizard.
 * <p>
 * <p>
 * <b>Scrum Master: -(yes/no)- no</b>
 *
 * <p>
 * <b>Area Leader: -(yes/no)- no</b>
 *
 * <h2>1. Notes</h2>
 * <p>
 *
 * <h2>2. Requirement</h2>
 *
 * The wizard window should display an edit box for each parameter of the
 * selected function. The user should use these edit boxes to enter the values
 * for each parameter of the function. As the user enters the values the wizard
 * should display (in a new region of the window) the result of the execution of
 * the formula or a message explaining the problem. The function list should now
 * include also the operators as well as the functions that are dynamically
 * loaded from java.lang.Math. The wizard should be now launched from an icon or
 * button located in the formula bar, between the label of the active cell and
 * the edit box of the formula/value of the current cell. The menu option should
 * be removed.
 *
 * <p>
 *
 * <h2>3. Analysis</h2>
 * <p>
 *
 * <h3>Identified Problems:</h3>
 * 1.Display an edit box for each parameter of the selected function.<p>
 * 2.Display the result of the execution of the formula or a message explaining
 * the problem as the parameters are inserted.
 * <p>
 * 3.Function list should include also the operators as well as the functions
 * that are dynamically loaded from java.lang.Math
 * <p>
 * 4.The wizard should be now launched from an icon.
 * <p>
 * 5.The menu option should be removed.<p>
 *
 * <h3>Proposed solution</h3>
 * 1. Make defaultTableModel cells editable so they can turn into edit boxes to insert function parameters.
 * <p>
 * 2. Add listener to know when the text box is updated and refresh the result
 * of the formula through a method yet to be implemented that returns the formula result.
 * <p>
 * 3. Change main method from Language class to update function list
 * <p>
 * 4 & 5. Simple changes in UI
 * <p>
 *
 * <h2>4. Design</h2>
 * <p>
 * <h3>4.1. Functional Tests</h3>
 * <p>
 * <h3>4.2. UC Realization</h3>
 * Sequence Diagram
 *
 *
 * <p>
 *
 * <h3>4.3. Classes</h3>
 * <p>
 * <h3>4.4. Design Patterns and Best Practices</h3>
 * <p>
 * <h2>5. Implementation</h2>
 * <p>
 * <p>
 * <h2>6. Integration/Demonstration</h2>
 * <p>
 *
 * <h2>7. Final Remarks</h2>
 * <p>
 *
 * <h2>8. Work Log</h2>
 * <p>
 * <b>Tuesday 06/06/2017</b>
 * <p>
 * Yesterday: our team distributed the funcionalities to be worked on this
 * sprint.
 * <p>
 * Today: I started the analysis process.
 * <p>
 * Blocking:---
 * <p>
 *
 *
 *
 * @author Diogo Guedes 1150613@isep.ipp.pt
 */
package lapr4.red.s2.lang.n1150613.FunctionWizard;