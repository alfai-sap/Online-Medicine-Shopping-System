import javax.swing.*; // Importing Swing components for GUI.
import java.awt.*; // Importing AWT components for layout management.
import java.io.File; // For file handling.
import javax.swing.table.DefaultTableModel; // For creating tables in the GUI.
import java.util.ArrayList; // Importing ArrayList for storing lists of items.
import java.util.List; // Importing List for general list usage.

    public class AdminPanelGUI {
    
    private JFrame frame; // The main window of the admin panel.
    private Inventory inventory; // Reference to the inventory management system.
    private JTextArea inventoryDetails; // TextArea to display the inventory.
    private LandingPageGUI landingPageGUI; // Reference to the landing page GUI.
    private UserManager userManager; // Reference to the user management system.
    private JFrame adminFrame;
    

    
    public AdminPanelGUI(Inventory inventory , LandingPageGUI landingPageGUI) {
        // Constructor for initializing the Admin Panel.
        this.inventory = Inventory.getInstance(); // Fetching singleton instance of Inventory.
        this.landingPageGUI = landingPageGUI; // Initializing reference to LandingPageGUI.
        this.userManager = UserManager.getInstance(); // Fetching singleton instance of UserManager.

        
        frame = new JFrame("Admin Panel"); // Creating the JFrame for the Admin Panel.
        frame.setSize(600, 500);  // Setting the initial size of the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closing the window when the user clicks on close.


        // TextArea to display inventory
        inventoryDetails = new JTextArea(inventory.displayInventory());
        inventoryDetails.setEditable(false);

        // Adding buttons for various functionalities.
        JButton addMedicineButton = new JButton("Add Medicine");
        addMedicineButton.addActionListener(e -> addMedicineDialog()); // ActionListener for adding a new medicine.

        JButton updateMedicineButton = new JButton("Update Medicine");
        updateMedicineButton.addActionListener(e -> updateMedicineDialog()); // ActionListener for updating existing medicine.

        JButton deleteMedicineButton = new JButton("Delete Medicine");
        deleteMedicineButton.addActionListener(e -> deleteMedicineDialog()); // ActionListener for deleting a medicine.

        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.addActionListener(e -> manageUsersDialog()); // ActionListener for managing users.

        JButton viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.addActionListener(e -> showOrderHistory()); // ActionListener for viewing orders.

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout()); // ActionListener for logging out.

        // Creating a panel to hold the buttons at the bottom of the window.
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addMedicineButton); // Add "Add Medicine" button to the panel.
        buttonPanel.add(updateMedicineButton); // Add "Update Medicine" button.
        buttonPanel.add(deleteMedicineButton); // Add "Delete Medicine" button.
        buttonPanel.add(logoutButton); // Add "Logout" button.
        buttonPanel.add(manageUsersButton); // Add "Manage Users" button.
        buttonPanel.add(viewOrdersButton); // Add "View Orders" button. 
        

        // Adding components to the frame: inventory details in the center and buttons at the bottom.
        frame.add(new JScrollPane(inventoryDetails), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true); // Make the window visible.
    }

    private void showOrderHistory() {
        // Step 1: Load all orders from the OrderManager class
        ArrayList<Order> allOrders = OrderManager.loadAllOrders();
    
        // Step 2: Create a modal dialog to display the orders
        JDialog dialog = new JDialog(frame, "All Orders", true); // Create a dialog window, with "true" to make it modal
        dialog.setSize(600, 500); // Set the size of the dialog window
        dialog.setLayout(new BorderLayout()); // Set the layout for the dialog (using BorderLayout)
    
        // Step 3: Define the columns for the orders table
        String[] columnNames = {"Order ID", "Customer ID", "Order Date", "Status", "Total Amount"};
        
        // Step 4: Create a DefaultTableModel with the defined column names
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0); // 0 means no rows initially
    
        // Step 5: Loop through all orders and add them as rows in the table model
        for (Order order : allOrders) {
            Object[] rowData = {
                order.getOrderId(), // Order ID
                order.getCustomerId(), // Customer ID who placed the order
                order.getOrderDate(), // Date when the order was placed
                order.getStatus(), // Status of the order (e.g., 'Delivered')
                String.format("PHP %.2f", order.getTotalAmount()) // Format the total amount as PHP currency
            };
            tableModel.addRow(rowData); // Add the row data to the table model
        }
    
        // Step 6: Create a JTable with the table model and add it to the dialog window
        JTable orderTable = new JTable(tableModel); // Create a JTable from the table model
        JScrollPane scrollPane = new JScrollPane(orderTable); // Wrap the table in a scroll pane for better visibility
        dialog.add(scrollPane, BorderLayout.CENTER); // Add the scrollable table to the center of the dialog
    
        // Step 7: Create a close button to close the dialog
        JButton closeButton = new JButton("Close"); // Create a "Close" button
        closeButton.addActionListener(e -> dialog.dispose()); // Add an action listener to close the dialog when clicked
        JPanel buttonPanel = new JPanel(); // Create a new panel for the buttons
        buttonPanel.add(closeButton); // Add the close button to the panel
    
        // Step 8: Add the button panel to the bottom of the dialog
        dialog.add(buttonPanel, BorderLayout.SOUTH); // Place the button panel at the bottom of the dialog
    
        // Step 9: Center the dialog on the screen relative to the main frame
        dialog.setLocationRelativeTo(frame);
    
        // Step 10: Make the dialog visible
        dialog.setVisible(true);
    }


    
    // Method to view orders dialog
    private void viewOrdersDialog() {
        // Step 1: Create a new JDialog to display the orders, it is modal (blocks interaction with other windows)
        JDialog dialog = new JDialog(frame, "Orders List", true);
        dialog.setSize(800, 600); // Set the dialog size to 800x600 pixels
        dialog.setLayout(new BorderLayout()); // Set the layout manager to BorderLayout
    
        // Step 2: Define the column names for the orders table
        String[] columnNames = {"Order ID", "Customer Name", "Order Date", "Status", "Total Amount"};
        
        // Step 3: Create a DefaultTableModel with the column names and no rows initially
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    
        // Step 4: Add dummy data for orders
        // Add some dummy orders
        for (int i = 1; i <= 5; i++) {
            Object[] rowData = {
                i, // Order ID
                "Customer " + i, // Customer's name
                "2024-12-0" + i, // Order date (dummy dates)
                "Placed", // Status (dummy status)
                String.format("PHP %.2f", 100.00 * i) // Total amount (dummy total amount)
            };
            tableModel.addRow(rowData); // Add the dummy data as a row in the table model
        }
    
        // Step 5: Create a JTable from the table model and add it to the dialog inside a JScrollPane (for scrolling)
        JTable orderTable = new JTable(tableModel); // Create the table with the data
        JScrollPane scrollPane = new JScrollPane(orderTable); // Add the table to a scroll pane to make it scrollable
        dialog.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the dialog
    
        // Step 6: Create a close button to close the dialog
        JButton closeButton = new JButton("Close"); // Create a close button
        closeButton.addActionListener(e -> dialog.dispose()); // When clicked, dispose the dialog (close it)
        
        // Step 7: Add the close button to a new panel at the bottom of the dialog
        JPanel buttonPanel = new JPanel(); // Create a new panel for the buttons
        buttonPanel.add(closeButton); // Add the close button to the panel
        
        // Step 8: Add the button panel to the bottom of the dialog
        dialog.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the south region of the dialog
    
        // Step 9: Center the dialog on the screen relative to the main frame
        dialog.setLocationRelativeTo(frame);
    
        // Step 10: Make the dialog visible to the user
        dialog.setVisible(true);
    }



    
    private void manageUsersDialog() {
    // Create a dialog for managing users
    JDialog dialog = new JDialog(frame, "Manage Users", true);
    dialog.setSize(600, 500);  // Set the dialog size
    dialog.setLayout(new BorderLayout());  // Use BorderLayout for the dialog

    // Define the column names for the user table
    String[] columnNames = {"User ID", "Name", "Email"};
    
    // Create a DefaultTableModel with column names and an initial empty row
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

    // Populate the table with user data from the UserManager
    // Assumes you have a method in your UserManager to get all users
    for (User user : userManager.getAllUsers()) { // Get all registered users
        Object[] rowData = {
            user.getId(),        // User ID
            user.getName(),      // User Name
            user.getEmail()      // User Email
        };
        tableModel.addRow(rowData);  // Add a row to the table model
    }

    // Create the JTable and set selection mode
    JTable userTable = new JTable(tableModel);
    userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Create a JPanel for the buttons below the table
    JPanel buttonPanel = new JPanel();
    
    // Add a "Delete User" button to the panel
    JButton deleteUserButton = new JButton("Delete User");
    deleteUserButton.addActionListener(e -> {
        // Get the selected row from the table
        int selectedRow = userTable.getSelectedRow();
        
        if (selectedRow == -1) {  // If no user is selected
            JOptionPane.showMessageDialog(dialog, 
                "Please select a user to delete.",  // Show a warning message
                "No User Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the user ID from the selected row
        String userId = tableModel.getValueAt(selectedRow, 0).toString();
        
        // Confirm the deletion
        int confirmDelete = JOptionPane.showConfirmDialog(
            dialog, 
            "Are you sure you want to delete this user?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmDelete == JOptionPane.YES_OPTION) {
            // Delete the user from UserManager
            userManager.deleteUser(userId);
            tableModel.removeRow(selectedRow);  // Remove the row from the table
            JOptionPane.showMessageDialog(dialog, "User deleted successfully.");
        }
    });
    
    // Add a "Close" button to the panel
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> dialog.dispose());  // Close the dialog when clicked
    
    // Add the buttons to the panel
    buttonPanel.add(deleteUserButton);
    buttonPanel.add(closeButton);

    // Add the table and the button panel to the dialog
    dialog.add(new JScrollPane(userTable), BorderLayout.CENTER);  // Add the table to the center
    dialog.add(buttonPanel, BorderLayout.SOUTH);  // Add the buttons to the bottom

    // Set the dialog to open in the center of the screen
    dialog.setLocationRelativeTo(frame);
    
    // Display the dialog
    dialog.setVisible(true);
}


    // Logout function to redirect to the login panel
    private void logout() {
        frame.getContentPane().removeAll();  // Clear current panel
        MainGUI mainGui = new MainGUI();     // Redirect to MainGUI (Login Panel)
        frame.dispose();                    // Close the current AdminPanelGUI
    }

      private void refreshInventoryDisplay() {
        // Check if inventoryDetails and landingPageGUI are properly initialized.
        if (inventoryDetails != null) {
            inventoryDetails.setText(inventory.displayInventory()); // Update the inventory display.
        } else {
            System.out.println("inventoryDetails is not initialized.");
        }
    
        // Refresh the landing page UI if landingPageGUI is not null.
        if (landingPageGUI != null) {
        } else {
            System.out.println("landingPageGUI is not initialized.");
        }
    }


    // Method to show the dialog for adding a new medicine
    private void addMedicineDialog() {
        // Create a new modal dialog with a title "Add New Medicine"
        JDialog dialog = new JDialog(frame, "Add New Medicine", true);
        dialog.setSize(600, 500);  // Set the size of the dialog to 600px by 500px
        dialog.setLayout(new BorderLayout());  // Set the layout manager to BorderLayout (areas: North, South, East, West, Center)
    
        
        // Step 1: Create JTextField components for various medicine attributes
        JTextField nameField = new JTextField(20);  // Create a text field for the medicine name (20 columns wide)
        JTextField priceField = new JTextField(20);  // Create a text field for the medicine price
        JTextField stockField = new JTextField(20);  // Create a text field for the stock amount
        JTextField descriptionField = new JTextField(20);  // Create a text field for the medicine description
        JTextField componentsField = new JTextField(20);  // Create a text field for the components of the medicine
        JTextField sideEffectsField = new JTextField(20);  // Create a text field for side effects of the medicine
    
        // Step 2: Create components for selecting and displaying an image
        JButton imageButton = new JButton("Select Image");  // Create a button to trigger image selection
        JTextField imagePathField = new JTextField(20);  // Create a text field to display the image file path
        imagePathField.setEditable(false);  // Make the image path text field non-editable
        JLabel imageLabel = new JLabel();  // Create a label to preview the selected image
    
        // Step 3: Action listener for the image selection button
        imageButton.addActionListener(e -> {
            // Open a file chooser dialog to select an image file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Medicine Image");  // Set the dialog title for the file chooser
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);  // Restrict file selection to files only
            int result = fileChooser.showOpenDialog(dialog);  // Show the open file dialog
    
            if (result == JFileChooser.APPROVE_OPTION) {  // If user approves the selection
                // Get the selected file and display its path in the text field
                File selectedFile = fileChooser.getSelectedFile();
                imagePathField.setText(selectedFile.getAbsolutePath());  // Set the file path in the image path field
    
                // Create an ImageIcon from the selected image and scale it
                ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(image);
    
                // Set the image icon as the preview image in the label
                imageLabel.setIcon(imageIcon);
            }
        });
    
        // Step 4: Set up the form layout for the medicine details using GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();  // Constraints for positioning components
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Make the components fill the available space horizontally
        gbc.insets = new Insets(5, 5, 5, 5);  // Add padding between components
    
        // Step 5: Add labels and input fields to the form for each medicine attribute
        gbc.gridx = 0;  // Set the position to the first column
        gbc.gridy = 0;  // Set the row to the first row
        panel.add(new JLabel("Name:"), gbc);  // Add label for Name field
        gbc.gridx = 1;  // Move to the second column
        panel.add(nameField, gbc);  // Add the text field for Name
    
        // Repeat for other fields (Price, Stock, Description, Components, Side Effects, Image Path)
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Price:"), gbc); gbc.gridx = 1; panel.add(priceField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Stock:"), gbc); gbc.gridx = 1; panel.add(stockField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Description:"), gbc); gbc.gridx = 1; panel.add(descriptionField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Components:"), gbc); gbc.gridx = 1; panel.add(componentsField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Side Effects:"), gbc); gbc.gridx = 1; panel.add(sideEffectsField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Image Path:"), gbc); gbc.gridx = 1; panel.add(imagePathField, gbc);
    
        // Step 6: Add image selection button and preview label
        gbc.gridx = 0; gbc.gridy = 7; panel.add(imageButton, gbc);  // Add Image Button
        gbc.gridx = 0; gbc.gridy = 8; panel.add(new JLabel("Image Preview:"), gbc);  // Add Image Preview label
        gbc.gridx = 1; panel.add(imageLabel, gbc);  // Add Image Preview label
    
        // Step 7: Create a panel for the buttons (Add and Cancel)
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Medicine");  // Create button for adding the medicine
        JButton cancelButton = new JButton("Cancel");  // Create button for canceling the action
    
        // Step 8: Action listener for the Add button to add a new medicine
        addButton.addActionListener(e -> {
            // Step 9: Get the text values from the input fields
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String stockText = stockField.getText().trim();
            String description = descriptionField.getText().trim();
            String components = componentsField.getText().trim();
            String sideEffects = sideEffectsField.getText().trim();
            String imagePath = imagePathField.getText().trim();
    
            // Validation checks for the input fields
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid name.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            double price = 0;
            int stock = 0;
    
            try {
                price = Double.parseDouble(priceText);  // Try to parse the price as a number
                if (price <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Price must be a positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid price. Please enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try {
                stock = Integer.parseInt(stockText);  // Try to parse the stock as an integer
                if (stock < 0) {
                    JOptionPane.showMessageDialog(dialog, "Stock cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid stock. Please enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Ensure the image is selected
            if (imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please select an image.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Step 10: Check if the medicine already exists in the inventory
            for (Medicine medicine : inventory.getMedicineList()) {
                if (medicine.getName().equalsIgnoreCase(name)) {  // Case-insensitive match for name
                    JOptionPane.showMessageDialog(dialog, "Medicine already exists in the inventory!", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                    return;  // Stop processing if a duplicate is found
                }
            }
            
    
            // Step 10: Create a new Medicine object with the validated data
            int medicineId = inventory.getMedicineList().size() + 1;  // Generate a new medicine ID
            Medicine newMedicine = new Medicine(medicineId, name, price, description, components, sideEffects, stock, imagePath);
    
            // Step 11: Add the new medicine to the inventory and refresh the display
            inventory.addMedicine(newMedicine);
            // Show confirmation message
            JOptionPane.showMessageDialog(dialog, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
            refreshInventoryDisplay();  // Update the inventory display
    
        });
    
        // Step 12: Action listener for the Cancel button to close the dialog without saving
        cancelButton.addActionListener(e -> dialog.dispose());
    
        // Step 13: Add the Add and Cancel buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
    
        // Step 14: Add the form panel and the button panel to the dialog
        dialog.add(new JScrollPane(panel), BorderLayout.CENTER);  // Add the form to the center of the dialog
        dialog.add(buttonPanel, BorderLayout.SOUTH);  // Add the button panel to the bottom
    
        // Step 15: Set the dialog's position relative to the main frame and show it
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);  // Display the dialog
    }


    // Method to show the dialog for updating an existing medicine
    private void updateMedicineDialog() {
        // Create a modal dialog to update medicine details with a title "Update Medicine"
        JDialog dialog = new JDialog(frame, "Update Medicine", true);
        dialog.setSize(600, 500);  // Set the dialog size to 600px by 500px
        dialog.setLayout(new BorderLayout());  // Set layout manager to BorderLayout (main areas: North, South, Center)
    
        // Step 1: Create JTextField components for various medicine attributes
        JTextField nameField = new JTextField(20);  // Text field for entering the medicine name (20 columns wide)
        JTextField priceField = new JTextField(20);  // Text field for entering the price of the medicine
        JTextField stockField = new JTextField(20);  // Text field for entering the stock quantity
        JTextField descriptionField = new JTextField(20);  // Text field for entering a description
        JTextField componentsField = new JTextField(20);  // Text field for entering the components of the medicine
        JTextField sideEffectsField = new JTextField(20);  // Text field for entering the side effects
        JTextField imagePathField = new JTextField(20);  // Text field for displaying the image file path
    
        // Step 2: Create components for selecting and displaying an image
        JButton imageButton = new JButton("Select Image");  // Button to select an image for the medicine
        imagePathField.setEditable(false);  // Make the image path field non-editable
        JLabel imageLabel = new JLabel();  // Label to preview the selected image
    
        // Step 3: Action listener for the image selection button
        imageButton.addActionListener(e -> {
            // Open a file chooser dialog to select an image file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Medicine Image");  // Set dialog title for the file chooser
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);  // Restrict selection to files only
            int result = fileChooser.showOpenDialog(dialog);  // Show the open file dialog and get the result
    
            // If the user selects a file, process it
            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected file and display its path in the image path field
                File selectedFile = fileChooser.getSelectedFile();
                imagePathField.setText(selectedFile.getAbsolutePath());
    
                // Create an ImageIcon from the selected image and scale it to fit the label
                ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);  // Scale image
                imageIcon = new ImageIcon(image);  // Create a new ImageIcon with the scaled image
    
                // Set the scaled image as the icon for the image preview label
                imageLabel.setIcon(imageIcon);
            }
        });
    
        // Step 4: Set up the form layout for the medicine details using GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());  // Create a panel with GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();  // Create constraints for positioning components in the grid
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Allow components to stretch horizontally
        gbc.insets = new Insets(5, 5, 5, 5);  // Add padding between components
    
        // Step 5: Add labels and input fields to the form for each medicine attribute
        gbc.gridx = 0;  // Set the position of the component to the first column
        gbc.gridy = 0;  // Set the position of the component to the first row
        panel.add(new JLabel("Medicine Name to Update:"), gbc);  // Add label for Name field
        gbc.gridx = 1;  // Move to the second column
        panel.add(nameField, gbc);  // Add the text field for the medicine name
    
        // Repeat for other fields (Price, Stock, Description, Components, Side Effects, Image Path)
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Set New price (optional):"), gbc); gbc.gridx = 1; panel.add(priceField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Add Stock (optional):"), gbc); gbc.gridx = 1; panel.add(stockField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Set Description (optional):"), gbc); gbc.gridx = 1; panel.add(descriptionField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Update Components (optional):"), gbc); gbc.gridx = 1; panel.add(componentsField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Update Side Effects (optional):"), gbc); gbc.gridx = 1; panel.add(sideEffectsField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Select New Image (optional):"), gbc); gbc.gridx = 1; panel.add(imagePathField, gbc);
    
        // Step 6: Add image selection button and preview label
        gbc.gridx = 0; gbc.gridy = 7; panel.add(imageButton, gbc);  // Add Image Button
        gbc.gridx = 0; gbc.gridy = 8; panel.add(new JLabel("Image Preview:"), gbc);  // Add Image Preview label
        gbc.gridx = 1; panel.add(imageLabel, gbc);  // Add Image Preview label
    
        // Step 7: Create a panel for the buttons (Update and Cancel)
        JPanel buttonPanel = new JPanel();  // Panel to hold buttons
        JButton updateButton = new JButton("Update Medicine");  // Create the Update button
        JButton cancelButton = new JButton("Cancel");  // Create the Cancel button
    
        // Step 8: Action listener for the Update button to update the medicine
        updateButton.addActionListener(e -> {
            // Step 9: Get the text values from the input fields
            String name = nameField.getText().trim();
            Medicine medicine = inventory.getMedicineByName(name);  // Find the medicine to update by name
    
            if (medicine == null) {
                JOptionPane.showMessageDialog(dialog, "Medicine not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Step 10: Validation checks for price and stock
            double price = 0;
            int stock = 0;
    
            if (!priceField.getText().isEmpty()) {
                try {
                    price = Double.parseDouble(priceField.getText());  // Try to parse price
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
                    stock = Integer.parseInt(stockField.getText());  // Try to parse stock
                    if (stock < 0) {
                        JOptionPane.showMessageDialog(dialog, "Stock cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid stock. Please enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
    
            // Step 11: Update medicine fields if they are not empty
            if (!priceField.getText().isEmpty()) {
                medicine.setPrice(price);  // Set the new price if provided
            }
            if (!stockField.getText().isEmpty()) {
                medicine.updateStock(stock);  // Update the stock if provided
            }
            if (!descriptionField.getText().isEmpty()) {
                medicine.setDescription(descriptionField.getText());  // Set the new description
            }
            if (!componentsField.getText().isEmpty()) {
                medicine.setComponents(componentsField.getText());  // Set the new components
            }
            if (!sideEffectsField.getText().isEmpty()) {
                medicine.setSideEffects(sideEffectsField.getText());  // Set the new side effects
            }
            if (!imagePathField.getText().isEmpty()) {
                medicine.setImagePath(imagePathField.getText());  // Set the new image path
            }
    
            // Step 12: Save the updated medicine and refresh the inventory display
            inventory.saveInventoryToFile();
            JOptionPane.showMessageDialog(dialog, "Medicine updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshInventoryDisplay();
            
        });
    
        // Step 13: Action listener for the Cancel button to close the dialog without saving
        cancelButton.addActionListener(e -> dialog.dispose());
    
        // Step 14: Add the Update and Cancel buttons to the button panel
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
    
        // Step 15: Add the form panel and the button panel to the dialog
        dialog.add(new JScrollPane(panel), BorderLayout.CENTER);  // Add the form panel to the center
        dialog.add(buttonPanel, BorderLayout.SOUTH);  // Add the button panel to the bottom
    
        // Step 16: Set the location of the dialog relative to the main frame and make it visible
        dialog.setLocationRelativeTo(frame);  // Center the dialog on the screen
        dialog.setVisible(true);  // Show the dialog
    }


    // Dialog for deleting medicine by name
    private void deleteMedicineDialog() {
        JTextField nameField = new JTextField(); // Create a JTextField for inputting the name of the medicine to be deleted
    
        // Display a dialog to ask for the name of the medicine to delete
        Object[] message = {
            "Enter the name of the medicine to delete:", nameField  // Show a prompt with the name field for input
        };
    
        // Show a confirmation dialog with OK and Cancel options
        int option = JOptionPane.showConfirmDialog(frame, message, "Delete Medicine", JOptionPane.OK_CANCEL_OPTION);
        
        // If the user clicks OK to proceed with deletion
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();  // Get the text entered in the name field
            Medicine medicine = inventory.getMedicineByName(name);  // Attempt to find the medicine in the inventory by its name
    
            // If the medicine is not found, show an error message
            if (medicine == null) {
                JOptionPane.showMessageDialog(frame, "Medicine not found.");  // Inform the user that the medicine was not found
                return;  // Exit the method if no medicine is found
            }
    
            // If the medicine is found, remove it from the inventory
            inventory.getMedicineList().remove(medicine);  // Remove the medicine from the inventory list
            inventory.saveInventoryToFile();  // Save the updated inventory to the file after deletion
            refreshInventoryDisplay();  // Refresh the inventory display to reflect the changes
            JOptionPane.showMessageDialog(frame, "Medicine deleted successfully!");  // Show a success message
        }
    
        // Refresh the inventory display in case the dialog is canceled
        refreshInventoryDisplay();  // Update the inventory display to show the latest data
    }

}
