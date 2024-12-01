import javax.swing.*; // For GUI components.
import java.awt.*; // For layout managers and UI controls.
import java.util.ArrayList; // For storing and managing lists of objects.

public class LandingPageGUI {
    private JPanel panel; // The main container panel for the landing page.
    private JPanel medicinesPanel; // Panel to display a grid of medicines.
    private Inventory inventory; // Reference to the inventory system.
    private Cart cart; // Reference to the user's shopping cart.
    private User user; // The currently logged-in user.
    private Customer customer; // If the user is a customer, cast here.

    // Constructor for initializing the landing page GUI.
    public LandingPageGUI(Inventory inventory, Cart cart) {
        this.inventory = Inventory.getInstance(); // Ensure a singleton instance of inventory.
        this.inventory = inventory; // Assign the provided inventory instance.
        this.cart = cart; // Assign the provided cart instance.

        User user = cart.getUser(); // Retrieve the user associated with the cart.

        // Ensure the user is logged in; otherwise, display a warning.
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Welcome! please login."); // Inform the user to log in.
            return; // Exit the constructor to prevent further initialization.
        }

        // Main layout setup for the landing page.
        panel = new JPanel(new BorderLayout()); // Use BorderLayout for organizing sections.

        JLabel titleLabel = new JLabel("Browse Medicines", JLabel.CENTER); // Title label.
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Set font for the title.
        panel.add(titleLabel, BorderLayout.NORTH); // Add title at the top.

        // Create and configure the panel to display medicines in a grid.
        JPanel medicinesPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Dynamic grid layout.
        updateMedicinesPanel(medicinesPanel, inventory, cart); // Populate the grid with medicines.

        // Add scroll functionality to the medicines panel.
        JScrollPane scrollPane = new JScrollPane(medicinesPanel); // Wrap medicinesPanel in a scroll pane.
        panel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the layout.

        // Create a panel for buttons at the bottom of the layout.
        JPanel bottomPanel = new JPanel(new FlowLayout()); // Use FlowLayout for the button panel.

        // "View Cart" button setup.
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(e -> new CartDetailsGUI(cart, inventory)); // Show cart details on click.
        bottomPanel.add(viewCartButton); // Add the button to the bottom panel.

        // "Logout" button setup.
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout()); // Logout and return to the main GUI on click.
        bottomPanel.add(logoutButton); // Add the button to the bottom panel.

        // "View Order History" button setup.
        JButton viewOrderHistoryButton = new JButton("View Order History");
        viewOrderHistoryButton.addActionListener(e -> {
            if (user == null) {
                JOptionPane.showMessageDialog(null, "No user associated with the cart."); // Handle missing user.
                return;
            }

            if (user instanceof Customer) { // If the user is a customer, show their order history.
                new OrderHistoryGUI((Customer) user);
            } else {
                JOptionPane.showMessageDialog(null, "Only customers can view order history."); // Warn if not a customer.
            }
        });
        bottomPanel.add(viewOrderHistoryButton); // Add the button to the bottom panel.

        panel.add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the layout.
    }

    // Refresh the medicines panel to reflect updates.
    public void refresh() {
        updateMedicinesPanel(medicinesPanel, inventory, cart); // Update the grid with current data.
    }

    // Update the medicines grid panel.
    private void updateMedicinesPanel(JPanel medicinesPanel, Inventory inventory, Cart cart) {
        medicinesPanel.removeAll(); // Clear existing content.
        ArrayList<Medicine> medicines = inventory.getMedicineList(); // Retrieve the list of medicines.

        for (Medicine medicine : medicines) { // Loop through each medicine.
            JPanel medicineCard = new JPanel(new BorderLayout()); // Create a card for each medicine.

            JLabel imageLabel = new JLabel(); // Label for the medicine's image.
            if (medicine.getImagePath() != null && !medicine.getImagePath().isEmpty()) {
                ImageIcon icon = new ImageIcon(new ImageIcon(medicine.getImagePath())
                        .getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH)); // Resize the image.
                imageLabel.setIcon(icon); // Set the image icon.
            } else {
                imageLabel.setText("No Image"); // Placeholder if no image is available.
            }
            medicineCard.add(imageLabel, BorderLayout.NORTH); // Add the image at the top.

            // Display the medicine's name, price, and stock information.
            JLabel nameLabel = new JLabel(medicine.getName()); // Medicine name.
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font style.
            JLabel priceLabel = new JLabel("Price: PHP" + medicine.getPrice()); // Medicine price.
            JLabel stockLabel = new JLabel("Stock: " + medicine.getStock()); // Medicine stock.

            JPanel infoPanel = new JPanel(new GridLayout(3, 1)); // Grid for the info.
            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(stockLabel);
            medicineCard.add(infoPanel, BorderLayout.CENTER); // Add info to the card center.

            // Add buttons for actions on the medicine.
            JPanel buttonPanel = new JPanel(new FlowLayout()); // Layout for the buttons.

            // "Add to Cart" button setup.
            JButton addToCartButton = new JButton("Add to Cart");
            addToCartButton.addActionListener(e -> {
                if (medicine.getStock() > 0) { // Check if the item is in stock.
                    cart.addMedicine(medicine, 1); // Add the medicine to the cart.
                    medicine.updateStock(-1); // Decrease stock by 1.
                    
                    // Show confirmation message
                    JOptionPane.showMessageDialog(null, 
                    medicine.getName() + " has been added to your cart.",
                    "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE); // Displaying the confirmation dialog
                    
                    inventory.saveInventoryToFile(); // Save the updated inventory.
                    refresh(); // Refresh the medicines panel.
                } else {
                    JOptionPane.showMessageDialog(null, "Out of stock!"); // Warn if out of stock.
                }
            });
            buttonPanel.add(addToCartButton); // Add the button to the button panel.
            
            // "View Details" button setup.
            JButton viewDetailsButton = new JButton("View Details");
            viewDetailsButton.addActionListener(e -> new MedicineDetailsGUI(medicine, cart)); // Show details on click.
            buttonPanel.add(viewDetailsButton); // Add the button to the button panel.

            medicineCard.add(buttonPanel, BorderLayout.SOUTH); // Add the buttons at the bottom.
            medicinesPanel.add(medicineCard); // Add the card to the grid panel.
        }

        medicinesPanel.revalidate(); // Refresh the UI layout.
        medicinesPanel.repaint(); // Redraw the panel.
    }

    // Logout functionality.
    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel); // Get the parent frame.
        frame.getContentPane().removeAll(); // Clear the frame content.
        new MainGUI(); // Return to the main GUI.
        frame.dispose(); // Close the current frame.
    }

    // Getter for the main panel.
    public JPanel getPanel() {
        return panel; // Return the main container panel.
    }
}
