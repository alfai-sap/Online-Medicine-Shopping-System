import javax.swing.*;

public class ECommerceApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> run(null, "Guest")); // Default to guest role
    }

    public static void run(User user, String role) {
        JFrame mainFrame = new JFrame("Medicine E-Commerce");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Separate functionality based on role
        if ("User".equalsIgnoreCase(role)) {
            tabbedPane.addTab("User Dashboard", new UserPanel(user).getPanel());
        } else if ("Admin".equalsIgnoreCase(role)) {
            tabbedPane.addTab("Admin Dashboard", new AdminPanel().getPanel());
        } else {
            tabbedPane.addTab("Welcome", new JLabel("Please log in to access the system!", JLabel.CENTER));
        }

        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }
}
