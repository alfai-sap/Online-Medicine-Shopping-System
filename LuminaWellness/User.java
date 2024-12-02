import java.util.regex.Pattern; // Import the Pattern class for validating email formats.

public class User {
    // Fields
    private int userId; // A unique identifier for each user.
    private String name; // The name of the user.
    private String email; // The email address of the user.
    private String password; // The password of the user.

    // Constructor
    /**
     * Constructor to initialize a User object with its details.
     */
    public User(int userId, String name, String email, String password) {
        this.userId = userId; // Assign the provided user ID.
        this.name = name; // Assign the provided name.
        this.email = email; // Assign the provided email.
        this.password = password; // Assign the provided password.
    }

    // Core Functionalities
    /**
     * Registers a user by validating input and assigning values.
     * @param name The name of the user.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return True if registration is successful, otherwise false.
     */
    public boolean register(String name, String email, String password) {
        if (validateInput(email, password)) { // Check if email and password are valid.
            this.name = name; // Set the name.
            this.email = email; // Set the email.
            this.password = password; // Set the password.
            return true; // Registration is successful.
        }
        return false; // Registration failed due to invalid input.
    }

    /**
     * Logs in a user by matching the provided email and password.
     * @param email The email to log in.
     * @param password The password to log in.
     * @return True if both email and password match; otherwise, false.
     */
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // Validation Helpers
    /**
     * Validates email format and password length.
     * @param email The email to validate.
     * @param password The password to validate.
     * @return True if the email and password are valid, otherwise false.
     */
    private boolean validateInput(String email, String password) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Regular expression for email validation.
        return !email.isEmpty() && // Ensure email is not empty.
               Pattern.matches(emailRegex, email) && // Check if the email matches the pattern.
               password.length() >= 6; // Ensure password is at least 6 characters long.
    }

    // Getters
    /**
     * Retrieves the user's name.
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the user's ID as a string.
     * @return The user's ID as a string.
     */
    public String getId() {
        return String.valueOf(userId);
    }

    /**
     * Retrieves the user's email.
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }
}
