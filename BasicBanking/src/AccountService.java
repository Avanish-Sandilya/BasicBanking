import java.sql.*;
import java.util.Scanner;

public class AccountService {
    private static final String DB_URL = "jdbc:sqlite:C://sql_lite//banking.db";

    public void createAccount(Scanner input) throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL)) {
            System.out.print("Enter name: ");
            String name = input.next();
            System.out.print("Enter balance: ");
            double balance = input.nextDouble();

            PreparedStatement stm = con.prepareStatement("INSERT INTO banking (customerName, balance) VALUES (?, ?)");
            stm.setString(1, name);
            stm.setDouble(2, balance);

            int rowsInserted = stm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new account has been created successfully!");
            }
        }
    }

    public void deleteAccount(Scanner input) throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL)) {
            System.out.print("Enter account number to delete: ");
            int accountNumber = input.nextInt();

            PreparedStatement stm = con.prepareStatement("DELETE FROM banking WHERE accountNumber = ?");
            stm.setInt(1, accountNumber);

            int rowsDeleted = stm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("No account found with that account number.");
            }
        }
    }

    public void checkBalance(Scanner input) throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL)) {
            System.out.print("Enter account number to check balance: ");
            int accountNumber = input.nextInt();

            PreparedStatement stm = con.prepareStatement("SELECT balance FROM banking WHERE accountNumber = ?");
            stm.setInt(1, accountNumber);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("The balance for account number " + accountNumber + " is: $" + balance);
            } else {
                System.out.println("No account found with that account number.");
            }
        }
    }

    public void withdraw(Scanner input) throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL)) {
            System.out.print("Enter account number to withdraw from: ");
            int accountNumber = input.nextInt();
            System.out.print("Enter amount to withdraw: ");
            double amount = input.nextDouble();

            // Check current balance
            PreparedStatement balanceCheck = con.prepareStatement("SELECT balance FROM banking WHERE accountNumber = ?");
            balanceCheck.setInt(1, accountNumber);
            ResultSet rs = balanceCheck.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (amount <= currentBalance) {
                    double newBalance = currentBalance - amount;

                    // Update balance
                    PreparedStatement stm = con.prepareStatement("UPDATE banking SET balance = ? WHERE accountNumber = ?");
                    stm.setDouble(1, newBalance);
                    stm.setInt(2, accountNumber);
                    stm.executeUpdate();
                    System.out.println("Withdrawal successful! New balance: $" + newBalance);
                } else {
                    System.out.println("Insufficient balance for withdrawal.");
                }
            } else {
                System.out.println("No account found with that account number.");
            }
        }
    }

    public void deposit(Scanner input) throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL)) {
            System.out.print("Enter account number to deposit into: ");
            int accountNumber = input.nextInt();
            System.out.print("Enter amount to deposit: ");
            double amount = input.nextDouble();

            // Update balance
            PreparedStatement stm = con.prepareStatement("UPDATE banking SET balance = balance + ? WHERE accountNumber = ?");
            stm.setDouble(1, amount);
            stm.setInt(2, accountNumber);

            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Deposit successful! Amount deposited: $" + amount);
            } else {
                System.out.println("No account found with that account number.");
            }
        }
    }
}
