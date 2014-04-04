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
public class MedicineBatch {
    private String codenumber;
    private long vendorid;
    private String batchNo;
    private Timestamp expiryDate;
    private long quantity;

    public MedicineBatch(String codenumber, long vendorid, String batchNo, Timestamp expiryDate, long quantity) throws SQLException, Exception {
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM medstocks WHERE batchno = '"+batchNo+"'");
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {
            throw new Exception("Batch Already exists");
        }
        this.codenumber = codenumber;
        this.vendorid = vendorid;
        this.batchNo = batchNo;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }
    
    public MedicineBatch(String batchno) throws SQLException {
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM medstocks WHERE batchno = '"+batchno+"'");
        ResultSet rs = pst.executeQuery();
        rs.next();
        codenumber = rs.getString(1);
        vendorid = rs.getLong(2);
        batchNo = batchno;
        expiryDate = rs.getTimestamp(4);
        quantity = rs.getLong(5);
        
    }

    public String getCodenumber() {
        return codenumber;
    }

    public long getVendorid() {
        return vendorid;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) throws SQLException {
        this.quantity = quantity;
        Statement stmt = dbInit.conn.createStatement();
        String sqlupdate = "UPDATE medstocks SET quantity = "+quantity+" WHERE batchno = '"+batchNo+"'";
        stmt.executeUpdate(sqlupdate);
    }
    
}
