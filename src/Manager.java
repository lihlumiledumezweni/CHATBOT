public class Manager {
    private int size;
    private String[] messageIds;
    private String[] recipients;
    private String[] messages;
    private String[] hashes;
    private String[] senders;
    private int messageCount;

    // Constructor to cleanly initialize array structures dynamically
    public Manager(int capacity) {
        this.size = capacity;
        this.messageIds = new String[size];
        this.recipients = new String[size];
        this.messages = new String[size];
        this.hashes = new String[size];
        this.senders = new String[size];
        this.messageCount = 0;
    }

    public void addMessage(String sender, String id, String recipient, String message, String hash) {
        if (messageCount < size) {
            senders[messageCount] = sender;
            messageIds[messageCount] = id;
            recipients[messageCount] = recipient;
            messages[messageCount] = message;
            hashes[messageCount] = hash;
            messageCount++;
        } else {
            System.out.println("Message storage is full.");
        }
    }

    public void displaySendersAndRecipients() {
        if (messageCount == 0) { System.out.println("No messages stored yet."); return; }
        System.out.println("Senders and Recipients:");
        for (int i = 0; i < messageCount; i++) {
            System.out.println("Sender: " + senders[i] + ", Recipient: " + recipients[i]);
        }
    }

    // UPDATED: Now returns the longest message string for JUnit assertion
    public String displayLongestMessage() {
        if (messageCount == 0) {
            String output = "No messages to display.";
            System.out.println(output);
            return output;
        }

        int maxIndex = 0;
        for (int i = 1; i < messageCount; i++) {
            if (messages[i] != null && messages[maxIndex] != null && messages[i].length() > messages[maxIndex].length()) {
                maxIndex = i;
            }
        }
        System.out.println("Longest Message: " + messages[maxIndex]);
        return messages[maxIndex];
    }

    // UPDATED: Returns a formatted String containing the Recipient and Message
    public String searchByMessageId(String id) {
        for (int i = 0; i < messageCount; i++) {
            if (messageIds[i] != null && messageIds[i].equals(id)) {
                String result = "Recipient: " + recipients[i] + " | Message: " + messages[i];
                System.out.println(result);
                return result;
            }
        }
        System.out.println("Message ID not found.");
        return "Message ID not found.";
    }

    // UPDATED: Returns a compiled string of all messages for the recipient
    public String searchMessagesByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (int i = 0; i < messageCount; i++) {
            if (recipients[i] != null && recipients[i].equals(recipient)) {
                sb.append(messages[i]).append(" ");
                found = true;
            }
        }

        String results = sb.toString().trim();
        if (!found) {
            System.out.println("No messages found for recipient: " + recipient);
            return "No messages found for recipient: " + recipient;
        } else {
            System.out.println("Match found: " + results);
            return results;
        }
    }

    // UPDATED: Returns exact POE assignment success string
    public String deleteMessageByHash(String hash) {
        for (int i = 0; i < messageCount; i++) {
            if (hashes[i] != null && hashes[i].equals(hash)) {
                String deletedText = messages[i];
                System.out.println("Deleting message: " + deletedText);

                // Shifting block handling element removal
                for (int j = i; j < messageCount - 1; j++) {
                    senders[j] = senders[j + 1];
                    messageIds[j] = messageIds[j + 1];
                    recipients[j] = recipients[j + 1];
                    messages[j] = messages[j + 1];
                    hashes[j] = hashes[j + 1];
                }
                messageCount--;
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        System.out.println("Hash not found.");
        return "Hash not found.";
    }

    // UPDATED: Returns a full string report of all stored entries
    public String displayFullReport() {
        if (messageCount == 0) {
            System.out.println("Full Message Report Empty.");
            return "Full Message Report Empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Full Message Report:\n");
        for (int i = 0; i < messageCount; i++) {
            sb.append("Sender: ").append(senders[i]).append("\n")
                    .append("Recipient: ").append(recipients[i]).append("\n")
                    .append("Message ID: ").append(messageIds[i]).append("\n")
                    .append("Message: ").append(messages[i]).append("\n")
                    .append("Hash: ").append(hashes[i]).append("\n-----\n");
        }
        System.out.print(sb.toString());
        return sb.toString();
    }
}