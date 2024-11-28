public class Payment {
    public double amount;
    private final String paymentMethod;
    private String paymentStatus;

    public Payment(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "Pending";
    }

    public void processPayment() {
        if (validatePayment()) {
            paymentStatus = "Completed";
            System.out.println("Payment of $" + amount + " via " + paymentMethod + " is successful.");
        } else {
            System.out.println("Payment validation failed.");
        }
    }

    private boolean validatePayment() {
        // For simplicity, we'll assume all payments are valid
        return amount > 0 && (paymentMethod.equals("Credit Card") || paymentMethod.equals("Cash"));
    }

    public void refund() {
        if ("Completed".equals(paymentStatus)) {
            paymentStatus = "Refunded";
            System.out.println("Payment of $" + amount + " has been refunded.");
        } else {
            System.out.println("Refund not possible. Current status: " + paymentStatus);
        }
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
