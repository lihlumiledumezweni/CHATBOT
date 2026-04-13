import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login auth = new Login();

        // --- REGISTRATION PHASE ---
        System.out.println("Enter First Name:");
        String fName = input.nextLine();

        System.out.println("Enter Last Name:");
        String lName = input.nextLine();

        System.out.println("Enter Username (Must have '_' and max 5 chars):");
        String user = input.nextLine();

        System.out.println("Enter Password (8+ chars, Uppercase, Number, Special):");
        String pass = input.nextLine();

        System.out.println("Enter Cell Phone Number (e.g., +27123456789):");
        String phone = input.nextLine();

        // FIXED: Now passing user, pass, and phone to match your new class
        String registrationMessage = auth.registerUser(user, pass, phone);
        System.out.println(registrationMessage);

        // --- LOGIN PHASE ---
        // Changed comparison to "Username successfully captured." to match your Login class return
        if (registrationMessage.contains("Username successfully captured")) {

            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                System.out.println("\n--- LOGIN ---");
                System.out.print("Enter Username: ");
                String enteredUser = input.nextLine();

                System.out.print("Enter Password: ");
                String enteredPass = input.nextLine();

                loginSuccessful = auth.loginUser(enteredUser, enteredPass);

                // FIXED: Passing fName and lName here as required by your returnLoginStatus method
                System.out.println(auth.returnLoginStatus(loginSuccessful, fName, lName));
            }
        }

        input.close();
    }
}