import java.util.ArrayList;

public class Cart {
    private final ArrayList<Medicine> medicines;

    public Cart() {
        this.medicines = new ArrayList<>();
    }

    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
    }

    public void removeMedicine(Medicine medicine) {
        medicines.remove(medicine);
    }

    public double calculateTotal() {
        double total = 0;
        for (Medicine medicine : medicines) {
            // Multiply the stock (quantity) by the price (unit price)
            total += medicine.getStock() * medicine.getPrice();
        }
        return total;
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
}
