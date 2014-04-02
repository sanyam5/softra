/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shop;

import java.util.ArrayList;

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
    
    public void sell(long quantity)
    {
        
    }
    
    public ArrayList<MedicineBatch> getExpiredbatches()
    {
        return batches;//wrong code
    }
    
    public void removeExpired()
    {
        
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
