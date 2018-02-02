package database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

import static values.strings.*;


public class MysqlDBConnector {
    Connection conn = null;
    private static String usernameDB=usernamedb;
    private static String passwordDB=passworddb;
    private static String DB=db;
    public static Connection connectdb()
    {
        try
        {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(DB,usernameDB,passwordDB);
            return conn;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
