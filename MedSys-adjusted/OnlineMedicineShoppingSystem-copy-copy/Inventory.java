import java.io.*;
import java.util.ArrayList;

public class Inventory implements Serializable {
    private static Inventory instance; //
    private ArrayList<Medicine> medicineList;
    private static final String INVENTORY_FILE = "inventory.txt";
    
    public Inventory() {
        this.medicineList = new ArrayList<>();
        loadInventoryFromFile();
    }

    //Singleton getInstance method
    public static synchronized Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }//
    public ArrayList<Medicine> getMedicineList() {
        return medicineList;
    }

    public void addMedicine(Medicine medicine) {
        medicineList.add(medicine);
        saveInventoryToFile();
    }
    
    public void saveInventoryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            for (Medicine medicine : medicineList) {
                writer.println(medicine.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }
    
    public void loadInventoryFromFile() {
        medicineList.clear(); // Clear existing list before loading
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Medicine medicine = Medicine.fromCSV(line);
                    medicineList.add(medicine);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing medicine: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing inventory file found. Starting with empty inventory.");
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
        }
    }

    public void updateStock(int medicineId, int quantity) {
        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineId() == medicineId) {
                medicine.updateStock(quantity);
                saveInventoryToFile(); // Save after stock update
                return;
            }
        }
        System.out.println("Medicine not found.");
    }

    public String displayInventory() {
        StringBuilder inventoryDetails = new StringBuilder();
        for (Medicine medicine : medicineList) {
            inventoryDetails.append(medicine.getDetails()).append("\n\n");
        }
        return inventoryDetails.toString();
    }

    public Medicine getMedicineByName(String name) {
        for (Medicine medicine : medicineList) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }
}
