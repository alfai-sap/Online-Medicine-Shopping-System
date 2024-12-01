public class Payment {
    private double amount;
    private String paymentMethod;
    private String paymentStatus;

    public Payment(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "Pending";
    }

    public void processPayment() {
        if (validatePayment()) {
            paymentStatus = "Completed";
            System.out.println("Payment of " + amount + " PHP via " + paymentMethod + " is successful.");
        } else {
            paymentStatus = "Failed";
            System.out.println("Payment validation failed.");
        }
    }

    private boolean validatePayment() {
        return amount > 0 && 
       (paymentMethod.equals("Credit Card") || 
        paymentMethod.equals("Cash") || 
        paymentMethod.equals("Gcash") || 
        paymentMethod.equals("Bank Transfer"));
    }

    public void refund() {
        if ("Completed".equals(paymentStatus)) {
            paymentStatus = "Refunded";
            System.out.println("Payment of " + amount + "php has been refunded.");
        } else {
            System.out.println("Refund not possible. Current status: " + paymentStatus);
        }
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}