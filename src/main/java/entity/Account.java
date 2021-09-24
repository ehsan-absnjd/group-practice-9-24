package entity;

public class Account {
    private int accountId;
    private long balance;
    private int userId;

    public Account(int accountId, int userId, long balance) {
        this.accountId = accountId;
        this.balance = balance;
        this.userId = userId;
    }

    public Account(long balance, int userId) {
        this.balance = balance;
        this.userId = userId;
    }

    public long getBalance() {
        return balance;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", userId=" + userId +
                '}';
    }
}
