import java.io.*;
import java.util.ArrayList;

public class Customer extends User {
    private Cart cart;
    private ArrayList<Order> orderHistory;
    private static final String ORDER_HISTORY_FILE_PREFIX = "customer_";

    public Customer(int userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.cart = new Cart(this);  // Pass the customer as the user to the cart
        this.orderHistory = loadOrderHistoryFromFile();  // Load the order history from file
    }


    public Cart getCart() {
        return cart;
    }

    public void addToCart(Medicine medicine) {
        cart.addMedicine(medicine, 1);
    }

    public void removeFromCart(Medicine medicine) {
        cart.removeMedicine(medicine);
    }

    public Order checkout() {
        User user = cart.getUser();  // Ensure user is associated with the cart
        
        if (user instanceof Customer) {
            Customer customer = (Customer) user;  // Cast to Customer
        
            // Create a new order with the correct customer and cart
            Order newOrder = new Order(customer, cart, "Placed");
        
            // Add the order to the order history
            orderHistory.add(newOrder);
        
            // Save the updated order history to the file
            saveOrderHistoryToFile();
        
            // Clear the cart after checkout
            cart.clearCart();
        
            return newOrder;
        } else {
            System.err.println("Error: User is not a Customer");
            return null;
        }
    }



    
    private void appendOrderToCentralFile(Order order) {
        File centralFile = new File("all_orders.txt");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(centralFile, true))) {
            oos.writeObject(order);
        } catch (IOException e) {
            System.err.println("Error saving order to central file: " + e.getMessage());
        }
    }


    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    private void saveOrderHistoryToFile() {
        File file = new File(getOrderHistoryFileName());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(orderHistory);
        } catch (IOException e) {
            System.err.println("Error saving order history: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Order> loadOrderHistoryFromFile() {
        File file = new File(getOrderHistoryFileName());
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading order history: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private String getOrderHistoryFileName() {
        return ORDER_HISTORY_FILE_PREFIX + getId() + "_order_history.txt"; // Ensure the file exists and is accessible
    }

}
