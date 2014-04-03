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

    public Medicine(String tradename,
            ArrayList<Vendor> supplyvendors, 
            long unitsellingprice,
            long purchasingprice
            ) 
    {
        this.tradename = tradename;
        this.supplyvendors = supplyvendors;
        this.unitsellingprice = unitsellingprice;
        this.purchasingprice = purchasingprice;        
    }
    
    public Medicine(String codenumber) throws SQLException {
        this.codenumber = codenumber;
        this.totalstock = 0;
        this.totalstock = 0;
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM medstocks WHERE codenumber = '"+this.codenumber+"' ORDER BY expDate");
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {            
            batches.add(new MedicineBatch(rs.getString(3)));
            totalstock += rs.getLong(5);
        }
        
        pst = dbInit.conn.prepareStatement("SELECT * FROM medvendors WHERE codenumber = '"+this.codenumber+"'");
        rs = pst.executeQuery();
        while(rs.next())
        {            
            supplyvendors.add(new Vendor(rs.getLong(2)));
        }
        pst = dbInit.conn.prepareStatement("SELECT * FROM medicines WHERE codenumber = '"+this.codenumber+"'");
        rs = pst.executeQuery();
        while(rs.next())
        {            
            tradename = rs.getString(2);
            unitsellingprice = rs.getLong(3);
            purchasingprice = rs.getLong(4);
        }
        pst = dbInit.conn.prepareStatement("SELECT * FROM medsales WHERE codenumber = '"+this.codenumber+"'");
        rs = pst.executeQuery();
        while(rs.next())
        {            
            totalsold += rs.getLong(2);            
        }
    }
    
    
    public void sell(long quantity, long  sellDate) throws Exception
    {
        if(totalstock>=quantity) 
        {
            totalstock -= quantity;
            totalsold += quantity;
            long tosell = quantity;
            Iterator<MedicineBatch> it = batches.iterator();
            while(tosell>0)
            {
                MedicineBatch mb = it.next();
                if(mb.getQuantity() < tosell)
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
            //to-do sql 
            if (dbInit.conn == null)
            {
                dbInit.startDb();
            }
            Statement stmt = dbInit.conn.createStatement();
            String sqlupdate = "DELETE from shopowner";
            stmt.executeUpdate(sqlupdate);
        } 
        else 
        {
            throw new Exception("Bitch nothing to sell");
        }
        
    }
    
    public ArrayList<MedicineBatch> getExpiredbatches()
    {
        ArrayList<MedicineBatch> expired = new ArrayList<MedicineBatch>();
        for(MedicineBatch mb : batches)
        {
            if(mb.getExpiryDate().getTime()< (new Date()).getTime() ) //how to gewt timestamp??
            {
                expired.add(mb);
            }
        }
        return expired;
    }
    
    public void removeExpired()
    {
        ArrayList<MedicineBatch> expired = getExpiredbatches();
        
    }
    public boolean addSupply(MedicineBatch medicineBatchToAdd)
    {
        return true;//wrong code
    }
    public ArrayList<Medicine> getTobeordered()
    {
        return new ArrayList<Medicine>();
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
