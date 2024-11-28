import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.regex.Pattern;

public class MainGUI {
    private JFrame frame;
    private JPanel loginPanel, signupPanel;
    private JTextField loginEmailField, signupEmailField, signupNameField;
    private JPasswordField loginPasswordField, signupPasswordField;
    private JButton loginButton, signupButton, switchToSignupButton, switchToLoginButton;
    private HashMap<String, String> users; // Maps email to hashed password
    private HashMap<String, User> userProfiles; // Stores full user profiles
    private User currentUser;

    public MainGUI() {
        frame = new JFrame("Medicine Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());

        users = new HashMap<>();
        userProfiles = new HashMap<>();

        // Pre-set admin account
        users.put("admin@gmail.com", hashPassword("admin1"));
        userProfiles.put("admin@gmail.com", new User("Admin", "admin@gmail.com", "admin1"));

        initializeLoginPanel();
        initializeSignupPanel();

        frame.add(loginPanel, "Login");
        frame.add(signupPanel, "Signup");

        frame.setVisible(true);
    }

    private void initializeLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel emailLabel = new JLabel("Email:");
        loginEmailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        loginPasswordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());

        switchToSignupButton = new JButton("Switch to Signup");
        switchToSignupButton.addActionListener(e -> switchToPanel("Signup"));

        loginPanel.add(emailLabel);
        loginPanel.add(loginEmailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(loginPasswordField);
        loginPanel.add(loginButton);
        loginPanel.add(switchToSignupButton);
    }

    private void initializeSignupPanel() {
        signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayout(5, 2, 10, 10));
        signupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Name:");
        signupNameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        signupEmailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        signupPasswordField = new JPasswordField();

        signupButton = new JButton("Signup");
        signupButton.addActionListener(e -> handleSignup());

        switchToLoginButton = new JButton("Switch to Login");
        switchToLoginButton.addActionListener(e -> switchToPanel("Login"));

        signupPanel.add(nameLabel);
        signupPanel.add(signupNameField);
        signupPanel.add(emailLabel);
        signupPanel.add(signupEmailField);
        signupPanel.add(passwordLabel);
        signupPanel.add(signupPasswordField);
        signupPanel.add(signupButton);
        signupPanel.add(switchToLoginButton);
    }

    private void handleLogin() {
        String email = loginEmailField.getText();
        String password = new String(loginPasswordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }

        // Check for admin login
        if (email.equals("admin@gmail.com") && password.equals("admin1")) {
            currentUser = new User("Admin", "admin@gmail.com", "admin1");
            JOptionPane.showMessageDialog(frame, "Admin Login Successful!");
            frame.dispose();
            ECommerceApp.run(currentUser, "Admin");
            return;
        }

        // Handle regular user login
        if (users.containsKey(email)) {
            String storedPasswordHash = users.get(email);
            if (hashPassword(password).equals(storedPasswordHash)) {
                currentUser = userProfiles.get(email);
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                ECommerceApp.run(currentUser, "User");
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect password.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "User not found.");
        }
    }

    private void handleSignup() {
        String name = signupNameField.getText();
        String email = signupEmailField.getText();
        String password = new String(signupPasswordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }

        if (!validateEmail(email)) {
            JOptionPane.showMessageDialog(frame, "Invalid email format.");
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(frame, "Password must be at least 6 characters long.");
            return;
        }

        if (users.containsKey(email)) {
            JOptionPane.showMessageDialog(frame, "Email already registered.");
            return;
        }

        String hashedPassword = hashPassword(password);
        users.put(email, hashedPassword);
        userProfiles.put(email, new User(name, email, hashedPassword));

        JOptionPane.showMessageDialog(frame, "Signup successful! Please log in.");
        switchToPanel("Login");
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode()); // Simple hash (replace with stronger method in production)
    }

    private void switchToPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}

class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
