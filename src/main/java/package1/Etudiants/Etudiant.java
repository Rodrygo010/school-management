package package1.Etudiants;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import package1.Note.Note;
import package1.absence.Absence;
import package1.classe.Classe;
import package1.paiement.Paiement;

@Entity
public class Etudiant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String motdepasse;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_inscription;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_naissance;
	private int numtel;
	private String adresse;
	private String nom_tuteur;
	private int num_tuteur;
	private String Photo_etudiant;
	private String Sex;
	private int age;
	
	
	private String role;
	
	
	@ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

	 private double totalPaiement; // Add this field
	    private double initialTotalPaiement; // New field

	    @OneToMany(mappedBy = "etudiant")
	    private Set<Paiement> paiements;
	    
	    
	    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Note> notes;
	    
	    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Absence> absences;
	
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getMotdepasse() {
		return motdepasse;
	}
	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}
	
	public Date getDate_naissance() {
		return date_naissance;
	}
	public void setDate_naissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}
	public Date getDate_inscription() {
		return date_inscription;
	}
	public void setDate_inscription(Date date_inscription) {
		this.date_inscription = date_inscription;
	}
	public int getNumtel() {
		return numtel;
	}
	public void setNumtel(int numtel) {
		this.numtel = numtel;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getNom_tuteur() {
		return nom_tuteur;
	}
	public void setNom_tuteur(String nom_tuteur) {
		this.nom_tuteur = nom_tuteur;
	}
	public int getNum_tuteur() {
		return num_tuteur;
	}
	public void setNum_tuteur(int num_tuteur) {
		this.num_tuteur = num_tuteur;
	}
	public String getPhoto_etudiant() {
		return Photo_etudiant;
	}
	public void setPhoto_etudiant(String photo_etudiant) {
		Photo_etudiant = photo_etudiant;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public Classe getClasse() {
		return classe;
	}
	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	public double getTotalPaiement() {
		return totalPaiement;
	}
	public void setTotalPaiement(double totalPaiement) {
		this.totalPaiement = totalPaiement;
	}
	public double getInitialTotalPaiement() {
		return initialTotalPaiement;
	}
	public void setInitialTotalPaiement(double initialTotalPaiement) {
		this.initialTotalPaiement = initialTotalPaiement;
	}
	public Set<Paiement> getPaiements() {
		return paiements;
	}
	public void setPaiements(Set<Paiement> paiements) {
		this.paiements = paiements;
	}
	
	
	
	
	
}
