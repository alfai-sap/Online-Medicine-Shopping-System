# Lumina Wellness - Online Medicine Shopping System (Project Prototype)

A comprehensive Java-based desktop application for online medicine shopping with separate interfaces for customers and administrators.

## 🚀 Features

### Customer Features
- **User Registration & Authentication**: Secure signup and login system
- **Medicine Browsing**: Browse available medicines with detailed information
- **Shopping Cart**: Add medicines to cart and manage quantities
- **Order Management**: Place orders and view order history
- **User Profile**: Manage personal information

### Admin Features
- **Inventory Management**: Add, update, and remove medicines from inventory
- **Order Tracking**: View and manage all customer orders
- **User Management**: Monitor customer accounts
- **Stock Management**: Track medicine availability and stock levels

## 🛠️ Technology Stack

- **Language**: Java
- **GUI Framework**: Java Swing
- **Data Storage**: File-based storage (CSV and serialized objects)
- **Architecture**: Object-Oriented Programming with Singleton pattern
- **IDE**: BlueJ/IntelliJ IDEA

## 🚦 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (recommended: IntelliJ IDEA, Eclipse, or BlueJ)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/alfai-sap/Online-Medicine-Shopping-System.git
   cd Online-Medicine-Shopping-System/LuminaWellness
   ```

2. **Compile the project**
   ```bash
   javac *.java
   ```

3. **Run the application**
   ```bash
   java MainGUI
   ```

### Alternative: Using JAR file
If available, you can run the pre-compiled JAR file:
```bash
java -jar OnlineMedSystem.jar
```

## 💻 Usage

### For Customers
1. **Registration**: Create a new account with name, email, and password
2. **Login**: Access your account using email and password
3. **Browse Medicines**: View available medicines with details like price, components, and side effects
4. **Add to Cart**: Select medicines and add them to your shopping cart
5. **Place Order**: Review cart and place your order
6. **Order History**: View your past orders and their status

### For Administrators
1. **Admin Login**: Use admin credentials to access the admin panel
2. **Manage Inventory**: Add new medicines, update existing ones, or remove out-of-stock items
3. **View Orders**: Monitor all customer orders and their details
4. **Stock Management**: Update medicine quantities and availability

## 📊 Data Storage

The application uses file-based storage:
- **[`inventory.txt`](LuminaWellness/inventory.txt)**: Stores medicine data in CSV format
- **`all_orders.txt`**: Central storage for all orders (serialized objects)
- **`customer_*_order_history.txt`**: Individual customer order histories

### Medicine Data Format
The [`Medicine`](LuminaWellness/Medicine.java) class uses CSV format with escaped commas:
```
medicineId,name,price,description,components,sideEffects,stock,imagePath
```

## 🏗️ Architecture

### Key Classes
- **[`MainGUI`](LuminaWellness/MainGUI.java)**: Application entry point and main interface
- **[`Inventory`](LuminaWellness/Inventory.java)**: Singleton class managing medicine inventory
- **[`Medicine`](LuminaWellness/Medicine.java)**: Entity class representing medicine objects
- **[`Customer`](LuminaWellness/Customer.java)**: Customer user implementation with cart and order history
- **[`Admin`](LuminaWellness/Admin.java)**: Administrator user with inventory management capabilities

### Design Patterns
- **Singleton Pattern**: Used in [`Inventory`](LuminaWellness/Inventory.java) class for centralized inventory management
- **Inheritance**: [`Customer`](LuminaWellness/Customer.java) and [`Admin`](LuminaWellness/Admin.java) extend base [`User`](LuminaWellness/User.java) class
- **Serialization**: Used for persistent data storage

## 🔧 Configuration

### Default Admin Credentials
- Email: `admin@luminawellness.com`
- Password: `admin123`

### File Paths
The application stores data files in the project directory. Image paths for medicines should be updated to match your local setup.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Java Swing documentation and community
- BlueJ IDE for educational Java development
- Contributors and testers

**Note**: This is an educational project demonstrating Java GUI programming and object-oriented design principles.
