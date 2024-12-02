import javax.swing.*; // Import Swing components for GUI.
import java.awt.*; // Import AWT for layout management.

public class MedicineDetailsGUI {

    /**
     * Displays the details of a medicine and allows adding it to the cart.
     *
     * @param medicine the medicine to display.
     * @param cart     the shopping cart.
     */
    public MedicineDetailsGUI(Medicine medicine, Cart cart) {
        JFrame frame = createFrame(medicine);
        JPanel mainPanel = createMainPanel(frame);

        // Add components to the main panel.
        mainPanel.add(createTitlePanel(medicine), BorderLayout.NORTH);
        mainPanel.add(createDetailsPanel(medicine), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(medicine, cart, frame), BorderLayout.SOUTH);

        frame.setVisible(true); // Display the frame.
    }

    /**
     * Creates and configures the JFrame.
     *
     * @param medicine the medicine for which the details are displayed.
     * @return the configured JFrame.
     */
    private JFrame createFrame(Medicine medicine) {
        JFrame frame = new JFrame(medicine.getName() + " - Details");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen.
        return frame;
    }

    /**
     * Creates the main panel with a border layout and padding.
     *
     * @param frame the parent frame.
     * @return the main panel.
     */
    private JPanel createMainPanel(JFrame frame) {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(mainPanel);
        return mainPanel;
    }

    /**
     * Creates the title panel displaying the medicine name.
     *
     * @param medicine the medicine to display.
     * @return the title panel.
     */
    private JPanel createTitlePanel(Medicine medicine) {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(medicine.getName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(54, 54, 54));
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    /**
     * Creates the details panel displaying the medicine image and details.
     *
     * @param medicine the medicine to display.
     * @return the details panel.
     */
    private JPanel createDetailsPanel(Medicine medicine) {
        JPanel detailsPanel = new JPanel(new BorderLayout(15, 15));

        // Image section.
        JLabel imageLabel = createImageLabel(medicine);
        detailsPanel.add(imageLabel, BorderLayout.NORTH);

        // Info section.
        JPanel infoPanel = createInfoPanel(medicine);
        detailsPanel.add(infoPanel, BorderLayout.CENTER);

        // Description section.
        JScrollPane descriptionScrollPane = createDescriptionScrollPane(medicine);
        detailsPanel.add(descriptionScrollPane, BorderLayout.SOUTH);

        return detailsPanel;
    }

    /**
     * Creates the label to display the medicine's image.
     *
     * @param medicine the medicine to display.
     * @return the image label.
     */
    private JLabel createImageLabel(Medicine medicine) {
        JLabel imageLabel = new JLabel();
        if (medicine.getImagePath() != null && !medicine.getImagePath().isEmpty()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(medicine.getImagePath()).getImage()
                    .getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        } else {
            imageLabel.setText("No Image");
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setForeground(new Color(150, 150, 150));
        }
        return imageLabel;
    }

    /**
     * Creates the panel to display the medicine's price and stock.
     *
     * @param medicine the medicine to display.
     * @return the info panel.
     */
    private JPanel createInfoPanel(Medicine medicine) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel priceLabel = new JLabel("Price: PHP " + medicine.getPrice());
        JLabel stockLabel = new JLabel("Stock: " + medicine.getStock());
        styleInfoLabel(priceLabel);
        styleInfoLabel(stockLabel);

        infoPanel.add(priceLabel);
        infoPanel.add(stockLabel);

        return infoPanel;
    }

    /**
     * Styles an info label.
     *
     * @param label the label to style.
     */
    private void styleInfoLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(new Color(54, 54, 54));
    }

    /**
     * Creates a scroll pane to display the medicine's detailed information.
     *
     * @param medicine the medicine to display.
     * @return the scroll pane.
     */
    private JScrollPane createDescriptionScrollPane(Medicine medicine) {
        JTextArea detailsArea = new JTextArea(medicine.getDetails(), 5, 40);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailsArea.setBackground(new Color(240, 240, 240));
        detailsArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        return scrollPane;
    }

    /**
     * Creates the bottom panel for input and buttons.
     *
     * @param medicine the medicine to add to the cart.
     * @param cart     the shopping cart.
     * @param frame    the parent frame.
     * @return the bottom panel.
     */
    private JPanel createBottomPanel(Medicine medicine, Cart cart, JFrame frame) {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setOpaque(false);

        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JTextField quantityField = new JTextField(5);
        quantityField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton addToCartButton = createAddToCartButton(medicine, cart, frame, quantityField);

        bottomPanel.add(quantityLabel);
        bottomPanel.add(quantityField);
        bottomPanel.add(addToCartButton);

        return bottomPanel;
    }

    /**
     * Creates the "Add to Cart" button with its action listener.
     *
     * @param medicine      the medicine to add.
     * @param cart          the shopping cart.
     * @param frame         the parent frame.
     * @param quantityField the text field for quantity input.
     * @return the "Add to Cart" button.
     */
    private JButton createAddToCartButton(Medicine medicine, Cart cart, JFrame frame, JTextField quantityField) {
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addToCartButton.setBackground(new Color(54, 54, 54));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setFocusPainted(false);

        addToCartButton.addActionListener(e -> {
            try {
                int quantity = Integer.parseInt(quantityField.getText().trim());
                if (quantity > 0 && medicine.getStock() >= quantity) {
                    cart.addMedicine(medicine, quantity);
                    medicine.updateStock(-quantity);
                    JOptionPane.showMessageDialog(frame, "Added to Cart", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity or out of stock.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for quantity.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        return addToCartButton;
    }
}
