/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recycle;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Morten
 */
@Entity
@Table(name = "publication")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Publication.findAll", query = "SELECT p FROM Publication p"),
    @NamedQuery(name = "Publication.findById", query = "SELECT p FROM Publication p WHERE p.id = :id"),
    @NamedQuery(name = "Publication.findByTitle", query = "SELECT p FROM Publication p WHERE p.title = :title"),
    @NamedQuery(name = "Publication.findByDescription", query = "SELECT p FROM Publication p WHERE p.description = :description"),
    @NamedQuery(name = "Publication.findByPickuptype", query = "SELECT p FROM Publication p WHERE p.pickuptype = :pickuptype"),
    @NamedQuery(name = "Publication.findByImageURL", query = "SELECT p FROM Publication p WHERE p.imageURL = :imageURL"),
    @NamedQuery(name = "Publication.findByPickupStartime", query = "SELECT p FROM Publication p WHERE p.pickupStartime = :pickupStartime"),
    @NamedQuery(name = "Publication.findByPickupEndtime", query = "SELECT p FROM Publication p WHERE p.pickupEndtime = :pickupEndtime"),
    @NamedQuery(name = "Publication.findByTimestamp", query = "SELECT p FROM Publication p WHERE p.timestamp = :timestamp")})
public class Publication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 150)
    @Column(name = "title")
    private String title;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 45)
    @Column(name = "pickuptype")
    private String pickuptype;
    @Size(max = 500)
    @Column(name = "imageURL")
    private String imageURL;
    @Size(max = 255)
    @Column(name = "pickup_startime")
    private String pickupStartime;
    @Size(max = 255)
    @Column(name = "pickup_endtime")
    private String pickupEndtime;
    @Size(max = 255)
    @Column(name = "timestamp")
    private String timestamp;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "Address_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address addressid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicationId")
    private Collection<Subscription> subscriptionCollection;

    public Publication() {
    }

    public Publication(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPickuptype() {
        return pickuptype;
    }

    public void setPickuptype(String pickuptype) {
        this.pickuptype = pickuptype;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPickupStartime() {
        return pickupStartime;
    }

    public void setPickupStartime(String pickupStartime) {
        this.pickupStartime = pickupStartime;
    }

    public String getPickupEndtime() {
        return pickupEndtime;
    }

    public void setPickupEndtime(String pickupEndtime) {
        this.pickupEndtime = pickupEndtime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Address getAddressid() {
        return addressid;
    }

    public void setAddressid(Address addressid) {
        this.addressid = addressid;
    }

    @XmlTransient
    public Collection<Subscription> getSubscriptionCollection() {
        return subscriptionCollection;
    }

    public void setSubscriptionCollection(Collection<Subscription> subscriptionCollection) {
        this.subscriptionCollection = subscriptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publication)) {
            return false;
        }
        Publication other = (Publication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "recycle.Publication[ id=" + id + " ]";
    }
    
}
