import javax.swing.*;
import java.awt.*;

public class PaymentGUI {
    private JFrame frame;
    private double totalAmount;
    private Cart cart; // Add cart as a field

    // Modified constructor to accept Cart
    public PaymentGUI(double totalAmount, Cart cart) {
        this.totalAmount = totalAmount;
        this.cart = cart; // Store the cart

        frame = new JFrame("Payment - Checkout");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1, 10, 10));

        // Display Total Amount
        JLabel amountLabel = new JLabel("Total Amount: PHP " + totalAmount, JLabel.CENTER);
        amountLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        frame.add(amountLabel);

        // Payment Method Selection
        JComboBox<String> paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "Cash", "Gcash", "Bank Transfer"});
        frame.add(new JLabel("Select Payment Method:", JLabel.CENTER));
        frame.add(paymentMethodComboBox);

        // Confirm Payment Button
        JButton confirmPaymentButton = new JButton("Confirm Payment");
        confirmPaymentButton.addActionListener(e -> {
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
            handlePayment(paymentMethod);
        });
        frame.add(confirmPaymentButton);

        frame.setVisible(true);
    }

    private void handlePayment(String paymentMethod) {
        Payment payment = new Payment(totalAmount, paymentMethod);
        payment.processPayment();

        if ("Completed".equals(payment.getPaymentStatus())) {
            if (cart.getUser() instanceof Customer) {
                Customer customer = (Customer) cart.getUser();

                // Place the order and clear the cart
                Order newOrder = customer.checkout();
                JOptionPane.showMessageDialog(frame, "Payment successful! Order placed.\n" +
                        "Order ID: " + newOrder.getOrderId() +
                        "\nTotal Amount: PHP " + totalAmount);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Payment failed. Please try again.");
        }

        frame.dispose();
    }
}
