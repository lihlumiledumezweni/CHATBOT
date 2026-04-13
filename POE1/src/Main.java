import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login auth = new Login();

        // --- REGISTRATION PHASE ---//
        System.out.println("Enter First Name:");
        String fName = input.nextLine();

        System.out.println("Enter Last Name:");
        String lName = input.nextLine();

        System.out.println("Enter Username (Must contain an underscore and it must not be more than max 5 characters):");
        String user = input.nextLine();

        System.out.println("Enter Password (The password must be at least 8 characters long,include Uppercase,include Number,include Special Character):");
        String pass = input.nextLine();

        System.out.println("Enter Cell Phone Number (Your cellphone must have an international code, the number must not include more than 10 characters):");
        String phone = input.nextLine();

        // FIXED: Now passing user, pass, and phone to match your new class
        String registrationMessage = auth.registerUser(user, pass, phone);
        System.out.println(registrationMessage);

        // --- LOGIN PHASE ---//
        if (registrationMessage.contains("Username successfully captured")) {

            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                System.out.println("\n--- LOGIN ---");
                System.out.print("Enter Username: ");
                String enteredUser = input.nextLine();

                System.out.print("Enter Password: ");
                String enteredPass = input.nextLine();

                loginSuccessful = auth.loginUser(enteredUser, enteredPass);


                System.out.println(auth.returnLoginStatus(loginSuccessful, fName, lName));
            }
        }
    }
}