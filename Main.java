
import java.util.ArrayList;
import java.util.Scanner;

// Interface
interface Transaction {
    void deposit(double amount);
    void withdraw(double amount);
}

// Abstract class
abstract class BankAccount implements Transaction {
    private int accountNumber;
    private String holderName;
    protected double balance;

    private ArrayList<String> history = new ArrayList<>();

    // Constructor
    BankAccount(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        history.add("Account created with balance: " + balance);
    }

    // Getters (Encapsulation)
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit method
    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        balance += amount;
        history.add("Deposited: " + amount);
        System.out.println("Deposit successful!");
    }

    // Withdraw method
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            history.add("Withdrawn: " + amount);
            System.out.println("Withdrawal successful!");
        }
    }

    // Abstract method
    abstract void displayAccountType();

    // Transaction history
    public void showHistory() {
        System.out.println("\n--- Transaction History ---");
        for (String item : history) {
            System.out.println(item);
        }
    }

    public void displayDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + holderName);
        System.out.println("Balance: " + balance);
        displayAccountType();
    }
}

// Inheritance
class SavingsAccount extends BankAccount {

    SavingsAccount(int accountNumber, String holderName,
                   double balance) {
        super(accountNumber, holderName, balance);
    }

    // Method overriding (Polymorphism)
    @Override
    void displayAccountType() {
        System.out.println("Account Type: Savings Account");
    }
}

// Main class
public class Main {

    static Scanner sc = new Scanner(System.in);
    static BankAccount account;

    public static void main(String[] args) {

        System.out.println("===== CREATE BANK ACCOUNT =====");

        try {
            System.out.print("Enter account number: ");
            int number = Integer.parseInt(sc.nextLine());

            if (number <= 0) {
                System.out.println("Account number must be positive.");
                return;
            }

            System.out.print("Enter account holder name: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter opening balance: ");
            double openingBalance =
                    Double.parseDouble(sc.nextLine());

            if (!Double.isFinite(openingBalance)
                    || openingBalance < 0) {
                System.out.println("Invalid opening balance.");
                return;
            }

            account = new SavingsAccount(
                    number, name, openingBalance);

            System.out.println("\nAccount created successfully!");

            while (true) {
                System.out.println("\n===== BANKING MENU =====");
                System.out.println("1. Deposit Money");
                System.out.println("2. Withdraw Money");
                System.out.println("3. Check Balance");
                System.out.println("4. Account Details");
                System.out.println("5. Transaction History");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice;

                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid menu number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        try {
                            double amount =
                                    Double.parseDouble(sc.nextLine());
                            account.deposit(amount);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid amount.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        try {
                            double amount =
                                    Double.parseDouble(sc.nextLine());
                            account.withdraw(amount);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid amount.");
                        }
                        break;

                    case 3:
                        System.out.println(
                                "Current Balance: " +
                                        account.getBalance());
                        break;

                    case 4:
                        account.displayDetails();
                        break;

                    case 5:
                        account.showHistory();
                        break;

                    case 6:
                        System.out.println("Thank you for banking!");
                        sc.close();
                        return;

                    default:
                        System.out.println(
                                "Invalid choice. Select 1 to 6.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric values.");
        }
    }
}
