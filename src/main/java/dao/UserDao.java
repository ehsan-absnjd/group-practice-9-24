package dao;

import entity.User;
import exception.UserException;

import java.sql.*;

public class UserDao {
    Connection connection;
    public UserDao( Connection connection){
        this.connection=connection;
    }
    public void save(User user){
        String query = "INSERT INTO user (first_name , last_name) VALUES(?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public User loadById(int id) throws UserException {
        String query = "SELECT * FROM user WHERE user_id=?";
        UserException exception = null;
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }else{
                exception=  new UserException("user with id:" + id + "not found" );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if( exception!=null)
            throw exception;



        return null;
    }

    public User[] loadAll() throws UserException{
        String countQuery = "SELECT COUNT(*) FROM user";
        String resultQuery = "SELECT * FROM user";
        try(Statement statement = connection.createStatement()){
            ResultSet countResultSet =statement.executeQuery(countQuery);
            countResultSet.next();
            int userCount = countResultSet.getInt(1);
            countResultSet.close();
            if(userCount>0){
                ResultSet resultSet = statement.executeQuery(resultQuery);
                User[] result =  convert(resultSet, userCount);
                resultSet.close();
                return result;
            }else{
                throw new UserException("there are no users");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    private User[] convert(ResultSet resultSet , int count) throws SQLException {
        User[] users = new User[count];
        int index=0;
        while(resultSet.next()){
            users[index++]=new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
        }
        return users;
    }
}
