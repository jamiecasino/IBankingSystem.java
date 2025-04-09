import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

class Account {
    public long accountNumber;
    public String holderName;
    public double balance;

    // Create constructor to initialize the class with the 3 params above
    public Account(long accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }
}

interface IBankingSystem {
    void createAccount(Account account);

    void updateAccountName(long accountNumber, String newHolderName);

    void deleteAccount(long accountNumber);

    void deposit(long accountNumber, double amount);

    void withdraw(long accountNumber, double amount);

    void printAllAccountSummariesByHolderName(String holderName);
}

class BankingSystem implements IBankingSystem {
    private List<Account> accountList = new ArrayList<Account>();

    public void printAllAccountsSummary() {
        for (Account account : accountList) {
            printAccountSummary(account);
        }
    }

    private void printAccountSummary(Account account) {
        String summary = String.format("{accountNumber: %d, holderName: %s, balance: %.2f}", account.accountNumber,account.holderName, account.balance);
        printMessage(summary);
    }

    public void printAccountSummary(long accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
        printAccountSummary(account);
    } else {
            printMessage("ACCOUNT NOT FOUND");
        }
    }

    // Use this method as a utility to locate the requested account.
    // For example, you can use this when updating an account.
    public Account findAccount(long accountNumber) {
        for (Account account : accountList) {
            if (account.accountNumber == accountNumber) {
                return account;
            }
        }
        return null; //Account not found
    }

    // IBankingSystem implementations
    public void createAccount(Account account) {
        accountList.add(account); //Adds the account to the list
    }

    @Override
    public void updateAccountName(long accountNumber, String newHolderName) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.holderName = newHolderName;
        } else {
            printMessage("ACCOUNT NOT FOUND");
        }
    }
    
    @Override
    public void deleteAccount(long accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            accountList.remove(account); //Removing account
            printMessage("ACCOUNT DELETED");
        } else { 
           printMessage("ACCOUNT NOT FOUND");
        }
    }
    
    @Override
    public void deposit(long accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.balance += amount;
        } else {
            printMessage("ACCOUNT NOT FOUND");
        }
    }
    
    @Override
    public void withdraw(long accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            if (account.balance >= amount) {
            account.balance -= amount; //Perform withdrawal
        } else {
            printMessage("INSUFFICIENT FUNDS"); 
        }
    } else {
        printMessage("ACCOUNT NOT FOUND");
    }
}

    @Override
    public void printAllAccountSummariesByHolderName(String holderName) {
        for (Account account : accountList) {
            if (account.holderName.equals(holderName)) {
                printAccountSummary(account);
            }
        }
    }
    
    
    public void printMessage(String message) {
        System.out.println(message);
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BankingSystem bank = new BankingSystem();

        // input handling
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int operationCount = Integer.parseInt(bufferedReader.readLine().replaceAll("\\s+$", "").split("=")[1].trim());
        bufferedReader.readLine();
        IntStream.range(0, operationCount).forEach(opCountItr -> {
            try {
                List<String> theInput = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(","))
                        .collect(toList());
                String action = theInput.get(0);
                String arg1 = theInput.size() > 1 ? theInput.get(1).trim() : null;
                String arg2 = theInput.size() > 2 ? theInput.get(2).trim() : null;
                String arg3 = theInput.size() > 3 ? theInput.get(3).trim() : null;
                ProcessInputs(bank, action, arg1, arg2, arg3);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
    });
        bufferedReader.close();
    }

/**
 * @param bank
 * @param action
 * @param arg1
 * @param arg2
 * @param arg3
 */
private static void ProcessInputs(BankingSystem bank, String action, String arg1, String arg2, String arg3) {
        long accountNumber;
        String holderName;
        double amount;
    
        switch (action) {
            case "createAccount":
                accountNumber = Long.parseLong(arg1);
                holderName = arg2;
                amount = Double.parseDouble(arg3);
                Account account = new Account(accountNumber, holderName, amount);
                bank.createAccount(account);
                break;

            case "deleteAccount":
                accountNumber = Long.parseLong(arg1);
                bank.deleteAccount(accountNumber);
                break;

            case "deposit": {
                accountNumber = Long.parseLong(arg1);
                amount = Double.parseDouble(arg2);
                bank.deposit(accountNumber, amount);
                break;
            }

            case "printAllAccountsSummary":
                bank.printAllAccountsSummary();
                break;

            case "printAllAccountSummariesByHolderName":
                holderName = arg1;
                bank.printAllAccountSummariesByHolderName(holderName);
                break;

            case "printAccountSummary":
                accountNumber = Long.parseLong(arg1);
                bank.printAccountSummary(accountNumber);
                break;

            case "updateAccountName":
                accountNumber = Long.parseLong(arg1);
                holderName = arg2;
                bank.updateAccountName(accountNumber, holderName);
                break;

            case "withdraw":
                accountNumber = Long.parseLong(arg1);
                amount = Double.parseDouble(arg2);
                bank.withdraw(accountNumber, amount);
                break;

            default: 
            if (action.equals("withdraw") || action.equals("deposit") || action.equals("printAccountSummary")) {
                Account account = bank.findAccount(accountNumber);
                if (account == null) {
                    bank.printMessage("ACCOUNT NOT FOUND");
                    break; //Exit switch statement
                }
            }
                throw new IllegalArgumentException("No know action name was provided.");
    }
}