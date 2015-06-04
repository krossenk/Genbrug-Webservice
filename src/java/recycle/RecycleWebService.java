/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recycle;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
/**
 *
 * @author Morten
 */
@WebService(serviceName = "RecycleWebService")
public class RecycleWebService 
{
    private String serverURL = "http://vmi19372.iry.dk:8880/RecycleWebService/images/";  
    
    
    
       /**
    * Creates a new User in the database. 
    * 
    * @param  user a newly created User object, not including the id.
    * @return      the id of the newly created User object, or -1 if there was an error.
    * @see         User
    */
    @WebMethod(operationName = "createUser")
    public long createUser(@WebParam(name = "user")User user)
    {
        long returnId = -1;
        Connection conn = null;
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            conn = RecycleSQLConnection.getConnection();
            
            returnId = sqlConn.writeUserObject(conn, user);
            
            conn.close();            
            
        } catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnId;
    }
    
           /**
     * returns a User object from the database based on id. 
     * 
     * @param  id   the id of user one wishes to retrieve
     * @return      the specified user if one exists, otherwise it returns null
     * @see         User
     */
    
    @WebMethod(operationName = "getUser")
    public User getUser(@WebParam(name = "id")long id)
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            return sqlConn.readUserObject(conn, id);            
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
       /**
    * Updates a User object in the database. 
    * 
    * @param  user a User object containing the changes to be updated, remember the id.
    * @see         User
    */
    @WebMethod(operationName = "updateUser")
    public void updateUser(@WebParam(name = "User")User user)
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            sqlConn.updateUserObject(conn, user);
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
           /**
     * validates User credentials against the database. 
     * 
     * @param  username a String containing the username.
     * @param  password a String containing the password.
     * @return      if the user is validated the User object will be returned.
     * @see         User
     */
    
    @WebMethod(operationName = "validateUser")
    public User validateUser(@WebParam(name = "username")String username,@WebParam(name = "password") String password)
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            return sqlConn.validateUserObject(conn, username, password);            
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }
    
              /**
     * Creates a new Publication object in the database. 
     * 
     * @param  publication a Publication object containing the newly created publication.
     * @return      the id of the newly created Publication, or -1 if there was an error.
     * @see         Publication
     */
    @WebMethod(operationName = "createPublication")
    public long createPublication(@WebParam(name = "publication")Publication publication)
    {
        long returnId = -1;
        Connection conn = null;
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            conn = RecycleSQLConnection.getConnection();
            
            returnId = sqlConn.createPublicationObject(conn, publication);
            
            conn.close(); 
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return returnId;
    }
    
              /**
    * returns a Publication object from the database based on id. 
    * 
    * @param  id   the id of the publication one wishes to retrieve
    * @return      the specified publication if one exists, otherwise it returns null
    * @see         Publication
    */
    
    @WebMethod(operationName = "getPublication")
    public Publication getPublication(@WebParam(name = "id")long id)
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            return sqlConn.readPublicationObject(conn, id);            
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
              /**
    * Returns a list of all Publication objects from the database in descending order. 
    * 
    * @return      a list of all Publication objects from the database in descending order.
    * @see         Publication
    */
    
    @WebMethod(operationName = "getAllPublications")
    public Collection<Publication> getAllPublications()
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            return sqlConn.readAllPublicationObjects(conn);            
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
              /**
    * Returns a list of all Subscriptions associated with the publication id specified. 
    * 
    * @param publicationId the id of the publication.
    * @return      a list of all subscription objects associated with the publication.
    * @see         Subscription
    */
    
    @WebMethod(operationName = "getPublicationSubscriptions")
    public Collection<Subscription> getPublicationSubscriptions(@WebParam(name = "publicationId")long publicationId)
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            return sqlConn.readPublicationSubscriptions(conn, publicationId);            
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
                  /**
    * Returns a list of all Subscriptions associated with the user id specified. 
    * 
    * @param userId the id of the user.
    * @return      a list of all subscription objects associated with the user.
    * @see         Subscription
    */
    
    @WebMethod(operationName = "getUserSubscriptions")
    public Collection<Subscription> getUserSubscriptions(@WebParam(name = "userId")long userId)
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            return sqlConn.readUserSubscriptions(conn, userId);
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
                 /**
    * Creates a new Subscription object in the database. 
    * 
    * @param  userId the id of the user associated with this subscription.
    * @param  publicationId the id of the publication associated with this subscription.
    * @see         Subscription
    */
    
    @WebMethod(operationName = "createSubscription")
    public void createSubscription(@WebParam(name = "userId")long userId,@WebParam(name = "publicationId") long publicationId)
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            sqlConn.createSubscriptionObject(conn, publicationId, userId);
        }
        catch(Exception ex)
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
                 /**
    * Saves a Publication image onto the server, and assigns a unique name for the image
    * 
    * @param  filename the name of the file.
    * @param  filedata a bytearray containing the image data.
    * @param  publicationId the id of the publication.
    * @return a String containing the url for the image on the server.
    * @see         Publication
    */
    
    @WebMethod(operationName = "saveImage")
    public String saveImage(@WebParam(name = "filename")String filename,@WebParam(name = "filedata") byte[] filedata,@WebParam(name = "publicationId")long publicationId)
    {
        String returnURL = "";
        String localFileName = "";
        
        Path path = Paths.get("/usr/local/glassfish/glassfish4/glassfish/domains/domain2/applications/RecycleWebService/images");
                      
        try
        {
            Date tmpDate = new Date();
            String[] fileNameParts = filename.split("\\.");
            if(fileNameParts.length > 1)
            {
                localFileName = fileNameParts[0] + tmpDate.getTime() + "." + fileNameParts[1];
            
                FileOutputStream outStream = new FileOutputStream(path.toString() + "/" + localFileName);
                BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream);
                bufOutStream.write(filedata);
                bufOutStream.close();            
            }
            else
            {
                return "filename is not correctly formatted !! (is there a .jpg extension ?)";
            }
            
        }
        catch(IOException ex)
        {
            return ex.getMessage();
        }
        
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            sqlConn.saveImageURLToDb(conn, serverURL + localFileName, publicationId);
            return serverURL + localFileName;
        }
        catch(Exception ex)
        {
            returnURL = ex.getMessage();
        }
        
        return returnURL;
    }
    
                  /**
    * Returns a list of all Category objects from the database. 
    * 
    * @return      a Collection of all Category objects from the database, or null if none exists.
    * @see         Category
    */
    
    @WebMethod(operationName = "getAllCategories")
    public Collection<Category> getAllCategories()
    {
        try
        {
            RecycleSQLConnection sqlConn = new RecycleSQLConnection();
            Connection conn = RecycleSQLConnection.getConnection();
            
            return sqlConn.readAllCategoryObjects(conn);
        }
        catch(Exception ex)
        {
            Logger.getLogger(RecycleWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
