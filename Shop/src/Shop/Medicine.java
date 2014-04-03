/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author arkanathpathak
 */
public class Medicine {

    private String codenumber;
    private String tradename;
    private ArrayList<Vendor> supplyvendors;
    private ArrayList<MedicineBatch> batches;
    private long unitsellingprice;
    private long purchasingprice;
    private long totalsold;
    private long totalstock;
    private Timestamp addDate;

    public Medicine(String tradename,
            ArrayList<Vendor> supplyvendors,
            long unitsellingprice,
            long purchasingprice) throws SQLException
    {
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM medstocks WHERE codenumber = '" + this.codenumber + "' ORDER BY expDate");
        ResultSet rs = pst.executeQuery();
        int counter = 0;
        while (rs.next())
        {
            counter++;
            batches.add(new MedicineBatch(rs.getString(3)));
            totalstock += rs.getLong(5);
        }
        counter++;
        this.codenumber = "MED"+counter;
        this.tradename = tradename;
        this.supplyvendors = supplyvendors;
        this.unitsellingprice = unitsellingprice;
        this.purchasingprice = purchasingprice;     
        this.addDate = new Timestamp(new Date().getTime());
    }

    public Medicine(String codenumber) throws SQLException {
        this.codenumber = codenumber;
        this.totalstock = 0;
        this.totalstock = 0;
        this.supplyvendors = new ArrayList<Vendor>();
        this.batches = new ArrayList<MedicineBatch>();
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM medstocks WHERE codenumber = '" + this.codenumber + "' ORDER BY expDate");
        ResultSet rs = pst.executeQuery();
        while (rs.next())
        {
            MedicineBatch medicineBatch = new MedicineBatch(rs.getString(3));
            batches.add(medicineBatch);
            if(rs.getTimestamp(4).getTime() > (new Date().getTime()))
                totalstock += rs.getLong(5);
        }

        pst = dbInit.conn.prepareStatement("SELECT * FROM medvendors WHERE codenumber = '" + this.codenumber + "'");
        rs = pst.executeQuery();
        while (rs.next())
        {
            supplyvendors.add(new Vendor(rs.getLong(2)));
        }
        pst = dbInit.conn.prepareStatement("SELECT * FROM medicines WHERE codenumber = '" + this.codenumber + "'");
        rs = pst.executeQuery();
        while (rs.next())
        {
            tradename = rs.getString(2);
            unitsellingprice = rs.getLong(3);
            purchasingprice = rs.getLong(4);
            addDate = rs.getTimestamp(5);
        }
        pst = dbInit.conn.prepareStatement("SELECT * FROM medsales WHERE codenumber = '" + this.codenumber + "'");
        rs = pst.executeQuery();
        while (rs.next())
        {
            totalsold += rs.getLong(2);
        }
    }

    public void sell(long quantity, long sellDate) throws Exception {
        
        if (totalstock >= quantity)
        {
            totalstock -= quantity;
            totalsold += quantity;
            long tosell = quantity;
            Iterator<MedicineBatch> it = batches.iterator();
            while (tosell > 0)
            {
                MedicineBatch mb = it.next();
                if(mb.getExpiryDate().getTime() <= (new Date()).getTime()) continue; //medicine already expired
                if (mb.getQuantity() < tosell)
                {
                    tosell -= mb.getQuantity();
                    mb.setQuantity(0);
                } 
                else
                {
                    mb.setQuantity(mb.getQuantity() - tosell);
                    tosell = 0;
                }
            }

            if (dbInit.conn == null)
            {
                dbInit.startDb();
            }
            PreparedStatement psInsert = dbInit.conn.prepareStatement("insert into medsales values (?, ?, ?)");
            psInsert.setString(1, codenumber);
            psInsert.setLong(2, quantity);
            psInsert.setTimestamp(3, new Timestamp(sellDate));
            psInsert.executeUpdate();
        } else
        {
            throw new Exception("Bitch nothing to sell");
        }

    }

    public ArrayList<MedicineBatch> getExpiredbatches() {
        ArrayList<MedicineBatch> expired = new ArrayList<MedicineBatch>();
        for (MedicineBatch mb : batches)
        {
            if (mb.getExpiryDate().getTime() < (new Date()).getTime()) //how to gewt timestamp??
            {
                expired.add(mb);
            }
        }
        return expired;
    }

    public void removeExpired() throws SQLException {
        ArrayList<MedicineBatch> expired = getExpiredbatches();
        for (MedicineBatch exmb : expired)
        {
            batches.remove(exmb);
            if (dbInit.conn == null)
            {
                dbInit.startDb();
            }
            Statement stmt = dbInit.conn.createStatement();
            String sqlupdate = "DELETE from medstocks WHERE batchno = '" + exmb.getBatchNo() + "'";
            stmt.executeUpdate(sqlupdate);
        }

    }

    public boolean addSupply(MedicineBatch medicineBatchToAdd) throws SQLException {
        PreparedStatement psInsert = dbInit.conn.prepareStatement("insert into medstocks values (?, ?, ?, ?, ?)");
        psInsert.setString(1, medicineBatchToAdd.getCodenumber());
        psInsert.setLong(2, medicineBatchToAdd.getVendorid());
        psInsert.setString(3, medicineBatchToAdd.getBatchNo());
        psInsert.setTimestamp(4, medicineBatchToAdd.getExpiryDate());
        psInsert.setLong(5, medicineBatchToAdd.getQuantity());
        psInsert.executeUpdate();
        return true;
    }


    public ArrayList<Medicine> getTobeordered() throws SQLException
    {
        ArrayList<Medicine> tobeordered =  new ArrayList<Medicine>();
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM medsales");
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {            
            String codenumber = rs.getString(1);
            Medicine toAddCandy = new Medicine(codenumber);
            long elapsedTime = (new Date()).getTime() - toAddCandy.addDate.getTime();
            long elapsedWeeks = elapsedTime/(7*24*3600*1000);
            if(elapsedWeeks==0) continue;
            if(totalstock*elapsedWeeks <= totalsold)
                tobeordered.add(toAddCandy);
        }
        return tobeordered;
    
    }
    public String getTradename() {
        return tradename;
    }

    public ArrayList<Vendor> getSupplyvendors() {
        return supplyvendors;
    }

    public ArrayList<MedicineBatch> getBatches() {
        return batches;
    }

    public long getUnitsellingprice() {
        return unitsellingprice;
    }

    public long getPurchasingprice() {
        return purchasingprice;
    }

    public long getTotalsold() {
        return totalsold;
    }

    public long getTotalstock() {
        return totalstock;
    }

    public String getCodenumber() {
        return codenumber;
    }

}
