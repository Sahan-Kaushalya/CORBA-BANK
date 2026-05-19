package lk.kaushalya.bcd.client;

import Banking.Account;
import Banking.AccountHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.Scanner;

public class ATMClient {
    public static void main(String[] args) {
        ORB orb = ORB.init(args, null);
        try {
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Account account = AccountHelper.narrow(ncRef.resolve_str("BankAccount"));

            System.out.println("\n===========================================================================");
            System.out.println("                            CORBA Banking System                    ");
            System.out.println("===========================================================================");

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Your Account ID : ");
            String accountNo = sc.nextLine();

            boolean running = true;
            while (running) {
                System.out.println("\n");
                System.out.println("===========================================================================");
                System.out.println("  1. Get Bank Name | 2. Deposit | 3. Withdraw | 4. Get Balance | 5. Exit");
                System.out.println("===========================================================================");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        String bankName = account.getBankName(accountNo);
                        System.out.println("\nBank Name: " + bankName);
                        break;

                    case 2:
                        System.out.println("\nEnter amount to deposit: ");
                        double depositAmount = sc.nextDouble();
                        account.deposit(accountNo, depositAmount);
                        System.out.println("LKR "+depositAmount+" deposited to account "+accountNo);
                        System.out.println("\n====================================");
                        System.out.println("         Deposit successful.");
                        System.out.println("====================================");
                        System.out.println("Current balance is LKR "+account.getBalance(accountNo) +" in account "+accountNo+"" +
                                "\n - "+account.getBankName(accountNo)+" -");
                        break;

                    case 3:
                        System.out.println("\nEnter amount to withdraw: ");
                        double withdrawAmount = sc.nextDouble();

                       try{
                           account.withdraw(accountNo, withdrawAmount);
                           System.out.println("LKR "+withdrawAmount+" withdrawn from account "+accountNo);
                           System.out.println("\n====================================");
                           System.out.println("         Withdraw successful.");
                           System.out.println("====================================");
                           System.out.println("Current balance is LKR "+account.getBalance(accountNo) + " in account "+accountNo+"" +
                                   "\n - "+account.getBankName(accountNo)+" -\n");
                       }catch (Exception e) {
                           System.out.println("\n====================================");
                           System.out.println("         Withdrawal failed.");
                           System.out.println("====================================");
                           System.out.println("Reason: " + e.getMessage());
                           System.out.println("Current balance is LKR " + account.getBalance(accountNo) + " in account " + accountNo + "" +
                                   "\n - " + account.getBankName(accountNo) + " -");
                       }
                        break;

                    case 4:
                        double balance = account.getBalance(accountNo);
                        System.out.println("\n Current Balance: LKR " + balance);
                        break;

                    case 5:
                        System.out.println("\n=============================================");
                        System.out.println("     Thank you for using our services.");
                        System.out.println("=============================================");
                        running = false;
                        break;

                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
