/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part2banksim;

/**
 *
 * @author nagyp
 */
public class AccountsManager {

    BankAccount[] bankAccounts = new BankAccount[10];

    void addAccount(BankAccount a) {
        for (int i = 0; i < bankAccounts.length; i++) {
            if (bankAccounts[i] == null) {
                bankAccounts[i] = a;
                System.out.println("New bank account was added on test track.");
                return;
            }
        }
        System.out.println("No empty account found on test track.");
    }

    int getTotalBalance() {
        int totalBlanace = 0;
        for (BankAccount a : bankAccounts) {
            if (a != null) {
                totalBlanace += a.getBalance();
            }
        }
        return totalBlanace;
    }

    String getAllAccountsDetails() {
        String all = "";
        for (BankAccount a : bankAccounts) {
            if (a != null) {
                String line = "Owner " + a.getOwner() + " has the following balance: " + a.getBalance() + " \n";
                all = all + line;
            }
        }
        return all;
    }

    boolean transfer(String fromOwnerName, String toOwnerName, int amount) {
        BankAccount fromOwner = null;
        BankAccount toOwner = null;

        for (BankAccount a : bankAccounts) {
            if (a != null) {
                if (a.getOwner().equals(fromOwnerName)) {
                    fromOwner = a;
                } else if (a.getOwner().equals(toOwnerName)) {
                    toOwner = a;
                }
            }
        }
        if ((null == fromOwner) || (null == toOwner)) {
            return false;
        }

        if ((0 >= amount) || (amount > fromOwner.getBalance())) {
            return false;
        }

        toOwner.setBalance(amount + toOwner.getBalance());
        fromOwner.setBalance(fromOwner.getBalance() - amount);

        return true;
    }

    public static void main(String[] args) {

        AccountsManager accountsManager = new AccountsManager();

        accountsManager.addAccount(new BankAccount("Ana", 50));
        accountsManager.addAccount(new BankAccount("Nicolae", 1000));
        accountsManager.addAccount(new BankAccount("Codruta", 6900));
        accountsManager.addAccount(new BankAccount("Corina", 10));
        accountsManager.addAccount(new BankAccount("Maria", 100));
        accountsManager.addAccount(new BankAccount("Gabi", 0));
        accountsManager.addAccount(new BankAccount("Raul", -3));
    }
}
