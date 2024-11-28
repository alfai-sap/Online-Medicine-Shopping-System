import java.util.ArrayList;

public class Admin {
    private int adminId;
    private String name;
    private String email;
    private String password;
    private ArrayList<Medicine> inventoryList; // Inventory list

    public Admin(int adminId, String name, String email, String password) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.inventoryList = new ArrayList<>(); // Initialize an empty inventory list
    }

    public ArrayList<Medicine> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(ArrayList<Medicine> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public void addMedicine(Medicine medicine) {
        inventoryList.add(medicine);
    }

    public void removeMedicine(int medicineId) {
        inventoryList.removeIf(medicine -> medicine.getMedicineId() == medicineId);
    }

    public ArrayList<Order> getOrders() {
        return null;
    }
}
