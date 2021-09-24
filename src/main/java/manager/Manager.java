package manager;

import dao.AccountDao;
import dao.TransactionDao;
import dao.UserDao;
import entity.Account;
import entity.Transaction;
import entity.User;
import exception.AccountException;

import java.sql.Connection;

public class Manager {
    Connection connection;
    TransactionDao transactionDao;
    AccountDao accountDao;
    UserDao userDao;
    public Manager(Connection connection){
        this.connection=connection;
        transactionDao =new TransactionDao( connection);
        accountDao = new AccountDao(connection);
        userDao = new UserDao(connection);
    }
    public void addUser(User user){
        userDao.save(user);
    }
    public User[] loadUsers(){
        User[] users=null;
        try {
            users=  userDao.loadAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return users;
    }
    public void addAccount(Account account){
        try{
            userDao.loadById(account.getUserId());
            accountDao.save( account);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Account[] loadAccounts(){
        Account[] accounts =null;
        try {
            accounts = accountDao.loadAll();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
    public Transaction[] loadTransactions() {
        Transaction[] transactions =null;
        try {
            transactions = transactionDao.loadAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return transactions;
    }
    public void withdraw(int accountId , long amount){
        try {
            if (hasEnoughCredit(accountId, amount)) {
                addToBalance(accountId, -amount);
                transactionDao.save(new Transaction(accountId, "withdraw", amount));
            } else
                System.out.println("this account doesn't have enough balance");
        } catch (AccountException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deposit(int accountId , long amount){

        try {
            addToBalance(accountId, amount);
            transactionDao.save(new Transaction(accountId, "deposit" , amount));

        } catch (AccountException e) {
            System.out.println(e.getMessage());
        }

    }
    public void transfer(int sourceAccountId , int targetAccountId , long amount ){

        try {
            accountDao.loadById(targetAccountId);
            if(hasEnoughCredit(sourceAccountId,amount)){
                addToBalance(sourceAccountId, -amount);
                addToBalance(targetAccountId, amount);
                transactionDao.save(new Transaction(sourceAccountId, targetAccountId, "transfer" , amount));
            }else
                System.out.println("this account doesn't have enough balance");
        } catch (AccountException e) {
            System.out.println(e.getMessage());
        }

    }
    private void addToBalance(int accountId, long amount) throws AccountException {
        accountDao.update(accountId,accountDao.loadById(accountId).getBalance()+amount);
    }
    private boolean hasEnoughCredit(int accountId, long amount) throws AccountException {
            Account account = accountDao.loadById(accountId);
            long remaining = account.getBalance() - amount;
            return remaining >= 0;

    }
}
