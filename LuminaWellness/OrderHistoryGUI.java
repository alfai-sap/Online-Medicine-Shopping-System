import javax.swing.*;  // For GUI components.
import java.awt.*;  // For layout management.
import java.awt.event.*;  // For handling button actions.
import java.util.ArrayList;  // For handling lists of orders.

public class OrderHistoryGUI {
    private JFrame frame;  // Main window for displaying order history.
    private Customer customer;  // The customer whose order history is displayed.

    /**
     * Constructor to initialize and display the Order History GUI.
     *
     * @param customer the customer whose order history is to be shown.
     */
    public OrderHistoryGUI(Customer customer) {
        this.customer = customer;  // Assign the customer to the instance variable.

        initializeFrame();  // Set up the frame and layout.
        populateOrderHistory();  // Populate the order history.
    }

    /**
     * Initializes the JFrame for the order history window.
     */
    private void initializeFrame() {
        frame = new JFrame("Order History");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title label for the frame.
        JLabel titleLabel = new JLabel("Order History", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);
    }

    /**
     * Populates the order history panel with the customer's orders.
     */
    private void populateOrderHistory() {
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));  // Vertical layout for orders.

        // Retrieve the orders and display them.
        ArrayList<Order> orders = customer.getOrderHistory();
        if (orders.isEmpty()) {
            JLabel noOrdersLabel = new JLabel("No orders found.", JLabel.CENTER);
            noOrdersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            historyPanel.add(noOrdersLabel);
        } else {
            for (Order order : orders) {
                JPanel orderPanel = createOrderPanel(order);
                historyPanel.add(orderPanel);
            }
        }

        // Wrap the history panel in a scroll pane.
        JScrollPane scrollPane = new JScrollPane(historyPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);  // Show the frame.
    }

    /**
     * Creates a panel displaying the details of an individual order.
     *
     * @param order the order to display.
     * @return the panel containing the order details.
     */
    private JPanel createOrderPanel(Order order) {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Display order details in a text area.
        JTextArea orderDetails = new JTextArea(order.toString());
        orderDetails.setEditable(false);
        orderPanel.add(new JScrollPane(orderDetails), BorderLayout.CENTER);

        // Panel for "Track" and "Cancel" buttons.
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton trackButton = createTrackButton(order);
        JButton cancelButton = createCancelButton(order);

        buttonPanel.add(trackButton);
        if (!"Canceled".equals(order.getStatus())) {
            buttonPanel.add(cancelButton);
        }
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);

        return orderPanel;
    }

    /**
     * Creates the "Track" button for each order.
     *
     * @param order the order to track.
     * @return the "Track" button.
     */
    private JButton createTrackButton(Order order) {
        JButton trackButton = new JButton("Track");
        trackButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Order Status: " + order.getStatus()));
        return trackButton;
    }

    /**
     * Creates the "Cancel" button for each order.
     *
     * @param order the order to cancel.
     * @return the "Cancel" button.
     */
    private JButton createCancelButton(Order order) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelOrderListener(order, customer));
        return cancelButton;
    }

    /**
     * Inner class to handle the cancel order functionality.
     */
    private class CancelOrderListener implements ActionListener {
        private Order order;  // The order to be canceled.
        private Customer customer;  // The customer who placed the order.

        /**
         * Constructor to initialize the cancel order listener.
         *
         * @param order     the order to be canceled.
         * @param customer  the customer who placed the order.
         */
        public CancelOrderListener(Order order, Customer customer) {
            this.order = order;
            this.customer = customer;
        }

        /**
         * Handles the cancel action when the "Cancel" button is clicked.
         *
         * @param e the action event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!"Processed".equals(order.getStatus())) {
                // Add the medicine stock back to the inventory.
                for (Medicine medicine : order.getCart().getMedicines()) {
                    int quantity = order.getCart().getQuantity(medicine);
                    medicine.updateStock(quantity);
                }

                // Save the updated inventory.
                Inventory.getInstance().saveInventoryToFile();

                // Cancel the order and refresh the order history.
                order.cancelOrder();
                JOptionPane.showMessageDialog(frame, "Order canceled successfully! Your money will be refunded.");
                new OrderHistoryGUI(customer);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Order cannot be canceled as it is already processed.");
            }
        }
    }
}
