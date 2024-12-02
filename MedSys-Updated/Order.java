// Importing LocalDateTime class from java.time package to work with date and time without time-zone
import java.time.LocalDateTime;

// Importing Serializable interface from java.io package, marking classes that can be serialized (converted to byte stream)
import java.io.Serializable;

// Importing ObjectInputStream class to deserialize objects (converting byte stream back to objects)
import java.io.ObjectInputStream;

// Importing IOException class to handle input/output exceptions such as file not found, network errors, etc.
import java.io.IOException;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;  // Ensures compatibility during deserialization across different versions of the class.
    private static int idCounter = OrderIdManager.loadCounter(); // Load the current order ID counter from a file

    static {
        // Register a shutdown hook to save the current order ID counter when the application closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            OrderIdManager.saveCounter(idCounter);  // Save the counter to a file when the program exits
        }));
    }

    private int orderId;  // Unique identifier for the order
    private int customerId;  // Customer's ID (stored as an integer)
    private transient Customer customer;  // Customer associated with the order (marked as transient to prevent serialization)
    private Cart cart;  // Cart associated with the order
    private double totalAmount;  // Total price of the order
    private String status;  // Current status of the order (e.g., "Placed", "Canceled", "Processed")
    private LocalDateTime orderDate;  // The date and time when the order was placed

    // Constructor to create a new order for a customer and their cart
    public Order(Customer customer, Cart customerCart, String status) {
        this.orderId = ++idCounter;  // Increment and assign a unique order ID
        this.customer = customer;  // Assign the customer who placed the order
        this.customerId = Integer.parseInt(customer.getId());  // Store the customer's ID as an integer

        // Create a new cart for this order based on the customer's cart
        this.cart = new Cart(customer);
        for (Medicine medicine : customerCart.getMedicines()) {
            // Add each medicine from the customer's cart to this order's cart
            this.cart.addMedicine(medicine, customerCart.getQuantity(medicine));
        }

        this.totalAmount = this.cart.calculateTotalPrice();  // Calculate the total amount of the order
        this.status = status;  // Set the status of the order
        this.orderDate = LocalDateTime.now();  // Set the order date to the current date and time
    }

    public int getCustomerId() {
        return customerId;  // Return the customer ID
    }

    public int getOrderId() {  // Getter for order ID
        return orderId;
    }

    public String getStatus() {  // Getter for order status
        return status;
    }

    // Method to place the order, marking its status as "Placed" and updating stock
    public void placeOrder() {
        this.status = "Placed";  // Change status to "Placed"
        for (Medicine medicine : cart.getMedicines()) {
            // Update the stock for each medicine in the order (decrease stock by the quantity in the cart)
            medicine.updateStock(-cart.getQuantity(medicine));
        }
    }

    // Method to cancel the order, updating the status and printing a cancellation message
    public void cancelOrder() {
        if (!"Processed".equals(this.status)) {  // Only allow cancellation if the order is not processed
            this.status = "Canceled";  // Set status to "Canceled"
            System.out.println("Order " + orderId + " has been canceled. Your money will be refunded.");
        }
    }

    // Getter for order date
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    // Getter for total amount
    public double getTotalAmount() {
        return totalAmount;
    }

    // Override toString() to provide a detailed string representation of the order
    @Override
    public String toString() {
        if (customer == null) {
            return "Order details unavailable (customer is null).";  // Handle case where the customer is null
        }
    
        StringBuilder orderDetails = new StringBuilder();  // Use StringBuilder for efficient string concatenation
        orderDetails.append("Order ID: ").append(orderId)
                    .append("\nCustomer: ").append(customer.getName())  // Customer's name
                    .append("\nOrder Date: ").append(orderDate)  // Order date
                    .append("\nStatus: ").append(status)  // Order status
                    .append("\nTotal Amount: PHP ").append(totalAmount)  // Total amount for the order
                    .append("\nItems:\n");
    
        // Loop through each medicine in the cart and add it to the order details
        for (Medicine medicine : cart.getMedicines()) {
            int quantity = cart.getQuantity(medicine);  // Get the quantity of this medicine in the cart
            orderDetails.append("- ")
                        .append(medicine.getName())  // Add the name of the medicine
                        .append(" x").append(quantity)  // Add the quantity
                        .append(" (PHP ").append(medicine.getPrice() * quantity).append(")\n");  // Add total price for this medicine
        }
    
        return orderDetails.toString();  // Return the final string with all order details
    }
}
