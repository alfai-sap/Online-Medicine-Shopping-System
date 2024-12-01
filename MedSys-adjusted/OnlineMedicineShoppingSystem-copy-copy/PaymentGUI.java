import javax.swing.*;
import java.awt.*;

public class PaymentGUI {
    private JFrame frame;  // The main window of the GUI
    private double totalAmount;  // The total amount to be paid
    private Cart cart;  // The shopping cart associated with the user

    // Modified constructor to accept the cart and totalAmount
    public PaymentGUI(double totalAmount, Cart cart) {
        this.totalAmount = totalAmount;  // Set the total amount to be paid
        this.cart = cart;  // Store the cart object for further use

        // Initialize the JFrame
        frame = new JFrame("Payment - Checkout");  // Set the title of the frame
        frame.setSize(600, 400);  // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close the window when the user closes it
        frame.setLayout(new GridLayout(5, 1, 10, 10));  // Set the layout of the frame

        // Display the total amount label
        JLabel amountLabel = new JLabel("Total Amount: PHP " + totalAmount, JLabel.CENTER);
        amountLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));  // Set the font size and style for the label
        frame.add(amountLabel);  // Add the label to the frame

        // Create a combo box to select the payment method
        JComboBox<String> paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "Cash", "Gcash", "Bank Transfer"});
        frame.add(new JLabel("Select Payment Method:", JLabel.CENTER));  // Add a label above the combo box
        frame.add(paymentMethodComboBox);  // Add the combo box to the frame

        // Add a button to confirm payment
        JButton confirmPaymentButton = new JButton("Confirm Payment");
        confirmPaymentButton.addActionListener(e -> {  // When the button is clicked, handle the payment
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();  // Get the selected payment method
            handlePayment(paymentMethod);  // Call handlePayment to process the payment
        });
        frame.add(confirmPaymentButton);  // Add the button to the frame

        // Make the frame visible to the user
        frame.setVisible(true);
    }

    // Method to handle the payment processing
    private void handlePayment(String paymentMethod) {
        double totalAmount = cart.calculateTotalPrice();  // Fetch the latest total from the cart
    
        // Initialize the Payment object and process it
        Payment payment = new Payment(totalAmount, paymentMethod);  // Create a new payment object
        payment.processPayment();  // Process the payment

        // Check the payment status after processing
        if ("Completed".equals(payment.getPaymentStatus())) {  // If the payment is successful
            if (cart.getUser() instanceof Customer) {  // Ensure the user is a Customer
                Customer customer = (Customer) cart.getUser();  // Cast the user to Customer
    
                // Place the order and clear the cart after payment
                Order newOrder = customer.checkout();  // Create a new order using the customer's checkout method
                JOptionPane.showMessageDialog(frame, "Payment successful! Order placed.\n" +  // Show a success message
                        "Order ID: " + newOrder.getOrderId() +  // Display the order ID
                        "\nTotal Amount: PHP " + totalAmount);  // Display the total amount of the order
            } else {
                JOptionPane.showMessageDialog(frame, "Payment successful, but user is not a customer.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Payment failed. Please try again.");  // If payment failed, show an error message
        }
    
        frame.dispose();  // Close the Payment GUI window after processing the payment
    }
}
