import java.util.ArrayList;

public class Customer extends User {
    private Cart cart;
    private ArrayList<Order> orderHistory;

    public Customer(int userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
    }

    
    public void addToCart(Medicine medicine) {
        cart.addMedicine(medicine);
    }

    public void removeFromCart(Medicine medicine) {
        cart.removeMedicine(medicine);
    }

    public Order checkout() {
        double totalAmount = cart.calculateTotalPrice();
        Order newOrder = new Order(orderHistory.size() + 1, this, cart, totalAmount, "Placed");
        orderHistory.add(newOrder);
        cart.clearCart();
        return newOrder;
    }

    public void viewOrderHistory() {
        for (Order order : orderHistory) {
            System.out.println(order);
        }
    }
}
