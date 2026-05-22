package temperature;

import java.io.*;
import java.nio.file.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class MainTest {
    private String testFilename = "test_temps.csv";
    private String summaryFile = "test_temps.csv_summary.txt";

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
        // Clean up test files
        Files.deleteIfExists(Paths.get(testFilename));
        Files.deleteIfExists(Paths.get(summaryFile));
    }

    @Test
    public void testProcessBatchCreatesValidSummary() throws IOException {
        // Process the test file
        Main.processBatch(testFilename);

        // Verify the summary file was created
        Path summaryPath = Paths.get(summaryFile);
        assertTrue("Summary file should be created", Files.exists(summaryPath));

        String content = Files.readString(summaryPath);
        assertTrue("Summary should contain total readings", content.contains("Total readings: 10"));
        assertTrue("Summary should contain valid readings", content.contains("Valid readings: 10"));
        assertTrue("Summary should contain error count", content.contains("Errors: 0"));
    }
}
