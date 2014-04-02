/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package softra;

import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author arkanathpathak
 */
public class Softra {

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
