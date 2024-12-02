import java.io.*;
import java.util.ArrayList;

public class Inventory implements Serializable {

    // Singleton instance of Inventory
    private static Inventory instance;

    // List to store all medicines
    private ArrayList<Medicine> medicineList;

    // File path to save/load inventory
    private static final String INVENTORY_FILE = "inventory.txt";

    // Constructor initializes the medicine list and loads the inventory from file
    public Inventory() {
        this.medicineList = new ArrayList<>();  // Initialize the medicine list
        loadInventoryFromFile();  // Load existing inventory from the file
    }

    // Singleton getInstance method
    public static synchronized Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();  // Create a new instance if not already created
        }
        return instance;  // Return the existing instance of Inventory
    }

    // Getter for the list of medicines
    public ArrayList<Medicine> getMedicineList() {
        return medicineList;  // Return the list of medicines
    }

    // Add a new medicine to the inventory and save to file
    public void addMedicine(Medicine medicine) {
        medicineList.add(medicine);  // Add a new medicine to the list
        saveInventoryToFile();  // Save the updated inventory to file
    }

    // Save the current inventory to the file
    public void saveInventoryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            // Save each medicine as a CSV string
            for (Medicine medicine : medicineList) {
                writer.println(medicine.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());  // Handle error if saving fails
        }
    }

    // Load the inventory from the file and populate the medicine list
    public void loadInventoryFromFile() {
        medicineList.clear();  // Clear the current list before loading from file
        
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;

            // Read each line (medicine data)
            while ((line = reader.readLine()) != null) {
                try {
                    Medicine medicine = Medicine.fromCSV(line);  // Convert the CSV string to a Medicine object
                    medicineList.add(medicine);  // Add the medicine to the list
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing medicine: " + e.getMessage());  // Handle parsing errors
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing inventory file found. Starting with empty inventory.");  // Start with empty list if file not found
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());  // Handle I/O errors
        }
    }

    // Update the stock for a specific medicine identified by its ID
    public void updateStock(int medicineId, int quantity) {
        // Loop through the medicine list
        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineId() == medicineId) {  // Check if the medicine matches the ID
                medicine.updateStock(quantity);  // Update the stock of the medicine
                saveInventoryToFile();  // Save the updated inventory to file
                return;
            }
        }
        System.out.println("Medicine not found.");  // If medicine not found by ID
    }

    // Display the inventory details as a string
    public String displayInventory() {
        StringBuilder inventoryDetails = new StringBuilder();  // Create a StringBuilder for displaying inventory
        
        // Loop through each medicine
        for (Medicine medicine : medicineList) {
            inventoryDetails.append(medicine.getDetails()).append("\n\n");  // Append each medicine's details to the string
        }
        
        return inventoryDetails.toString();  // Return the full inventory as a string
    }

    // Retrieve a medicine by its name
    public Medicine getMedicineByName(String name) {
        // Loop through the medicine list
        for (Medicine medicine : medicineList) {
            if (medicine.getName().equalsIgnoreCase(name)) {  // Check if the medicine matches the given name (case insensitive)
                return medicine;  // Return the found medicine
            }
        }
        return null;  // Return null if no medicine with that name is found
    }
}
