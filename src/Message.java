import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Message {
    // Instance variables matching marking criteria tables
    private String messageID;
    private int numMessagesSent; // Tracks current message sequence index
    private String recipient;
    private String messageText;
    private String messageHash;

    // Static counters for overall tracking
    private static int globalMessageCounter = 0;
    private static int totalMessagesSent = 0;

    // Constructor
    public Message() {
        // Automatically increment and assign the message counter sequence
        this.numMessagesSent = globalMessageCounter;
        globalMessageCounter++;
        this.messageID = generateRandomID();
    }

    // Helper method to auto-generate a 10-digit random message ID string
    private String generateRandomID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // Method: checkMessageID() - Ensures message ID is not more than 10 characters
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Validation Method for Unit Testing input lengths
    public String validateMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        } else {
            int exceededBy = text.length() - 250;
            return "Message exceeds 250 characters by " + exceededBy + "; please reduce the size.";
        }
    }

    // Method: checkRecipientCell() - Validates cell phone constraints
    public String checkRecipientCell(String phone) {
        if (phone.matches("^\\+27\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Method: createMessageHash() - Generates the uppercase signature string
    public String createMessageHash(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "00:0:EMPTY";
        }

        String idPrefix = (this.messageID != null && this.messageID.length() >= 2)
                ? this.messageID.substring(0, 2)
                : "00";

        String[] words = text.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");

        this.messageHash = (idPrefix + ":" + this.numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }

    // Method: SentMessage() - Handles the structural routing choice logic
    public String SentMessage(int choice, String phone, String text) {
        if (text.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }

        this.recipient = phone;
        this.messageText = text;
        this.messageHash = createMessageHash(text);

        switch (choice) {
            case 1: // Send Message
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2: // Disregard Message
                return "Press 0 to delete the message.";
            case 3: // Store Message
                String jsonContent = storeMessage();
                File jsonFile = new File("stored_message.json");

                try {
                    // FIXED: Check if the file already exists and has text inside it
                    boolean appendComma = jsonFile.exists() && jsonFile.length() > 0;

                    try (FileWriter writer = new FileWriter(jsonFile, true)) {
                        if (appendComma) {
                            // Places the comma BEFORE the new message to cleanly separate them
                            writer.write(",\n" + jsonContent);
                        } else {
                            // If it's the very first message, write it perfectly clean
                            writer.write(jsonContent);
                        }
                        return "Message successfully appended and saved to 'stored_message.json'.";
                    }
                } catch (IOException e) {
                    return "Message fields set, but failed to write JSON file: " + e.getMessage();
                }
            default:
                return "Invalid selection routing.";
        }
    }

    // Method: printMessages() - Returns current active structured details string
    public String printMessages() {
        return "Message ID: " + this.messageID + "\n" +
                "Message Hash: " + this.messageHash + "\n" +
                "Recipient: " + this.recipient + "\n" +
                "Message: " + this.messageText;
    }

    // Method: returnTotalMessagess() - Returns total processed sent counter across instances
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // Method: storeMessage() - Formats variables as a clean JSON string natively without third-party libraries
    public String storeMessage() {
        String id = (this.messageID != null) ? this.messageID : "";
        String phone = (this.recipient != null) ? this.recipient : "";
        String text = (this.messageText != null) ? this.messageText : "";
        String hash = (this.messageHash != null) ? this.messageHash : "";

        text = text.replace("\"", "\\\"");

        return "{\n" +
                "  \"messageID\": \"" + id + "\",\n" +
                "  \"numMessagesSent\": " + this.numMessagesSent + ",\n" +
                "  \"recipient\": \"" + phone + "\",\n" +
                "  \"messageText\": \"" + text + "\",\n" +
                "  \"messageHash\": \"" + hash + "\"\n" +
                "}";
    }

    // --- GETTERS & SETTERS ---
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getNumMessagesSent() {
        return numMessagesSent;
    }

    public void setNumMessagesSent(int numMessagesSent) {
        this.numMessagesSent = numMessagesSent;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }
}