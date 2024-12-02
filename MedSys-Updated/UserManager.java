import java.io.*;  // Importing necessary classes for input/output operations (e.g., reading/writing files)
import java.util.*;  // Importing utilities like HashMap, ArrayList, etc.

public class UserManager {
    private static UserManager instance;  // Singleton instance of UserManager
    private static final String USER_FILE = "users.csv";  // The file where user data is stored
    private HashMap<String, String> users;  // A map to store email -> hashed password
    private HashMap<String, User> userProfiles;  // A map to store email -> User object (Admin or Customer)

    // Constructor to initialize user manager and load users
    public UserManager() {
        users = new HashMap<>();  // Initialize the users map
        userProfiles = new HashMap<>();  // Initialize the user profiles map
        loadUsers();  // Load users from the file into memory when the UserManager is created
    }

    // Singleton pattern: ensures only one instance of UserManager exists
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();  // Create a new instance if it doesn't exist
        }
        return instance;  // Return the single instance
    }

    // Save users to the user file (users.csv)
    public void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {  // Open the file for writing
            for (Map.Entry<String, User> entry : userProfiles.entrySet()) {  // Loop through each user profile
                User user = entry.getValue();  // Get the User object (Admin or Customer)
                String userType = user instanceof Admin ? "ADMIN" : "CUSTOMER";  // Determine if the user is an Admin or Customer
                writer.println(String.format("%s,%s,%s,%s,%s", 
                    userType,  // Write the user type (ADMIN or CUSTOMER)
                    user.getId(),  // Write the user ID
                    user.getName(),  // Write the user name
                    user.getEmail(),  // Write the user email
                    users.get(user.getEmail())  // Write the hashed password for the user
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());  // Handle any IO errors during saving
        }
    }

    // Load users from the user file (users.csv)
    public void loadUsers() {
        File file = new File(USER_FILE);  // Check if the user file exists
        if (!file.exists()) {  // If the file doesn't exist
            initializeDefaultAdmin();  // Create a default admin if no users exist
            return;
        }

        // Read user data from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {  // Open the file for reading
            String line;
            while ((line = reader.readLine()) != null) {  // Read each line in the file
                String[] parts = line.split(",");  // Split the line by commas to extract data
                if (parts.length == 5) {  // Check if the line contains the correct number of parts
                    String userType = parts[0];  // Get the user type (ADMIN or CUSTOMER)
                    int id = Integer.parseInt(parts[1]);  // Get the user ID
                    String name = parts[2];  // Get the user name
                    String email = parts[3];  // Get the user email
                    String hashedPassword = parts[4];  // Get the hashed password

                    users.put(email, hashedPassword);  // Add the user to the users map

                    // Create a new Admin or Customer object based on the user type and add to userProfiles map
                    if ("ADMIN".equals(userType)) {
                        userProfiles.put(email, new Admin(id, name, email, hashedPassword));
                    } else {
                        userProfiles.put(email, new Customer(id, name, email, hashedPassword));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());  // Handle any IO errors during loading
            initializeDefaultAdmin();  // Create a default admin in case of failure
        }
    }

    // Initialize a default admin user if the user file is empty or doesn't exist
    private void initializeDefaultAdmin() {
        String adminEmail = "admin@lumina.com";  // Default admin email
        String adminPassword = "admin123";  // Default admin password (no hash)
        users.put(adminEmail, adminPassword);  // Add the admin email and password to the users map
        userProfiles.put(adminEmail, new Admin(1, "Admin", adminEmail, adminPassword));  // Create a new Admin object and add it to userProfiles
        saveUsers();  // Save the default admin to the file
    }

    // Add a new user (Admin or Customer) to the system
    public boolean addUser(String name, String email, String password, boolean isAdmin) {
        if (users.containsKey(email)) {  // If the email already exists in the system, return false
            return false;
        }

        String hashedPassword = password;  // In this simplified version, no hashing is done (password is plain text)
        int newId = userProfiles.size() + 1;  // Generate a new ID for the user
        
        // Add the user to the maps
        users.put(email, hashedPassword);
        if (isAdmin) {
            userProfiles.put(email, new Admin(newId, name, email, hashedPassword));
        } else {
            userProfiles.put(email, new Customer(newId, name, email, hashedPassword));
        }

        saveUsers();  // Save the updated user data to the file
        return true;
    }

    // Validate a user's email and password during login
    public User validateUser(String email, String password) {
        if (users.containsKey(email) && users.get(email).equals(password)) {
            return userProfiles.get(email);  // Return the user object if credentials are correct
        }
        return null;  // Return null if the credentials are incorrect
    }

    // Hash the password (simplified version)
    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());  // Convert the password into a hash string
    }

    // Return a list of all users (Admin or Customer)
    public List<User> getAllUsers() {
        return new ArrayList<>(userProfiles.values());  // Return all user profiles as a list
    }

    // Delete a user from the system by email
    public boolean deleteUser(String email) {
        System.out.println("Attempting to delete user with email: " + email);  // Debug log
        
        if (userProfiles.containsKey(email)) {  // If the user exists
            users.remove(email);  // Remove the email from the users map
            userProfiles.remove(email);  // Remove the user profile from userProfiles map

            System.out.println("User with email " + email + " removed successfully.");  // Debug log
            saveUsers();  // Save the updated user data to the file
            return true;
        }

        System.out.println("User with email " + email + " not found.");  // Debug log if the user doesn't exist
        return false;  // Return false if the user is not found
    }

    // Getter for users map (email -> hashed password)
    public HashMap<String, String> getUsers() {
        return users;
    }

    // Getter for userProfiles map (email -> User object)
    public HashMap<String, User> getUserProfiles() {
        return userProfiles;
    }
}
