import java.util.ArrayList;

public class Admin extends User {
    private Inventory inventory;

    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.inventory = new Inventory();
    }

    public void addMedicine(Medicine medicine) {
        inventory.addMedicine(medicine);
    }

    public void updateMedicine(Medicine medicine) {
        inventory.updateStock(medicine.getMedicineId(), medicine.getStock());
    }

    public void viewAllOrders(ArrayList<Order> orders) {
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public void manageStock() {
        inventory.displayInventory();
    }
}
