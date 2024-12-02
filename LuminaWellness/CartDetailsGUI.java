import javax.swing.*;  // For GUI creation.
import java.awt.*;  // For layout management.
import java.util.ArrayList;  // For handling lists of objects.

/**
 * GUI for displaying and managing the user's cart details.
 */
public class CartDetailsGUI {
    private JFrame frame;  // Main window for the cart details.
    private Cart cart;  // Reference to the user's shopping cart.
    private Inventory inventory;  // Reference to the inventory system.

    /**
     * Constructs the CartDetailsGUI to display the user's cart.
     *
     * @param cart      the shopping cart to display.
     * @param inventory the inventory system to manage stock updates.
     */
    public CartDetailsGUI(Cart cart, Inventory inventory) {
        this.cart = cart;
        this.inventory = inventory;

        initializeFrame();
    }

    /**
     * Initializes and configures the main frame for the cart details.
     */
    private void initializeFrame() {
        frame = new JFrame("Cart Details");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Add components to the frame.
        frame.add(createTitleLabel(), BorderLayout.NORTH);
        frame.add(createCartScrollPane(), BorderLayout.CENTER);
        frame.add(createBottomPanel(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Creates the title label for the cart details window.
     *
     * @return the title label.
     */
    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Your Cart", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        return titleLabel;
    }

    /**
     * Creates the scroll pane containing the cart panel.
     *
     * @return the scroll pane with the cart panel.
     */
    private JScrollPane createCartScrollPane() {
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        updateCartPanel(cartPanel);

        return new JScrollPane(cartPanel);
    }

    /**
     * Creates the bottom panel with the checkout button.
     *
     * @return the bottom panel.
     */
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> handleCheckout());
        bottomPanel.add(checkoutButton);
        return bottomPanel;
    }

    /**
     * Updates the cart panel with the list of medicines and their details.
     *
     * @param cartPanel the panel to update.
     */
    private void updateCartPanel(JPanel cartPanel) {
        cartPanel.removeAll();

        if (cart.getMedicines().isEmpty()) {
            cartPanel.add(new JLabel("Your cart is empty.", JLabel.CENTER));
        } else {
            for (Medicine medicine : cart.getMedicines()) {
                cartPanel.add(createItemPanel(medicine));
            }
        }

        cartPanel.revalidate();
        cartPanel.repaint();
    }

    /**
     * Creates a panel displaying the details of a medicine in the cart.
     *
     * @param medicine the medicine to display.
     * @return the panel for the medicine item.
     */
    private JPanel createItemPanel(Medicine medicine) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 10));
        itemPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        itemPanel.setBackground(new Color(250, 250, 250));

        itemPanel.add(createImageLabel(medicine), BorderLayout.WEST);
        itemPanel.add(createInfoPanel(medicine), BorderLayout.CENTER);
        itemPanel.add(createButtonPanel(medicine), BorderLayout.SOUTH);

        return itemPanel;
    }

    /**
     * Creates a label displaying the medicine's image.
     *
     * @param medicine the medicine to display.
     * @return the image label.
     */
    private JLabel createImageLabel(Medicine medicine) {
        JLabel imageLabel = new JLabel();
        if (medicine.getImagePath() != null && !medicine.getImagePath().isEmpty()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(medicine.getImagePath())
                    .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        } else {
            imageLabel.setText("No Image");
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        return imageLabel;
    }

    /**
     * Creates a panel displaying the medicine's details.
     *
     * @param medicine the medicine to display.
     * @return the details panel.
     */
    private JPanel createInfoPanel(Medicine medicine) {
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(medicine.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel priceLabel = new JLabel("Price: PHP " + medicine.getPrice());
        JLabel quantityLabel = new JLabel("Quantity: " + cart.getQuantity(medicine));

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(quantityLabel);

        return infoPanel;
    }

    /**
     * Creates a panel with a remove button for the medicine.
     *
     * @param medicine the medicine to remove.
     * @return the button panel.
     */
    private JPanel createButtonPanel(Medicine medicine) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton removeButton = new JButton("Remove");

        removeButton.addActionListener(e -> handleRemove(medicine));
        buttonPanel.add(removeButton);

        return buttonPanel;
    }

    /**
     * Handles the checkout process.
     */
    private void handleCheckout() {
        if (cart.getMedicines().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Your cart is empty!");
            return;
        }

        new PaymentGUI(cart.calculateTotalPrice(), cart);
        frame.dispose();
    }

    /**
     * Handles removing a medicine from the cart.
     *
     * @param medicine the medicine to remove.
     */
    private void handleRemove(Medicine medicine) {
        int quantityRemoved = cart.getQuantity(medicine);
        cart.removeMedicine(medicine);
        medicine.updateStock(quantityRemoved);
        inventory.saveInventoryToFile();

        JOptionPane.showMessageDialog(null, medicine.getName() + " successfully removed from your cart.", "Item Removed", JOptionPane.INFORMATION_MESSAGE);
        updateCartPanel((JPanel) frame.getContentPane().getComponent(1)); // Refresh the cart panel.
    }
}
