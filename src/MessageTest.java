import org.junit.Test;
import static org.junit.Assert.*;

public class
MessageTest {

    // --- 1. TEST MESSAGE LENGTH LIMITS (SUCCESS & FAILURE) ---
    @Test
    public void testValidateMessageLengthSuccess() {
        Message msg = new Message();
        String validText = "Hi Mike, can you join us for dinner tonight?";
        String expected = "Message ready to send.";
        assertEquals(expected, msg.validateMessageLength(validText));
    }

    @Test
    public void testValidateMessageLengthFailure() {
        Message msg = new Message();
        // Construct an intentionally long string exceeding 250 characters
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            longText.append("abcdefghij"); // 260 characters total
        }
        String expected = "Message exceeds 250 characters by 10; please reduce the size.";
        assertEquals(expected, msg.validateMessageLength(longText.toString()));
    }

    // --- 2. TEST RECIPIENT PHONE FORMATTING (SUCCESS & FAILURE) ---
    @Test
    public void testCheckRecipientCellSuccess() {
        Message msg = new Message();
        // Test Data for Task 1: Valid SA Cell Number
        String validCell = "+27718693002";
        String expected = "Cell phone number successfully captured.";
        assertEquals(expected, msg.checkRecipientCell(validCell));
    }

    @Test
    public void testCheckRecipientCellFailure() {
        Message msg = new Message();
        // Test Data for Task 2: Invalid formatting (Missing international code prefix '+')
        String invalidCell = "08575975889";
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        assertEquals(expected, msg.checkRecipientCell(invalidCell));
    }

    // --- 3. TEST MESSAGE HASH FORMAT GENERATION ---
    @Test
    public void testCreateMessageHash() {
        Message msg = new Message();

        // Setup state to align precisely with the worksheet's test case specifications
        msg.setMessageID("0012345678");
        msg.setNumMessagesSent(0);

        String sampleText = "Hi Mike, can you join us for dinner tonight?";
        // Expected format: ID(00) + ":" + Num(0) + ":" + FIRSTWORD(HI) + LASTWORD(TONIGHT)
        String expectedHash = "00:0:HITONIGHT";

        assertEquals(expectedHash, msg.createMessageHash(sampleText));
    }

    // --- 4. TEST MESSAGE ACTION ROUTING LOGIC (SentMessage) ---
    @Test
    public void testSentMessageOptions() {
        Message msg = new Message();
        String cell = "+27718693002";
        String text = "Sample message text.";

        // Option 1: Send Message
        assertEquals("Message successfully sent.", msg.SentMessage(1, cell, text));

        // Option 2: Disregard Message
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2, cell, text));

        // Option 3: Store Message
        assertEquals("Message successfully stored.", msg.SentMessage(3, cell, text));
    }

    // --- 5. TEST MESSAGE ID STRUCTURE ---
    @Test
    public void testMessageIDCreation() {
        Message msg = new Message();
        // Verify that the generated tracking ID string is populated and satisfies size constraints
        assertNotNull(msg.getMessageID());
        assertTrue(msg.checkMessageID());
        assertEquals(10, msg.getMessageID().length());
    }
}