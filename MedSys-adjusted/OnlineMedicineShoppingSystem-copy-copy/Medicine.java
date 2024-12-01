import java.io.Serializable; // To allow the Medicine class to be serialized for file storage or network transmission.

public class Medicine implements Serializable { // Make the class Serializable to be able to save and retrieve objects.
    
    // Declare the fields (attributes) of the Medicine class.
    private int medicineId;  // Unique identifier for each medicine.
    private String name;  // The name of the medicine.
    private double price;  // The price of the medicine.
    private String description;  // Detailed description of the medicine.
    private String components;  // Ingredients or components of the medicine.
    private String sideEffects;  // Possible side effects of the medicine.
    private int stock;  // Available quantity of the medicine in stock.
    private String imagePath;  // Path to the image representing the medicine.

    // Method to convert the Medicine object into a CSV format for easy saving.
    public String toCSV() {
        // Return a CSV string with medicine details. Escape commas in the fields to avoid breaking the CSV format.
        return String.format("%d,%s,%.2f,%s,%s,%s,%d,%s", 
            medicineId, 
            name.replace(",", "&#44;"), // Replace commas with an escape sequence to prevent breaking CSV format.
            price, 
            description.replace(",", "&#44;"),  // Escape commas in description.
            components.replace(",", "&#44;"), // Escape commas in components.
            sideEffects.replace(",", "&#44;"), // Escape commas in side effects.
            stock, 
            imagePath.replace(",", "&#44;")  // Escape commas in image path.
        );
    }

    // Static method to create a Medicine object from a CSV string.
    public static Medicine fromCSV(String csv) {
        // Split the CSV string into individual parts.
        String[] parts = csv.split(",");
        if (parts.length == 8) {
            // If there are 8 parts (valid CSV format), create and return a Medicine object.
            return new Medicine(
                Integer.parseInt(parts[0]), // Parse medicineId.
                parts[1].replace("&#44;", ","), // Unescape commas.
                Double.parseDouble(parts[2]), // Parse price.
                parts[3].replace("&#44;", ","), // Unescape description.
                parts[4].replace("&#44;", ","), // Unescape components.
                parts[5].replace("&#44;", ","), // Unescape side effects.
                Integer.parseInt(parts[6]), // Parse stock.
                parts[7].replace("&#44;", ",") // Unescape imagePath.
            );
        }
        throw new IllegalArgumentException("Invalid CSV format for Medicine"); // Throw exception if the CSV format is invalid.
    }

    // Constructor to create a Medicine object with all details.
    public Medicine(int medicineId, String name, double price, String description, String components, String sideEffects, int stock, String imagePath) {
        this.medicineId = medicineId; // Set the unique medicineId.
        this.name = name; // Set the name of the medicine.
        this.price = price; // Set the price.
        this.description = description; // Set the description.
        this.components = components; // Set the components.
        this.sideEffects = sideEffects; // Set the side effects.
        this.stock = stock; // Set the stock (quantity available).
        this.imagePath = imagePath; // Set the path to the image representing the medicine.
    }

    // Method to return a detailed string description of the medicine.
    public String getDetails() {
        return "Name: " + name +
               "\nPrice: PHP" + price +
               "\nDescription: " + description +
               "\nComponents: " + components +
               "\nSide Effects: " + sideEffects +
               "\nStock: " + stock +
               "\nImage Path: " + imagePath;
    }

    // Check if the requested quantity of the medicine is available in stock.
    public boolean isAvailable(int quantity) {
        return stock >= quantity; // Return true if the stock is enough.
    }

    // Update the stock based on the quantity being added or removed.
    public void updateStock(int quantity) {
        stock += quantity; // Increase or decrease stock based on the input quantity.
    }

    // Getter methods for all the fields (attributes).
    public int getMedicineId() { return medicineId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getImagePath() { return imagePath; }
    public String getDescription() { return description; }

    // Setter methods for updating the fields.
    public void setPrice(double price) {
        this.price = price; // Set the price.
    }

    public void setDescription(String description) {
        this.description = description; // Set the description.
    }

    public void setComponents(String components) {
        this.components = components; // Set the components.
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects; // Set the side effects.
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath; // Set the image path.
    }
}
