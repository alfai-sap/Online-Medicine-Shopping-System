import java.util.ArrayList; // For handling lists of orders.

/**
 * Represents an administrator with the ability to manage inventory and view orders.
 * Inherits from the User class.
 */
public class Admin extends User {
    private Inventory inventory; // The inventory managed by the admin.

    // Constructor
    /**
     * Initializes an Admin object with user details.
     *
     * @param userId    Unique identifier for the admin.
     * @param name      Name of the admin.
     * @param email     Email of the admin.
     * @param password  Password of the admin.
     */
    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password); // Call the constructor of the User class.
        this.inventory = new Inventory(); // Initialize the inventory for the admin.
    }

    // Inventory Management
    /**
     * Adds a new medicine to the inventory.
     *
     * @param medicine The medicine to add.
     */
    public void addMedicine(Medicine medicine) {
        inventory.addMedicine(medicine); // Add the medicine to the inventory.
    }

    /**
     * Updates the stock of an existing medicine in the inventory.
     *
     * @param medicine The medicine with updated stock information.
     */
    public void updateMedicine(Medicine medicine) {
        inventory.updateStock(medicine.getMedicineId(), medicine.getStock()); 
    }

    /**
     * Displays the current stock in the inventory.
     */
    public void manageStock() {
        inventory.displayInventory(); // Display the inventory details.
    }

    // Order Management
    /**
     * Views all orders placed by users.
     *
     * @param orders The list of all orders.
     */
    public void viewAllOrders(ArrayList<Order> orders) {
        for (Order order : orders) { // Iterate through the list of orders.
            System.out.println(order); // Print each order's details to the console.
        }
    }
}
