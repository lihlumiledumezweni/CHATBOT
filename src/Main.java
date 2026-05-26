import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login auth = new Login();

        String registrationResult = "";
        String firstName = "";
        String lastName = "";

        // --- REGISTRATION PHASE (Keeps looping until registration criteria pass) ---
        while (true) {
            System.out.println("=== User Registration ===");
            System.out.print("Enter first name: ");
            firstName = input.nextLine();
            System.out.print("Enter last name: ");
            lastName = input.nextLine();
            System.out.print("Enter username (must contain underscore and be ≤ 5 chars): ");
            String username = input.nextLine();
            System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special char): ");
            String password = input.nextLine();
            System.out.print("Enter cell phone number (+27XXXXXXXXX): ");
            String phone = input.nextLine();

            registrationResult = auth.registerUser(username, password, phone);
            System.out.println("\n" + registrationResult);

            // Break out of the registration loop only if successful
            if (registrationResult.contains("successfully captured")) {
                break;
            }
            System.out.println("Registration failed. Please try again with valid inputs.\n");
        }

        // --- LOGIN PHASE ---
        boolean loginSuccessful = false;

        while (!loginSuccessful) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();
            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();

            loginSuccessful = auth.loginUser(loginUsername, loginPassword);
            String loginMessage = auth.returnLoginStatus(loginSuccessful, firstName, lastName);
            System.out.println("\n" + loginMessage);
        }

        // --- QUICKCHAT APPLICATION LOGIC ---
        if (loginSuccessful) {
            System.out.println("\nWelcome to QuickChat.");
            System.out.print("How many messages do you wish to enter? ");
            int totalCount = input.nextInt();
            input.nextLine(); // Clear the scanner buffer

            int createdCount = 0;
            Message lastProcessedMessage = null;

            while (createdCount < totalCount) {
                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose an option: ");
                int choice = input.nextInt();
                input.nextLine(); // Clear scanner buffer

                if (choice == 3) {
                    System.out.println("Exiting application...");
                    break;
                } else if (choice == 2) {
                    System.out.println("Coming Soon.");
                } else if (choice == 1) {
                    // Instantiate tracking object
                    Message messageObj = new Message();

                    System.out.print("Enter Recipient Cell Number (+27XXXXXXXXX): ");
                    String targetPhone = input.nextLine();
                    System.out.println(messageObj.checkRecipientCell(targetPhone));

                    System.out.print("Enter Message text (Max 250 characters): ");
                    String txt = input.nextLine();
                    System.out.println(messageObj.validateMessageLength(txt));

                    System.out.println("\nAction Options:");
                    System.out.println("1 - Send Message");
                    System.out.println("2 - Disregard Message");
                    System.out.println("3 - Store Message to send later");
                    System.out.print("Select action: ");
                    int actionChoice = input.nextInt();
                    input.nextLine(); // Clear buffer

                    // Routes action (Your updated Message class handles writing the JSON automatically here!)
                    String actionResult = messageObj.SentMessage(actionChoice, targetPhone, txt);
                    System.out.println(actionResult);

                    // Print details if successfully sent or saved
                    if (actionChoice == 1 || actionChoice == 3) {
                        System.out.println("\n--- Captured Details ---");
                        System.out.println(messageObj.printMessages());
                        lastProcessedMessage = messageObj;
                    }

                    createdCount++;
                }
            }

            // Display final tracking accumulation totals at closure
            if (lastProcessedMessage != null) {
                System.out.println("\nTotal cumulative messages sent during this run: " + lastProcessedMessage.returnTotalMessagess());
            }
        }
    }
}