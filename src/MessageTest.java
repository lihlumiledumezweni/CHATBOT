import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {

    private Manager manager;

    @Before
    public void setUp() {
        manager = new Manager(100);

        // Seeding exact criteria test data rows 1 to 4
        manager.addMessage("System", "MID001", "+27834557896", "Did you get the cake?", "HASH1");
        manager.addMessage("System", "MID002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "HASH2");
        manager.addMessage("System", "MID003", "+27834484567", "Yohoooo, I am at your gate.", "HASH3");
        manager.addMessage("System", "MID004", "0838884567", "It is dinner time !", "HASH4");
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        String report = manager.displayFullReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
    }

    @Test
    public void testDisplayLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, manager.displayLongestMessage());
    }

    @Test
    public void testSearchByMessageId() {
        String expectedResponse = "Recipient: 0838884567 | Message: It is dinner time !";
        assertEquals(expectedResponse, manager.searchByMessageId("MID004"));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        manager.addMessage("System", "MID005", "+27838884567", "Ok, I am leaving without you.", "HASH5");

        String results = manager.searchMessagesByRecipient("+27838884567");
        assertTrue(results.contains("Where are you? You are late!"));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageByHash() {
        String deletionReport = manager.deleteMessageByHash("HASH2");

        String expectedOutput = "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        assertEquals(expectedOutput, deletionReport);
    }
}