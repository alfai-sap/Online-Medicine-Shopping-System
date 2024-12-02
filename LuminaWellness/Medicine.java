import java.io.Serializable; // To allow the Medicine class to be serialized for file storage or network transmission.

/**
 * Represents a medicine with various details and functionality.
 * Implements Serializable for file storage and network transmission.
 */
public class Medicine implements Serializable {
    // Constants
    private static final long serialVersionUID = 1L;

    // Fields
    private int medicineId;       // Unique identifier for each medicine.
    private String name;          // Name of the medicine.
    private double price;         // Price of the medicine.
    private String description;   // Description of the medicine.
    private String components;    // Ingredients or components of the medicine.
    private String sideEffects;   // Possible side effects of the medicine.
    private int stock;            // Available quantity in stock.
    private String imagePath;     // Path to the image representing the medicine.

    // Constructor
    public Medicine(int medicineId, String name, double price, String description, String components, String sideEffects, int stock, String imagePath) {
        this.medicineId = medicineId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.components = components;
        this.sideEffects = sideEffects;
        this.stock = stock;
        this.imagePath = imagePath;
    }

    // CSV Conversion
    /**
     * Converts the medicine details to a CSV-formatted string.
     * Escapes commas to maintain CSV integrity.
     */
    public String toCSV() {
        return String.format("%d,%s,%.2f,%s,%s,%s,%d,%s",
                medicineId,
                name.replace(",", "&#44;"),
                price,
                description.replace(",", "&#44;"),
                components.replace(",", "&#44;"),
                sideEffects.replace(",", "&#44;"),
                stock,
                imagePath.replace(",", "&#44;"));
    }

    /**
     * Creates a Medicine object from a CSV-formatted string.
     * Unescapes commas in the fields.
     *
     * @param csv CSV string representing a medicine.
     * @return a new Medicine object.
     */
    public static Medicine fromCSV(String csv) {
        String[] parts = csv.split(",");
        if (parts.length == 8) {
            return new Medicine(
                    Integer.parseInt(parts[0]),
                    parts[1].replace("&#44;", ","),
                    Double.parseDouble(parts[2]),
                    parts[3].replace("&#44;", ","),
                    parts[4].replace("&#44;", ","),
                    parts[5].replace("&#44;", ","),
                    Integer.parseInt(parts[6]),
                    parts[7].replace("&#44;", ",")
            );
        }
        throw new IllegalArgumentException("Invalid CSV format for Medicine");
    }

    // Details and Availability
    /**
     * Returns a detailed string representation of the medicine.
     */
    public String getDetails() {
        return String.format("Name: %s\nPrice: PHP%.2f\nDescription: %s\nComponents: %s\nSide Effects: %s\nStock: %d\nImage Path: %s",
                name, price, description, components, sideEffects, stock, imagePath);
    }

    /**
     * Checks if the requested quantity is available in stock.
     *
     * @param quantity Quantity to check.
     * @return true if the stock is sufficient, false otherwise.
     */
    public boolean isAvailable(int quantity) {
        return stock >= quantity;
    }

    // Stock Management
    /**
     * Updates the stock by adding or subtracting the specified quantity.
     *
     * @param quantity Quantity to update (positive to add, negative to remove).
     */
    public void updateStock(int quantity) {
        this.stock += quantity;
    }

    /**
     * Reduces the stock by the specified quantity.
     *
     * @param quantity Quantity to remove.
     */
    public void removeStock(int quantity) {
        this.stock -= quantity;
    }

    // Getters
    public int getMedicineId() {
        return medicineId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
