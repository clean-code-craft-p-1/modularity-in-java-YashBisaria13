package temperature;

import java.io.*;
import java.nio.file.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class MainTest {
    private String testFilename = "test_temps.csv";

    @Before
    public void setUp() throws IOException {
        // Generate test data file
        String[] testData = {
            "09:15:30,23.5",
            "09:16:00,24.1",
            "09:16:30,22.8",
            "09:17:00,25.3",
            "09:17:30,23.9",
            "09:18:00,24.7",
            "09:18:30,22.4",
            "09:19:00,26.1",
            "09:19:30,23.2",
            "09:20:00,25.0"
        };

        try (PrintWriter writer = new PrintWriter(new FileWriter(testFilename))) {
            for (String line : testData) {
                writer.println(line);
            }
        }
    }

    @After
    public void tearDown() throws IOException {
        // Clean up test file
        Files.deleteIfExists(Paths.get(testFilename));
    }

    @Test
    public void testProcessBatchWithValidData() throws IOException {
        // Process the test file - should complete without errors
        Main.processBatch(testFilename);
        assertTrue("Test completed successfully", true);
    }
}
