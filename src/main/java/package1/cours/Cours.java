package package1.cours;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import package1.Etudiants.Professeurs;
import package1.classe.Classe;

@Entity

public class Cours {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int coursId;
	
	private String titre;
	private String imageCours;
	private String vedioCours;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datecours;
	private String CoursInformation;
	
	
	
	
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_id")
    private Professeurs professeurs;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;
    
    
    
    
    
    
   

    
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public int getCoursId() {
		return coursId;
	}

	public void setCoursId(int coursId) {
		this.coursId = coursId;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getImageCours() {
		return imageCours;
	}

	public void setImageCours(String imageCours) {
		this.imageCours = imageCours;
	}

	public String getVedioCours() {
		return vedioCours;
	}

	public void setVedioCours(String vedioCours) {
		this.vedioCours = vedioCours;
	}

	public Date getDatecours() {
		return datecours;
	}

	public void setDatecours(Date datecours) {
		this.datecours = datecours;
	}

	public String getCoursInformation() {
		return CoursInformation;
	}

	public void setCoursInformation(String coursInformation) {
		CoursInformation = coursInformation;
	}

	

	public Professeurs getProfesseurs() {
		return professeurs;
	}

	public void setProfesseurs(Professeurs professeurs) {
		this.professeurs = professeurs;
	}
	
	
	
}
