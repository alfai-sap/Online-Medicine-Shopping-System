import java.util.regex.Pattern; // Import the Pattern class for validating email formats.

public class User {
    private int userId; // A unique identifier for each user.
    private String name; // The name of the user.
    private String email; // The email address of the user.
    private String password; // The password of the user.

    // Constructor to initialize a User object with its details.
    public User(int userId, String name, String email, String password) {
        this.userId = userId; // Assign the provided user ID.
        this.name = name; // Assign the provided name.
        this.email = email; // Assign the provided email.
        this.password = password; // Assign the provided password.
    }

    // Method to register a user by validating input and assigning values.
    public boolean register(String name, String email, String password) {
        if (validateInput(email, password)) { // Check if email and password are valid.
            this.name = name; // Set the name.
            this.email = email; // Set the email.
            this.password = password; // Set the password.
            return true; // Registration is successful.
        }
        return false; // Registration failed due to invalid input.
    }

    // Method to log in a user by matching the provided email and password.
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password); 
        // Returns true if both email and password match; otherwise, false.
    }

    // Helper method to validate email format and password length.
    private boolean validateInput(String email, String password) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // A regular expression for email validation.
        return !email.isEmpty() && // Ensure email is not empty.
               Pattern.matches(emailRegex, email) && // Check if the email matches the pattern.
               password.length() >= 6; // Ensure password is at least 6 characters long.
    }

    // Getter method to retrieve the user's name.
    public String getName() {
        return name; // Return the name of the user.
    }

    // Getter method to retrieve the user's ID as a string.
    public String getId() {
        return String.valueOf(userId); // Convert the user ID to a string and return it.
    }

    // Getter method to retrieve the user's email.
    public String getEmail() {
        return email; // Return the email of the user.
    }
}
