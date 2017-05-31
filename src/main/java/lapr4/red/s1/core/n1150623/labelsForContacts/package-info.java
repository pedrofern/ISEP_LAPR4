/**
 * Technical documentation regarding the user story Core02.1: Comments on Cells.
 * 
 * <p>
 * <b>-Note: this is a template/example of the individual documentation that each team member must produce each sprint. Suggestions on how to build this documentation will appear between '-' like this one. You should remove these suggestions in your own technical documentation. You may also define a different template for your team to use with the agreement of your supervisor-</b>
 * <p>
 * <b>Scrum Master: -(yes/no)- no</b>
 *
 * <p>
 * <b>Area Leader: -(yes/no)- yes</b>
 *
 * <h2>1. Notes</h2>
 *
 * -Notes about the week's work.-
 * <p>
 * -In this section you should register important notes regarding your work during the sprint.
 * For instance, if you spend significant time helping a colleague or if you work in more than a feature.-
 *
 * <h2>2. Requirement</h2>
 *
 * - The user should be able to generate labels for contacts.
 * - A PDF document should be generated in which each page has a label of one contact.
 * - Labels must include at least the name, photograph, addresses, emails and telephone numbers of the contact.
 * - The user should also have the option to select if the events of the contacts should be included in the PDF.
 * - In this case the user must enter the date interval that is used to select the events.
 * - It should be possible to select the contacts using regular expression on the name of the contact and/or selecting a
 * specific town or city.
 *
 * <p>
 * <b>Use Case "Labels for Contacts":</b> The user opens the contacts page and chooses to export contact information to pdf. The system asks the user about the possibility of including the contact's event list. The user selects his preference. The system converts information about contacts to pdf according to the option chosen by the user.
 * 
 *  
 * <h2>3. Analysis</h2>
 *
 * To export the contact's information, there is a need to know how to access it, how it's persisted and where.
 * The sequence diagrams of the use case 10.1 that is responsible for contact information and related events persistence, tells us that:
 *
 * <h3>Create and Persist Contact [Core-10.1]</h3>
 * <p>
 * <img src="../uc_create_contact.png" alt="image">
 * <p>
 *
 * <h3>Create and Persist Event [Core-10.1]</h3>
 * <p>
 * <img src="uc_create_event.png" alt="image">
 * <p>
 *
 *
 * All information can be found in the repositories.
 * Now the problem lays on getting the exportation to pdf done and to solve that problem, as it's said in the manual, we will use the
 * external library <b><i>"iText"</i></b> (to be inserted in the pom.xml file).
 *
 *
 * <h2>4. Design</h2>
 *
 * <h3>4.1. Functional Tests</h3>
 *
 * Following the analysis and seeing the requirements mentioned int the manual, the principal functionality of this use case is to correctly export information in system to a .pdf file.
 * This way we can start by coding a unit test that that verifies if the value exportation result has all the information nedded.
 * As usual, in a test driven development approach tests normally fail in the beginning. The idea is that the tests will pass in the end.
 *
 * <p>
 *
 * <h3>4.2. UC Realization</h3>
 *
 * To realize this user story we will need to create a class that can transform information into a well structured .pdf file. We will also need to create a Controller class that shall pull from the repositories all the information needed. We also will have to add a JButton to the existing contact and events window that will initiate this Use Case.
 * The following diagrams illustrate core aspects of the design of the solution for this use case.
 * <p>
 * 
 *
 * <h3>User Selects Contact Information Exportation</h3>
 * The following diagram illustrates what happens when the user selects, in the contact's page, the exportation button, witch sends de contact information without events (in this diagram) to a .pdf file. The idea is that when this happens the data will be retrieved from the repositories and is send to a parser to be converted into a pdf file.
 * <p>
 *     --------------
 * <DIGRAM -> DIGRAM HOW IT WORKS>
 *  ----------------------
 * <h3>User Selects Contact Information Exportation With Event List</h3>
 * The following diagram illustrates what happens when the user selects, in the contact's page, the exportation button, witch send de contact information and the even list of each one of them to a .pdf file. The idea is that when this happens the data will be retrieved from the repositories and is send to a parser to be converted into a pdf file.
 * <p>
 *     ----------------
 * <DIGRAM -> DIGRAM HOW IT WORKS>
 * ----------------------------
 *
 * <h3>4.3. Classes</h3>
 * 
 * //////////////CLASS DIAGRAM//////////////-
 * 
 * <h3>4.4. Design Patterns and Best Practices</h3>
 * 
 * -Describe new or existing design patterns used in the issue-
 * <p>
 * -You can also add other artifacts to document the design, for instance, database models or updates to the domain model-
 * 
 * <h2>5. Implementation</h2>
 * 
 * -Reference the code elements that where updated or added-
 * <p>
 * -Also refer all other artifacts that are related to the implementation and where used in this issue. As far as possible you should use links to the commits of your work-
 * 
 * <h2>6. Integration/Demonstration</h2>
 * 
 * -In this section document your contribution and efforts to the integration of your work with the work of the other elements of the team and also your work regarding the demonstration (i.e., tests, updating of scripts, etc.)-
 * 
 * <h2>7. Final Remarks</h2>
 * 
 * -In this section present your views regarding alternatives, extra work and future work on the issue.-
 * <p>
 * As an extra this use case also implements a small cell visual decorator if the cell has a comment. This "feature" is not documented in this page.
 * 
 * 
 * <h2>8. Work Log</h2> 
 * 
 * -Insert here a log of you daily work. This is in essence the log of your daily standup meetings.-
 * <p>
 * Example
 * <p>
 * <b>Tuesday</b>
 * <p>
 * I worked on:
 * <p>
 * 1. Running the application;
 * 2. Analysing the existing code;
 * <p>
 * Today
 * <p>
 *
 * 1. Began and concluded the Analysis of this Use Case
 * <p>
 * Blocking:
 * <p>
 * 1. None
 * <p>
 * <b>Wednesday</b>
 * <p>
 * Yesterday I worked on: 
 * <p>
 * 1. ...
 * <p>
 * Today
 * <p>
 * 1. ...
 * <p>
 * Blocking:
 * <p>
 * 1. ...
 * 
 * <h2>9. Self Assessment</h2> 
 * 
 * -Insert here your self-assessment of the work during this sprint regarding Rubrics R3, R6 and R7.-
 * 
 * <h3>R3. Rubric Requirements Fulfilment: 3</h3>
 * 
 * 3- some defects. The student did fulfil all the requirements and also did justify the eventual options related to the interpretation/analysis of the problem.
 * 
 * <h3>R6. Rubric Requirements Analysis: 4</h3>
 * 
 * 4- correct. There is a robust and very complete analysis of the problem with well chosen technical artifacts (diagrams, grammars, etc.) for its documentation and without errors.
 * 
 * <h3>R7. Rubric Design and Implement: 2</h3>
 * 
 * 2- many defects. The code follows good practices although some design patterns should have been applied. The technical documentation covers the majority of the solution although it may have some errors. However the appropriate type of technical artifacts for documenting design are present and the ideia behind the solution is understandable. Code does not "goes against" the design options of the original code of the project. Unit tests seem to cover a significant amount of functionalities (e.g., more than 50%) but there was not test first approach.
 * 
 * @author 1150623 Guilherme Ferriera
 */
package lapr4.red.s1.core.n1150623.labelsForContacts;
