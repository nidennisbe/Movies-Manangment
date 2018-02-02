package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    /**
     * This method used to verify that user is existed on tb_user in mysql Database
     * @param userName store username
     * @param password store password
     */
    public boolean checkUserExists(String userName,String password) throws SQLException {
        boolean userExists=false;
        Connection connection = MysqlDBConnector.connectdb();
        String query = ("SELECT * FROM tb_user WHERE username = '" + userName + "' AND password = '" + password + "'");
        assert connection != null;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            userExists=true;
            connection.close();
        }else {
            userExists=false;
        }
        return userExists;
    }
}
