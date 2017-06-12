package lapr4.blue.s2.ipc.n1151452.netanalyzer;

import lapr4.blue.s2.ipc.n1151452.netanalyzer.domain.TrafficCount;
import lapr4.blue.s2.ipc.n1151452.netanalyzer.domain.TrafficInputStream;
import lapr4.blue.s2.ipc.n1151452.netanalyzer.domain.TrafficOutputStream;
import lapr4.blue.s2.ipc.n1151452.netanalyzer.domain.transmission.OpenTransmission;
import lapr4.blue.s2.ipc.n1151452.netanalyzer.domain.watchdogs.TrafficCounter;
import lapr4.blue.s2.ipc.n1151452.netanalyzer.domain.watchdogs.TrafficCounterListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Tests a traffic counter
 *
 * @author Daniel Gonçalves [1151452@isep.ipp.pt]
 *         on 10/06/17.
 */
@SuppressWarnings("Duplicates")
public class TrafficCounterTest {

    /**
     * A test listener class to log counts
     */
    private class TestCounterListener implements TrafficCounterListener {

        @Override
        public void fireNewTrafficCount(TrafficCount count) {
            log.add(count);
        }
    }

    private Set<TrafficCount> log = new HashSet<>();
    private FileOutputStream fos = null;
    private FileInputStream fis = null;
    private File testFile;
    private TrafficInputStream in = null;
    private TrafficOutputStream out = null;

    @Before
    public void setUp() throws IOException {

        TrafficCounter.getInstance().turnOn();

        TrafficCounterListener subscriber = new TestCounterListener();
        TrafficCounter.getInstance().addTrafficCounterListener(subscriber);

        InetAddress address = InetAddress.getByName("192.1.1.1");
        int port = 8888;

        testFile = new File("test.txt");
        //noinspection ResultOfMethodCallIgnored
        testFile.createNewFile();

        fos = new FileOutputStream(testFile);
        out = new TrafficOutputStream(fos, address, port, new OpenTransmission());
        fis = new FileInputStream(testFile);
        in = new TrafficInputStream(fis, address, port, new OpenTransmission());

        out.addSubscriber(TrafficCounter.getInstance());
        in.addSubscriber(TrafficCounter.getInstance());
    }

    @After
    public void tearDown() throws IOException {

        //noinspection ResultOfMethodCallIgnored
        testFile.delete();
        fis.close();
        in.close();
        fos.close();
        out.close();
    }

    /**
     * Tests if the counter sends the count to a listener
     *
     * @throws IOException I/O Exception
     * @throws ClassNotFoundException class not found
     * @throws InterruptedException thread interruption
     */
    @Test
    public void ensureCounterSendsCountToListener() throws IOException, ClassNotFoundException, InterruptedException {

        String tester = "tester";
        int tester2 = 1;

        out.writeObject(tester);
        in.readObject();
        out.writeObject(tester2);
        in.readObject();

        // Wait until worker ends to continue test (for Debugging purposes)
        Thread threads[] = new Thread[Thread.activeCount()];
        Thread.enumerate(threads);
        for (Thread worker :
                threads) {
            if (worker.getName().contains("traffic-publisher") ||
                    worker.getName().contains("traffic-flusher")) {
                //noinspection StatementWithEmptyBody
                while (worker.getState() == Thread.State.RUNNABLE) ;
            }
        }

        Thread.sleep(1000L);

        assertTrue(!log.isEmpty());
    }
}