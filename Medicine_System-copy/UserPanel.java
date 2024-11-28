import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserPanel {
    private JPanel panel;
    private DefaultListModel<Product> productListModel;
    private DefaultListModel<String> cartListModel;
    private List<String> cart;

    public UserPanel(User user) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Welcome message with user's name (if user is not null)
        String welcomeMessage = user != null ? "Welcome, " + user.getName() + "!" : "Welcome, Guest!";
        JLabel welcomeLabel = new JLabel(welcomeMessage, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Split panel for products and cart
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createProductPanel(), createCartPanel());
        splitPane.setDividerLocation(400);
        panel.add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createProductPanel() {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());
        productPanel.setBorder(BorderFactory.createTitledBorder("Products"));

        // Product List
        productListModel = new DefaultListModel<>();
        populateProductList();
        
        JList<Product> productList = new JList<>(productListModel);
        productList.setCellRenderer(new ProductRenderer()); // Custom renderer to display product image and name
        productPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        // Add to Cart Button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> {
            Product selectedProduct = productList.getSelectedValue();
            if (selectedProduct != null) {
                cart.add(selectedProduct.getName());
                cartListModel.addElement(selectedProduct.getName());
                JOptionPane.showMessageDialog(panel, selectedProduct.getName() + " added to cart!");
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a product to add to cart.");
            }
        });
        productPanel.add(addToCartButton, BorderLayout.SOUTH);

        return productPanel;
    }

    private JPanel createCartPanel() {
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Cart"));

        // Cart List
        cartListModel = new DefaultListModel<>();
        cart = new ArrayList<>();
        JList<String> cartList = new JList<>(cartListModel);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        // Remove from Cart Button
        JButton removeFromCartButton = new JButton("Remove from Cart");
        removeFromCartButton.addActionListener(e -> {
            String selectedProduct = cartList.getSelectedValue();
            if (selectedProduct != null) {
                cart.remove(selectedProduct);
                cartListModel.removeElement(selectedProduct);
                JOptionPane.showMessageDialog(panel, selectedProduct + " removed from cart!");
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a product to remove from cart.");
            }
        });
        cartPanel.add(removeFromCartButton, BorderLayout.NORTH);

        // Checkout Button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Your cart is empty!");
            } else {
                StringBuilder receipt = new StringBuilder("You have purchased:\n");
                for (String item : cart) {
                    receipt.append("- ").append(item).append("\n");
                }
                cart.clear();
                cartListModel.clear();
                JOptionPane.showMessageDialog(panel, receipt.toString());
            }
        });
        cartPanel.add(checkoutButton, BorderLayout.SOUTH);

        return cartPanel;
    }

    private void populateProductList() {
        // Example products with images
        productListModel.addElement(new Product("Product 1", "₱10.00", new ImageIcon("path_to_image/product1.jpg")));
        productListModel.addElement(new Product("Product 2", "₱20.00", new ImageIcon("path_to_image/product2.jpg")));
        productListModel.addElement(new Product("Product 3", "₱15.00", new ImageIcon("path_to_image/product3.jpg")));
        productListModel.addElement(new Product("Product 4", "₱25.00", new ImageIcon("path_to_image/product4.jpg")));
        productListModel.addElement(new Product("Product 5", "₱30.00", new ImageIcon("path_to_image/product5.jpg")));
    }

    public JPanel getPanel() {
        return panel;
    }

    // Custom renderer for displaying product images and names
    class ProductRenderer extends JPanel implements ListCellRenderer<Product> {
        private JLabel nameLabel;
        private JLabel priceLabel;
        private JLabel imageLabel;

        public ProductRenderer() {
            setLayout(new BorderLayout());
            nameLabel = new JLabel();
            priceLabel = new JLabel();
            imageLabel = new JLabel();
            add(imageLabel, BorderLayout.WEST);
            add(nameLabel, BorderLayout.CENTER);
            add(priceLabel, BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Product> list, Product value, int index, boolean isSelected, boolean cellHasFocus) {
            nameLabel.setText(value.getName());
            priceLabel.setText(value.getPrice());
            imageLabel.setIcon(value.getImage());

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }

    // Product class to store product information
    class Product {
        private String name;
        private String price;
        private ImageIcon image;

        public Product(String name, String price, ImageIcon image) {
            this.name = name;
            this.price = price;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public ImageIcon getImage() {
            return image;
        }
    }
}
