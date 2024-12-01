import java.io.*;
import java.util.ArrayList;

public class OrderManager {
    private static final String CENTRAL_ORDER_FILE = "all_orders.txt";

    public static void saveOrder(Order order) {
        ArrayList<Order> orders = loadAllOrders();
        orders.add(order);
        saveAllOrders(orders);
    }

    public static ArrayList<Order> loadAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        File file = new File(CENTRAL_ORDER_FILE);
        if (!file.exists()) return orders;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            orders = (ArrayList<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }
        return orders;
    }

    public static void saveAllOrders(ArrayList<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CENTRAL_ORDER_FILE))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }
}
