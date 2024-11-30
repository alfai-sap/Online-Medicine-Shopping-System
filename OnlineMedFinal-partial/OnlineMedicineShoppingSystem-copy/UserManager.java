import java.io.*;
import java.util.*;

public class UserManager {
    private static UserManager instance;
    private static final String USER_FILE = "users.csv";
    private HashMap<String, String> users;
    private HashMap<String, User> userProfiles;

    public UserManager() {
        users = new HashMap<>();
        userProfiles = new HashMap<>();
        loadUsers();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
            for (Map.Entry<String, User> entry : userProfiles.entrySet()) {
                User user = entry.getValue();
                String userType = user instanceof Admin ? "ADMIN" : "CUSTOMER";
                writer.println(String.format("%s,%s,%s,%s,%s", 
                    userType, 
                    user.getId(), 
                    user.getName(), 
                    user.getEmail(), 
                    users.get(user.getEmail()) // hashed password
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public void loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            initializeDefaultAdmin();
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

    private void initializeDefaultAdmin() {
        String adminEmail = "admin@lumina.com";
        String adminPassword = hashPassword("admin123");
        users.put(adminEmail, adminPassword);
        userProfiles.put(adminEmail, new Admin(1, "Admin", adminEmail, adminPassword));
        saveUsers();
    }

    public boolean addUser(String name, String email, String password, boolean isAdmin) {
        if (users.containsKey(email)) {
            return false;
        }

        String hashedPassword = hashPassword(password);
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

    public User validateUser(String email, String password) {
        if (users.containsKey(email) && 
            users.get(email).equals(hashPassword(password))) {
            return userProfiles.get(email);
        }
        return null;
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userProfiles.values());
    }

    public boolean deleteUser(String email) {
        // Debug: Log the email being deleted
        System.out.println("Attempting to delete user with email: " + email);
    
        if (userProfiles.containsKey(email)) {
            users.remove(email);              // Remove from users map
            userProfiles.remove(email);       // Remove from userProfiles map
    
            // Debug: Log successful deletion
            System.out.println("User with email " + email + " removed successfully.");
    
            saveUsers();                      // Persist changes
            return true;
        }
    
        // Debug: Log if user not found
        System.out.println("User with email " + email + " not found.");
        return false;
    }


    public HashMap<String, String> getUsers() {
        return users;
    }

    public HashMap<String, User> getUserProfiles() {
        return userProfiles;
    }
}
