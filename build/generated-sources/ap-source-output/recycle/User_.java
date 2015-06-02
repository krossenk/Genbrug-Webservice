package recycle;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import recycle.Publication;
import recycle.Subscription;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-31T17:27:00")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, Subscription> subscriptionCollection;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> firstname;
    public static volatile CollectionAttribute<User, Publication> publicationCollection;
    public static volatile SingularAttribute<User, String> phonenumber;
    public static volatile SingularAttribute<User, String> usertype;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> profileimageURL;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> lastname;

}