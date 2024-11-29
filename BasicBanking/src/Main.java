import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        BankingApp app = new BankingApp();
        try {
            app.start();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
