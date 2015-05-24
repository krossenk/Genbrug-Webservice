/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recycle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Morten
 */
public class RecycleSQLConnection 
{
    
    static final String WRITE_USER_SQL = "INSERT INTO user(username, password, firstname, lastname, profile_imageURL"
            + ", phoneNumber, usertype, Address_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    static final String WRITE_USER_SQL2 = "INSERT INTO user(username, password, firstname, lastname, profile_imageURL"
            + ", phoneNumber, usertype) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    static final String VALIDATE_USER_SQL = "SELECT * FROM user WHERE username= ? AND password= ?";
    
    static final String READ_USER_SQL = "SELECT * FROM user WHERE id = ?";
    
    static final String READ_ADDRESS_SQL = "SELECT * FROM address WHERE id = ?";
    
    static final String WRITE_ADDRESS_SQL = "INSERT INTO address(street, zipcode, city, country) VALUES (?,?,?,?)";
    
    static final String UPDATE_ADDRESS_SQL = "UPDATE address SET street = ?, zipcode = ?, city = ?,"
            + "country = ? WHERE id = ?";
    
    static final String UPDATE_USER_SQL = "UPDATE user SET username = ?, password = ?, firstname = ?,"
            + "lastname = ?, profile_imageURL = ?,phonenumber = ?, usertype = ? WHERE id = ?";
    
    static final String WRITE_PUBLICATION_SQL = "INSERT INTO publication(title, description, pickuptype, "
            + "imageURL, pickup_startime, pickup_endtime, user_id, category_id, timestamp) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
    
    static final String READ_PUBLICATION_SQL = "SELECT * FROM publication WHERE id = ?";
    
    static final String READ_ALL_PUBLICATION_SQL = "SELECT * FROM publication";
    
    static final String WRITE_PUBLICATION_IMAGEURL_SQL = "UPDATE publication SET imageURL= ? WHERE id= ?";
    
    static final String READ_CATEGORY_SQL = "SELECT * FROM category WHERE id = ?";
    
    static final String READ_ALL_CATEGORIES_SQL = "SELECT * FROM category";
    
    static final String WRITE_SUBSCRIPTION_SQL = "INSERT IGNORE INTO subscription(user_id, publication_id) VALUES (?,?)";
    
    static final String READ_SUBSCRIPTIONS_SQL = "SELECT * FROM subscription WHERE publication_id = ?";
    
    static final String READ_USER_SUBSCRIPTIONS_SQL = "SELECT * FROM subscription WHERE user_id = ?";
    
    public static Connection getConnection() throws Exception 
    {
        String url = "jdbc:mysql://localhost/recycledb";
        String username = "krk";
        String password = "krk1382";
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            Logger.getLogger(RecycleSQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }

    public long writeUserObject(Connection conn, User user) throws Exception 
    {
        PreparedStatement pstmt = null;
        
        int addressId = createAddressObject(conn, user.getAddressid());
        
        if(addressId == -1)
        {
            pstmt = conn.prepareStatement(WRITE_USER_SQL2, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstname());
            pstmt.setString(4, user.getLastname());
            pstmt.setString(5, user.getProfileimageURL());
            pstmt.setString(6, user.getPhonenumber());
            pstmt.setString(7, user.getUsertype());
        }
        else
        {
            pstmt = conn.prepareStatement(WRITE_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstname());
            pstmt.setString(4, user.getLastname());
            pstmt.setString(5, user.getProfileimageURL());
            pstmt.setString(6, user.getPhonenumber());
            pstmt.setString(7, user.getUsertype());
            pstmt.setInt(8, addressId);                
        }          

        pstmt.executeUpdate();
        
        
        // get the generated key for the id
        ResultSet rs = pstmt.getGeneratedKeys();
        long id = -1;
        rs.next();
        id = rs.getLong(1);
        

        rs.close();
        pstmt.close();

        return id;
    }    
  
    public User readUserObject(Connection conn, long id) throws Exception 
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_USER_SQL);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        User tempUser = new User();
        tempUser.setId(rs.getLong(1));
        tempUser.setUsername(rs.getString(2));
        tempUser.setPassword(rs.getString(3));
        tempUser.setFirstname(rs.getString(4));
        tempUser.setLastname(rs.getString(5));
        tempUser.setProfileimageURL(rs.getString(6));
        tempUser.setPhonenumber(rs.getString(7));
        tempUser.setUsertype(rs.getString(8));
        
        tempUser.setAddressid(getUserAddress(conn, rs.getInt(9)));

        rs.close();
        pstmt.close();

        return tempUser;
    }
    
