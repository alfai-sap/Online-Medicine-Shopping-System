import javax.swing.*;  // Importing Swing components for creating the user interface
import java.awt.*;  // Importing AWT components for layout management
import java.awt.event.ActionEvent;  // Importing ActionEvent to handle button actions
import java.awt.event.ActionListener;  // Importing ActionListener to listen for button actions
import java.util.ArrayList;  // Importing ArrayList to store multiple orders

public class OrderHistoryGUI {
    private JFrame frame;  // The main window (frame) of the OrderHistoryGUI
    private Customer customer;  // The customer whose order history is displayed

    public OrderHistoryGUI(Customer customer) {
        this.customer = customer;  // Assign the passed customer to the class variable

        // Create and set up the JFrame for the order history window
        frame = new JFrame("Order History");  // Create a new JFrame with the title "Order History"
        frame.setSize(600, 400);  // Set the size of the frame to 600x400 pixels
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close the frame when the user clicks the close button
        frame.setLayout(new BorderLayout());  // Use BorderLayout for placing components in the frame

        // Title Label at the top of the frame
        JLabel titleLabel = new JLabel("Order History", JLabel.CENTER);  // Create a label with text "Order History" centered
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));  // Set the font of the title label
        frame.add(titleLabel, BorderLayout.NORTH);  // Add the title label to the top of the frame (North)

        // Order History Panel to hold individual order details
        JPanel historyPanel = new JPanel();  // Create a panel to hold all the orders
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));  // Use a vertical box layout for the history panel

        // Get the list of orders for the customer
        ArrayList<Order> orders = customer.getOrderHistory();
        if (orders.isEmpty()) {  // If there are no orders, show a message
            JLabel noOrdersLabel = new JLabel("No orders found.", JLabel.CENTER);  // Create a label to indicate no orders
            noOrdersLabel.setFont(new Font("Arial", Font.PLAIN, 16));  // Set the font of the label
            historyPanel.add(noOrdersLabel);  // Add the "No orders found" label to the history panel
        } else {
            // If there are orders, display each one
            for (Order order : orders) {
                JPanel orderPanel = new JPanel(new BorderLayout());  // Create a panel for each order with BorderLayout
                orderPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  // Add empty borders around each order panel

                // Create a text area to display the order details
                JTextArea orderDetails = new JTextArea(order.toString());  // Convert the order to a string and display it
                orderDetails.setEditable(false);  // Make the text area non-editable
                orderPanel.add(new JScrollPane(orderDetails), BorderLayout.CENTER);  // Add the text area to the center of the panel with scroll functionality

                // Buttons panel for "Track" and "Cancel" buttons
                JPanel buttonPanel = new JPanel(new FlowLayout());  // Create a panel to hold the buttons in a flow layout
                JButton trackButton = new JButton("Track");  // Create a button labeled "Track"
                trackButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Order Status: " + order.getStatus()));  // Show the order status when clicked
                JButton cancelButton = new JButton("Cancel");  // Create a button labeled "Cancel"
                cancelButton.addActionListener(new CancelOrderListener(order, customer, historyPanel));  // Add listener to cancel the order when clicked
                buttonPanel.add(trackButton);  // Add the "Track" button to the button panel
                buttonPanel.add(cancelButton);  // Add the "Cancel" button to the button panel

                orderPanel.add(buttonPanel, BorderLayout.SOUTH);  // Add the buttons panel to the bottom of the order panel
                historyPanel.add(orderPanel);  // Add the order panel to the history panel
            }
        }

        // Add the history panel to the frame inside a JScrollPane (to allow scrolling if the orders are many)
        JScrollPane scrollPane = new JScrollPane(historyPanel);
        frame.add(scrollPane, BorderLayout.CENTER);  // Add the scroll pane with order history to the center of the frame

        // Make sure the frame is visible
        frame.setVisible(true);  // Set the frame to visible so the user can interact with it
    }

    // Inner class to handle canceling an order
    private class CancelOrderListener implements ActionListener {
        private Order order;  // The order to be canceled
        private Customer customer;  // The customer who placed the order
        private JPanel historyPanel;  // The panel displaying the order history

        // Constructor to initialize the cancel listener with the order, customer, and history panel
        public CancelOrderListener(Order order, Customer customer, JPanel historyPanel) {
            this.order = order;  // Set the order
            this.customer = customer;  // Set the customer
            this.historyPanel = historyPanel;  // Set the panel where orders are displayed
        }

        @Override
        public void actionPerformed(ActionEvent e) {  // Handle the "Cancel" button click
            if (!"Processed".equals(order.getStatus())) {  // If the order is not already processed, it can be canceled
                order.cancelOrder();  // Cancel the order
                JOptionPane.showMessageDialog(frame, "Order canceled successfully! Your Money will be Refunded.");  // Show a confirmation message
                new OrderHistoryGUI(customer);  // Refresh the order history GUI
                frame.dispose();  // Close the current order history window
            } else {
                JOptionPane.showMessageDialog(frame, "Order cannot be canceled as it is already processed.");  // Show a message if the order is processed and cannot be canceled
            }
        }
    }
}
