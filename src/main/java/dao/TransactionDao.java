package dao;

import entity.Account;
import entity.Transaction;
import entity.User;
import exception.TransactionException;

import java.sql.*;

public class TransactionDao {
    Connection connection;
    public TransactionDao(Connection connection){
        this.connection=connection;
    }
    public void save(Transaction transaction){
        boolean hasTargetId = (transaction.getTargetAccountId()!=0);
        String query = hasTargetId ? "INSERT INTO transaction (source_account_id  , type , amount, target_account_id , date_time) VALUES(?,?,?,?,NOW())" :
                "INSERT INTO transaction (source_account_id ,  type , amount , date_time) VALUES(?,?,?,NOW())";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, transaction.getSourceAccountId());
            preparedStatement.setString (2, transaction.getType() );
            preparedStatement.setLong (3, transaction.getAmount() );
            if(hasTargetId)
                preparedStatement.setInt (4, transaction.getTargetAccountId());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
//
//    public Transaction loadById(int id){
//        String query = "SELECT * FROM transaction WHERE transaction_id=?";
//        try(PreparedStatement statement = connection.prepareStatement(query)){
//            statement.setInt(1,id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()){
//                return new Transaction(resultSet.getInt(1),
//                        resultSet.getInt(2),
//                        resultSet.getInt(3),
//                        resultSet.getString(4),
//                        resultSet.getLong(5),
//                        resultSet.getString(6));
//            }else{
//
//            }
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//
//
//        return null;
//    }


    public Transaction[] loadAll() throws TransactionException{
        TransactionException exception=null;
        String countQuery = "SELECT COUNT(*) FROM transaction";
        String resultQuery = "SELECT * FROM transaction";
        try(Statement statement = connection.createStatement()){
            ResultSet countResultSet =statement.executeQuery(countQuery);
            countResultSet.next();
            int transactionCount = countResultSet.getInt(1);
            countResultSet.close();
            if(transactionCount>0){
                ResultSet resultSet = statement.executeQuery(resultQuery);
                Transaction[] result = convert(resultSet, transactionCount);
                resultSet.close();
                return result;
            }else{
                exception=  new TransactionException("there are no transactions");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (exception!=null)
            throw exception;
        return null;
    }
    private Transaction[] convert(ResultSet resultSet , int count) throws SQLException {
        Transaction[] transactions = new Transaction[count];
        int index=0;
        while(resultSet.next()){
            transactions[index++]=new Transaction(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getLong(5),
                    resultSet.getString(6));
        }
        return transactions;
    }
}
