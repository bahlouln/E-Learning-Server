package spring.jpa.model;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Etudiant {

    @Id
    @Column(unique = true)
    private String matricule;

    private String nom;
    private String prenom;
    private String email;

    @Temporal(TemporalType.DATE)  
    private Date dateInscription;

    // Many-to-Many avec Cours
    @ManyToMany(mappedBy = "etudiants")
    private List<Cours> coursInscrits = new ArrayList<>();

    // One-to-Many vers Inscription
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Inscription> inscriptions = new ArrayList<>();

    // One-to-Many vers Note
    @OneToMany(mappedBy = "etudiant")
    private List<Note> notes = new ArrayList<>();

    
    
    
	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}

	public List<Cours> getCoursInscrits() {
		return coursInscrits;
	}

	public void setCoursInscrits(List<Cours> coursInscrits) {
		this.coursInscrits = coursInscrits;
	}

	public List<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	}

    
