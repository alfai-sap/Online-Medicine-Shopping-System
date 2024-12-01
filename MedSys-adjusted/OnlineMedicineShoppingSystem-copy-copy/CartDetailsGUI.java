import javax.swing.*;
import java.awt.*;

public class CartDetailsGUI {
    private JFrame frame;
    private Cart cart;

    public CartDetailsGUI(Cart cart, Inventory inventory) {
        this.cart = cart; // Store the cart object

        frame = new JFrame("Cart Details");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Your Cart", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Main Cart Panel
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        updateCartPanel(cartPanel); // Populate cart items

        // Scroll Pane for Cart
        JScrollPane scrollPane = new JScrollPane(cartPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Checkout Button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> {
            if (cart.getMedicines().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Your cart is empty!");
                return;
            }

            // Redirect to PaymentGUI instead of placing the order
            new PaymentGUI(cart.calculateTotalPrice(), cart);

            frame.dispose(); // Close the Cart Details GUI
        });

        frame.add(checkoutButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void updateCartPanel(JPanel cartPanel) {
        cartPanel.removeAll(); // Clear existing components

        if (cart.getMedicines().isEmpty()) {
            cartPanel.add(new JLabel("Your cart is empty.", JLabel.CENTER));
        } else {
            for (Medicine medicine : cart.getMedicines()) {
                JPanel itemPanel = new JPanel(new BorderLayout());
                JTextArea detailsTextArea = new JTextArea(
                    String.format(
                        "Name: %s\nPrice: %.2f\nQuantity: %d",
                        medicine.getName(),
                        medicine.getPrice(),
                        cart.getQuantity(medicine)
                    )
                );
                detailsTextArea.setEditable(false);
                itemPanel.add(detailsTextArea, BorderLayout.CENTER);
                cartPanel.add(itemPanel);
                
                // Add remove button for each item
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(e -> {
                    cart.removeMedicine(medicine);
                    updateCartPanel(cartPanel); // Refresh cart display after removal
                });
                itemPanel.add(removeButton, BorderLayout.SOUTH);
                cartPanel.add(itemPanel);
            }
        }

        cartPanel.revalidate();
        cartPanel.repaint();
    }
}
