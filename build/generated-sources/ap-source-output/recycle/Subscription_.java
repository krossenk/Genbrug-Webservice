package recycle;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import recycle.Publication;
import recycle.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-24T11:11:04")
@StaticMetamodel(Subscription.class)
public class Subscription_ { 

    public static volatile SingularAttribute<Subscription, Long> id;
    public static volatile SingularAttribute<Subscription, Publication> publicationId;
    public static volatile SingularAttribute<Subscription, User> userId;

}