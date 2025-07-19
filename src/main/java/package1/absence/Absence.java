package package1.absence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import package1.Etudiants.Etudiant;
import package1.matiere.Matiére;

@Entity
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateAbsence;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;
    

    @ManyToMany
    @JoinTable(
        name = "absence_matiere",
        joinColumns = @JoinColumn(name = "absence_id"),
        inverseJoinColumns = @JoinColumn(name = "matiere_id")
    )
    private List<Matiére> matieres = new ArrayList<>();
    
    private int heures;

    // Getters and setters
    

    // getters et setters pour heures
    public int getHeures() {
        return heures;
    }

    public void setHeures(int heures) {
        this.heures = heures;
    }

    
    public Long getId() {
        return id;
    }

   

	
	public List<Matiére> getMatieres() {
		return matieres;
	}

	public void setMatieres(List<Matiére> matieres) {
		this.matieres = matieres;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Date getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(Date dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}


