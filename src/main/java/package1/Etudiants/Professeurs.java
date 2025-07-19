package package1.Etudiants;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import package1.absence.Absence;
import package1.cours.Cours;
import package1.matiere.MatiereRepository;
import package1.matiere.Matiére;

@Entity
public class Professeurs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String email;
	private String motdepasse;
	private String Adresse;
	private String Sex;
	private int numtel;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date Datenaissance;
	private String classe;
	private String Image;
	private String nom;
	private String prenom;
	private String age;
	
	
	private String role;
	
	
	
	
	
	
	 @OneToMany(mappedBy = "professeurs", cascade = CascadeType.ALL)
	    private Set<Cours> cours;
	 
	 @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	    @JoinTable(
	        name = "professeurs_matieres",
	        joinColumns = @JoinColumn(name = "professeur_id"),
	        inverseJoinColumns = @JoinColumn(name = "matiere_id")
	    )
	    private Set<Matiére> matieres = new HashSet<>();

	   

	   
	            	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Set<Cours> getCours() {
		return cours;
	}
	public void setCours(Set<Cours> cours) {
		this.cours = cours;
	}
	public Set<Matiére> getMatieres() {
		return matieres;
	}
	public void setMatieres(Set<Matiére> matieres) {
		this.matieres = matieres;
	}
	
	public void setMatieres(List<MatiereRepository> matieres2) {
		// TODO Auto-generated method stub
		
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
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
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotdepasse() {
		return motdepasse;
	}
	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}
	public String getAdresse() {
		return Adresse;
	}
	public void setAdresse(String adresse) {
		Adresse = adresse;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public int getNumtel() {
		return numtel;
	}
	public void setNumtel(int numtel) {
		this.numtel = numtel;
	}
	public Date getDatenaissance() {
		return Datenaissance;
	}
	public void setDatenaissance(Date datenaissance) {
		Datenaissance = datenaissance;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public Professeurs(int id, String email, String motdepasse, String adresse, String sex, int numtel,
			Date datenaissance, String classe) {
		super();
		this.id = id;
		this.email = email;
		this.motdepasse = motdepasse;
		Adresse = adresse;
		Sex = sex;
		this.numtel = numtel;
		Datenaissance = datenaissance;
		this.classe = classe;
	}
	public Professeurs() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Professeurs [id=" + id + ", email=" + email + ", motdepasse=" + motdepasse + ", Adresse=" + Adresse
				+ ", Sex=" + Sex + ", numtel=" + numtel + ", Datenaissance=" + Datenaissance + ", classe=" + classe
				+ "]";
	}
	
	
	
}
