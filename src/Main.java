import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login auth = new Login();


            // Place this directly inside the "if (loginSuccessful)" logic branch in Main.java
            System.out.println("\nWelcome to QuickChat.");
            System.out.print("How many messages wish to enter? ");
            int totalCount = input.nextInt();
            input.nextLine(); // Clear the buffer scanner newline character

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

                    // Routes action
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
