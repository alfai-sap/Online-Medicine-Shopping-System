import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminPanel {
    private JPanel panel;
    private DefaultListModel<String> productListModel;
    private ArrayList<String> productDatabase;

    public AdminPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Admin Dashboard"));

        // Product Management Section
        productListModel = new DefaultListModel<>();
        productDatabase = new ArrayList<>();
        populateProductDatabase();

        JList<String> productList = new JList<>(productListModel);
        panel.add(new JScrollPane(productList), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Add Product Button
        JButton addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(e -> {
            String newProduct = JOptionPane.showInputDialog(panel, "Enter new product:");
            if (newProduct != null && !newProduct.trim().isEmpty()) {
                productDatabase.add(newProduct);
                productListModel.addElement(newProduct);
                JOptionPane.showMessageDialog(panel, "Product added!");
            }
        });
        actionPanel.add(addProductButton);

        // Remove Product Button
        JButton removeProductButton = new JButton("Remove Selected Product");
        removeProductButton.addActionListener(e -> {
            String selectedProduct = productList.getSelectedValue();
            if (selectedProduct != null) {
                productDatabase.remove(selectedProduct);
                productListModel.removeElement(selectedProduct);
                JOptionPane.showMessageDialog(panel, "Product removed!");
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a product to remove.");
            }
        });
        actionPanel.add(removeProductButton);

        panel.add(actionPanel, BorderLayout.SOUTH);
    }

    private void populateProductDatabase() {
        // Example products
        productDatabase.add("Product 1 - ₱10.00");
        productDatabase.add("Product 2 - ₱20.00");
        productDatabase.add("Product 3 - ₱15.00");
        productDatabase.add("Product 4 - ₱25.00");
        productDatabase.add("Product 5 - ₱30.00");

        for (String product : productDatabase) {
            productListModel.addElement(product);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
