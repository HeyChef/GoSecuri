package epsi.chef.gosecuri.entity;

import java.util.Date;

import com.google.cloud.firestore.annotation.Exclude;

public class User {
    private String name;
    private String imageURL;
    private String label;
    private Date   cdate;
    @Exclude
    private String userId;

    public User( long id, String name, String label, Date cdate ) {
        super();
        this.name = name;
        this.label = label;
        this.cdate = cdate;
    }

    public User() {

    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL( String imageURL ) {
        this.imageURL = imageURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId( String userId ) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate( Date cdate ) {
        this.cdate = cdate;
    }

}
