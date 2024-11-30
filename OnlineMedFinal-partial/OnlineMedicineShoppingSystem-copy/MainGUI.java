import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class MainGUI {
    private JFrame frame;
    private JPanel loginPanel, signupPanel;
    private JTextField loginEmailField, signupEmailField, signupNameField;
    private JPasswordField loginPasswordField, signupPasswordField;
    private JButton loginButton, signupButton, switchToSignupButton, switchToLoginButton;
    private User currentUser;
    private UserManager userManager;
    private User user;
    //shared objects
    private Inventory inventory; 
    private Cart cart;           
    private LandingPageGUI landingPageGUI;
    
    public MainGUI() {
        frame = new JFrame("Medicine Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());
    
        // Initialize UserManager
        userManager = new UserManager();
        
        inventory = Inventory.getInstance();
        cart = new Cart(user);           
        landingPageGUI = new LandingPageGUI(inventory, cart); 
        
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

        switchToSignupButton = new JButton("Signup");
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

        switchToLoginButton = new JButton("Login");
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
        String email = loginEmailField.getText().trim();
        String password = new String(loginPasswordField.getPassword());
    
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }
    
        User user = userManager.validateUser(email, password);
        if (user != null) {
            currentUser = user;
            
            // Create the cart for the logged-in user
            cart = new Cart(currentUser); 
            
            // Reinitialize the landing page GUI with the new cart
            landingPageGUI = new LandingPageGUI(inventory, cart);
            
            if (user instanceof Admin) { 
                JOptionPane.showMessageDialog(frame, "Admin Login Successful!");
                switchToAdminPanel(); 
            } else {
                JOptionPane.showMessageDialog(frame, "Customer Login Successful!");
                switchToLandingPage(); 
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid email or password.");
        }
    }

    private void switchToAdminPanel() {
        frame.getContentPane().removeAll(); 
        new AdminPanelGUI(inventory, landingPageGUI); 
        frame.dispose();                   
    }

    private void handleSignup() {
        String name = signupNameField.getText().trim();
        String email = signupEmailField.getText().trim();
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

        boolean signupSuccessful = userManager.addUser(name, email, password, false);
        if (signupSuccessful) {
            JOptionPane.showMessageDialog(frame, "Signup successful! Please log in.");
            switchToPanel("Login");
        } else {
            JOptionPane.showMessageDialog(frame, "Email already registered.");
        }
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    private void switchToPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), panelName);
    }

    private void switchToLandingPage() {
        frame.getContentPane().removeAll(); 
        frame.getContentPane().add(landingPageGUI.getPanel()); 
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}