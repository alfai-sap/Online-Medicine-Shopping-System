import javax.swing.*; // Import the Swing library for graphical user interface (GUI) components like buttons, labels, and text fields.
import java.awt.*; // Import the AWT library for layout management and other UI controls.

public class MedicineDetailsGUI {
    // Constructor to display the details of a medicine and add it to the cart.
    public MedicineDetailsGUI(Medicine medicine, Cart cart) {
        // Create a new window (JFrame) with the medicine's name as the title.
        JFrame frame = new JFrame(medicine.getName() + " - Details");
        frame.setSize(600, 500); // Set the size of the window to 600 pixels wide and 500 pixels tall.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window when the close button is clicked.
        frame.setLocationRelativeTo(null); // Center the window on the screen.

        // Create a main panel with a layout manager that arranges components in different regions (like top, center, bottom).
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the edges of the panel.
        frame.add(mainPanel); // Add the main panel to the frame.

        // Create a label for the medicine's name and set its appearance.
        JLabel title = new JLabel(medicine.getName(), JLabel.CENTER); // Align the text to the center.
        title.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Set the font to Segoe UI, bold, and size 24.
        title.setForeground(new Color(54, 54, 54)); // Set the font color to dark grey.
        mainPanel.add(title, BorderLayout.NORTH); // Add the title label to the top of the frame.

        // Create another panel to hold the image and other details of the medicine.
        JPanel detailsPanel = new JPanel(new BorderLayout(15, 15));
        detailsPanel.setOpaque(false); // Make the panel transparent so the background is visible.

        // Create a label to display the medicine's image.
        JLabel imageLabel = new JLabel();
        if (medicine.getImagePath() != null && !medicine.getImagePath().isEmpty()) {
            // If an image path is provided, load and scale the image.
            ImageIcon icon = new ImageIcon(new ImageIcon(medicine.getImagePath()).getImage()
                    .getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon); // Set the image icon.
        } else {
            // If no image is provided, display a placeholder text.
            imageLabel.setText("No Image");
            imageLabel.setHorizontalAlignment(JLabel.CENTER); // Center the placeholder text.
            imageLabel.setForeground(new Color(150, 150, 150)); // Set the text color to light grey.
        }
        detailsPanel.add(imageLabel, BorderLayout.NORTH); // Add the image label to the top of the details panel.

        // Create another panel for the medicine details like price and stock.
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Arrange components vertically.
        infoPanel.setOpaque(false); // Make this panel transparent.

        // Create labels for the medicine's price and stock.
        JLabel priceLabel = new JLabel("Price: PHP " + medicine.getPrice());
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Set the font for price.
        priceLabel.setForeground(new Color(54, 54, 54)); // Set the font color to dark grey.

        JLabel stockLabel = new JLabel("Stock: " + medicine.getStock());
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Set the font for stock.
        stockLabel.setForeground(new Color(54, 54, 54)); // Set the font color to dark grey.

        // Add the price and stock labels to the info panel.
        infoPanel.add(priceLabel);
        infoPanel.add(stockLabel);
        detailsPanel.add(infoPanel, BorderLayout.CENTER); // Add the info panel to the center of the details panel.

        // Create a text area to show detailed information about the medicine.
        JTextArea detailsArea = new JTextArea(5, 40); // Create a text area with 5 rows and 40 columns.
        detailsArea.setText(medicine.getDetails()); // Set the details text in the text area.
        detailsArea.setEditable(false); // Make the text area read-only.
        detailsArea.setLineWrap(true); // Enable line wrapping.
        detailsArea.setWrapStyleWord(true); // Wrap words to avoid splitting them.
        detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Set a readable font.
        detailsArea.setCaretPosition(0); // Start from the top of the text area when opened.
        detailsArea.setBackground(new Color(240, 240, 240)); // Set a light background color for the text area.
        
        // Wrap the text area in a scroll pane to enable scrolling if the text exceeds the visible area.
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220))); // Add a soft border around the scroll pane.
        detailsPanel.add(scrollPane, BorderLayout.SOUTH); // Add the scroll pane to the bottom of the details panel.

        mainPanel.add(detailsPanel, BorderLayout.CENTER); // Add the details panel to the center of the main panel.

        // Create a bottom panel for input and buttons like quantity and Add to Cart.
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Arrange components with centered alignment.
        bottomPanel.setOpaque(false); // Make this panel transparent.

        // Create a label and input field for the quantity of medicine.
        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Set the font for quantity label.
        JTextField quantityField = new JTextField(5); // Create a text field to input the quantity, with a width of 5 characters.
        quantityField.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Set the font for the quantity input.

        // Create a button to add the medicine to the cart.
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Set the font for the button.
        addToCartButton.setBackground(new Color(54, 54, 54)); // Set a dark grey background for the button.
        addToCartButton.setForeground(Color.WHITE); // Set the button text color to white.
        addToCartButton.setFocusPainted(false); // Remove the focus border when the button is clicked.
        
        // Define the action to be performed when the "Add to Cart" button is clicked.
        addToCartButton.addActionListener(e -> {
            try {
                // Try to parse the input from the quantity field into an integer.
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0) {
                    // If the quantity is valid (greater than 0), add the medicine to the cart.
                    cart.addMedicine(medicine, quantity);
                    // Show a success message.
                    JOptionPane.showMessageDialog(frame, "Added to Cart", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose(); // Close the window after adding the medicine to the cart.
                } else {
                    // If the quantity is not valid, show an error message.
                    JOptionPane.showMessageDialog(frame, "Enter a valid quantity greater than zero.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                // If the quantity input is not a valid number, show an error message.
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for quantity.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add the quantity label, input field, and button to the bottom panel.
        bottomPanel.add(quantityLabel);
        bottomPanel.add(quantityField);
        bottomPanel.add(addToCartButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the bottom of the main panel.

        frame.setVisible(true); // Make the frame visible so the user can interact with it.
    }
}
