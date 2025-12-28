package spring.jpa.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Cours {
	@Id
	@GeneratedValue
	private Long id; // identifiant technique auto-généré
	@Column(unique = true, nullable = false)	
	 private String code;
	 private String titre;
	 private String description;
	 @ManyToMany(mappedBy = "cours")
	 private List<Formateur> formateurs = new ArrayList<>();
	 @ManyToMany
	 @JoinTable(
	     name = "cours_etudiants",
	     joinColumns = @JoinColumn(name = "cours_id"),
	     inverseJoinColumns = @JoinColumn(name = "etudiant_matricule")
	 )
	 private List<Etudiant> etudiants = new ArrayList<>();



	 @OneToMany(mappedBy = "cours")
	 private List<Inscription> inscriptions = new ArrayList<>();
	 
	 
	 public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Formateur> getFormateurs() {
		return formateurs;
	}
	public void setFormateurs(List<Formateur> formateurs) {
		this.formateurs = formateurs;
	}
	public List<Etudiant> getEtudiants() {
		return etudiants;
	}
	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}
	public List<Inscription> getInscriptions() {
		return inscriptions;
	}
	public void setInscriptions(List<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}
	 

}
