package spring.jpa.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Etudiant {

    @Id
    @Column(nullable = false, unique = true)
    private String matricule; // identifiant métier (non auto-généré)

    private String nom;
    private String prenom;
    private String email;

    @Column(name = "date_inscription")
    private String dateInscription; // tu peux utiliser LocalDate si tu veux



    // 🔹 Relation ManyToMany avec Groupe
    @ManyToMany(mappedBy = "etudiants", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Groupe> groupes = new ArrayList<>();


    // --- Getters & Setters ---
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

    public String getDateInscription() {
        return dateInscription;
    }
    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }


    public List<Groupe> getGroupes() {
        return groupes;
    }
    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }
}
