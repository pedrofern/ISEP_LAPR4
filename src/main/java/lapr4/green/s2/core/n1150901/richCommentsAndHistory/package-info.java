/**
 * Technical documentation regarding the user story Core02.3 - Rich Comments and History.
 *
 * <p>
 * <b>Scrum Master: -(yes/no)- no</b>
 *
 * <p>
 * <b>Area Leader: -(yes/no)- no</b>
 * <p>
 *
 * <h2>1. Notes</h2>
 * No notes to be reported.
 * <p>
 * 
 * <h2>2. Requirement</h2>
 * <b>Statement:</b><p>
 * Cleansheets should support rich content in comments. For instance, the user
 * should be able to apply style and format to the comments. It should also
 * exist an history of changes. The user interface should display the history of
 * changes to a comment and allow the user to make a new version of a comment
 * based on an old one. It also should have a search feature, allowing the user
 * to search for comments based on text patterns (including the history in the
 * search).
 * <p>
 * 
 * <h2>3. Analysis</h2>
 * This user story can be "divided" in 3 sub-tasks that will be the following:
 * <p>
 * <h3>Apply style and format to comments:</h3>
 * - The style and format is already applied to the contents of cells. I should
 * study how this is being done and then apply it to the comments.
 * <p>
 * <h3>Make a history of changes of a comment:</h3>
 * - In a beggining thought there should be a new section associated with the
 * "Comments Side Bar" where after a comment being selected it must show all the
 * previous changes to it or if there were none made.
 * <p>
 * <h3>Search feature to search comments by text patterns:</h3>
 * - There should be an input place together with a button that when clicked
 * should search in the active comments as also as the comments in the history.
 * There may be a label to identify if the comment is active or it is in
 * history. It shouldn't be necessary to write the whole word that the user
 * wants to search, after he introduces a small pattern (eg: Un), there should
 * be displayed all comments initiated by "Un".
 * <p>
 *
 * <h2>4. Open Questions</h2>
 * <ol>
 * <li>The style and format should be applied after the comment is already
 * registered, during the process of add the comment to the cell or in both
 * cases?</li>
 * <li>What does it mean when it says that the user is allowed to make a new
 * version of a comment?</li>
 * </ol>
 * <p>
 * 
 * <h2>5. Answers</h2>
 * <ul>
 * <li>2 - Basically on the history of a comment its shown the several changes that
 * comment took and maybe with a "double click" the actual comment is changed to
 * the selected comment.</li>
 * </ul>
 * <p>
 * 
 * <h2>6. Assumptions</h2>
 * <ol>
 * <li>The history of a comment is persisted with all the comments of each
 * cell.</li>
 * <li>In the search of a comment the capitalization should not be ignored.</li>
 * <p>
 *
 * <h2>7. Unit Tests</h2>
 *
 * <b>Test1:</b> historyUpdatesWhenUserEditsCommentTest
 * <p>
 * There should be a way to verify if the history of a comment updates correctly
 * and saves every changes.
 * <p>
 *
 * <b>Test2:</b> searchOnlyNeedsTextPatternsTest
 * <p>
 * There should be a way to verify if the search feature actually works with
 * only text patterns and doesn't need full words.
 * <p>
 *
 * <b>Test3:</b> formatIsAppliedToCommentTest
 * <p>
 * There should be a way to verify if the format is correctly applied to the
 * comment selected.
 * <p>
 *
 * <b>Test4:</b> styleIsAppliedToCommentTest
 * <p>
 * There should be a way to verify if the style is correctly applied to the
 * comment selected.
 * <p>
 *
 * <b>Test5:</b> oldCommentBecomesNewCommentTest<p>
 * There should be a way to verify that a comment from the history of changes
 * can become "a new version" and "come back" to the current comments of a cell.
 * <p>
 *
 * @author Miguel Silva (1150901)
 */
package lapr4.green.s2.core.n1150901.richCommentsAndHistory;