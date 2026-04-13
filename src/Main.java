import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // FIXED: Added the variable name 'input' here
        Scanner input = new Scanner(System.in);
        Login auth = new Login();

        // --- REGISTRATION PHASE ---
        System.out.println("Enter First Name:");
        String fName = input.nextLine(); // Changed from scanner to input

        System.out.println("Enter Last Name:");
        String lName = input.nextLine(); // Changed from scanner to input

        System.out.println("Enter Username:");
        String user = input.nextLine();

        System.out.println("Enter Password:");
        String pass = input.nextLine();

        // Process Registration
        String registrationMessage = auth.registerUser(user, pass, fName, lName);
        System.out.println(registrationMessage);

        // --- LOGIN PHASE ---
        if (registrationMessage.equals("Username and password successfully captured")) {

            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                System.out.println("\n--- LOGIN ---");
                System.out.print("Enter Username: ");
                String enteredUser = input.nextLine();

                System.out.print("Enter Password: ");
                String enteredPass = input.nextLine();

                loginSuccessful = auth.loginUser(enteredUser, enteredPass);
                System.out.println(auth.returnLoginStatus(loginSuccessful));
            }
        }

        input.close(); // Good practice to close the scanner
    }
}