package lk.kaushalya.bcd.server;

import Banking.AccountPOA;
import Banking.InsufficientBalance;

import java.util.HashMap;

public class AccountImpl extends AccountPOA {

    private HashMap<String, Double> db = new HashMap<>();

    public AccountImpl() {
        db.put("001123", 1000.0);
        db.put("002123", 2500.0);
        db.put("003123", 3250.0);
        db.put("004123", 4000.0);
        db.put("005123", 1500.0);
        db.put("001234", 500.0);
    }

    @Override
    public String getBankName(String accountNo) {

        // Extract the bank code from the account number

        String bankCode = accountNo.substring(0,3);
        switch (bankCode) {
            case "001":
                return "Bank of Ceylon";
            case "002":
                return "Commercial Bank";
            case "003":
                return "Sampath Bank";
            case "004":
                return "Hatton National Bank";
            case "005":
                return "People's Bank";
            default:
                return "Unknown Bank";
        }
    }

    @Override
    public void deposit(String accountNo, double amount) {
        double currentBalance = db.getOrDefault(accountNo,0.0);
        db.put(accountNo, currentBalance + amount);
        System.out.println("Server Log: LKR "+amount+" deposited to account "+accountNo);
    }

    @Override
    public void withdraw(String accountNo, double amount) throws InsufficientBalance {
        double currentBalance = db.getOrDefault(accountNo,0.0);

        if(db.get(accountNo) <= 1000) {
            System.out.println("Server Log: Warning! Low balance in account " + accountNo);
            return;
        }

         if(currentBalance <= amount + 5){
             System.out.println("Server Log: Failed to withdraw requested amount from account "+accountNo);
             throw new InsufficientBalance("Transaction Denied: Insufficient balance in account "+accountNo);
         }

         db.put(accountNo, currentBalance - amount);
         System.out.println("Server Log: LKR "+amount+" withdrawn from account "+accountNo);

    }

    @Override
    public double getBalance(String accountNo) {
        return db.getOrDefault(accountNo,0.0);
    }
}
