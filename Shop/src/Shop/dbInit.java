/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
                s.execute("create table medicines(codenumber varchar(100), tradename varchar(100), unitprice bigint, purchaseprice bigint, addDate timestamp)");
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
            rs1 = dbmd.getTables(null, "APP", "MEDSALES", null);
            if (!rs1.next())
            {
                s.execute("create table medsales(codenumber varchar(100),quantity bigint, sellDate timestamp)");
                System.out.println("Created table medsales");
            } else
            {
                System.out.println("Table medsales Exists");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void clearUser() throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        String sqlupdate = "DELETE from shopowner";
        stmt.executeUpdate(sqlupdate);
        dbInit.refreshAll();
    }

    public static void refreshAll() throws SQLException {
        dbInit.startDb();
        dbInit.refreshMedicines();
        dbInit.refreshOwner();
        dbInit.refreshVendors();
    }

    public static void refreshOwner() throws SQLException {
        PreparedStatement ps = dbInit.conn.prepareStatement("SELECT * from shopowner");
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            Shop.owner = new ShopOwner(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getTimestamp(6));
        }
    }

    public static void refreshMedicines() throws SQLException {
        ArrayList<Medicine> all = new ArrayList<Medicine>();
        PreparedStatement ps = dbInit.conn.prepareStatement("SELECT * from medicines");
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            all.add(new Medicine(rs.getString(1)));
        }
        Shop.allMedicines = all;
    }

    public static void refreshVendors() throws SQLException {
        ArrayList<Vendor> all = new ArrayList<Vendor>();
        PreparedStatement ps = dbInit.conn.prepareStatement("SELECT * from vendors");
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            all.add(new Vendor(rs.getLong(1)));
        }
        Shop.allVendors = all;
    }

    public static void printMedicines() throws SQLException {
        System.out.println("Printing Medicines");
        PreparedStatement psInsert = conn.prepareStatement("SELECT * from medicines");
        ResultSet rs = psInsert.executeQuery();
        while (rs.next())
        {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getLong(3) + "|" + rs.getLong(4) + "|" + dateFormat.format(rs.getTimestamp(5)));
        }
    }

    public static void printVendors() throws SQLException {
        System.out.println("Printing Vendors");
        PreparedStatement psInsert = conn.prepareStatement("SELECT * from vendors");
        ResultSet rs = psInsert.executeQuery();
        while (rs.next())
        {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(rs.getLong(1) + "|" + rs.getString(2) + "|" + rs.getString(3));
        }
    }

    public static void printMedVendors() throws SQLException {
        System.out.println("Printing MedVendors");
        PreparedStatement psInsert = conn.prepareStatement("SELECT * from medvendors");
        ResultSet rs = psInsert.executeQuery();
        while (rs.next())
        {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(rs.getString(1) + "|" + rs.getLong(2));
        }
    }

    public static void printMedBatches() throws SQLException {
        System.out.println("Printing medstocks");
        PreparedStatement psInsert = conn.prepareStatement("SELECT * from medstocks");
        ResultSet rs = psInsert.executeQuery();
        while (rs.next())
        {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(rs.getString(1) + "|" + rs.getLong(2) + "|" + rs.getString(3) + "|" + dateFormat.format(rs.getTimestamp(4)) + "|" + rs.getLong(5));
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
