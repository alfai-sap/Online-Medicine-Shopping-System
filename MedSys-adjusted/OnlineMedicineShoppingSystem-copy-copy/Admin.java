import java.util.ArrayList; // Import ArrayList for handling lists of orders.

public class Admin extends User { // The Admin class inherits from the User class.
    private Inventory inventory; // The inventory that the admin can manage.

    // Constructor to initialize an Admin object with its details.
    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password); // Call the constructor of the User class to set user details.
        this.inventory = new Inventory(); // Initialize the inventory for the admin.
    }

    // Method to add a new medicine to the inventory.
    public void addMedicine(Medicine medicine) {
        inventory.addMedicine(medicine); // Add the specified medicine to the inventory.
    }

    // Method to update the stock of an existing medicine in the inventory.
    public void updateMedicine(Medicine medicine) {
        inventory.updateStock(medicine.getMedicineId(), medicine.getStock()); 
        // Update the stock of the medicine in the inventory based on its ID.
    }

    // Method to view all orders made by users.
    public void viewAllOrders(ArrayList<Order> orders) {
        for (Order order : orders) { // Loop through the list of orders.
            System.out.println(order); // Print each order's details to the console.
        }
    }

    // Method to manage and display the current stock in the inventory.
    public void manageStock() {
        inventory.displayInventory(); // Display the inventory details.
    }
}
