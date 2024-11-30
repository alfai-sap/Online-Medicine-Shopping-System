import java.util.ArrayList;

public class Customer extends User {
    private Cart cart;
    private User user;
    private ArrayList<Order> orderHistory;

    public Customer(int userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.cart = new Cart(this);
        this.orderHistory = new ArrayList<>();
    }

    
    public void addToCart(Medicine medicine) {
        cart.addMedicine(medicine , 1);
    }

    public void removeFromCart(Medicine medicine) {
        cart.removeMedicine(medicine);
    }

    public Order checkout() {
        // Create a new order
        Order newOrder = new Order(orderHistory.size() + 1, this, cart, "Placed");
    
        // Add the order to history
        orderHistory.add(newOrder);
    
        // Clear the cart
        cart.clearCart(); // This should reset the medicines and quantities lists
    
        return newOrder;
    }




        public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

}
