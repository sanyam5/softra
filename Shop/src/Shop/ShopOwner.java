/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import java.sql.*;
import java.text.*;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author arkanathpathak
 */
public class ShopOwner {

    private static String name;
    private static String password;
    private static String address;
    private static String phoneno;
    private static String emailid;
    private static Timestamp regDate;

    

    public ShopOwner() throws SQLException {
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        PreparedStatement pst = dbInit.conn.prepareStatement("SELECT * FROM shopowner");
        ResultSet rs = pst.executeQuery();
        rs.next();
        name = rs.getString(1);
        password = rs.getString(2);
        address = rs.getString(3);
        phoneno = String.valueOf(rs.getLong(4));
        emailid = rs.getString(5);
        regDate = rs.getTimestamp(6);
    }

    public ShopOwner(String name, String password, String address, String phoneno, String emailid, Timestamp regDate){
        final Pattern rfc2822 = Pattern.compile ("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        String regex = "[0-9]+";
        try{
        if (dbInit.conn == null)
        {
            dbInit.startDb();
        }
        this.name = name;
        this.password = password;
        this.address = address;
        this.phoneno = phoneno;
        this.emailid = emailid;
        this.regDate = regDate;
        Statement stmt = dbInit.conn.createStatement();
        String sqlupdate = "DELETE from shopowner";
        stmt.executeUpdate(sqlupdate);
        PreparedStatement psInsert = dbInit.conn.prepareStatement("insert into shopowner values (?, ?, ?, ?, ? , ?)");
        psInsert.setString(1, name);
        psInsert.setString(2, password);
        psInsert.setString(3, address);
        
        
        if(!phoneno.matches(regex))
        {
            throw new Exception("Invalid Phone No., Try Again!");
        }
        psInsert.setLong(4, Long.parseLong(phoneno));
        psInsert.setString(5, emailid);
        if(!(rfc2822.matcher(emailid).matches())) 
        {
            throw new Exception("Invalid E-mail, Try Again!");
        }
        psInsert.setTimestamp(6, regDate);
        psInsert.executeUpdate();
        }
        catch(Exception e)
        {
            this.address = e.getMessage();
            this.name= null;    
        }

    }

    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }

    public static String getAddress() {
        return address;
    }

    public static String getPhoneno() {
        return phoneno;
    }

    public static String getEmailid() {
        return emailid;
    }

    public static Timestamp getRegDate() {
        return regDate;
    }

}
