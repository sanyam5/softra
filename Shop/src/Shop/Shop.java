/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import java.sql.*;
import java.text.ParseException;
import java.util.*;

/**
 *
 * @author arkanathpathak
 */
public class Shop {

    private static ShopOwner owner;
    public static ArrayList<Medicine> allMedicines;
    public static ArrayList<Vendor> allVendors;

    public static void Login(String password) {
        if (owner.getPassword().equals(password))
        {
            //continue logging in
        } else
        {
            //show dialog
        }
    }

    public static boolean Register(ShopOwner inp) {
        if (inp.getName() != null)
        {
            owner = inp;
            return true;
        } else
        {
            return false;
        }
    }

    public static boolean addMedicine(Medicine inp) {
        try
        {
            PreparedStatement ps = dbInit.conn.prepareStatement("SELECT * from medicines WHERE codenumber = '"+inp.getCodenumber()+"'");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                throw new Exception("Medicine with that codenumber already exists!");
            }
            PreparedStatement psInsert = dbInit.conn.prepareStatement("insert into medicines values (?, ?, ?, ?, ?)");
            psInsert.setString(1, inp.getCodenumber());
            psInsert.setString(2, inp.getTradename());
            psInsert.setLong(3, inp.getUnitsellingprice());
            psInsert.setLong(4, inp.getPurchasingprice());
            psInsert.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));
            psInsert.executeUpdate();
            for(Vendor in:inp.getSupplyvendors())
            {
                psInsert = dbInit.conn.prepareStatement("insert into medvendors values (?, ?)");
                psInsert.setString(1, inp.getCodenumber());
                psInsert.setLong(2,in.getId());
                psInsert.executeUpdate();
            }
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void showProfit(Timestamp date1, Timestamp date2) {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ParseException {

        Shop.Register(new ShopOwner("Arkanath", "pass", "B-121", "981821", "x@y.com", new Timestamp(new java.util.Date().getTime())));
        dbInit.startDb();
        ShopOwner a = new ShopOwner();
        System.out.println(a.getPhoneno());
        long id = new Vendor("Hzaari Baag", "phekubabu").getId();
        System.out.println(id);
        ArrayList<Vendor> ch = new ArrayList<Vendor>();
        ch.add(new Vendor(id));
        Shop.addMedicine(new Medicine("Paracetamol", ch, 50, 40));
        dbInit.refreshMedicines();
        dbInit.printMedicines();
    }

}
