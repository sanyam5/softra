/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shop;

import java.sql.*;
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
        //sql code populates this class with id
        this.id = id;
        
    }
    public void addNewMedicine(Medicine medicineToAdd) throws SQLException
    {
        medicinelist.add(medicineToAdd.getCodenumber());
        //sql code adds
        PreparedStatement psInsert = dbInit.conn.prepareStatement("insert into medvendors values (?, ?)");
        psInsert.setString(1, medicineToAdd.getCodenumber());
        psInsert.setLong(2, id);
        psInsert.executeUpdate();
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
