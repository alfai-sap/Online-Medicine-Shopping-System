public class Payment {
    private double amount;  // The amount to be paid (e.g., in PHP)
    private String paymentMethod;  // The payment method used (e.g., "Credit Card", "Cash", etc.)
    private String paymentStatus;  // The current status of the payment (e.g., "Pending", "Completed", "Failed")

    // Constructor to initialize the payment with the amount and method
    public Payment(double amount, String paymentMethod) {
        this.amount = amount;  // Set the amount for the payment
        this.paymentMethod = paymentMethod;  // Set the payment method
        this.paymentStatus = "Pending";  // Default payment status is "Pending"
    }

    // Method to process the payment
    public void processPayment() {
        if (validatePayment()) {  // Check if the payment is valid
            paymentStatus = "Completed";  // Set payment status to "Completed"
            System.out.println("Payment of " + amount + " PHP via " + paymentMethod + " is successful.");
        } else {
            paymentStatus = "Failed";  // Set payment status to "Failed" if validation fails
            System.out.println("Payment validation failed.");
        }
    }

    // Method to validate the payment (checks if the amount is valid and if the method is accepted)
    private boolean validatePayment() {
        return amount > 0 &&  // Amount must be greater than zero
               (paymentMethod.equals("Credit Card") ||  // Valid payment methods: Credit Card, Cash, Gcash, Bank Transfer
                paymentMethod.equals("Cash") || 
                paymentMethod.equals("Gcash") || 
                paymentMethod.equals("Bank Transfer"));
    }

    // Method to refund the payment
    public void refund() {
        if ("Completed".equals(paymentStatus)) {  // Check if the payment has already been completed
            paymentStatus = "Refunded";  // Change status to "Refunded"
            System.out.println("Payment of " + amount + " PHP has been refunded.");
        } else {
            System.out.println("Refund not possible. Current status: " + paymentStatus);  // Show message if refund is not possible
        }
    }

    // Getter method to return the current payment status
    public String getPaymentStatus() {
        return paymentStatus;  // Return the current status of the payment
    }
}
