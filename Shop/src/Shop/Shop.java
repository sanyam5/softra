/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import static Shop.dbInit.conn;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author arkanathpathak
 */
public class Shop {

    public static ShopOwner owner;
    public static ArrayList<Medicine> allMedicines;
    public static ArrayList<Vendor> allVendors;

    public static void Login(String password, Login in) {
        if (owner.getPassword().equals(password))
        {
            new LoggedIn().setVisible(true);
            in.setVisible(false);
            //continue logging in
        } else
        {
            JOptionPane.showMessageDialog(null, "Wrong Password");
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
            JOptionPane.showMessageDialog(null, inp.getAddress());
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

    public static AbstractMap.SimpleEntry<Long, Long> showProfit(Timestamp date1, Timestamp date2) throws SQLException {
        Long profit = new Long(0);
        Long revenue = new Long(0);
        
        PreparedStatement psInsert = conn.prepareStatement("SELECT * from medsales");
        ResultSet rs = psInsert.executeQuery();
        while (rs.next())
        {
            long a = rs.getTimestamp(3).getTime();
            if(a>=date1.getTime() && a<=date2.getTime())
            {
                Medicine med = new Medicine(rs.getString(1));
                profit += new Long(med.getUnitsellingprice()-med.getPurchasingprice());
                revenue += new Long(med.getUnitsellingprice());
            }
        }
        return new AbstractMap.SimpleEntry<Long, Long>(revenue,profit);
    }
    
    public static Timestamp ddmmYYYY2Timestamp(int dd, int mm, int YYYY) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = dateFormat.parse(dd + "/" + mm + "/" + YYYY);
        long time = date.getTime();
        return new Timestamp(time);
    }
    public static Timestamp ddmmYYYY2Timestamp(String dd, String mm, String YYYY) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = dateFormat.parse(dd + "/" + mm + "/" + YYYY);
        long time = date.getTime();
        return new Timestamp(time);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ParseException, Exception {

        dbInit.refreshAll();
        dbInit.printMedicines();
        dbInit.printMedSales();
        Medicine a = new Medicine("MED1");
        System.out.println(a.getTotalstock());
        new AccountsInfo();
//        a.sell(2, new java.util.Date().getTime());
//        new Timestamp
//        new Login();
//        Shop.Register(new ShopOwner("Arkanath", "pass", "B-121", "981821", "x@y.com", new Timestamp(new java.util.Date().getTime())));
//        dbInit.startDb();
//        ShopOwner a = new ShopOwner();
//        System.out.println(a.getPhoneno());
//        long id = new Vendor("Hzaari Baag", "phekubabu").getId();
//        System.out.println(id);
//        ArrayList<Vendor> ch = new ArrayList<Vendor>();
//        ch.add(new Vendor(id));
//        //Shop.addMedicine(new Medicine("Paracetamol", ch, 50, 40));
//        dbInit.refreshMedicines();
//        dbInit.printMedicines();
//        long check = new java.util.Date().getTime();
//        long oneweek = 1000*60*60*24*30;
//        allMedicines.get(0).addSupply(new MedicineBatch("MED1", id, "PRCT102", Shop.ddmmYYYY2Timestamp(01, 05, 2014), 4));
//        dbInit.printMedBatches();
//        dbInit.printVendors();
    }

    public Shop() {
    }

}
