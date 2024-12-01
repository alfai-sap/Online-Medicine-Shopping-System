import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

public class OrderIdManager {
    private static final String COUNTER_FILE = "order_id_counter.txt";

    public static int loadCounter() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTER_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0; // Default to 0 if file not found or invalid
        }
    }

    public static void saveCounter(int counter) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COUNTER_FILE))) {
            writer.println(counter);
        } catch (IOException e) {
            System.err.println("Error saving order ID counter: " + e.getMessage());
        }
    }
}
