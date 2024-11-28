import java.util.ArrayList;

public class Cart {
    private ArrayList<Medicine> medicines;
    private double discountPercentage;

    public Cart() {
        this.medicines = new ArrayList<>();
        this.discountPercentage = 0; // Default no discount
    }

    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
    }

    public void removeMedicine(Medicine medicine) {
        medicines.remove(medicine);
    }

    // Calculate the total number of items in the cart
    public int calculateItemCount() {
        return medicines.size(); // Returns the total number of medicines
    }

    // Calculate the total price of all items in the cart
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Medicine medicine : medicines) {
            totalPrice += medicine.getPrice(); // Add the price of each medicine
        }
        return totalPrice - (totalPrice * discountPercentage / 100);
    }

    public String displayCart() {
        StringBuilder cartDetails = new StringBuilder();
        for (Medicine medicine : medicines) {
            cartDetails.append(medicine.getDetails()).append("\n");
        }
        return cartDetails.toString();
    }

    public void clearCart() {
        medicines.clear();
    }
    
    // Apply a discount to the cart
    public void applyDiscount(double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            System.out.println("Invalid discount percentage.");
        } else {
            this.discountPercentage = discountPercentage;
        }
    }

    // Get current discount
    public double getDiscountPercentage() {
        return discountPercentage;
    }
}