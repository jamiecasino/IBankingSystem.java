import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful! Current balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful! Current balance: $" + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }
}

public class Main {
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String option;

        do {
            System.out.println("\nWelcome to Simple Bank!");
            System.out.println("1. Create an account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Check balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    createAccount(scanner);
                    break;
                case "2":
                    depositMoney(scanner);
                    break;
                case "3":
                    withdrawMoney(scanner);
                    break;
                case "4":
                    checkBalance(scanner);
                    break;
                case "5":
                    System.out.println("Thank you for using Simple Bank. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (!option.equals("5"));

        scanner.close();
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter account holder name: ");
        String accountHolderName = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account already exists with this account number.");
        } else {
            BankAccount newAccount = new BankAccount(accountNumber, accountHolderName);
            accounts.put(accountNumber, newAccount);
            System.out.println("Account created successfully!");
        }
    }

    private static void depositMoney(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // consume newline
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawMoney(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // consume newline
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void checkBalance(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);
            System.out.println("Account holder: " + account.getAccountHolderName());
            System.out.println("Current balance: $" + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }
}
