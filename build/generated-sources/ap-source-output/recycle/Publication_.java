package recycle;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import recycle.Address;
import recycle.Category;
import recycle.Subscription;
import recycle.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-31T17:27:00")
@StaticMetamodel(Publication.class)
public class Publication_ { 

    public static volatile CollectionAttribute<Publication, Subscription> subscriptionCollection;
    public static volatile SingularAttribute<Publication, String> pickupStartime;
    public static volatile SingularAttribute<Publication, String> pickupEndtime;
    public static volatile SingularAttribute<Publication, String> imageURL;
    public static volatile SingularAttribute<Publication, String> description;
    public static volatile SingularAttribute<Publication, Long> id;
    public static volatile SingularAttribute<Publication, String> title;
    public static volatile SingularAttribute<Publication, User> userId;
    public static volatile SingularAttribute<Publication, Category> categoryId;
    public static volatile SingularAttribute<Publication, Address> addressid;
    public static volatile SingularAttribute<Publication, String> pickuptype;
    public static volatile SingularAttribute<Publication, String> timestamp;

}