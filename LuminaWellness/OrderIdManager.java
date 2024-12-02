// Importing the PrintWriter class, which is used to write formatted text to a file
import java.io.PrintWriter;

// Importing the BufferedReader class, which is used for reading text from a file efficiently
import java.io.BufferedReader;

// Importing the IOException class, which is used to handle input-output exceptions like file not found or errors during reading/writing
import java.io.IOException;

// Importing the FileReader class, which is used to read characters from a file
import java.io.FileReader;

// Importing the FileWriter class, which is used to write characters to a file
import java.io.FileWriter;

public class OrderIdManager {
    // Constant to define the file name where the order ID counter is stored
    private static final String COUNTER_FILE = "order_id_counter.txt";

    // Method to load the order ID counter from the file
    public static int loadCounter() {
        // Using a try-with-resources block to automatically close resources after usage
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTER_FILE))) {
            // Reading the first line from the file and converting it into an integer (order ID counter)
            return Integer.parseInt(reader.readLine()); // If the file exists and has a valid number, return it
        } catch (IOException | NumberFormatException e) {
            // If there is an error reading the file (IOException) or invalid format (NumberFormatException),
            // return 0 as the default order ID counter
            return 0;
        }
    }

    // Method to save the updated order ID counter to the file
    public static void saveCounter(int counter) {
        // Using try-with-resources to open the file for writing and automatically close it after writing
        try (PrintWriter writer = new PrintWriter(new FileWriter(COUNTER_FILE))) {
            // Writing the updated counter value to the file
            writer.println(counter); 
        } catch (IOException e) {
            // If an error occurs while writing to the file, print an error message
            System.err.println("Error saving order ID counter: " + e.getMessage());
        }
    }
}
