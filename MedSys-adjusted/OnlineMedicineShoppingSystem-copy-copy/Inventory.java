import java.io.*;
import java.util.ArrayList;

public class Inventory implements Serializable {
    private static Inventory instance;  // Singleton instance of Inventory
    private ArrayList<Medicine> medicineList;  // List to store all medicines
    private static final String INVENTORY_FILE = "inventory.txt";  // File path to save/load inventory
    
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

    public ArrayList<Medicine> getMedicineList() {
        return medicineList;  // Return the list of medicines
    }

    public void addMedicine(Medicine medicine) {
        medicineList.add(medicine);  // Add a new medicine to the list
        saveInventoryToFile();  // Save the updated inventory to file
    }
    
    public void saveInventoryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            for (Medicine medicine : medicineList) {
                writer.println(medicine.toCSV());  // Save each medicine as a CSV string
            }
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());  // Handle error if saving fails
        }
    }
    
    public void loadInventoryFromFile() {
        medicineList.clear();  // Clear the current list before loading from file
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {  // Read each line (medicine data)
                try {
                    Medicine medicine = Medicine.fromCSV(line);  // Convert the CSV string to a Medicine object
                    medicineList.add(medicine);  // Add the medicine to the list
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing medicine: " + e.getMessage());  // Handle parsing errors
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing inventory file found. Starting with empty inventory.");  // If no file found, start with empty list
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());  // Handle I/O errors
        }
    }

    public void updateStock(int medicineId, int quantity) {
        for (Medicine medicine : medicineList) {  // Loop through the medicine list
            if (medicine.getMedicineId() == medicineId) {  // Check if the medicine matches the ID
                medicine.updateStock(quantity);  // Update the stock of the medicine
                saveInventoryToFile();  // Save the updated inventory to file
                return;
            }
        }
        System.out.println("Medicine not found.");  // If medicine not found by ID
    }

    public String displayInventory() {
        StringBuilder inventoryDetails = new StringBuilder();  // Create a StringBuilder for displaying inventory
        for (Medicine medicine : medicineList) {  // Loop through each medicine
            inventoryDetails.append(medicine.getDetails()).append("\n\n");  // Append each medicine's details to the string
        }
        return inventoryDetails.toString();  // Return the full inventory as a string
    }

    public Medicine getMedicineByName(String name) {
        for (Medicine medicine : medicineList) {  // Loop through the medicine list
            if (medicine.getName().equalsIgnoreCase(name)) {  // Check if the medicine matches the given name (case insensitive)
                return medicine;  // Return the found medicine
            }
        }
        return null;  // Return null if no medicine with that name is found
    }
}
