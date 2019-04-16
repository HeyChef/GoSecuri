package epsi.chef.gosecuri.entity;

public class Materiel {

	Long id;
	String libelle;

	public Materiel(Long id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
