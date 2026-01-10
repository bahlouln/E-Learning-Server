package spring.jpa.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Groupe {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private int nombreLimite;

    // Relation ManyToMany avec Etudiant
    @ManyToMany
    @JoinTable(
        name = "groupe_etudiants",
        joinColumns = @JoinColumn(name = "groupe_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "etudiant_matricule", referencedColumnName = "matricule")
    )
    private List<Etudiant> etudiants = new ArrayList<>();



    // Relation ManyToOne avec Cours
    @ManyToOne
    private Cours cours;

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNombreLimite() {
        return nombreLimite;
    }

    public void setNombreLimite(int nombreLimite) {
        this.nombreLimite = nombreLimite;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }
}
