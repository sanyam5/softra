/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softra;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author arkanathpathak
 */
public class dbInit {

    public static void startDb() {
        Connection conn = null;

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
            }
            else 
            {
                System.out.println("Table Exists");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        startDb();
    }

}
