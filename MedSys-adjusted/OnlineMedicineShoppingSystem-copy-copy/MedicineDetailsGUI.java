import javax.swing.*; // For GUI components.
import java.awt.*; // For layout managers and UI controls.

public class MedicineDetailsGUI {
    // Constructor to display medicine details and add it to the cart.
    public MedicineDetailsGUI(Medicine medicine, Cart cart) {
        // Create a new JFrame with the medicine's name as the title.
        JFrame frame = new JFrame(medicine.getName() + " - Details");
        frame.setSize(500, 500); // Set the size of the frame.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window when exited.

        // Create a title label with the medicine's name.
        JLabel title = new JLabel(medicine.getName(), JLabel.CENTER); // Center-align the text.
        title.setFont(new Font("Serif", Font.BOLD, 24)); // Set a bold font for emphasis.
        frame.add(title, BorderLayout.NORTH); // Add the title to the top of the frame.

        // Create a text area to display detailed information about the medicine.
        JTextArea detailsArea = new JTextArea(10, 40); // 10 rows, 40 columns.
        detailsArea.setText(medicine.getDetails()); // Set the details of the medicine.
        detailsArea.setEditable(false); // Make the text area read-only.
        frame.add(new JScrollPane(detailsArea), BorderLayout.CENTER); // Add the text area in a scroll pane at the center.

        // Create a panel for the input field and button at the bottom of the frame.
        JPanel panel = new JPanel(); // Default FlowLayout used.
        JLabel quantityLabel = new JLabel("Quantity:"); // Label to prompt quantity input.
        JTextField quantityField = new JTextField(5); // Input field for quantity (5 columns).

        // Create an "Add to Cart" button.
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> { // Action listener for the button.
            try {
                int quantity = Integer.parseInt(quantityField.getText()); // Parse the input to an integer.
                if (quantity > 0) { // Check if the quantity is valid.
                    cart.addMedicine(medicine, quantity); // Add the specified quantity of medicine to the cart.
                    JOptionPane.showMessageDialog(frame, "Added to Cart"); // Notify the user of success.
                    frame.dispose(); // Close the details window after adding to the cart.
                } else {
                    JOptionPane.showMessageDialog(frame, "Enter a valid quantity greater than zero."); // Invalid input message.
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for quantity."); // Error message for invalid input.
            }
        });

        // Add components to the bottom panel.
        panel.add(quantityLabel); // Add the label to the panel.
        panel.add(quantityField); // Add the input field to the panel.
        panel.add(addToCartButton); // Add the button to the panel.

        frame.add(panel, BorderLayout.SOUTH); // Add the panel to the bottom of the frame.

        frame.setVisible(true); // Make the frame visible.
    }
}
