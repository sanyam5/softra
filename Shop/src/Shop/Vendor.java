/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author hawck
 */
public class Vendor {
    private long id;
    private String address;
    private ArrayList<String> medicinelist;
    private String name;
    
     public Vendor(long id)
    {
        
        this.id = id;
        //sql code populates this class with id
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM vendors WHERE id = '"+id+"'");
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {
            this.address = rs.getString(1);        
            this.medicinelist.add(rs.getString(2));
            this.name = rs.getString(3);  
        }
        
    }
    public void addNewMedicine(Medicine medicineToAdd)
    {
        medicinelist.add(medicineToAdd.codenumber);
        //sql code adds
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMedicinelist(ArrayList<String> medicinelist) {
        this.medicinelist = medicinelist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getMedicinelist() {
        return medicinelist;
    }

    public String getName() {
        return name;
    }
}
