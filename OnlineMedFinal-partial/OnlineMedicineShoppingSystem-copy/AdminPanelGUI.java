import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;


public class AdminPanelGUI {
    private JFrame frame;
    private Inventory inventory;
    private JTextArea inventoryDetails;
    private LandingPageGUI landingPageGUI; // Reference to LandingPageGUI
    private UserManager userManager;
    
    public AdminPanelGUI(Inventory inventory , LandingPageGUI landingPageGUI) {
        this.inventory = Inventory.getInstance(); // Use singleton
        this.landingPageGUI = landingPageGUI; // Initialize the reference
        this.userManager = UserManager.getInstance(); // Use singleton
        // Set up the JFrame for Admin Panel
        frame = new JFrame("Admin Panel");
        frame.setSize(600, 500);  // Initial size (You can adjust width and height here)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TextArea to display inventory
        inventoryDetails = new JTextArea(inventory.displayInventory());
        inventoryDetails.setEditable(false);

        // Buttons for various actions
        JButton addMedicineButton = new JButton("Add Medicine");
        addMedicineButton.addActionListener(e -> addMedicineDialog());

        JButton updateMedicineButton = new JButton("Update Medicine");
        updateMedicineButton.addActionListener(e -> updateMedicineDialog());

        JButton deleteMedicineButton = new JButton("Delete Medicine");
        deleteMedicineButton.addActionListener(e -> deleteMedicineDialog());
        
        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.addActionListener(e -> manageUsersDialog());
        
        // New Button to view orders
        JButton viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.addActionListener(e -> viewOrdersDialog()); // Opens the orders list dialog


        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        
        

        // Panel to hold the buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addMedicineButton);
        buttonPanel.add(updateMedicineButton);
        buttonPanel.add(deleteMedicineButton);
        buttonPanel.add(logoutButton); // Add Logout Button
        buttonPanel.add(manageUsersButton);
        buttonPanel.add(viewOrdersButton);  

        // Layout the panel with the inventory details in the center and buttons at the bottom
        frame.add(new JScrollPane(inventoryDetails), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Make the window visible
        frame.setVisible(true);
    }
    
    
    
    // Method to view orders dialog
    private void viewOrdersDialog() {
        JDialog dialog = new JDialog(frame, "Orders List", true);
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());

        // Create a table model to display orders
        String[] columnNames = {"Order ID", "Customer Name", "Order Date", "Status", "Total Amount"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate the table with order data
        List<User> users = userManager.getAllUsers(); // Works with List<User>
        for (User user : users) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                for (Order order : customer.getOrderHistory()) {
                    Object[] rowData = {
                        order.getOrderId(),
                        customer.getName(),
                        order.getOrderDate(),
                        order.getStatus(),
                        String.format("PHP %.2f", order.getTotalAmount())
                    };
                    tableModel.addRow(rowData);
                }
            }
        }

        // Create the table
        JTable orderTable = new JTable(tableModel);

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(orderTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Close button at the bottom
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    
    private void manageUsersDialog() {
        JDialog dialog = new JDialog(frame, "Manage Users", true);
        dialog.setSize(600, 500);
        dialog.setLayout(new BorderLayout());
    
        // Create a table model to display user details
        String[] columnNames = {"User ID", "Name", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    
        // Populate the table with user data
        // Assumes you have a method in your User management system to get all users
        for (User user : userManager.getAllUsers()) { 
        Object[] rowData = {
            user.getId(),
            user.getName(),
            user.getEmail()
        };
        tableModel.addRow(rowData);
    }
    
        // Create the table
        JTable userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        // Buttons for user management
        JPanel buttonPanel = new JPanel();
        JButton deleteUserButton = new JButton("Delete User");
        JButton closeButton = new JButton("Close");
    
        deleteUserButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please select a user to delete.", 
                    "No User Selected", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            String userId = tableModel.getValueAt(selectedRow, 0).toString();
            
            // Confirm deletion
            int confirmDelete = JOptionPane.showConfirmDialog(
                dialog, 
                "Are you sure you want to delete this user?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION
            );
    
            if (confirmDelete == JOptionPane.YES_OPTION) {
                // Assumes a method in UserManager to delete a user by ID
                userManager.deleteUser(userId);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(dialog, "User deleted successfully.");
            }
        });
    
        closeButton.addActionListener(e -> dialog.dispose());
    
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(closeButton);
    
        // Combine table and buttons
        dialog.add(new JScrollPane(userTable), BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    // Logout function to redirect to the login panel
    private void logout() {
        frame.getContentPane().removeAll();  // Clear current panel
        MainGUI mainGui = new MainGUI();     // Redirect to MainGUI (Login Panel)
        frame.dispose();                    // Close the current AdminPanelGUI
    }

    private void refreshInventoryDisplay() {
        inventoryDetails.setText(inventory.displayInventory());
        landingPageGUI.refresh(); // Refresh the landing page UI
    }

    private void addMedicineDialog() {
    JDialog dialog = new JDialog(frame, "Add New Medicine", true);
    dialog.setSize(600, 500);
    dialog.setLayout(new BorderLayout());

    // Input fields
    JTextField nameField = new JTextField(20);
    JTextField priceField = new JTextField(20);
    JTextField stockField = new JTextField(20);
    JTextField descriptionField = new JTextField(20);
    JTextField componentsField = new JTextField(20);
    JTextField sideEffectsField = new JTextField(20);

    // JFileChooser to select image
    JButton imageButton = new JButton("Select Image");
    JTextField imagePathField = new JTextField(20);
    imagePathField.setEditable(false);

    JLabel imageLabel = new JLabel();

    // Action for selecting an image file
    imageButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Medicine Image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(dialog);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePathField.setText(selectedFile.getAbsolutePath());

            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        }
    });

