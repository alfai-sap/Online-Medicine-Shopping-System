import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderHistoryGUI {
    private JFrame frame;
    private Customer customer;

    public OrderHistoryGUI(Customer customer) {
        this.customer = customer; // Assign the customer
        frame = new JFrame("Order History");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Order History", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Order History Panel
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));

        ArrayList<Order> orders = customer.getOrderHistory();
        if (orders.isEmpty()) {
            JLabel noOrdersLabel = new JLabel("No orders found.", JLabel.CENTER);
            noOrdersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            historyPanel.add(noOrdersLabel);
        } else {
            for (Order order : orders) {
                JPanel orderPanel = new JPanel(new BorderLayout());
                orderPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JTextArea orderDetails = new JTextArea(order.toString());
                orderDetails.setEditable(false);
                orderPanel.add(new JScrollPane(orderDetails), BorderLayout.CENTER);

                // Buttons
                JPanel buttonPanel = new JPanel(new FlowLayout());
                JButton trackButton = new JButton("Track");
                trackButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Order Status: " + order.getStatus()));
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new CancelOrderListener(order, customer, historyPanel));
                buttonPanel.add(trackButton);
                buttonPanel.add(cancelButton);

                orderPanel.add(buttonPanel, BorderLayout.SOUTH);
                historyPanel.add(orderPanel);
            }
        }

        JScrollPane scrollPane = new JScrollPane(historyPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make sure the frame is visible
        frame.setVisible(true);
    }

    private class CancelOrderListener implements ActionListener {
        private Order order;
        private Customer customer;
        private JPanel historyPanel;

        public CancelOrderListener(Order order, Customer customer, JPanel historyPanel) {
            this.order = order;
            this.customer = customer;
            this.historyPanel = historyPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!"Processed".equals(order.getStatus())) {
                order.cancelOrder();
                JOptionPane.showMessageDialog(frame, "Order canceled successfully!");
                new OrderHistoryGUI(customer); // Refresh the GUI
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Order cannot be canceled as it is already processed.");
            }
        }
    }
}
