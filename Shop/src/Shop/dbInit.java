/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import java.sql.*;

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
            ResultSet rs1 = dbmd.getTables(null, "APP", "SHOPOWNER", null);
            if (!rs1.next())
            {
                s.execute("create table shopowner(name varchar(200), password varchar(100), address varchar(200), phoneno bigint, email varchar(100), regDate timestamp)");
                System.out.println("Created table shopowner");
            } else
            {
                System.out.println("Table shopowner Exists");
            }
            rs1 = dbmd.getTables(null, "APP", "MEDICINES", null);
            if (!rs1.next())
            {
                s.execute("create table medicines(codenumber varchar(100), tradename varchar(100), unitprice bigint, purchaseprice bigint)");
                System.out.println("Created table medicines");
            } else
            {
                System.out.println("Table medicines Exists");
            }
            rs1 = dbmd.getTables(null, "APP", "VENDORS", null);
            if (!rs1.next())
            {
                s.execute("create table vendors(id bigint, address varchar(200), name varchar(200))");
                System.out.println("Created table vendors");
            } else
            {
                System.out.println("Table vendors Exists");
            }
            rs1 = dbmd.getTables(null, "APP", "MEDVENDORS", null);
            if (!rs1.next())
            {
                s.execute("create table medvendors(codenumber varchar(100),vendorid bigint)");
                System.out.println("Created table medvendors");
            } else
            {
                System.out.println("Table medvendors Exists");
            }
            rs1 = dbmd.getTables(null, "APP", "MEDSTOCKS", null);
            if (!rs1.next())
            {
                s.execute("create table medstocks(codenumber varchar(100),vendorid bigint, batchno varchar(100), expDate timestamp, quantity bigint)");
                System.out.println("Created table medstocks");
            } else
            {
                System.out.println("Table medstocks Exists");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

//    public static void addEntry(String s, int t) throws SQLException {
//        PreparedStatement psInsert = conn.prepareStatement("insert into users values (?, ?)");
//        psInsert.setString(1, s);
//        psInsert.setInt(2, t);
//        psInsert.executeUpdate();
//    }

    public static void main(String[] args) throws SQLException {
        startDb();
//        addEntry("hua",1);
//        printEntries();
    }

}
