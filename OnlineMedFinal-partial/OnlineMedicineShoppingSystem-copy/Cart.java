import java.util.ArrayList;
import java.io.Serializable;

public class Cart implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Medicine> medicines; // Stores the unique medicines in the cart
    private ArrayList<Integer> quantities; // Stores the corresponding quantities for each medicine
    private transient User user; // Mark non-serializable fields as transient
    private double discountPercentage;

    public Cart(User user) {
        this.medicines = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.user = user;
        this.discountPercentage = 0;   // Default no discount
    }
    
    public User getUser() { // Method to return the associated user
        return user;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public int getQuantity(Medicine medicine) {
        int index = medicines.indexOf(medicine);
        if (index != -1) {
            return quantities.get(index);
        }
        return 0;
    }

    public void addMedicine(Medicine medicine, int quantity) {
        int index = medicines.indexOf(medicine);

        if (index != -1) {
            // Medicine already exists in the cart, update its quantity
            quantities.set(index, quantities.get(index) + quantity);
        } else {
            // Add the medicine as a new entry
            medicines.add(medicine);
            quantities.add(quantity);
        }

        System.out.println(medicine.getName() + " added to the cart.");
        // For debugging purposes, print cart contents
        System.out.println("Current cart: " + displayCart());
    }

    public void removeMedicine(Medicine medicine) {
        int index = medicines.indexOf(medicine);
        if (index != -1) {
            medicines.remove(index);
            quantities.remove(index); // Remove corresponding quantity
            System.out.println(medicine.getName() + " removed from the cart.");
        }
    }

    public void clearCart() {
        medicines.clear();
        quantities.clear();
        System.out.println("Cart has been cleared. Items count: " + medicines.size());
    }



    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (int i = 0; i < medicines.size(); i++) {
            totalPrice += medicines.get(i).getPrice() * quantities.get(i);
        }
        return totalPrice - (totalPrice * discountPercentage / 100);
    }


    public String displayCart() {
        StringBuilder cartDetails = new StringBuilder();
        for (int i = 0; i < medicines.size(); i++) {
            Medicine medicine = medicines.get(i);
            cartDetails.append(medicine.getName())
                    .append(" x")
                    .append(quantities.get(i))
                    .append(" - PHP")
                    .append(medicine.getPrice() * quantities.get(i))
                    .append("\n");
        }
        cartDetails.append("\nTotal: PHP").append(calculateTotalPrice());
        return cartDetails.toString();
    }

    public void applyDiscount(double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            System.out.println("Invalid discount percentage.");
        } else {
            this.discountPercentage = discountPercentage;
        }
    }
}
