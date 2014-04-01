/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softra;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author arkanathpathak
 */
public class dbInit {

    static Connection conn = null;

    public static void startDb() {

        try
        {
            String url = "jdbc:derby:";
            String dbName = "dbname";
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName + ";create=true");
            Statement s = conn.createStatement();
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs1 = dbmd.getTables(null, "APP", "USERS", null);
            if (!rs1.next())
            {
                s.execute("create table users(username varchar(40), score int)");
                System.out.println("Created table users");
            } else
            {
                System.out.println("Table Exists");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void printEntries() throws SQLException {
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM users");
        ResultSet rs = pst.executeQuery();
        int rollcall = 0;
        while (rs.next())
        {
            System.out.println(rs.getString(1) + " , " + rs.getInt(2));
        }
    }

    public static void addEntry(String s, int t) throws SQLException {
        PreparedStatement psInsert = conn.prepareStatement("insert into users values (?, ?)");
        psInsert.setString(1, s);
        psInsert.setInt(2, t);
        psInsert.executeUpdate();
    }

    public static void main(String[] args) throws SQLException {
        startDb();
//        addEntry("hua",1);
        printEntries();
    }

}
