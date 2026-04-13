import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginTest {

    Login login = new Login();


    @org.junit.Test
    @Test
    public void testUsernameCorrect() {
        // Test Data: "kyl_1"
        boolean result = login.checkUserName("kyl_1");
        assertTrue(result);
    }

    @Test
    public void testUsernameIncorrect() {
        // Test Data: "kyle !!!!!!!"
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        String actual = login.registerUser("kyle!!!!!!!", "P@ssword1", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testPasswordComplexitySuccess() {
        // Test Data: "Ch&&sec@ke99!"
        boolean result = login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(result);
    }

    @Test
    public void testPasswordComplexityFailure() {
        // Test Data: "password"
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        String actual = login.registerUser("kyl_1", "password", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneFormatSuccess() {
        // Test Data: +27838968976
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue(result);
    }

    @Test
    public void testPhoneFormatFailure() {
        // Test Data: 08966553
        boolean result = login.checkCellPhoneNumber("08966553");
        assertFalse(result);
    }

    // --- LOGIN TESTS (assertTrue/False) ---
    @Test
    public void testLoginSuccessful() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }

    @Test
    public void testLoginFailed() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean result = login.loginUser("kyl_1", "wrongPass");
        assertFalse(result);
    }
}