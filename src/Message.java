import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Message {
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int globalMessageCounter = 0;
    private static int totalMessagesSent = 0;

    // Fixed constructor signature to accept fields straight from input loop
    public Message(String recipient, String messageText) {
        this.numMessagesSent = globalMessageCounter;
        globalMessageCounter++;
        this.messageID = generateRandomID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash(messageText);
    }

    private String generateRandomID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public boolean checkMessageId() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    public String checkMessageLength() {
        if (this.messageText != null && this.messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int exceededBy = (this.messageText != null ? this.messageText.length() : 0) - 250;
            return "Message exceeds 250 characters by " + exceededBy + "; please reduce the size.";
        }
    }

    public String checkRecipientCell() {
        if (this.recipient != null && this.recipient.matches("^\\+27\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

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

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                String jsonContent = storeMessage();
                File jsonFile = new File("stored_message.json");

                try {
                    boolean appendComma = jsonFile.exists() && jsonFile.length() > 0;
                    try (FileWriter writer = new FileWriter(jsonFile, true)) {
                        if (appendComma) {
                            writer.write(",\n" + jsonContent);
                        } else {
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

    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public static String printMessages() {
        return "Global engine stack logged " + globalMessageCounter + " total cycle run interaction instances.";
    }

    // Lowercase matching getters to bridge the Main file cleanly
    public String getMessageId() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}