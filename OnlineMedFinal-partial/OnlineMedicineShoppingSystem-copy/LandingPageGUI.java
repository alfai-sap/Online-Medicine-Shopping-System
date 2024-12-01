import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LandingPageGUI {
    private JPanel panel;
    private JPanel medicinesPanel; // Declare medicinesPanel as a class variable
    private Inventory inventory;
    private Cart cart;

    public LandingPageGUI(Inventory inventory, Cart cart) {
        this.inventory = Inventory.getInstance();
        this.inventory = inventory;
        this.cart = cart;
        
        panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Browse Medicines", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Medicines Panel
        JPanel medicinesPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Dynamic grid layout
        updateMedicinesPanel(medicinesPanel, inventory, cart);

        JScrollPane scrollPane = new JScrollPane(medicinesPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(e -> new CartDetailsGUI(cart, inventory));
        bottomPanel.add(viewCartButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        bottomPanel.add(logoutButton);
        
        JButton viewOrderHistoryButton = new JButton("View Order History");
        viewOrderHistoryButton.addActionListener(e -> {
            User user = cart.getUser();
            if (user == null) {
                JOptionPane.showMessageDialog(null, "No user associated with the cart.");
                return;
            }
        
            if (user instanceof Customer) {
                new OrderHistoryGUI((Customer) user);
            } else {
                JOptionPane.showMessageDialog(null, "Only customers can view order history.");
            }
        });


        bottomPanel.add(viewOrderHistoryButton);


        panel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void refresh() {
        updateMedicinesPanel(medicinesPanel, inventory, cart);
    }

    private void updateMedicinesPanel(JPanel medicinesPanel, Inventory inventory, Cart cart) {
        medicinesPanel.removeAll(); // Clear previous content
        ArrayList<Medicine> medicines = inventory.getMedicineList();
    
        for (Medicine medicine : medicines) {
            // Medicine Card
            JPanel medicineCard = new JPanel(new BorderLayout());
            JLabel imageLabel = new JLabel();
            if (medicine.getImagePath() != null && !medicine.getImagePath().isEmpty()) {
                ImageIcon icon = new ImageIcon(new ImageIcon(medicine.getImagePath())
                        .getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
            } else {
                imageLabel.setText("No Image");
            }
            medicineCard.add(imageLabel, BorderLayout.NORTH);
    
            // Medicine Info
            JLabel nameLabel = new JLabel(medicine.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            JLabel priceLabel = new JLabel("Price: $" + medicine.getPrice());
            JLabel stockLabel = new JLabel("Stock: " + medicine.getStock());
    
            JPanel infoPanel = new JPanel(new GridLayout(3, 1));
            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(stockLabel);
            medicineCard.add(infoPanel, BorderLayout.CENTER);
    
            // Buttons Panel
            JPanel buttonPanel = new JPanel(new FlowLayout());
            
            // Add to Cart Button
            JButton addToCartButton = new JButton("Add to Cart");
            addToCartButton.addActionListener(e -> {
                if (medicine.getStock() > 0) {
                    cart.addMedicine(medicine, 1);
                    medicine.updateStock(-1);
                    inventory.saveInventoryToFile();
                    refresh(); // Update the display
                } else {
                    JOptionPane.showMessageDialog(null, "Out of stock!");
                }
            });
            buttonPanel.add(addToCartButton);
    
            // View Details Button
            JButton viewDetailsButton = new JButton("View Details");
            viewDetailsButton.addActionListener(e -> new MedicineDetailsGUI(medicine, cart));
            buttonPanel.add(viewDetailsButton);
    
            medicineCard.add(buttonPanel, BorderLayout.SOUTH);
            medicinesPanel.add(medicineCard);
        }
    
        medicinesPanel.revalidate();
        medicinesPanel.repaint();
    }

    // Logout function
    private void logout() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.getContentPane().removeAll();
        new MainGUI();
        frame.dispose();
    }

    public JPanel getPanel() {
        return panel;
    }
}
