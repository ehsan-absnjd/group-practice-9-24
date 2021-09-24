package entity;

public class Transaction {
    private int transactionId;
    private int sourceAccountId;
    private int targetAccountId;
    private String type;
    private long amount;
    private String dateTime;

    public Transaction(int transactionId, int sourceAccountId, int targetAccountId, String type, long amount) {
        this.transactionId = transactionId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.type = type;
        this.amount = amount;
    }

    public Transaction(int sourceAccountId, int targetAccountId, String type, long amount) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.type = type;
        this.amount = amount;
    }

    public Transaction(int sourceAccountId, String type, long amount) {
        this.sourceAccountId = sourceAccountId;
        this.type = type;
        this.amount = amount;
    }

    public Transaction(int transactionId, int sourceAccountId, int targetAccountId, String type, long amount, String dateTime) {
        this.transactionId = transactionId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public int getSourceAccountId() {
        return sourceAccountId;
    }

    public int getTargetAccountId() {
        return targetAccountId;
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        if(targetAccountId!=0)
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", dateTime='" + dateTime + '\'' +
                '}';
        else
            return "Transaction{" +
                    "transactionId=" + transactionId +
                    ", sourceAccountId=" + sourceAccountId +

                    ", type='" + type + '\'' +
                    ", amount=" + amount +
                    ", dateTime='" + dateTime + '\'' +
                    '}';
    }
}
