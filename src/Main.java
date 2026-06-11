import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Assuming your Login class is implemented elsewhere in your project
        Login login = new Login();

        // Instantiating the manager with a capacity of 100
        Manager manager = new Manager(100);

        // AUTOMATIC SEEDING: Pre-populates the system with required assignment test data

        System.out.println("--- Registration ---");

        System.out.println("Enter First Name:");
        String fName = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lName = scanner.nextLine();

        System.out.println("Enter Username (Must include “_” and be 5 characters long.):");
        String user = scanner.nextLine();

        System.out.println("Enter Password (Min 8 chars, with uppercase, number, and special character.):");
        String pass = scanner.nextLine();

        System.out.println("Enter Cell (Include country code (e.g., +27), then up to 10 digits):");
        String cell = scanner.nextLine();

        String regStatus = login.registerUser(user, pass, cell, lName);
        System.out.println("\n" + regStatus);


        if (regStatus.contains("successfully added") || regStatus.contains("successfully captured")) {
            System.out.println("\n--- Login ---");
            boolean success = false;

            while (!success) {
                System.out.println("Enter Username:");
                String loginUser = scanner.nextLine();

                System.out.println("Enter Password:");
                String loginPass = scanner.nextLine();

                success = login.loginUser(loginUser, loginPass);
                System.out.println(login.returnLoginStatus(success, fName, lName));
            }


            System.out.println("\nWelcome to QuickChat.");
            System.out.println("How many messages do you want to send?");
            int numMessages = Integer.parseInt(scanner.nextLine());

            int messageCount = 0;
            boolean running = true;

            while (running) {
                System.out.println("1) Send Messages");
                System.out.println("2) Stored Messages Management");
                System.out.println("3) Quit Application");
                System.out.println("Choose an option (1-3):");

                int menuChoice = Integer.parseInt(scanner.nextLine());

                switch (menuChoice) {
                    case 1:
                        if (messageCount >= numMessages) {
                            System.out.println("You have reached your message limit.");
                            break;
                        }

                        messageCount++;

                        System.out.println("Enter recipient number:");
                        String recipient = scanner.nextLine();

                        String messageBody = "";
                        Message tempMsg = null;

                        // Validation loop focuses strictly on text sizing constraints
                        while (true) {
                            System.out.println("Please enter a message of less than 250 characters:");
                            messageBody = scanner.nextLine();

                            tempMsg = new Message(recipient, messageBody);
                            String lengthCheck = tempMsg.checkMessageLength();

                            System.out.println(lengthCheck);
                            if (lengthCheck.equals("Message ready to send.")) break;
                        }

                        System.out.println(tempMsg.checkRecipientCell());

                        System.out.println("\n1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message");
                        System.out.println("Choose (1-3):");
                        int sendChoice = Integer.parseInt(scanner.nextLine());

                        // This executes file operations for Option 3
                        String executionStatus = tempMsg.SentMessage(sendChoice);
                        System.out.println(executionStatus);

                        // FIXED: Saves live user messages to parallel arrays if they chose to Send or Store
                        if (sendChoice == 1 || sendChoice == 3) {
                            manager.addMessage(fName, tempMsg.getMessageId(), recipient, messageBody, tempMsg.getMessageHash());
                            System.out.println("Message successfully committed to application memory storage.");
                        } else {
                            System.out.println("Message dropped.");
                        }

                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID: " + tempMsg.getMessageId());
                        System.out.println("Message Hash: " + tempMsg.getMessageHash());
                        System.out.println("Recipient: " + tempMsg.getRecipient());
                        System.out.println("Message: " + messageBody);
                        break;

                    case 2:
                        System.out.println("\n--- SUB-MENU: STORED MESSAGES OPTION ---");
                        System.out.println("a. Display sender and recipient of all stored messages");
                        System.out.println("b. Display the longest stored message");
                        System.out.println("c. Search for a message ID and display corresponding recipient & message");
                        System.out.println("d. Search for all the messages stored for a particular recipient");
                        System.out.println("e. Delete a message using the message hash");
                        System.out.println("f. Display a report that lists the full details of all messages");
                        System.out.println("Choose execution action (a-f):");

                        String subChoice = scanner.nextLine().trim().toLowerCase();

                        System.out.println("\n--------------------------------------------------------------------------------");
                        switch (subChoice) {
                            case "a":
                                manager.displaySendersAndRecipients();
                                break;
                            case "b":
                                manager.displayLongestMessage();
                                break;
                            case "c":
                                System.out.println("Enter Target Message ID to Search:");
                                String idTarget = scanner.nextLine().trim();
                                manager.searchByMessageId(idTarget);
                                break;
                            case "d":
                                System.out.println("Enter Target Recipient Number to Filter All Messages:");
                                String recipientTarget = scanner.nextLine().trim();
                                manager.searchMessagesByRecipient(recipientTarget);
                                break;
                            case "e":
                                System.out.println("Enter Unique Message Hash Key To Remove:");
                                String hashTarget = scanner.nextLine().trim();
                                manager.deleteMessageByHash(hashTarget);
                                break;
                            case "f":
                                manager.displayFullReport();
                                break;
                            default:
                                System.out.println("Error: Selection sub-character context was invalid.");
                        }
                        System.out.println("--------------------------------------------------------------------------------");
                        break;

                    case 3:
                        running = false;
                        System.out.println("Closing systems down. Thank you.");
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            }

            // Summary metrics printed upon application quit
            System.out.println("\nTotal messages sent during this session: " + Message.returnTotalMessagess());
            System.out.println("\n--- Message Archive Summary ---");
            System.out.println(Message.printMessages());
        } else {
            System.out.println("Registration failed. Application terminating.");
        }
        scanner.close();
    }
}