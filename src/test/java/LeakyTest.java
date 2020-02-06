import com.sun.management.HotSpotDiagnosticMXBean;
import org.apache.commons.io.FileUtils;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.management.MBeanServer;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LeakyTest {

    private static final String TESTS_OUTPUT = "tests-output";
    private Map<String, Object> m = new HashMap<>();

    @BeforeClass
    public static void init() throws IOException {
        FileUtils.deleteDirectory(new File(TESTS_OUTPUT));
    }

    @Test
    public void leakTest() throws IOException {
        new Thread(() -> {
            int i = 0;
            while (true) {
                i++;
                m.put(String.valueOf(i), new Object());
            }
        }).start();

        Files.createDirectory(Paths.get(TESTS_OUTPUT));
        dumpHeap("tests-output/dump.hprof", true);

    }

    private static void dumpHeap(String filePath, boolean live) throws IOException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        HotSpotDiagnosticMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(
                server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
        mxBean.dumpHeap(filePath, live);
    }
}
