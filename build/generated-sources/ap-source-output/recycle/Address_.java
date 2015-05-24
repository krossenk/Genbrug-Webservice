package recycle;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import recycle.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-24T11:11:04")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, Integer> zipcode;
    public static volatile SingularAttribute<Address, String> country;
    public static volatile SingularAttribute<Address, String> city;
    public static volatile SingularAttribute<Address, String> street;
    public static volatile CollectionAttribute<Address, User> userCollection;
    public static volatile SingularAttribute<Address, Integer> id;

}