    public void updateUserObject(Connection conn, User user) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_USER_SQL);        
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getFirstname());
        pstmt.setString(4, user.getLastname());
        pstmt.setString(5, user.getProfileimageURL());
        pstmt.setString(6, user.getPhonenumber());
        pstmt.setString(7, user.getUsertype());
        pstmt.setLong(8, user.getId());
        
                
        pstmt.executeUpdate();
        
        pstmt.close();
    }
    
    private int createAddressObject(Connection conn, Address address) throws Exception
    {
        if(address != null)
        {
            PreparedStatement pstmt = conn.prepareStatement(WRITE_ADDRESS_SQL, Statement.RETURN_GENERATED_KEYS);  

            // set input parameters
            pstmt.setString(1, address.getStreet());
            pstmt.setInt(2, address.getZipcode());
            pstmt.setString(3, address.getCity());
            pstmt.setString(4, address.getCountry());

            pstmt.executeUpdate();

            // get the generated key for the id
            ResultSet rs = pstmt.getGeneratedKeys();
            int id = -1;
            rs.next();
            id = rs.getInt(1);


            rs.close();
            pstmt.close();

            return id;
        }
        
        return -1;
        
    }
    
    private Address getUserAddress(Connection conn, int addressId) throws Exception
    {
        Address tempAddress = null;
        
        if(addressId != 0)
        {
            PreparedStatement pstmt = conn.prepareStatement(READ_ADDRESS_SQL);
            pstmt.setInt(1, addressId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();        

            tempAddress = new Address();
            tempAddress.setId(rs.getInt(1));
            tempAddress.setStreet(rs.getString(2));
            tempAddress.setZipcode(rs.getInt(3));
            tempAddress.setCity(rs.getString(4));
            tempAddress.setCountry(rs.getString(5));

            rs.close();
            pstmt.close();
        }
        
        
        return tempAddress;
    }
    
    private void updateAddressObject(Connection conn, Address address) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_ADDRESS_SQL);
        pstmt.setLong(1, address.getId());
        pstmt.setString(2, address.getStreet());
        pstmt.setInt(3, address.getZipcode());
        pstmt.setString(4, address.getCountry());        
                
        pstmt.executeUpdate();
        
        pstmt.close();
    }   
  
    public User validateUserObject(Connection conn, String username, String password) throws Exception
    {
        PreparedStatement pstmnt = conn.prepareStatement(VALIDATE_USER_SQL);

        pstmnt.setString(1, username);
        pstmnt.setString(2, password);

        ResultSet rs = pstmnt.executeQuery();

        if(!rs.isBeforeFirst())
        {
            return null;
        }
        else
        {
            rs.next();
            User tempUser = new User();
            tempUser.setId(rs.getLong(1));
            tempUser.setUsername(rs.getString(2));
            tempUser.setPassword(rs.getString(3));
            tempUser.setFirstname(rs.getString(4));
            tempUser.setLastname(rs.getString(5));
            tempUser.setProfileimageURL(rs.getString(6));
            tempUser.setPhonenumber(rs.getString(7));
            tempUser.setUsertype(rs.getString(8));

            return tempUser;
        }

    }
  
    public long createPublicationObject(Connection conn, Publication pub) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(WRITE_PUBLICATION_SQL, Statement.RETURN_GENERATED_KEYS);  

    // set input parameters
        pstmt.setString(1, pub.getTitle());
        pstmt.setString(2, pub.getDescription());
        pstmt.setString(3, pub.getPickuptype());
        pstmt.setString(4, pub.getImageURL());
        pstmt.setString(5, pub.getPickupStartime());
        pstmt.setString(6, pub.getPickupEndtime());
        pstmt.setLong(7, pub.getUserId().getId());
        pstmt.setInt(8, pub.getCategoryId().getId());
        pstmt.setString(9, pub.getTimestamp());
        pstmt.executeUpdate();
        // get the generated key for the id
        ResultSet rs = pstmt.getGeneratedKeys();
        long id = -1;
        rs.next();
        id = rs.getLong(1);
        

        rs.close();
        pstmt.close();

        return id;
    }
  
    public Publication readPublicationObject(Connection conn, long id) throws Exception 
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_PUBLICATION_SQL);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        Publication tempPub = new Publication();
        tempPub.setId(rs.getLong(1));
        tempPub.setTitle(rs.getString(2));
        tempPub.setDescription(rs.getString(3));
        tempPub.setPickuptype(rs.getString(4));
        tempPub.setImageURL(rs.getString(5));
        tempPub.setPickupStartime(rs.getString(6));
        tempPub.setPickupEndtime(rs.getString(7));
        tempPub.setUserId(this.readUserObject(conn, rs.getLong(8)));
        tempPub.setCategoryId(this.readCategoryObject(conn, rs.getInt(9)));
        tempPub.setTimestamp(rs.getString(10));
        tempPub.setSubscriptionCollection(readPublicationSubscriptions(conn, id));

        rs.close();
        pstmt.close();

        return tempPub;
    }
    
    public Collection<Publication> readAllPublicationObjects(Connection conn) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_ALL_PUBLICATION_SQL);        
        ResultSet rs = pstmt.executeQuery();
        Collection<Publication> tempCollection = new ArrayList<>();
        
        if(!rs.isBeforeFirst())
        {
            return null;
        }
        else
        {
            while(rs.next())
            {
                Publication tempPub = new Publication();
                tempPub.setId(rs.getLong(1));
                tempPub.setTitle(rs.getString(2));
                tempPub.setDescription(rs.getString(3));
                tempPub.setPickuptype(rs.getString(4));
                tempPub.setImageURL(rs.getString(5));
                tempPub.setPickupStartime(rs.getString(6));
                tempPub.setPickupEndtime(rs.getString(7));
                tempPub.setUserId(this.readUserObject(conn, rs.getLong(8)));
                tempPub.setCategoryId(this.readCategoryObject(conn, rs.getInt(9)));
                tempPub.setSubscriptionCollection(readPublicationSubscriptions(conn, tempPub.getId()));
                tempPub.setTimestamp(rs.getString(10));
                tempCollection.add(tempPub);
            }
        }        

        rs.close();
        pstmt.close();

        return tempCollection;
        
    }
  
    public Category readCategoryObject(Connection conn, int id) throws Exception 
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_CATEGORY_SQL);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        Category tempCat = new Category();
        tempCat.setId(rs.getInt(1));
        tempCat.setCategoryname(rs.getString(2));

        rs.close();
        pstmt.close();

        return tempCat;
    }
    
    public Collection<Category> readAllCategoryObjects(Connection conn) throws Exception 
    {
        Collection<Category> tempList = new ArrayList<>();
        
        PreparedStatement pstmt = conn.prepareStatement(READ_ALL_CATEGORIES_SQL);
        
        ResultSet rs = pstmt.executeQuery();
        
        while(rs.next())
        {
            Category tempCat = new Category();
            tempCat.setId(rs.getInt(1));
            tempCat.setCategoryname(rs.getString(2));
            tempList.add(tempCat);
        }
        

        rs.close();
        pstmt.close();

        return tempList;
    }
    
    public void createSubscriptionObject(Connection conn, long publicationId, long userId) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(WRITE_SUBSCRIPTION_SQL);  

        // set input parameters
        pstmt.setLong(1, userId);
        pstmt.setLong(2, publicationId);
        
        pstmt.executeUpdate();
        
        conn.commit();
        
        pstmt.close();
    }
    
    public Collection<Subscription> readUserSubscriptions(Connection conn, long id) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_USER_SUBSCRIPTIONS_SQL);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();

        Collection<Subscription> tempCollection = new ArrayList<>();

        if(!rs.isBeforeFirst())
        {
            return null;
        }
        else
        {
            while(rs.next())
            {
                Subscription tempSub = new Subscription();
                tempSub.setId(rs.getLong(1));
                tempSub.setPublicationId(new Publication(rs.getLong(2)));
                tempSub.setUserId(new User(rs.getLong(3)));
                tempCollection.add(tempSub);
            }
        }

        rs.close();
        pstmt.close();

        return tempCollection;
    }
  
    public Collection<Subscription> readPublicationSubscriptions(Connection conn, long id) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_SUBSCRIPTIONS_SQL);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();

        Collection<Subscription> tempCollection = new ArrayList<>();

        if(!rs.isBeforeFirst())
        {
            return null;
        }
        else
        {
            while(rs.next())
            {
                Subscription tempSub = new Subscription();
                tempSub.setId(rs.getLong(1));
                tempSub.setPublicationId(new Publication(rs.getLong(2)));
                tempSub.setUserId(new User(rs.getLong(3)));
                tempCollection.add(tempSub);
            }
        }

        rs.close();
        pstmt.close();

        return tempCollection;        
    }
    
    public void saveImageURLToDb(Connection conn, String url, long publicationid) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(WRITE_PUBLICATION_IMAGEURL_SQL);
        pstmt.setString(1, url);
        pstmt.setLong(2, publicationid);
        pstmt.executeUpdate();       
    }
    
}
