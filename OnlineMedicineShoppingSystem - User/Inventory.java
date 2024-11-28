import java.util.ArrayList;

public class Inventory {
    public ArrayList<Medicine> medicineList;

    public Inventory() {
        this.medicineList = new ArrayList<>();
    }

    public void addMedicine(Medicine medicine) {
        medicineList.add(medicine);
    }

    public void updateStock(int medicineId, int quantity) {
        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineId() == medicineId) {
                medicine.updateStock(quantity);
                return;
            }
        }
        System.out.println("Medicine not found in inventory.");
    }

    public void displayInventory() {
        for (Medicine medicine : medicineList) {
            System.out.println(medicine.getDetails());
        }
    }
    
    // Add this getter
    public ArrayList<Medicine> getMedicineList() {
        return medicineList;
    }
}