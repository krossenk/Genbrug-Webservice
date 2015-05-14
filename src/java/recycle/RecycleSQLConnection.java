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
            + ", phoneNumber, usertype) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    static final String VALIDATE_USER_SQL = "SELECT * FROM user WHERE username= ? AND password= ?";
    
    static final String READ_USER_SQL = "SELECT * FROM user WHERE id = ?";
    
    static final String WRITE_PUBLICATION_SQL = "INSERT INTO publication(title, description, pickuptype, "
            + "imageURL, pickup_startime, pickup_endtime, user_id, category_id, timestamp) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
    
    static final String READ_PUBLICATION_SQL = "SELECT * FROM publication WHERE id = ?";
    
    static final String READ_ALL_PUBLICATION_SQL = "SELECT * FROM publication";
    
    static final String WRITE_PUBLICATION_IMAGEURL_SQL = "UPDATE publication SET imageURL= ? WHERE id= ?";
    
    static final String READ_CATEGORY_SQL = "SELECT * FROM category WHERE id = ?";
    
    static final String WRITE_SUBSCRIPTION_SQL = "INSERT INTO subscription(user_id, publication_id) VALUES (?,?)";
    
    static final String READ_SUBSCRIPTIONS_SQL = "SELECT * FROM subscription WHERE publication_id = ?";     
    
    public static Connection getConnection() throws Exception 
    {
        String url = "jdbc:mysql://localhost/mydb";
        String username = "root";
        String password = "";
        
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
        PreparedStatement pstmt = conn.prepareStatement(WRITE_USER_SQL);  

        // set input parameters
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getFirstname());
        pstmt.setString(4, user.getLastname());
        pstmt.setString(5, user.getProfileimageURL());
        pstmt.setString(6, user.getPhonenumber());
        pstmt.setString(7, user.getUsertype());
        pstmt.executeUpdate();
        
        conn.commit();
        // get the generated key for the id
        ResultSet rs = pstmt.getGeneratedKeys();
        int id = -1;
        rs.next();
        id = rs.getInt(1);
        

        rs.close();
        pstmt.close();

        return id;
    }
  
    public User readUserObject(Connection conn, int id) throws Exception 
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_USER_SQL);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        User tempUser = new User();
        tempUser.setId(rs.getInt(1));
        tempUser.setUsername(rs.getString(2));
        tempUser.setPassword(rs.getString(3));
        tempUser.setFirstname(rs.getString(4));
        tempUser.setLastname(rs.getString(5));
        tempUser.setProfileimageURL(rs.getString(6));
        tempUser.setPhonenumber(rs.getString(7));
        tempUser.setUsertype(rs.getString(8));

        rs.close();
        pstmt.close();

        return tempUser;
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
            tempUser.setId(rs.getInt(1));
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
        PreparedStatement pstmt = conn.prepareStatement(WRITE_PUBLICATION_SQL);  

    // set input parameters
        pstmt.setString(1, pub.getTitle());
        pstmt.setString(2, pub.getDescription());
        pstmt.setString(3, pub.getPickuptype());
        pstmt.setString(4, pub.getImageURL());
        pstmt.setString(5, pub.getPickupStartime());
        pstmt.setString(6, pub.getPickupEndtime());
        pstmt.setInt(7, pub.getUserId().getId());
        pstmt.setInt(8, pub.getCategoryId().getId());
        pstmt.setString(9, pub.getTimestamp());
        pstmt.executeUpdate();
        conn.commit();
        // get the generated key for the id
        ResultSet rs = pstmt.getGeneratedKeys();
        int id = -1;
        rs.next();
        id = rs.getInt(1);
        

        rs.close();
        pstmt.close();

        return id;
    }
  
    public Publication readPublicationObject(Connection conn, int id) throws Exception 
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_PUBLICATION_SQL);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        Publication tempPub = new Publication();
        tempPub.setId(rs.getInt(1));
        tempPub.setTitle(rs.getString(2));
        tempPub.setDescription(rs.getString(3));
        tempPub.setPickuptype(rs.getString(4));
        tempPub.setImageURL(rs.getString(5));
        tempPub.setPickupStartime(rs.getString(6));
        tempPub.setPickupEndtime(rs.getString(7));
        tempPub.setUserId(this.readUserObject(conn, rs.getInt(8)));
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
                tempPub.setId(rs.getInt(1));
                tempPub.setTitle(rs.getString(2));
                tempPub.setDescription(rs.getString(3));
                tempPub.setPickuptype(rs.getString(4));
                tempPub.setImageURL(rs.getString(5));
                tempPub.setPickupStartime(rs.getString(6));
                tempPub.setPickupEndtime(rs.getString(7));
                tempPub.setUserId(this.readUserObject(conn, rs.getInt(8)));
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
    
    public void createSubscriptionObject(Connection conn, int publicationId, int userId) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(WRITE_SUBSCRIPTION_SQL);  

        // set input parameters
        pstmt.setInt(1, userId);
        pstmt.setInt(2, publicationId);
        
        pstmt.executeUpdate();
        
        conn.commit();
        
        pstmt.close();
    }
  
    public Collection<Subscription> readPublicationSubscriptions(Connection conn, int id) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(READ_SUBSCRIPTIONS_SQL);
        pstmt.setInt(1, id);
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
                tempSub.setId(rs.getInt(1));
                tempSub.setPublicationId(new Publication(rs.getInt(2)));
                tempSub.setUserId(new User(rs.getInt(3)));
                tempCollection.add(tempSub);
            }
        }

        rs.close();
        pstmt.close();

        return tempCollection;        
    }
    
    public void saveImageURLToDb(Connection conn, String url, int publicationid) throws Exception
    {
        PreparedStatement pstmt = conn.prepareStatement(WRITE_PUBLICATION_IMAGEURL_SQL);
        pstmt.setString(1, url);
        pstmt.setInt(2, publicationid);
        pstmt.executeUpdate();       
    }
    
}
