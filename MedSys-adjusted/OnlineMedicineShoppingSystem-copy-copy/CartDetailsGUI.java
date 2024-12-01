import javax.swing.*;  // Import Swing components for GUI creation.
import java.awt.*;  // Import AWT components for GUI layout.

public class CartDetailsGUI {
    private JFrame frame;  // The main window for CartDetailsGUI.
    private Cart cart;  // Reference to the Cart object for the current user.

    public CartDetailsGUI(Cart cart, Inventory inventory) {
        this.cart = cart;  // Store the cart object passed in the constructor.

        frame = new JFrame("Cart Details");  // Create a new JFrame with the title "Cart Details".
        frame.setSize(600, 400);  // Set the window size to 600x400 pixels.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close the frame when the user clicks the close button.
        frame.setLayout(new BorderLayout());  // Use BorderLayout for organizing components in the frame.

        // Title Label at the top of the window.
        JLabel titleLabel = new JLabel("Your Cart", JLabel.CENTER);  // Create a label with text "Your Cart" aligned to the center.
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));  // Set font style to Serif, bold, size 24.
        frame.add(titleLabel, BorderLayout.NORTH);  // Add the title label to the top (North) section of the frame.

        // Main Cart Panel for displaying cart items.
        JPanel cartPanel = new JPanel();  // Create a JPanel to hold cart items.
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));  // Use a vertical box layout for the cart panel.
        updateCartPanel(cartPanel);  // Call method to populate the cart panel with items.

        // Scroll Pane for Cart (allows scrolling if items exceed the visible area).
        JScrollPane scrollPane = new JScrollPane(cartPanel);  // Wrap the cart panel in a scroll pane.
        frame.add(scrollPane, BorderLayout.CENTER);  // Add the scroll pane to the center section of the frame.

        // Checkout Button at the bottom of the window.
        JButton checkoutButton = new JButton("Checkout");  // Create a button labeled "Checkout".
        checkoutButton.addActionListener(e -> {
            if (cart.getMedicines().isEmpty()) {  // Check if the cart is empty.
                JOptionPane.showMessageDialog(frame, "Your cart is empty!");  // Show a message if cart is empty.
                return;  // Return without proceeding to checkout if cart is empty.
            }

            // Redirect to PaymentGUI and pass the total price and cart.
            new PaymentGUI(cart.calculateTotalPrice(), cart);

            frame.dispose();  // Close the Cart Details window after proceeding to checkout.
        });

        frame.add(checkoutButton, BorderLayout.SOUTH);  // Add the checkout button to the bottom (South) section of the frame.
        frame.setVisible(true);  // Make the frame visible to the user.
    }

    // Method to update the cart panel with the list of medicines and their details.
    private void updateCartPanel(JPanel cartPanel) {
        cartPanel.removeAll();  // Clear any existing components in the cart panel.

        if (cart.getMedicines().isEmpty()) {  // If the cart has no medicines.
            cartPanel.add(new JLabel("Your cart is empty.", JLabel.CENTER));  // Display "Your cart is empty." message.
        } else {
            for (Medicine medicine : cart.getMedicines()) {  // Iterate through each medicine in the cart.
                JPanel itemPanel = new JPanel(new BorderLayout());  // Create a new panel to display each item.
                JTextArea detailsTextArea = new JTextArea(  // Create a text area to show medicine details.
                    String.format(  // Use String.format to display formatted medicine details.
                        "Name: %s\nPrice: %.2f\nQuantity: %d",  // Format: Name, Price, Quantity.
                        medicine.getName(),
                        medicine.getPrice(),
                        cart.getQuantity(medicine)
                    )
                );
                detailsTextArea.setEditable(false);  // Make the text area non-editable.
                itemPanel.add(detailsTextArea, BorderLayout.CENTER);  // Add the text area to the center of the item panel.
                cartPanel.add(itemPanel);  // Add the item panel to the cart panel.

                // Add "Remove" button to each item.
                JButton removeButton = new JButton("Remove");  // Create a button labeled "Remove".
                removeButton.addActionListener(e -> {  // Add action listener for the remove button.
                    cart.removeMedicine(medicine);  // Remove the selected medicine from the cart.
                    updateCartPanel(cartPanel);  // Refresh the cart display after removal.
                });
                itemPanel.add(removeButton, BorderLayout.SOUTH);  // Add the remove button to the bottom of the item panel.
                cartPanel.add(itemPanel);  // Add the item panel with the remove button to the cart panel.
            }
        }

        cartPanel.revalidate();  // Revalidate the cart panel to update the layout.
        cartPanel.repaint();  // Repaint the cart panel to reflect changes.
    }
}