    // Set up GridBagLayout for the dialog form
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Name
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Name:"), gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    // Price
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("Price:"), gbc);
    gbc.gridx = 1;
    panel.add(priceField, gbc);

    // Stock
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("Stock:"), gbc);
    gbc.gridx = 1;
    panel.add(stockField, gbc);

    // Description
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel("Description:"), gbc);
    gbc.gridx = 1;
    panel.add(descriptionField, gbc);

    // Components
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(new JLabel("Components:"), gbc);
    gbc.gridx = 1;
    panel.add(componentsField, gbc);

    // Side Effects
    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(new JLabel("Side Effects:"), gbc);
    gbc.gridx = 1;
    panel.add(sideEffectsField, gbc);

    // Image Path
    gbc.gridx = 0;
    gbc.gridy = 6;
    panel.add(new JLabel("Image Path:"), gbc);
    gbc.gridx = 1;
    panel.add(imagePathField, gbc);

    // Image Button
    gbc.gridx = 0;
    gbc.gridy = 7;
    panel.add(imageButton, gbc);

    // Image Preview
    gbc.gridx = 0;
    gbc.gridy = 8;
    panel.add(new JLabel("Image Preview:"), gbc);
    gbc.gridx = 1;
    panel.add(imageLabel, gbc);

    // Buttons panel
    JPanel buttonPanel = new JPanel();
    JButton addButton = new JButton("Add Medicine");
    JButton cancelButton = new JButton("Cancel");

    addButton.addActionListener(e -> {
        // Validation logic
        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();
        String stockText = stockField.getText().trim();
        String description = descriptionField.getText().trim();
        String components = componentsField.getText().trim();
        String sideEffects = sideEffectsField.getText().trim();
        String imagePath = imagePathField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please enter a valid name.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price = 0;
        int stock = 0;

        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) {
                JOptionPane.showMessageDialog(dialog, "Price must be a positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Invalid price. Please enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            stock = Integer.parseInt(stockText);
            if (stock < 0) {
                JOptionPane.showMessageDialog(dialog, "Stock cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Invalid stock. Please enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (imagePath.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please select an image.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If all validations pass
        int medicineId = inventory.getMedicineList().size() + 1;
        Medicine newMedicine = new Medicine(medicineId, name, price, description, components, sideEffects, stock, imagePath);
        inventory.addMedicine(newMedicine);
        refreshInventoryDisplay();
        JOptionPane.showMessageDialog(dialog, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
    });

    cancelButton.addActionListener(e -> dialog.dispose());

    buttonPanel.add(addButton);
    buttonPanel.add(cancelButton);

    // Add components to dialog
    dialog.add(new JScrollPane(panel), BorderLayout.CENTER);
    dialog.add(buttonPanel, BorderLayout.SOUTH);

    dialog.setLocationRelativeTo(frame);
    dialog.setVisible(true);
}

    private void updateMedicineDialog() {
    JDialog dialog = new JDialog(frame, "Update Medicine", true);
    dialog.setSize(600, 500);
    dialog.setLayout(new BorderLayout());

    // Input fields
    JTextField nameField = new JTextField(20);
    JTextField priceField = new JTextField(20);
    JTextField stockField = new JTextField(20);
    JTextField descriptionField = new JTextField(20);
    JTextField componentsField = new JTextField(20);
    JTextField sideEffectsField = new JTextField(20);
    JTextField imagePathField = new JTextField(20);

    // JFileChooser to select image
    JButton imageButton = new JButton("Select Image");
    imagePathField.setEditable(false);

    JLabel imageLabel = new JLabel();

    // Action for selecting an image file
    imageButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Medicine Image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(dialog);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePathField.setText(selectedFile.getAbsolutePath());

            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        }
    });

    // Set up GridBagLayout for the dialog form
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Name (to find medicine to update)
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Medicine Name to Update:"), gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    // Price (optional update)
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("New Price (optional):"), gbc);
    gbc.gridx = 1;
    panel.add(priceField, gbc);

    // Stock (optional update)
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("New Stock (optional):"), gbc);
    gbc.gridx = 1;
    panel.add(stockField, gbc);

    // Description (optional update)
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel("New Description (optional):"), gbc);
    gbc.gridx = 1;
    panel.add(descriptionField, gbc);

    // Components (optional update)
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(new JLabel("New Components (optional):"), gbc);
    gbc.gridx = 1;
    panel.add(componentsField, gbc);

    // Side Effects (optional update)
    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(new JLabel("New Side Effects (optional):"), gbc);
    gbc.gridx = 1;
    panel.add(sideEffectsField, gbc);

    // Image Path (optional update)
    gbc.gridx = 0;
    gbc.gridy = 6;
    panel.add(new JLabel("New Image Path (optional):"), gbc);
    gbc.gridx = 1;
    panel.add(imagePathField, gbc);

    // Image Button
    gbc.gridx = 0;
    gbc.gridy = 7;
    panel.add(imageButton, gbc);

    // Image Preview
    gbc.gridx = 0;
    gbc.gridy = 8;
    panel.add(new JLabel("Image Preview:"), gbc);
    gbc.gridx = 1;
    panel.add(imageLabel, gbc);

    // Buttons panel
    JPanel buttonPanel = new JPanel();
    JButton updateButton = new JButton("Update Medicine");
    JButton cancelButton = new JButton("Cancel");

    updateButton.addActionListener(e -> {
        // Validation logic
        String name = nameField.getText().trim();
        Medicine medicine = inventory.getMedicineByName(name);

        if (medicine == null) {
            JOptionPane.showMessageDialog(dialog, "Medicine not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation inputs
        double price = 0;
        int stock = 0;

        if (!priceField.getText().isEmpty()) {
            try {
                price = Double.parseDouble(priceField.getText());
                if (price <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Price must be a positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid price. Please enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (!stockField.getText().isEmpty()) {
            try {
                stock = Integer.parseInt(stockField.getText());
                if (stock < 0) {
                    JOptionPane.showMessageDialog(dialog, "Stock cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid stock. Please enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Update medicine fields if they are not empty
        if (!priceField.getText().isEmpty()) {
            medicine.setPrice(price);
        }
        if (!stockField.getText().isEmpty()) {
            medicine.updateStock(stock);
        }
        if (!descriptionField.getText().isEmpty()) {
            medicine.setDescription(descriptionField.getText());
        }
        if (!componentsField.getText().isEmpty()) {
            medicine.setComponents(componentsField.getText());
        }
        if (!sideEffectsField.getText().isEmpty()) {
            medicine.setSideEffects(sideEffectsField.getText());
        }
        if (!imagePathField.getText().isEmpty()) {
            medicine.setImagePath(imagePathField.getText());
        }

        inventory.saveInventoryToFile();
        refreshInventoryDisplay();
        JOptionPane.showMessageDialog(dialog, "Medicine updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
    });

    cancelButton.addActionListener(e -> dialog.dispose());

    buttonPanel.add(updateButton);
    buttonPanel.add(cancelButton);

    // Add components to dialog
    dialog.add(new JScrollPane(panel), BorderLayout.CENTER);
    dialog.add(buttonPanel, BorderLayout.SOUTH);

    dialog.setLocationRelativeTo(frame);
    dialog.setVisible(true);
}

    // Dialog for deleting medicine by name
    private void deleteMedicineDialog() {
        JTextField nameField = new JTextField();

        Object[] message = {
            "Enter the name of the medicine to delete:", nameField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Delete Medicine", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            Medicine medicine = inventory.getMedicineByName(name);

            if (medicine == null) {
                JOptionPane.showMessageDialog(frame, "Medicine not found.");
                return;
            }

            inventory.getMedicineList().remove(medicine);
            inventory.saveInventoryToFile(); // Explicitly save after deletion
            refreshInventoryDisplay();
            JOptionPane.showMessageDialog(frame, "Medicine deleted successfully!");
        }
        
        refreshInventoryDisplay();
    }
}
