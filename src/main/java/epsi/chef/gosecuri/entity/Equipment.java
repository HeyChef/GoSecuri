package epsi.chef.gosecuri.entity;

import com.google.cloud.firestore.annotation.Exclude;

public class Equipment {

    private Long    quantite;
    private String  libelle;
    @Exclude
    private String  docId;
    @Exclude
    private boolean isChecked = false;

    public Equipment( Long quantite, String libelle ) {
        super();
        this.quantite = quantite;
        this.libelle = libelle;
    }

    public Equipment() {

    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite( Long quantite ) {
        this.quantite = quantite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle( String libelle ) {
        this.libelle = libelle;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId( String docId ) {
        this.docId = docId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked( boolean isChecked ) {
        this.isChecked = isChecked;
    }
}
