/**
 * <p>Technical documentation regarding the user story <b>IPC01.2: Sharing's Automatic Update</b>.</p>
 *
 * <p><b>Scrum Master: no</b></p>
 * <p><b>Team Leader: no</b></p>
 *
 *
 *
 *
 * <h2>1. Notes</h2>
 *
 * <p>This functional increment is the continuation of the <b>IPC01.1</b> and <b>IPC01.2</b> functional increment.</p>
 * <p>The communication framework is part of this feature and it was implemented in the first iteration.</p>
 * <p>The cell's style sharing is part of this feature and it was implemented in the second iteration.</p>
 * <p>
 *     Since this FI is an increment to the IPC01.1 and IPC01.2, all the documentation presented there takes effect here. Any
 *     kind of modification will be referred in this page.
 * </p>
 *
 *
 * <h2>2. Requirements</h2>
 *
 * <p>It should be possible to have multiple cell shares active at the same time.</p>
 * <p>Each of the shares should have a unique name.</p>
 * <p>The location (i.e., range address) of the share in each instance of Cleansheets may be different.</p>
 * <p>It should be possible to share ranges that include cells with formulas.</p>
 *
 *
 *
 * <h3>3. Analysis</h3>
 *
 * <h4>Clarifications with the product owner</h4>
 * <p><b>Q: </b>What does it mean that "Each of the shares should have a unique name."? Each range of shared cells have a name or each share of a cell(each modification) has a name?</p>
 * <p><b>A: </b>[Asked by hip-chat, had no answer.]</p>
 * <p><b>ASSUMPTION:</b> Each of the shared range of cells have a unique name</p>
 *
 * <h4>Brainstorming</h4>
 * <p> We have two fundamental preoccupations in this use case:</p>
 * <p> One is make the receiver [of the cells] aware of the share by asking him where to put the shared cells.</p>
 * <p> Other is to give a name and make the users able to see it easily</p>
 *
 * <h4>Multiple Shares Active:</h4>
 *
 * <p> As the implementation was done in the previous sprint, multiple shares can already be made since each share is an individual "activity"</p>
 * <p> The formula recognition from the shared cells is already implemented from the previous iteration</p>
 *
 * <h5>What's missing?</h5>
 * <p> -&gt; The sharing cannot be stopped;</p>
 * <p> -&gt; A connection can't be "disconnected";</p>
 * <p> -&gt; There is no waring as to inform the user of mutual cells being shared at the same time (ex. form A1:D20 and at the same time A1:A2);</p>
 * <p> -&gt; The receiver cannot choose the location of the shared cells</p>
 * <p> -&gt; The shared blocks of cells do not have names</p>
 * <h6>(Optional)</h6>
 * <p> -&gt; Highlight shared blocks of cells</p>
 *
 *
 *
 * <h5>Analysis 1 - Relation between both instances:</h5>
 * <img src="ipc_01_1_analysis1.png" alt="image">
 *
 * <h5>Analysis 2 - Relation between Classes:</h5>
 * <img src="ipc_01_1_analysis2.png" alt="image">
 *
 *
 * <h5>Transferring Data</h5>
 *      <p>As it was already made, this is how the UDP and TCP protocols are used in this UC:</p>
 *
 *      <h6>UDP - Client</h6>
 *               <img src="ipc_01_1_design_udp_client.png" alt="image">
 *      <h6>UDP - Server</h6>
 *               <img src="ipc_01_1_design_udp_server.png" alt="image">
 *
 *      <h6>TCP - Client</h6>
 *               <img src="ipc_01_1_design_tcp_client.png" alt="image">
 *      <h6>TCP - Server</h6>
 *               <img src="ipc_01_1_design_tcp_server.png" alt="image">
 *
 * <h5>How the cells behave during transfer</h5>
 *              <h6>Styles creation for transfer</h6>
 *              <img src="start_sharing.png" alt="image">
 *              <h6>Share changes</h6>
 *              <img src="share_cell_content_sd.png" alt="image">
 *              <h6>Receive changes</h6>
 *              <img src="receive_cell_content_sd.png" alt="image">
 *
 * <h2>4. Tests</h2>
 *
 * <h3>4.1. Unit Tests</h3>
 * <p>For all the DTOs, it's necessary to create unit tests for the access methods. Some examples are:</p>
 * <ol>
 *     <li>getBackgroundColor for StyleDTO</li>
 *     <li>getAddress for CellStyleDTO</li>
 *     <li>getContent for CellContentDTO</li>
 *     <li>...</li>
 * </ol>
 * <p>Also, an util class will be created to create styles from StyleDTOs and vice versa. Some tests are:</p>
 * <ol>
 *     <li>setStyleFromDTO</li>
 *     <li>createStyleDtoFromCell</li>
 * </ol>
 *
 * <h3>4.2. Functional Tests Plan</h3>
 * <p>5.1.1. Test the real time sharing</p>
 * <ol>
 *     <li>Run two instances of CleanSheets in the same local network</li>
 *     <li>Ensure the instances are reachable</li>
 *     <li>In both instances, go to the Network sidebar and press the "Activate" button</li>
 *     <li>
 *         With one instance, select the address of the other instance and press the "Connect" button
 *         <ul>
 *             <li>Either choose to make a safe connection or not (it's not relevant to test this functionality)</li>
 *         </ul>
 *     </li>
 *     <li>
 *         With one instance, fill the cells from B2 to to D4
 *         <ul>
 *             <li>Give a style to that cells</li>
 *             <li>
 *                 In the list of the connected hosts, select the other instance address and press the "Share Cells"
 *                 button
 *             </li>
 *         </ul>
 *     </li>
 *     <li>On the other instance, check if the shared cells appear in the worksheet</li>
 *     <li>
 *         with both instances, side by side, try to change the cells and make sure that it is updating on the other
 *         side
 *     </li>
 * </ol>
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * <h2>5. Design</h2>
 *
 *
 * <h3>5.4. Design Patterns</h3>
 * <p>To make the communication possible, the observer pattern was used to make notifications about the updates.</p>
 * <p>Also, all good practices dictated by SOLID and GRASP was followed, to make the software maintainable.</p>
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * <h3>6. Implementation</h3>
 *
 * <h4>6.1. Changed classes</h4>
 * <p>
 *     It was necessary to change some already existing classes instead of creating new ones / making the extend
 *     mechanism.
 * </p>
 * <p>
 *     All classes related to the cells transfer were altered in order to add a new attribute (name for the share)
 *
 * </p>
 * <p>
 *     The class {@link lapr4.green.s1.ipc.n1150532.comm.CommExtension} was also modified. Now the style and content are applyed
 *     ate the same time, so that it can be synchronized with the shares and range selection by the client.
 * </p>
 *
 *
 * <p>A new Class was created to support every alterations received and apply them to the cells:</p>
 * <img src="cd_ipc_1.3.png" alt="image">
 *
 *
 *
 *
 * @author Guilherme Ferreira 1150623
 */
package lapr4.red.s3.ipc.n1150623.MultipleSharing;