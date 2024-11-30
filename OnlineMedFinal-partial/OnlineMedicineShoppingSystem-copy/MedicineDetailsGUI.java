import javax.swing.*;
import java.awt.*;

public class MedicineDetailsGUI {
    public MedicineDetailsGUI(Medicine medicine, Cart cart) {
        JFrame frame = new JFrame(medicine.getName() + " - Details");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel(medicine.getName(), JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        JTextArea detailsArea = new JTextArea(10, 40);
        detailsArea.setText(medicine.getDetails());
        detailsArea.setEditable(false);
        frame.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(5);
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityField.getText());
            cart.addMedicine(medicine, quantity);
            JOptionPane.showMessageDialog(frame, "Added to Cart");
            frame.dispose();
        });

        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(addToCartButton);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
