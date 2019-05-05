package epsi.chef.gosecuri.entity;

import com.google.cloud.firestore.annotation.Exclude;

public class Location {
    private String userId;
    private String equipmentsId;
    @Exclude
    private String docId;

    public Location( String userId, String equipmentsId ) {
        super();
        this.userId = userId;
        this.equipmentsId = equipmentsId;
    }

    public Location() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId( String userId ) {
        this.userId = userId;
    }

    public String getEquipmentsId() {
        return equipmentsId;
    }

    public void setEquipmentsId( String equipmentsId ) {
        this.equipmentsId = equipmentsId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId( String docId ) {
        this.docId = docId;
    }
}
