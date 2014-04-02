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
    private ShopOwner owner;
    private ArrayList<Medicine> allMedicines;
    private ArrayList<Vendor> allVendors;
    
    public void Login(String password)
    {
        
    }
    
    public boolean Register(String password)
    {
        return true;
    }
    
    public boolean addMedicine(Medicine inp)
    {
        return true;
    }
    
    public void showProfit(Timestamp date1, Timestamp date2)
    {
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ParseException {
        // TODO code application logic herez
//        new ShopOwner("Arka","pass","B-121, LLR", "9818", "arkanath@x.xom", ShopOwner.ddmmYYYY2Timestamp(2, 12, 2011));
        ShopOwner a = new ShopOwner();
        System.out.println(a.getPhoneno());
    }
    
}
