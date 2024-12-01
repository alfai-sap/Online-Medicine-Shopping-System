import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Order implements Serializable{
    private static final long serialVersionUID = 1L;
    private static int idCounter = OrderIdManager.loadCounter(); // Load from file
    static {
        // Save the counter when the application shuts down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            OrderIdManager.saveCounter(idCounter);
        }));
    }
    private int orderId;
    private int customerId;
    private transient Customer customer; // Transient since it won't be serialized
    private Cart cart;
    private double totalAmount;
    private String status;
    private LocalDateTime orderDate;

    public Order(Customer customer, Cart customerCart, String status) {
        this.orderId = ++idCounter; // Increment and assign a unique ID
        this.customer = customer;
        this.customerId = Integer.parseInt(customer.getId()); // Store the customer ID as integer
        
        // Create a new Cart instance for this order
        this.cart = new Cart(customer);
        for (Medicine medicine : customerCart.getMedicines()) {
            this.cart.addMedicine(medicine, customerCart.getQuantity(medicine));
        }

        this.totalAmount = this.cart.calculateTotalPrice(); 
        this.status = status;
        this.orderDate = LocalDateTime.now();
    }

    public int getCustomerId(){
        return customerId;
    }
    public int getOrderId() { // Add this getter for orderId
        return orderId;
    }

    public String getStatus() { // Add this getter for status
        return status;
    }

    public void placeOrder() {
        this.status = "Placed";
        for (Medicine medicine : cart.getMedicines()) {
            medicine.updateStock(-cart.getQuantity(medicine));
        }
    }

    public void cancelOrder() {
        if (!"Processed".equals(this.status)) {
            this.status = "Canceled";
            System.out.println("Order " + orderId + " has been canceled. Your money will be refunded");
        }
    }


    // Getter for orderDate
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    // Getter for totalAmount
    public double getTotalAmount() {
        return totalAmount;
    }


    @Override
    public String toString() {
        if (customer == null) {
            return "Order details unavailable (customer is null)."; // Handle case where customer is null
        }
    
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order ID: ").append(orderId)
                    .append("\nCustomer: ").append(customer.getName())  // Avoid null pointer exception
                    .append("\nOrder Date: ").append(orderDate)
                    .append("\nStatus: ").append(status)
                    .append("\nTotal Amount: PHP ").append(totalAmount)
                    .append("\nItems:\n");
    
        for (Medicine medicine : cart.getMedicines()) {
            int quantity = cart.getQuantity(medicine);
            orderDetails.append("- ")
                        .append(medicine.getName())
                        .append(" x").append(quantity)
                        .append(" (PHP ").append(medicine.getPrice() * quantity).append(")\n");
        }
    
        return orderDetails.toString();
    }

    

}
