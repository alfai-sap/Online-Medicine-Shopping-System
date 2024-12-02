import javax.swing.*; // For GUI components.
import java.awt.*; // For layout managers and UI controls.
import java.util.ArrayList; // For storing and managing lists of objects.
import java.awt.event.*; // For handling events.

public class LandingPageGUI {
    private JPanel panel; // Main container panel for the landing page.
    private JPanel medicinesPanel; // Panel to display a grid of medicines.
    private Inventory inventory; // Reference to the inventory system.
    private Cart cart; // Reference to the user's shopping cart.

    // Constructor for initializing the landing page GUI.
    public LandingPageGUI(Inventory inventory, Cart cart) {
        this.inventory = inventory;
        this.cart = cart;

        // Retrieve the user associated with the cart.
        User user = cart.getUser();
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Welcome! Please login.", "Login Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Main container panel with a BorderLayout.
        panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        

        // Title label at the top.
        JLabel titleLabel = new JLabel("Lumina Medicines", JLabel.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 28));
        titleLabel.setForeground(new Color(54, 54, 54)); // Dark grey color for a modern look.
        panel.add(titleLabel, BorderLayout.NORTH);

        // Medicines grid panel wrapped in a scroll pane.
        medicinesPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JScrollPane scrollPane = new JScrollPane(medicinesPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220))); // Light grey border.
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for buttons.
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton viewCartButton = createButton("View Cart", e -> new CartDetailsGUI(cart, inventory));
        JButton viewOrderHistoryButton = createButton("Order History", e -> handleOrderHistory(user));
        JButton logoutButton = createButton("Logout", e -> logout());

        bottomPanel.add(viewCartButton);
        bottomPanel.add(viewOrderHistoryButton);
        bottomPanel.add(logoutButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Populate medicines panel.
        updateMedicinesPanel();
    }

    // Update the medicines grid panel with inventory data.
    private void updateMedicinesPanel() {
        medicinesPanel.removeAll();
        ArrayList<Medicine> medicines = inventory.getMedicineList();

        for (Medicine medicine : medicines) {
            JPanel medicineCard = new JPanel(new BorderLayout(10, 10));
            medicineCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            medicineCard.setBackground(new Color(250, 250, 250)); // Soft white for card background.

            // Medicine image at the top.
            JLabel imageLabel = new JLabel();
            if (medicine.getImagePath() != null && !medicine.getImagePath().isEmpty()) {
                ImageIcon icon = new ImageIcon(new ImageIcon(medicine.getImagePath()).getImage()
                        .getScaledInstance(120, 120, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
            } else {
                imageLabel.setText("No Image");
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
            }
            medicineCard.add(imageLabel, BorderLayout.NORTH);

            // Medicine details in the center.
            JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
            infoPanel.setOpaque(false);
            JLabel nameLabel = new JLabel(medicine.getName());
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            JLabel priceLabel = new JLabel("Price: PHP " + medicine.getPrice());
            JLabel stockLabel = new JLabel("Stock: " + medicine.getStock());
            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(stockLabel);
            medicineCard.add(infoPanel, BorderLayout.CENTER);

            // Action buttons at the bottom.
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            buttonPanel.setOpaque(false);
            JButton addToCartButton = createButton("Add to Cart", e -> handleAddToCart(medicine));
            JButton viewDetailsButton = createButton("View Details", e -> new MedicineDetailsGUI(medicine, cart));
            buttonPanel.add(addToCartButton);
            buttonPanel.add(viewDetailsButton);
            medicineCard.add(buttonPanel, BorderLayout.SOUTH);

            medicinesPanel.add(medicineCard);
        }

        medicinesPanel.revalidate();
        medicinesPanel.repaint();
    }

    // Handle adding a medicine to the cart.
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

    // Handle viewing order history for customers.
    private void handleOrderHistory(User user) {
        if (user instanceof Customer) {
            new OrderHistoryGUI((Customer) user);
        } else {
            JOptionPane.showMessageDialog(null, "Only customers can view order history.", "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Logout functionality.
    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.getContentPane().removeAll();
        new MainGUI();
        frame.dispose();
    }

    // Create a styled button.
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(54, 54, 54)); // Dark grey background.
        button.setForeground(Color.WHITE); // White text.
        button.setFocusPainted(false); // Remove focus border.
        button.addActionListener(action);
        return button;
    }

    // Getter for the main panel.
    public JPanel getPanel() {
        return panel;
    }
}
