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

    public void displayLongestMessage() {
        if (messageCount == 0) {
            System.out.println("No messages to display.");
            return;
        }

        int maxIndex = 0;
        for (int i = 1; i < messageCount; i++) {
            if (messages[i] != null && messages[maxIndex] != null && messages[i].length() > messages[maxIndex].length()) {
                maxIndex = i;
            }
        }
        System.out.println("Longest Message: " + messages[maxIndex]);
    }

    public void searchByMessageId(String id) {
        for (int i = 0; i < messageCount; i++) {
            if (messageIds[i] != null && messageIds[i].equals(id)) {
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("Message: " + messages[i]);
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    public void searchMessagesByRecipient(String recipient) {
        boolean found = false;
        for (int i = 0; i < messageCount; i++) {
            if (recipients[i] != null && recipients[i].equals(recipient)) {
                System.out.println("Message to " + recipient + ": " + messages[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for recipient: " + recipient);
        }
    }

    public void deleteMessageByHash(String hash) {
        for (int i = 0; i < messageCount; i++) {
            if (hashes[i] != null && hashes[i].equals(hash)) {
                System.out.println("Deleting message: " + messages[i]);
                // Shifting block handling element removal
                for (int j = i; j < messageCount - 1; j++) {
                    senders[j] = senders[j + 1];
                    messageIds[j] = messageIds[j + 1];
                    recipients[j] = recipients[j + 1];
                    messages[j] = messages[j + 1];
                    hashes[j] = hashes[j + 1];
                }
                messageCount--;
                return;
            }
        }
        System.out.println("Hash not found.");
    }

    public void displayFullReport() {
        if (messageCount == 0) { System.out.println("Full Message Report Empty."); return; }
        System.out.println("Full Message Report:");
        for (int i = 0; i < messageCount; i++) {
            System.out.println("Sender: " + senders[i]);
            System.out.println("Recipient: " + recipients[i]);
            System.out.println("Message ID: " + messageIds[i]);
            System.out.println("Message: " + messages[i]);
            System.out.println("Hash: " + hashes[i]);
            System.out.println("-----");
        }
    }
}