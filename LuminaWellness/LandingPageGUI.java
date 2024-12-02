import javax.swing.*; // For GUI components.
import java.awt.*; // For layout managers and UI controls.
import java.util.ArrayList; // For storing and managing lists of objects.
import java.awt.event.*; // For handling events.

/**
 * Landing page GUI for customers.
 * Displays available medicines, cart, and options for order history and logout.
 */
public class LandingPageGUI {
    private JPanel panel; // Main container panel.
    private JPanel medicinesPanel; // Panel to display a grid of medicines.
    private Inventory inventory; // Reference to the inventory system.
    private Cart cart; // Reference to the user's shopping cart.

    // Constructor to initialize the landing page.
    public LandingPageGUI(Inventory inventory, Cart cart) {
        this.inventory = inventory;
        this.cart = cart;

        // Check if the user is logged in.
        User user = cart.getUser();
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Welcome! Please login.", "Login Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Initialize main panel with a border layout.
        panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add header, content, and footer sections.
        panel.add(createHeader(), BorderLayout.NORTH);
        panel.add(createMedicineScrollPanel(), BorderLayout.CENTER);
        panel.add(createFooter(user), BorderLayout.SOUTH);

        // Populate medicines.
        updateMedicinesPanel();
    }

    /**
     * Creates the header section with a title.
     *
     * @return the header panel.
     */
    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("Lumina Medicines", JLabel.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 28));
        titleLabel.setForeground(new Color(54, 54, 54)); // Dark grey color.
        headerPanel.add(titleLabel);
        return headerPanel;
    }

    /**
     * Creates the scrollable panel for displaying medicines.
     *
     * @return the scrollable medicines panel.
     */
    private JScrollPane createMedicineScrollPanel() {
        medicinesPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JScrollPane scrollPane = new JScrollPane(medicinesPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220))); // Light grey border.
        return scrollPane;
    }

    /**
     * Creates the footer with action buttons.
     *
     * @param user the logged-in user.
     * @return the footer panel.
     */
    private JPanel createFooter(User user) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.add(createButton("View Cart", e -> new CartDetailsGUI(cart, inventory)));
        footerPanel.add(createButton("Order History", e -> handleOrderHistory(user)));
        footerPanel.add(createButton("Logout", e -> logout()));
        return footerPanel;
    }

    /**
     * Updates the medicines panel with data from the inventory.
     */
    public void updateMedicinesPanel() {
        medicinesPanel.removeAll();
        ArrayList<Medicine> medicines = inventory.getMedicineList();

        for (Medicine medicine : medicines) {
            medicinesPanel.add(createMedicineCard(medicine));
        }

        medicinesPanel.revalidate();
        medicinesPanel.repaint();
    }

    /**
     * Creates a card for displaying medicine details and actions.
     *
     * @param medicine the medicine to display.
     * @return the medicine card panel.
     */
    private JPanel createMedicineCard(Medicine medicine) {
        JPanel medicineCard = new JPanel(new BorderLayout(10, 10));
        medicineCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        medicineCard.setBackground(new Color(250, 250, 250)); // Soft white background.

        // Add image.
        JLabel imageLabel = new JLabel();
        if (medicine.getImagePath() != null && !medicine.getImagePath().isEmpty()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(medicine.getImagePath()).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        } else {
            imageLabel.setText("No Image");
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        medicineCard.add(imageLabel, BorderLayout.NORTH);

        // Add details.
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setOpaque(false);
        infoPanel.add(new JLabel(medicine.getName(), JLabel.LEFT));
        infoPanel.add(new JLabel("Price: PHP " + medicine.getPrice(), JLabel.LEFT));
        infoPanel.add(new JLabel("Stock: " + medicine.getStock(), JLabel.LEFT));
        medicineCard.add(infoPanel, BorderLayout.CENTER);

        // Add action buttons.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.add(createButton("Add to Cart", e -> handleAddToCart(medicine)));
        buttonPanel.add(createButton("View Details", e -> new MedicineDetailsGUI(medicine, cart)));
        medicineCard.add(buttonPanel, BorderLayout.SOUTH);

        return medicineCard;
    }

    /**
     * Handles adding a medicine to the cart.
     *
     * @param medicine the medicine to add.
     */
    private void handleAddToCart(Medicine medicine) {
        if (medicine.getStock() > 0) {
            cart.addMedicine(medicine, 1);
            medicine.updateStock(-1);
            JOptionPane.showMessageDialog(null, medicine.getName() + " has been added to your cart.", "Added to Cart", JOptionPane.INFORMATION_MESSAGE);
            inventory.saveInventoryToFile();
            updateMedicinesPanel();
        } else {
            JOptionPane.showMessageDialog(null, "Out of stock!", "Stock Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles viewing order history for the customer.
     *
     * @param user the user requesting order history.
     */
    private void handleOrderHistory(User user) {
        if (user instanceof Customer) {
            new OrderHistoryGUI((Customer) user);
        } else {
            JOptionPane.showMessageDialog(null, "Only customers can view order history.", "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Logs out the user and switches to the main GUI.
     */
    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.getContentPane().removeAll();
        new MainGUI();
        frame.dispose();
    }

    /**
     * Creates a styled button with an action listener.
     *
     * @param text   the button text.
     * @param action the action to perform on click.
     * @return the styled button.
     */
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(54, 54, 54)); // Dark grey background.
        button.setForeground(Color.WHITE); // White text.
        button.setFocusPainted(false); // Remove focus border.
        button.addActionListener(action);
        return button;
    }

    /**
     * Gets the main panel of the landing page.
     *
     * @return the main panel.
     */
    public JPanel getPanel() {
        return panel;
    }
}
