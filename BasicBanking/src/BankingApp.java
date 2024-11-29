import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
    private final Scanner input;
    private final AccountService accountService;

    public BankingApp() {
        this.input = new Scanner(System.in);
        this.accountService = new AccountService();
    }

    public void start() throws SQLException {
        int userChoice = 0;

        while (userChoice != 6) {
            displayMenu();
            userChoice = getUserChoice();
            switch (userChoice) {
                case 1 -> accountService.createAccount(input);
                case 2 -> accountService.deleteAccount(input);
                case 3 -> accountService.checkBalance(input);
                case 4 -> accountService.withdraw(input);
                case 5 -> accountService.deposit(input);
                case 6 -> System.out.println("Closing the application. Goodbye!");
                default -> System.out.println("Invalid input");
            }
        }

        input.close();
    }

    private void displayMenu() {
        System.out.println("""
                1. Create Account
                2. Delete Account
                3. Check Balance
                4. Withdraw
                5. Deposit
                6. Close App
                """);
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            if (choice >= 1 && choice <= 6) {
                return choice;
            }
        } else {
            input.next(); // Clear invalid input
        }
        return -1;
    }
}
