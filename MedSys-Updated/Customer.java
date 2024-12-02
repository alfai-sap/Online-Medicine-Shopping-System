import java.io.*; // Import necessary classes for file input/output operations.
import java.util.ArrayList; // Import ArrayList to handle lists of orders.

public class Customer extends User { // Customer class inherits from the User class.
    private Cart cart; // Cart object to store items the customer adds.
    private ArrayList<Order> orderHistory; // List to keep track of the customer's past orders.
    private static final String ORDER_HISTORY_FILE_PREFIX = "customer_"; // Prefix for order history file names.

    // Constructor to initialize a Customer object.
    public Customer(int userId, String name, String email, String password) {
        super(userId, name, email, password); // Call the User class constructor to set user details.
        this.cart = new Cart(this); // Initialize a cart and associate it with this customer.
        this.orderHistory = loadOrderHistoryFromFile(); // Load the customer's order history from a file.
    }

    // Getter method to access the customer's cart.
    public Cart getCart() {
        return cart;
    }

    // Method to add a medicine to the customer's cart.
    public void addToCart(Medicine medicine) {
        cart.addMedicine(medicine, 1); // Add one unit of the specified medicine to the cart.
    }

    // Method to remove a medicine from the customer's cart.
    public void removeFromCart(Medicine medicine) {
        cart.removeMedicine(medicine); // Remove the specified medicine from the cart.
    }

    // Method to handle the checkout process.
    public Order checkout() {
        User user = cart.getUser(); // Get the user associated with the cart.

        // Ensure the user is a Customer before proceeding.
        if (user instanceof Customer) {
            Customer customer = (Customer) user; // Cast the user to Customer.

            // Create a new order with the customer's cart.
            Order newOrder = new Order(customer, cart, "Placed");

            // Add the new order to the customer's order history.
            orderHistory.add(newOrder);

            // Save the updated order history to a file.
            saveOrderHistoryToFile();

            // Clear the cart after the checkout process is complete.
            cart.clearCart();

            return newOrder; // Return the newly created order.
        } else {
            System.err.println("Error: User is not a Customer"); // Error handling for invalid user type.
            return null;
        }
    }

    // Private method to append a new order to a central file storing all orders.
    private void appendOrderToCentralFile(Order order) {
        File centralFile = new File("all_orders.txt"); // File to store all orders.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(centralFile, true))) {
            oos.writeObject(order); // Serialize and save the order to the file.
        } catch (IOException e) {
            System.err.println("Error saving order to central file: " + e.getMessage()); // Handle exceptions.
        }
    }

    // Getter method to access the customer's order history.
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    // Private method to save the customer's order history to a file.
    private void saveOrderHistoryToFile() {
        File file = new File(getOrderHistoryFileName()); // Get the file name for this customer's order history.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(orderHistory); // Serialize and save the order history to the file.
        } catch (IOException e) {
            System.err.println("Error saving order history: " + e.getMessage()); // Handle exceptions.
        }
    }

    // Private method to load the customer's order history from a file.
    @SuppressWarnings("unchecked")
    private ArrayList<Order> loadOrderHistoryFromFile() {
        File file = new File(getOrderHistoryFileName()); // Get the file name for this customer's order history.

        // If the file does not exist, return an empty order history.
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Order>) ois.readObject(); // Deserialize and load the order history.
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading order history: " + e.getMessage()); // Handle exceptions.
            return new ArrayList<>(); // Return an empty order history on failure.
        }
    }

    // Private method to construct the file name for this customer's order history.
    private String getOrderHistoryFileName() {
        return ORDER_HISTORY_FILE_PREFIX + getId() + "_order_history.txt"; 
        // Example: "customer_1_order_history.txt" for a customer with ID 1.
    }
}
