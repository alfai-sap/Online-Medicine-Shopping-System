import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private Customer customer;
    private Cart cart;
    private double totalAmount;
    private String status;
    private LocalDateTime orderDate; // New attribute for order date and time

    public Order(int orderId, Customer customer, Cart cart, double totalAmount, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.cart = cart;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = LocalDateTime.now();
    }

    public void placeOrder() {
        this.status = "Placed";
    }

    public String trackOrder() {
        return this.status;
    }

    public void cancelOrder() {
        if (!"Processed".equals(this.status)) {
            this.status = "Canceled";
        }
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + 
               ", Total Amount: $" + totalAmount + 
               ", Status: " + status + 
               ", Customer: " + customer.getName() + 
               ", Items: " + cartDetails() + 
               ", Order Date: " + orderDate;
    }

    private String cartDetails() {
        // Fetching cart details as a String
        return cart.displayCart();
    }

    // Getters and Setters for attributes if needed
    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }
}