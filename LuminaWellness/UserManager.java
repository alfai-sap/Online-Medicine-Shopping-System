import java.io.*; // For input/output operations.
import java.util.*; // For collections like HashMap and ArrayList.

/**
 * Manages the users (Admin and Customer) in the system.
 * Handles user creation, authentication, and persistence.
 */
public class UserManager {
    private static UserManager instance; // Singleton instance of UserManager.
    private static final String USER_FILE = "users.csv"; // File for storing user data.
    private HashMap<String, String> users; // Maps email to hashed passwords.
    private HashMap<String, User> userProfiles; // Maps email to User objects.

    // Constructor
    /**
     * Initializes the UserManager and loads user data from a file.
     */
    public UserManager() {
        users = new HashMap<>();
        userProfiles = new HashMap<>();
        loadUsers(); // Load user data from the file.
    }

    // Singleton Pattern
    /**
     * Ensures only one instance of UserManager exists.
     * 
     * @return The singleton instance of UserManager.
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // User Persistence
    /**
     * Saves all users to the user file.
     */
    public void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
            for (Map.Entry<String, User> entry : userProfiles.entrySet()) {
                User user = entry.getValue();
                String userType = user instanceof Admin ? "ADMIN" : "CUSTOMER";
                writer.println(String.format("%s,%s,%s,%s,%s",
                    userType, // User type (ADMIN or CUSTOMER).
                    user.getId(), // User ID.
                    user.getName(), // User name.
                    user.getEmail(), // User email.
                    users.get(user.getEmail()) // Hashed password.
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Loads all users from the user file.
     */
    public void loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            initializeDefaultAdmin(); // Create a default admin if the file doesn't exist.
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String userType = parts[0];
                    int id = Integer.parseInt(parts[1]);
                    String name = parts[2];
                    String email = parts[3];
                    String hashedPassword = parts[4];

                    users.put(email, hashedPassword);

                    if ("ADMIN".equals(userType)) {
                        userProfiles.put(email, new Admin(id, name, email, hashedPassword));
                    } else {
                        userProfiles.put(email, new Customer(id, name, email, hashedPassword));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
            initializeDefaultAdmin();
        }
    }

    /**
     * Initializes a default admin if no users exist.
     */
    private void initializeDefaultAdmin() {
        String adminEmail = "admin@lumina.com";
        String adminPassword = "admin123"; // Default password (no hash).
        users.put(adminEmail, adminPassword);
        userProfiles.put(adminEmail, new Admin(1, "Admin", adminEmail, adminPassword));
        saveUsers();
    }

    // User Management
    /**
     * Adds a new user to the system.
     * 
     * @param name      The name of the user.
     * @param email     The email of the user.
     * @param password  The password of the user.
     * @param isAdmin   Whether the user is an admin.
     * @return True if the user was successfully added, false if the email already exists.
     */
    public boolean addUser(String name, String email, String password, boolean isAdmin) {
        if (users.containsKey(email)) {
            return false;
        }

        String hashedPassword = password; // No hashing in this simplified example.
        int newId = userProfiles.size() + 1;

        users.put(email, hashedPassword);
        if (isAdmin) {
            userProfiles.put(email, new Admin(newId, name, email, hashedPassword));
        } else {
            userProfiles.put(email, new Customer(newId, name, email, hashedPassword));
        }

        saveUsers();
        return true;
    }

    /**
     * Deletes a user from the system.
     * 
     * @param email The email of the user to delete.
     * @return True if the user was successfully deleted, false if the user was not found.
     */
    public boolean deleteUser(String email) {
        if (userProfiles.containsKey(email)) {
            users.remove(email);
            userProfiles.remove(email);
            saveUsers();
            return true;
        }
        return false;
    }

    // User Authentication
    /**
     * Validates user credentials during login.
     * 
     * @param email     The email of the user.
     * @param password  The password of the user.
     * @return The User object if credentials are correct, otherwise null.
     */
    public User validateUser(String email, String password) {
        if (users.containsKey(email) && users.get(email).equals(password)) {
            return userProfiles.get(email);
        }
        return null;
    }

    // Utility Methods
    /**
     * Gets a list of all users.
     * 
     * @return A list of all User objects.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(userProfiles.values());
    }

    /**
     * Gets the map of email to hashed passwords.
     * 
     * @return A map of email to hashed passwords.
     */
    public HashMap<String, String> getUsers() {
        return users;
    }

    /**
     * Gets the map of email to User objects.
     * 
     * @return A map of email to User objects.
     */
    public HashMap<String, User> getUserProfiles() {
        return userProfiles;
    }
}
