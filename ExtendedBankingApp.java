package mini_project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// BankAccount class for managing account operations
class BankAccount {
    private double balance;
    private List<String> transactionHistory;

    // Constructor
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with initial balance: ₹" + initialBalance);
    }

    // Get balance
    public double getBalance() {
        return balance;
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: ₹" + amount + " | New Balance: ₹" + balance);
            System.out.printf("Deposit successful! New balance: ₹%.2f%n", balance);
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
        }
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawn: ₹" + amount + " | New Balance: ₹" + balance);
            System.out.printf("Withdrawal successful! New balance: ₹%.2f%n", balance);
        } else if (amount > balance) {
            System.out.println("Insufficient funds! Your balance is ₹" + balance);
        } else {
            System.out.println("Invalid withdrawal amount. Please enter a positive value.");
        }
    }

    // Transfer method
    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            recipient.deposit(amount);
            transactionHistory.add("Transferred: ₹" + amount + " to another account | New Balance: ₹" + balance);
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Invalid transfer amount or insufficient funds.");
        }
    }

    // Print transaction history
    public void printTransactionHistory() {
        System.out.println("\n===== Transaction History =====");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}

// User class to handle authentication
class User {
    private String username;
    private String password;
    private BankAccount account;

    // Constructor
    public User(String username, String password, double initialBalance) {
        this.username = username;
        this.password = password;
        this.account = new BankAccount(initialBalance);
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public BankAccount getAccount() {
        return account;
    }
}

// Main Banking Application
public class ExtendedBankingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Dummy user database
        User user = new User("user123", "password", 1000);
        BankAccount savingsAccount = new BankAccount(500);

        System.out.println("===== Welcome to Secure Banking System =====");
        System.out.print("Enter Username: ");
        String inputUsername = scanner.next();
        System.out.print("Enter Password: ");
        String inputPassword = scanner.next();

        // Authentication
        if (!user.getUsername().equals(inputUsername) || !user.authenticate(inputPassword)) {
            System.out.println("Invalid credentials! Exiting...");
            return;
        }

        System.out.println("Login successful! Welcome, " + user.getUsername());

        // Banking Menu
        while (true) {
            System.out.println("\n===== Banking Menu =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and 6.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            BankAccount userAccount = user.getAccount();

            switch (choice) {
                case 1:
                    System.out.printf("Your current balance is: ₹%.2f%n", userAccount.getBalance());
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    if (scanner.hasNextDouble()) {
                        userAccount.deposit(scanner.nextDouble());
                    } else {
                        System.out.println("Invalid input! Please enter a valid amount.");
                        scanner.next();
                    }
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    if (scanner.hasNextDouble()) {
                        userAccount.withdraw(scanner.nextDouble());
                    } else {
                        System.out.println("Invalid input! Please enter a valid amount.");
                        scanner.next();
                    }
                    break;

                case 4:
                    System.out.print("Enter amount to transfer to savings account: ");
                    if (scanner.hasNextDouble()) {
                        userAccount.transfer(savingsAccount, scanner.nextDouble());
                    } else {
                        System.out.println("Invalid input! Please enter a valid amount.");
                        scanner.next();
                    }
                    break;

                case 5:
                    userAccount.printTransactionHistory();
                    break;

                case 6:
                    System.out.println("Logging out... Thank you for using Secure Banking System!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option! Please select a number between 1 and 6.");
            }
        }
    }
}
