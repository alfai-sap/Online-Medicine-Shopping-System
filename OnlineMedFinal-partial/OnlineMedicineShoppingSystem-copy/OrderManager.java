import java.io.*;
import java.util.ArrayList;

public class OrderManager {
    private static final String CENTRAL_ORDER_FILE = "all_orders.txt";

    public static ArrayList<Order> loadAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        File centralFile = new File(CENTRAL_ORDER_FILE);

        if (!centralFile.exists()) {
            return orders;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(centralFile))) {
            while (true) {
                try {
                    Order order = (Order) ois.readObject();
                    orders.add(order);
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading all orders: " + e.getMessage());
        }

        return orders;
    }
}
