import java.util.regex.Pattern;
public class User {
    private int userId;
    private String name;
    private String email;
    private String password;

    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean register(String name, String email, String password) {
        if (validateInput(email, password)) {
            this.name = name;
            this.email = email;
            this.password = password;
            return true;
        }
        return false;
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    private boolean validateInput(String email, String password) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return !email.isEmpty() && Pattern.matches(emailRegex, email) && password.length() >= 6;
    }

    // Add this getter
    public String getName() {
        return name;
    }
    
    
    public String getId() {
    return String.valueOf(userId);
    }
    
    public String getEmail() {
        return email;
    }
    
}
