package package1.matiere;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import package1.Etudiants.Professeurs;
import package1.Note.Note;
import package1.absence.Absence;
import jakarta.persistence.JoinColumn;


@Entity
public class Matiére {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String matiére;
	
	  @ManyToMany(mappedBy = "matieres")
	    private Set<Professeurs> professeurs = new HashSet<>();

	  @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Note> notes;
	    
	  @ManyToMany(mappedBy = "matieres")
	    private List<Absence> absences;
	  
	    
	    
	    
	   
	    

	public List<Note> getNotes() {
			return notes;
		}

		public void setNotes(List<Note> notes) {
			this.notes = notes;
		}

		public List<Absence> getAbsences() {
			return absences;
		}

		public void setAbsences(List<Absence> absences) {
			this.absences = absences;
		}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatiére() {
		return matiére;
	}

	public void setMatiére(String matiére) {
		this.matiére = matiére;
	}

	public Set<Professeurs> getProfesseurs() {
		return professeurs;
	}

	public void setProfesseurs(Set<Professeurs> professeurs) {
		this.professeurs = professeurs;
	}
	
	
}
