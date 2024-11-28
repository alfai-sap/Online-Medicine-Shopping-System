public class Medicine {
    private int medicineId;
    private String name;
    private double price;
    private String components;
    private String sideEffects;
    private int stock;
    private String imagePath; // New attribute for storing the image path

    public Medicine(int medicineId, String name, double price, String components, String sideEffects, int stock, String imagePath) {
        this.medicineId = medicineId;
        this.name = name;
        this.price = price;
        this.components = components;
        this.sideEffects = sideEffects;
        this.stock = stock;
        this.imagePath = imagePath; // Initialize the image path
    }

    public double getPrice() { // Add this method to return the price
        return price;
    }
    
    public int getMedicineId() {
        return medicineId;
    }

    public String getDetails() {
        return "ID: " + medicineId + ", Name: " + name + ", Price: $" + price + 
               ", Components: " + components + ", Side Effects: " + sideEffects + 
               ", Stock: " + stock + ", Image Path: " + imagePath;
    }

    public boolean isAvailable() {
        return stock > 0;
    }

    public void updateStock(int quantity) {
        stock += quantity;
    }

    public int getStock() {
        return stock;
    }

    public String getImagePath() {
        return imagePath;
    }
    
    // Add this method
    public String getName() {
        return name;
    }
    
    
}
