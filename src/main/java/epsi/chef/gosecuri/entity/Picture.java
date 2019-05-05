package epsi.chef.gosecuri.entity;

import com.google.cloud.firestore.annotation.Exclude;

public class Picture {

    private String faceToken;
    private String imageURL;
    private String userId;
    @Exclude
    private String docId;

    public Picture( String faceToken, String imageURL, String userId ) {
        super();
        this.faceToken = faceToken;
        this.imageURL = imageURL;
        this.userId = userId;
    }

    public Picture() {

    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken( String faceToken ) {
        this.faceToken = faceToken;
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

    public String getDocId() {
        return docId;
    }

    public void setDocId( String docId ) {
        this.docId = docId;
    }

}
