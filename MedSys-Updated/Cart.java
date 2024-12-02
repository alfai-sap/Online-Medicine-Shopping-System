import java.util.ArrayList;
import java.io.Serializable;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;  // Ensures that the class can be serialized and deserialized properly
    
    private ArrayList<Medicine> medicines;  // List to store medicines added to the cart
    private ArrayList<Integer> quantities;  // List to store the quantity of each corresponding medicine
    private transient User user;  // The user associated with this cart. Marked transient as it should not be serialized
    private double discountPercentage;  // Discount applied to the total price

    public Cart(User user) {
        this.medicines = new ArrayList<>();  // Initialize the list of medicines
        this.quantities = new ArrayList<>();  // Initialize the list of quantities
        this.user = user;  // Associate the user with this cart
        this.discountPercentage = 0;  // Default no discount
    }
    
    public User getUser() {  // Method to return the associated user
        return user;
    }

    public ArrayList<Medicine> getMedicines() {  // Method to return the list of medicines in the cart
        return medicines;
    }

    public int getQuantity(Medicine medicine) {  // Method to get the quantity of a specific medicine in the cart
        int index = medicines.indexOf(medicine);  // Find the index of the medicine in the cart
        if (index != -1) {
            return quantities.get(index);  // Return the quantity at the found index
        }
        return 0;  // Return 0 if the medicine is not found in the cart
    }

    public void addMedicine(Medicine medicine, int quantity) {  // Method to add a medicine to the cart
        int index = medicines.indexOf(medicine);  // Check if the medicine is already in the cart

        if (index != -1) {  // If medicine already exists in the cart
            quantities.set(index, quantities.get(index) + quantity);  // Update the quantity of the existing medicine
        } else {
            medicines.add(medicine);  // If medicine is not in the cart, add it
            quantities.add(quantity);  // Add the corresponding quantity
        }

        System.out.println(medicine.getName() + " added to the cart.");  // Debugging output
        System.out.println("Current cart: " + displayCart());  // Print the updated cart contents
    }

    public void removeMedicine(Medicine medicine) {  // Method to remove a medicine from the cart
        int index = medicines.indexOf(medicine);  // Find the index of the medicine in the cart
        if (index != -1) {  // If medicine is found
            medicines.remove(index);  // Remove the medicine from the cart
            quantities.remove(index);  // Remove the corresponding quantity
            System.out.println(medicine.getName() + " removed from the cart.");  // Debugging output
        }
    }

    public void clearCart() {  // Method to clear all items from the cart
        medicines.clear();  // Clear the list of medicines
        quantities.clear();  // Clear the list of quantities
        System.out.println("Cart has been cleared. Items count: " + medicines.size());  // Debugging output
    }

    public double calculateTotalPrice() {  // Method to calculate the total price of items in the cart
        double totalPrice = 0.0;  // Initialize total price to 0
        for (int i = 0; i < medicines.size(); i++) {  // Loop through each medicine in the cart
            totalPrice += medicines.get(i).getPrice() * quantities.get(i);  // Add the price for each medicine multiplied by its quantity
        }
        return totalPrice - (totalPrice * discountPercentage / 100);  // Apply the discount and return the total price
    }

    public String displayCart() {  // Method to generate a string displaying the contents of the cart
        StringBuilder cartDetails = new StringBuilder();  // Use StringBuilder for efficient string concatenation
        for (int i = 0; i < medicines.size(); i++) {  // Loop through each medicine in the cart
            Medicine medicine = medicines.get(i);  // Get the medicine at the current index
            cartDetails.append(medicine.getName())  // Append the name of the medicine
                    .append(" x")  // Append "x" to indicate quantity
                    .append(quantities.get(i))  // Append the quantity of the medicine
                    .append(" - PHP")  // Append "PHP" to indicate the currency
                    .append(medicine.getPrice() * quantities.get(i))  // Append the total price for this medicine
                    .append("\n");  // New line after each medicine
        }
        cartDetails.append("\nTotal: PHP").append(calculateTotalPrice());  // Append the total price at the end
        return cartDetails.toString();  // Return the complete cart details as a string
    }

    public void applyDiscount(double discountPercentage) {  // Method to apply a discount to the cart
        if (discountPercentage < 0 || discountPercentage > 100) {  // Check if the discount percentage is valid
            System.out.println("Invalid discount percentage.");  // Print error message if invalid
        } else {
            this.discountPercentage = discountPercentage;  // Set the discount percentage if valid
        }
    }
}
