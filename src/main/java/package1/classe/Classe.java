package package1.classe;


import java.util.List;

import jakarta.persistence.*;
import package1.Etudiants.Etudiant;
import package1.cours.Cours;
import package1.niveau.Niveau;

@Entity
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;
    
    @OneToMany(mappedBy = "classe")
    private List<Etudiant> etudiants;
    
    @OneToMany(mappedBy = "classe")
    private List<Cours> cours;
    
    
    

	public List<Cours> getCours() {
		return cours;
	}

	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}

	public List<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

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

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

    
}    
	


