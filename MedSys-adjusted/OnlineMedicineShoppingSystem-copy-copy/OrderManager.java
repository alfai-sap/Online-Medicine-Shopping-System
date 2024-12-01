// Importing necessary classes
import java.io.*;  // For File, InputStream, OutputStream, and Exception handling
import java.util.ArrayList;  // For using the ArrayList to store orders

public class OrderManager {
    // The name of the file that will store all the orders
    private static final String CENTRAL_ORDER_FILE = "all_orders.txt";

    // Method to save a single order
    public static void saveOrder(Order order) {
        // Load all existing orders into an ArrayList
        ArrayList<Order> orders = loadAllOrders();
        
        // Add the new order to the list of orders
        orders.add(order);
        
        // Save the updated list of orders back to the file
        saveAllOrders(orders);
    }

    // Method to load all orders from the file
    public static ArrayList<Order> loadAllOrders() {
        // Create an empty list to hold the orders
        ArrayList<Order> orders = new ArrayList<>();
        
        // Create a File object pointing to the orders file
        File file = new File(CENTRAL_ORDER_FILE);
        
        // If the file does not exist, return the empty list (no orders)
        if (!file.exists()) return orders;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Read the ArrayList of orders from the file
            orders = (ArrayList<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Catch errors that might occur while reading the file
            System.err.println("Error loading orders: " + e.getMessage());
        }
        
        // Return the list of orders
        return orders;
    }

    // Method to save all orders to the file
    public static void saveAllOrders(ArrayList<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CENTRAL_ORDER_FILE))) {
            // Write the ArrayList of orders to the file
            oos.writeObject(orders);
        } catch (IOException e) {
            // Catch errors that might occur while writing to the file
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }
}
