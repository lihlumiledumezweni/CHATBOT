class Login {

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
}