public class Medicine {
    private int medicineId;
    private String name;
    private double price;
    private String components;
    private String sideEffects;
    private int stock;

    // Constructor
    public Medicine(int medicineId, String name, double price, String components, String sideEffects, int stock) {
        this.medicineId = medicineId;
        this.name = name;
        this.price = price;
        this.components = components;
        this.sideEffects = sideEffects;
        this.stock = stock;
    }

    // Getters
    public int getStock() {
        return stock;  // Returns stock quantity as int
    }

    public double getPrice() {
        return price;  // Returns price as double
    }

    public String getDetails() {
        return name + " - Price: " + price + " Stock: " + stock;
    }

    public int getMedicineId() {
        return 0;
    }

    public void updateStock(int quantity) {

    }
}
