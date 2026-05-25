import java.util.Scanner;

public class Login {
    private String storedUsername;
    private String storedPassword;
    private String storedFirstName;
    private String storedLastName;

    // 1. Username check: must contain underscore (_) and be <= 5 characters
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // 2. Password complexity check
    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[^a-zA-Z0-9].*");
    }

    // 3. Cell phone check: International code (+27) and no more than 10 digits after it
    public boolean checkCellPhoneNumber(String phone) {
        return phone.matches("^\\+27\\d{1,10}$");
    }

    // 4. Register user
    public String registerUser(String username, String password, String phone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Store details
        this.storedUsername = username;
        this.storedPassword = password;

        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    // 5. Login check
    public boolean loginUser(String username, String password) {
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    // 6. Return login status message
    public String returnLoginStatus(boolean success, String firstName, String lastName) {
        if (success) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== User Registration ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter username (must contain underscore and be ≤ 5 chars): ");
        String username = scanner.nextLine();
        System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special char): ");
        String password = scanner.nextLine();
        System.out.print("Enter cell phone number (+27XXXXXXXXX): ");
        String phone = scanner.nextLine();

        // Register user
        String registrationResult = login.registerUser(username, password, phone);
        System.out.println("\n" + registrationResult);

        // Login attempt
        if (registrationResult.contains("successfully captured")) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccess = login.loginUser(loginUsername, loginPassword);
            String loginMessage = login.returnLoginStatus(loginSuccess, firstName, lastName);
            System.out.println("\n" + loginMessage);
        }

    }
}