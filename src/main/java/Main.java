import config.Config;
import dao.AccountDao;
import dao.TransactionDao;
import dao.UserDao;
import entity.Account;
import entity.Transaction;
import entity.User;
import manager.Manager;
import org.mariadb.jdbc.MariaDbDataSource;
import util.Scanner;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    static Scanner scanner =new Scanner();
    static Manager manager;
    public static void main(String[] args) {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        Connection connection=null;
        try {
            dataSource.setUrl(Config.URL);
            dataSource.setUser(Config.USERNAME);
            dataSource.setPassword(Config.PASSWORD);
            connection= dataSource.getConnection();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        manager = new Manager(connection);

        int command;
        do{
            System.out.println("1)add user 2)show users 3)add account 4)show accounts 5)deposit 6)withdraw 7)transfer 8)show transactions");
            command= scanner.getInt();
            switch (command){
                case 1:
                    addUser();
                    break;
                case 2:
                    showUsers();
                    break;
                case 3:
                    addAccount();
                    break;
                case 4:
                    showAccounts();
                    break;
                case 5:
                    deposit();
                    break;
                case 6:
                    withdreaw();
                    break;
                case 7:
                    transfer();
                    break;
                case 8:
                    showTransactions();
                    break;
            }

        }while(command!=10);

    }

    private static void showTransactions() {
        Transaction[] transactions = manager.loadTransactions();
        if (transactions==null)
            return;
        for(Transaction transaction : transactions)
            System.out.println(transaction);
    }

    private static void transfer() {
        System.out.println("source account id");
        int sourceId = scanner.getInt();
        System.out.println("destination account id");
        int destinationId = scanner.getInt();
        System.out.println("amount");
        long amount = scanner.getLong();
        manager.transfer(sourceId,destinationId,amount);
    }

    private static void withdreaw() {
        System.out.println("account id");
        int id = scanner.getInt();
        System.out.println("amount");
        long amount = scanner.getLong();
        manager.withdraw(id,amount);
    }

    private static void deposit() {
        System.out.println("account id");
        int id = scanner.getInt();
        System.out.println("amount");
        long amount = scanner.getLong();
        manager.deposit (id,amount);
    }

    private static void addAccount() {
        System.out.println("user id");
        int id = scanner.getInt();
        System.out.println("initial balance");
        long balance = scanner.getLong();
        Account account = new Account( balance , id);
        manager.addAccount(account);

    }

    private static void showAccounts() {
        Account[] accounts = manager.loadAccounts();
        if (accounts==null)
            return;
        for(Account account : accounts)
            System.out.println(account);
    }

    private static void showUsers() {
        User[] users = manager.loadUsers();
        if (users==null)
            return;
        for(User user : users)
            System.out.println(user);
    }

    private static void addUser() {
        System.out.println("first name");
        String firstName = scanner.getString();
        System.out.println("last name");
        String lastName = scanner.getString();
        manager.addUser(new User(firstName,lastName));
    }
}
