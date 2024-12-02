import javax.swing.*; // Import Swing library for GUI components.
import java.awt.*; // Import AWT for layout and UI controls.
import java.awt.event.*; // Import AWT for event handling.
import java.util.regex.Pattern; // Import Pattern for regex validation.

public class MainGUI {
    // Main frame for the GUI application.
    private JFrame frame;
    
    // Panels for the login and signup interfaces.
    private JPanel loginPanel, signupPanel;
    
    // Input fields for login and signup forms.
    private JTextField loginEmailField, signupEmailField, signupNameField;
    private JPasswordField loginPasswordField, signupPasswordField;
    
    // Buttons for navigation and actions in the GUI.
    private JButton loginButton, signupButton, switchToSignupButton, switchToLoginButton;
    
    // Currently logged-in user and UserManager instance for managing users.
    private User currentUser;
    private UserManager userManager;
    
    // Shared objects used across the application.
    private User user; // Placeholder for the current user.
    private Inventory inventory; // Inventory system for medicines.
    private Cart cart; // Shopping cart for the user.
    private LandingPageGUI landingPageGUI; // Main user interface after login.

    // Constructor for the MainGUI.
    public MainGUI() {
        // Initialize the main frame.
        frame = new JFrame("Lumina Wellness");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout()); // Use CardLayout to switch between panels.
        frame.setLocationRelativeTo(null); // Center the frame on the screen.
        // Initialize UserManager for managing users.
        userManager = new UserManager();
        
        // Initialize shared objects.
        inventory = Inventory.getInstance(); // Singleton instance of Inventory.
        cart = new Cart(user); // Create a cart for the current user.
        landingPageGUI = new LandingPageGUI(inventory, cart); // Initialize the landing page.

        // Initialize the login and signup panels.
        initializeLoginPanel();
        initializeSignupPanel();

        // Add the panels to the frame.
        frame.add(loginPanel, "Login");
        frame.add(signupPanel, "Signup");

