import java.io.Serializable;

public class Medicine implements Serializable{
    private int medicineId;
    private String name;
    private double price;
    private String description;
    private String components;
    private String sideEffects;
    private int stock;
    private String imagePath;
    
    public String toCSV() {
        return String.format("%d,%s,%.2f,%s,%s,%s,%d,%s", 
            medicineId, 
            name.replace(",", "&#44;"), // Escape commas in the data
            price, 
            description.replace(",", "&#44;"), 
            components.replace(",", "&#44;"), 
            sideEffects.replace(",", "&#44;"), 
            stock, 
            imagePath.replace(",", "&#44;")
        );
    }

    // Static method to create Medicine from CSV
    public static Medicine fromCSV(String csv) {
        String[] parts = csv.split(",");
        if (parts.length == 8) {
            // Unescape commas
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

    public String getDetails() {
        return "Name: " + name +
               "\nPrice: PHP" + price +
               "\nDescription: " + description +
               "\nComponents: " + components +
               "\nSide Effects: " + sideEffects +
               "\nStock: " + stock +
               "\nImage Path: " + imagePath;
    }

    public boolean isAvailable(int quantity) {
        return stock >= quantity;
    }

    public void updateStock(int quantity) {
        stock += quantity;
    }

    // Getters
    public int getMedicineId() {return medicineId;}
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getImagePath() { return imagePath; }
    public String getDescription() {return description;}
    // Setters
    public void setPrice(double price) {
        this.price = price;
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
