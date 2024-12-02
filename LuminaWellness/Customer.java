import java.io.*; // For file input/output operations.
import java.util.ArrayList; // For handling lists of orders.

/**
 * Represents a customer with a shopping cart and order history.
 * Inherits from the User class.
 */
public class Customer extends User {
    private Cart cart; // The customer's shopping cart.
    private ArrayList<Order> orderHistory; // The customer's past orders.
    private static final String ORDER_HISTORY_FILE_PREFIX = "customer_"; // Prefix for order history file names.

    // Constructor
    /**
     * Initializes a Customer object with user details.
     *
     * @param userId    Unique identifier for the customer.
     * @param name      Name of the customer.
     * @param email     Email of the customer.
     * @param password  Password of the customer.
     */
    public Customer(int userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.cart = new Cart(this); // Associate a cart with this customer.
        this.orderHistory = loadOrderHistoryFromFile(); // Load order history from a file.
    }

    // Cart Operations
    /**
     * Gets the customer's shopping cart.
     *
     * @return The cart object.
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Adds a medicine to the customer's cart.
     *
     * @param medicine The medicine to add.
     */
    public void addToCart(Medicine medicine) {
        cart.addMedicine(medicine, 1); // Add one unit of the medicine.
    }

    /**
     * Removes a medicine from the customer's cart.
     *
     * @param medicine The medicine to remove.
     */
    public void removeFromCart(Medicine medicine) {
        cart.removeMedicine(medicine);
    }

    // Order Management
    /**
     * Proceeds to checkout and creates a new order.
     *
     * @return The newly created order.
     */
    public Order checkout() {
        if (cart.getUser() instanceof Customer customer) {
            Order newOrder = new Order(customer, cart, "Placed");
            orderHistory.add(newOrder); // Add order to history.
            saveOrderHistoryToFile(); // Save the updated history.
            cart.clearCart(); // Clear the cart after checkout.
            return newOrder;
        } else {
            System.err.println("Error: User is not a Customer");
            return null;
        }
    }

    /**
     * Gets the customer's order history.
     *
     * @return The list of past orders.
     */
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    // File Management for Order History
    /**
     * Saves the customer's order history to a file.
     */
    private void saveOrderHistoryToFile() {
        File file = new File(getOrderHistoryFileName());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(orderHistory); // Serialize and save the order history.
        } catch (IOException e) {
            System.err.println("Error saving order history: " + e.getMessage());
        }
    }

    /**
     * Loads the customer's order history from a file.
     *
     * @return The list of past orders or an empty list if none exist.
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Order> loadOrderHistoryFromFile() {
        File file = new File(getOrderHistoryFileName());

        if (!file.exists()) {
            return new ArrayList<>(); // Return empty history if file doesn't exist.
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Order>) ois.readObject(); // Deserialize the order history.
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading order history: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Constructs the file name for the customer's order history.
     *
     * @return The file name as a string.
     */
    private String getOrderHistoryFileName() {
        return ORDER_HISTORY_FILE_PREFIX + getId() + "_order_history.txt";
    }

    // Central Order File Management
    /**
     * Appends an order to the central file storing all orders.
     *
     * @param order The order to append.
     */
    private void appendOrderToCentralFile(Order order) {
        File centralFile = new File("all_orders.txt");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(centralFile, true))) {
            oos.writeObject(order); // Serialize and save the order.
        } catch (IOException e) {
            System.err.println("Error saving order to central file: " + e.getMessage());
        }
    }
}