        frame.setVisible(true); // Make the frame visible.
    }

    // Create and configure the login panel
    private void initializeLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout()); // BorderLayout to arrange components
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Add padding

        // Header panel with the logo and brand name
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center the logo and text

        JLabel logoLabel = new JLabel(); // Placeholder for the logo image
        try {
            // Replace with the actual path to your logo image file
            ImageIcon logoIcon = new ImageIcon("C:\\lumina\\LuminaWellness\\images\\logo.jpg");
            Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(logoImage));
        } catch (Exception e) {
            logoLabel.setText("LOGO"); // Fallback if logo fails to load
        }

        JLabel brandLabel = new JLabel("LUMINA WELLNESS");
        brandLabel.setFont(new Font("Calibri", Font.ITALIC, 24));
        brandLabel.setForeground(Color.BLACK); // Optional: adjust color if needed
        
        
        headerPanel.add(logoLabel);
        headerPanel.add(brandLabel);
        
        // Login form panel
        JPanel loginFormPanel = new JPanel();
        loginFormPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns (for fields and labels)
        loginFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around form

        JLabel emailLabel = new JLabel("Email:");
        loginEmailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        loginPasswordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(54, 54, 54)); // Set background color
        loginButton.setForeground(Color.WHITE); // Set text color
        loginButton.addActionListener(e -> handleLogin()); // Attach the login handler

        switchToSignupButton = new JButton("Signup");
        switchToSignupButton.setBackground(new Color(54, 54, 54));
        switchToSignupButton.setForeground(Color.WHITE);
        switchToSignupButton.addActionListener(e -> switchToPanel("Signup"));

        // Add components to the login form
        loginFormPanel.add(emailLabel);
        loginFormPanel.add(loginEmailField);
        loginFormPanel.add(passwordLabel);
        loginFormPanel.add(loginPasswordField);
        loginFormPanel.add(loginButton);
        loginFormPanel.add(switchToSignupButton);

        // Add the header and login form to the main login panel
        loginPanel.add(headerPanel, BorderLayout.NORTH); // Header at the top
        loginPanel.add(loginFormPanel, BorderLayout.CENTER); // Form in the center
    }

    // Create and configure the signup panel
    private void initializeSignupPanel() {
        
        signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayout(6, 2, 10, 10)); // Grid layout for the signup form
        signupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Header panel with the logo and brand name
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center the logo and text

        JLabel logoLabel = new JLabel(); // Placeholder for the logo image
        try {
            // Replace with the actual path to your logo image file
            ImageIcon logoIcon = new ImageIcon("C:\\lumina\\LuminaWellness\\images\\logo.jpg");
            Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(logoImage));
        } catch (Exception e) {
            logoLabel.setText("LOGO"); // Fallback if logo fails to load
        }
        
        JLabel brandLabel = new JLabel("LUMINA WELLNESS");
        brandLabel.setFont(new Font("Calibri", Font.ITALIC, 24));
        brandLabel.setForeground(Color.BLACK); // Optional: adjust color if needed
        
        JLabel Label = new JLabel("SIGNUP");
        Label.setFont(new Font("Poppins", Font.ITALIC, 12));
        Label.setForeground(Color.BLACK); // Optional: adjust color if needed
        
        
        headerPanel.add(logoLabel);
        headerPanel.add(brandLabel);
        headerPanel.add(Label);
        
        JLabel nameLabel = new JLabel("Name:");
        signupNameField = new JTextField();
        
        JLabel emailLabel = new JLabel("Email:");
        signupEmailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        signupPasswordField = new JPasswordField();

        signupButton = new JButton("Signup");
        signupButton.setBackground(new Color(54, 54, 54));
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(e -> handleSignup());

        switchToLoginButton = new JButton("Login");
        switchToLoginButton.setBackground(new Color(54, 54, 54));
        switchToLoginButton.setForeground(Color.WHITE);
        switchToLoginButton.addActionListener(e -> switchToPanel("Login"));

        // Add components to the signup form
        signupPanel.add(nameLabel);
        signupPanel.add(signupNameField);
        signupPanel.add(emailLabel);
        signupPanel.add(signupEmailField);
        signupPanel.add(passwordLabel);
        signupPanel.add(signupPasswordField);
        signupPanel.add(signupButton);
        signupPanel.add(switchToLoginButton);
    }

    // Handle the login process.
    private void handleLogin() {
        String email = loginEmailField.getText().trim(); // Get email input.
        String password = new String(loginPasswordField.getPassword()); // Get password input.

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields."); // Validation.
            return;
        }

        User user = userManager.validateUser(email, password); // Validate user credentials.
        if (user != null) {
            currentUser = user; // Set the current user.

            // Initialize a new cart for the logged-in user.
            cart = new Cart(currentUser); 
            landingPageGUI = new LandingPageGUI(inventory, cart); // Update the landing page.

            if (user instanceof Customer) { // Check if the user is a customer.
                JOptionPane.showMessageDialog(frame, "Customer Login Successful!");
                switchToLandingPage(); // Switch to the landing page.
            } else if (user instanceof Admin) { // Check if the user is an admin.
                JOptionPane.showMessageDialog(frame, "Admin Login Successful!");
                switchToAdminPanel(); // Switch to the admin panel.
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid email or password."); // Show error.
        }
    }

    // Switch to the admin panel.
    private void switchToAdminPanel() {
        frame.getContentPane().removeAll(); // Clear the frame content.
        new AdminPanelGUI(inventory, landingPageGUI); // Initialize and show the admin panel.
        frame.dispose(); // Close the main frame.
    }

    // Handle the signup process.
    private void handleSignup() {
        String name = signupNameField.getText().trim(); // Get name input.
        String email = signupEmailField.getText().trim(); // Get email input.
        String password = new String(signupPasswordField.getPassword()); // Get password input.

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields."); // Validation.
            return;
        }

        if (!validateEmail(email)) { // Validate email format.
            JOptionPane.showMessageDialog(frame, "Invalid email format.");
            return;
        }

        if (password.length() < 6) { // Validate password length.
            JOptionPane.showMessageDialog(frame, "Password must be at least 6 characters long.");
            return;
        }

        // Add a new user and show the result.
        boolean signupSuccessful = userManager.addUser(name, email, password, false);
        if (signupSuccessful) {
            JOptionPane.showMessageDialog(frame, "Signup successful! Please log in.");
            switchToPanel("Login"); // Switch to the login panel.
        } else {
            JOptionPane.showMessageDialog(frame, "Email already registered.");
        }
    }

    // Validate the email format.
    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Regex for email validation.
        return Pattern.matches(emailRegex, email);
    }

    // Switch between panels in the CardLayout.
    private void switchToPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), panelName);
    }

    // Switch to the landing page for customers.
    private void switchToLandingPage() {
        if (currentUser instanceof Customer) { // Ensure the user is a customer.
            Customer customer = (Customer) currentUser;
            cart = customer.getCart(); // Use the customer's cart.
            landingPageGUI = new LandingPageGUI(inventory, cart); // Update the landing page.
            frame.getContentPane().removeAll(); // Clear the frame content.
            frame.getContentPane().add(landingPageGUI.getPanel()); // Add the landing page panel.
            frame.revalidate(); // Refresh the frame.
            frame.repaint();
        }
    }

    // Main method to launch the GUI.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new); // Create and show the MainGUI.
    }
}