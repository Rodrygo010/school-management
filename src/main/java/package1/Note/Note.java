package package1.Note;



import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import package1.Etudiants.Etudiant;
import package1.matiere.Matiére;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valeur;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matiere_id")
    private Matiére matiere;
    
    private double noteExam;
    private double noteControle;
    private double noteProf;
    
    
    


    // Getters and setters

    public double getNoteExam() {
		return noteExam;
	}

	public void setNoteExam(double noteExam) {
		this.noteExam = noteExam;
	}

	public double getNoteControle() {
		return noteControle;
	}

	public void setNoteControle(double noteControle) {
		this.noteControle = noteControle;
	}

	public double getNoteProf() {
		return noteProf;
	}

	public void setNoteProf(double noteProf) {
		this.noteProf = noteProf;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Matiére getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiére matiere) {
        this.matiere = matiere;
    }
}
