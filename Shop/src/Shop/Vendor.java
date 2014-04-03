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

    //sql code populates this class with id
    public Vendor(String address, String name) throws SQLException
    {
        this.address = address;
        this.name = name;
        PreparedStatement ps = dbInit.conn.prepareStatement("SELECT * from vendors");
        ResultSet rs = ps.executeQuery();
        long count= 0;
        while(rs.next())
        {
            count ++;
        }
        this.id = (long) (Math.random()*100*(1000000007))+count;
        PreparedStatement psInsert = dbInit.conn.prepareStatement("insert into vendors values (?, ?, ?)");
        psInsert.setLong(1,id);
        psInsert.setString(2,address);
        psInsert.setString(3,name);
        psInsert.executeUpdate();
    }
    
    public Vendor(long id) throws SQLException {

        this.id = id;
        //sql code populates this class with id
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM vendors WHERE id = " + id);
        ResultSet rs = pst.executeQuery();
        while (rs.next())
        {
            this.address = rs.getString(2);
            this.name = rs.getString(3);
            medicinelist = new ArrayList<String>();
            PreparedStatement pst2 = dbInit.conn.prepareStatement("SELECT * FROM medvendors WHERE vendorid = " + id + "");
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next())
            {
                String codenumber = rs2.getString(1);
                if(!medicinelist.contains(codenumber)) medicinelist.add(codenumber);
            }
        }

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
