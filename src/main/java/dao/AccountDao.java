package dao;

import entity.Account;
import entity.User;
import exception.AccountException;

import java.sql.*;

public class AccountDao {
    Connection connection;
    public AccountDao(Connection connection){
        this.connection=connection;
    }
    public void save(Account account){
        String query = "INSERT INTO account (user_id , balance) VALUES(?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setLong (2, account.getBalance());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(int id , long newBalance){

        String query = "UPDATE account SET balance=? WHERE account_id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, newBalance);
            preparedStatement.setInt (2, id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Account loadById(int id) throws  AccountException{
        AccountException exception =null;
        String query = "SELECT * FROM account WHERE account_id=?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new Account(resultSet.getInt(1),
                        resultSet.getInt(3),
                        resultSet.getLong(2)
                        );
            }else{
                exception =  new AccountException("user with id:" + id + "not found");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (exception!=null)
            throw exception;
        return null;
    }

    public Account[] loadAll() throws  AccountException{
        String countQuery = "SELECT COUNT(*) FROM account";
        String resultQuery = "SELECT * FROM account";
        try(Statement statement = connection.createStatement()){
            ResultSet countResultSet =statement.executeQuery(countQuery);
            countResultSet.next();
            int accountCount = countResultSet.getInt(1);
            countResultSet.close();
            if(accountCount>0){
                ResultSet resultSet = statement.executeQuery(resultQuery);
                Account[] result = convert(resultSet, accountCount);
                resultSet.close();
                return result;
            }else{
                throw  new AccountException("no accounts found");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    private Account[] convert(ResultSet resultSet , int count) throws SQLException {
        Account[] accounts = new Account[count];
        int index=0;
        while(resultSet.next()){
            accounts[index++]=new Account(resultSet.getInt(1),
                    resultSet.getInt(3),
                    resultSet.getLong(2)
            );
        }
        return accounts;
    }
}
