package package1.Etudiants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import package1.MyUserDetailService;
import package1.classe.Classe;
import package1.classe.ClasseRepository;
import package1.niveau.Niveau;
import package1.niveau.NiveauRepository;




@Controller
@RequestMapping("/etudiants")
public class EtudiantController {
    @Autowired
    public EtudiantRepository etudiantrepo;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    public MyUserDetailService myUserDetailService;
    @Autowired
    public ProfRepository profrepo;
    @Autowired
    private NiveauRepository niveauRepository;

    @Autowired
    private ClasseRepository classeRepository;
  
    
    
  
    
    @GetMapping("/etudiant")
    public String VoirEtudiants(Model model) {
    	List<Etudiant> etudiant = etudiantrepo.findAll();
    	for (Etudiant etudiant1 : etudiant) {
            int age = calculateAge(etudiant1.getDate_naissance());
            etudiant1.setAge(age);
        }
    	model.addAttribute("etudiant", etudiant);
    		return "etudiant";
    }
    
    private int calculateAge(Date dateOfBirth) {
        LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
    
    @GetMapping("/etudiant/{id}")
    public String afficherDetailsEtudiant(@PathVariable("id") int id, Model model) {
        Optional<Etudiant> etudiantOpt = etudiantrepo.findById(id);
       
        if (etudiantOpt.isPresent()) {
            Etudiant etudiant = etudiantOpt.get();
            int age = calculateAge(etudiant.getDate_naissance());
            etudiant.setAge(age); // Calculer et définir l'âge de l'étudiant
            model.addAttribute("etudiant", etudiant);
            return "about-etudiant"; // Le nom de la vue pour afficher les détails de l'étudiant
        } else {
        	return "redirect:/etudiants/etudiant"; // Redirection vers la liste des étudiants si l'étudiant n'est pas trouvé
        }
    }
    
    
    
    

   

   
  

    
    @GetMapping("/ajouteretudiant")
    public String AfficherFormAjout(Model model) { 
    	List<Niveau> niveaux = niveauRepository.findAll();
        List<Classe> classes = classeRepository.findAll();
        
        model.addAttribute("niveaux", niveaux);
        model.addAttribute("classes", classes);
        return "add-etudiant";
    }
    
    @PostMapping("/ajouteretudiant")
    public String AjouterEtudiant(@RequestParam String adresse,
                                   @RequestParam String nom,
                                   @RequestParam String prenom,
                                   @RequestParam String email,
                                   @RequestParam String mdp,
                                   @RequestParam Long classeId, // Change parameter type to Long
                                   @RequestParam Long niveauId, // Add parameter for niveauId
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_naissance,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_inscription,
                                   @RequestParam int num_tel,
                                   @RequestParam String nom_tuteur,
                                   @RequestParam int num_tuteur,
                                   @RequestParam MultipartFile photo_etudiant,
                                   @RequestParam String sex,
                                   @RequestParam String role,
                                   @RequestParam double initialTotalPaiement) throws IOException {
        Etudiant etudiant = new Etudiant();
        etudiant.setAdresse(adresse);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);
        etudiant.setMotdepasse(passwordEncoder.encode(mdp));

        // Set the date of birth, registration date, and other details

        etudiant.setDate_naissance(date_naissance);
        etudiant.setDate_inscription(date_inscription);
        etudiant.setNumtel(num_tel);
        etudiant.setNom_tuteur(nom_tuteur);
        etudiant.setNum_tuteur(num_tuteur);
        etudiant.setPhoto_etudiant(Base64.encodeBase64String(photo_etudiant.getBytes()));
        etudiant.setSex(sex);
        etudiant.setRole(role);
        etudiant.setInitialTotalPaiement(initialTotalPaiement);
        etudiant.setTotalPaiement(initialTotalPaiement);
        
        // Retrieve and set the Classe and Niveau
        Classe classe = classeRepository.findById(classeId).orElse(null);
        Niveau niveau = niveauRepository.findById(niveauId).orElse(null);
        if (classe != null && niveau != null) {
            etudiant.setClasse(classe);
            etudiant.getClasse().setNiveau(niveau);
        }
        
        etudiantrepo.save(etudiant);
        return "redirect:/etudiants/etudiant";
    }



@GetMapping("/delet/{id}")
public String supprimer(@PathVariable("id") Integer id) {
	
	etudiantrepo.deleteById(id);
	
	return "redirect:/etudiants/etudiant";
}




@GetMapping("/edit/{id}")
public String afficherFormulaireEdition(@PathVariable int id, Model model) {
    Optional<Etudiant> etudiantOptional = etudiantrepo.findById(id);
    
    List<Niveau> niveaux = niveauRepository.findAll();
    List<Classe> classes = classeRepository.findAll();
    
    if (etudiantOptional.isPresent()) {
        Etudiant etudiant = etudiantOptional.get();
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("niveaux", niveaux);
        model.addAttribute("classes", classes);
        
        return "edit-etudiant";
    } else {
        // Handle the case where the etudiant with the given ID is not found
    	return "redirect:/etudiants/etudiant";
    }
}

@PostMapping("/edit/{id}")
public String editerEtudiant(@PathVariable int id,
                              @RequestParam String adresse,
                              @RequestParam String nom,
                              @RequestParam String prenom,
                              @RequestParam String email,
                              @RequestParam String motdepasse,
                              @RequestParam Long classeId, // Change parameter type to Long
                              @RequestParam Long niveauId, // Add parameter for niveauId
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_naissance,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_inscription,
                              @RequestParam int numtel,
                              @RequestParam String nom_tuteur,
                              @RequestParam int num_tuteur,
                              @RequestParam MultipartFile photo_etudiant,
                              @RequestParam String sex,
                              @RequestParam String role,
                              @RequestParam double initialTotalPaiement) throws IOException {
    Optional<Etudiant> etudiantOptional = etudiantrepo.findById(id);
    if (etudiantOptional.isPresent()) {
        Etudiant etudiant = etudiantOptional.get();
        etudiant.setAdresse(adresse);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        
        etudiant.setEmail(email);
        // Vérification si le mot de passe a été modifié
        if (motdepasse != null && !motdepasse.isEmpty()) {
            etudiant.setMotdepasse(passwordEncoder.encode(motdepasse));
        }
        
        etudiant.setDate_naissance(date_naissance);
        etudiant.setDate_inscription(date_inscription);
        etudiant.setNumtel(numtel);
        etudiant.setNom_tuteur(nom_tuteur);
        etudiant.setNum_tuteur(num_tuteur);
        if (!photo_etudiant.isEmpty()) {
            etudiant.setPhoto_etudiant(Base64.encodeBase64String(photo_etudiant.getBytes()));
        }
        etudiant.setSex(sex);
        etudiant.setRole(role);
        etudiant.setInitialTotalPaiement(initialTotalPaiement);
        etudiant.setTotalPaiement(initialTotalPaiement);
        
        
     // Retrieve and set the Classe and Niveau
        Classe classe = classeRepository.findById(classeId).orElse(null);
        Niveau niveau = niveauRepository.findById(niveauId).orElse(null);
        if (classe != null && niveau != null) {
            etudiant.setClasse(classe);
            etudiant.getClasse().setNiveau(niveau);
        }
        
        etudiantrepo.save(etudiant);
        return "redirect:/etudiants/etudiant";
    } else {
        // Handle the case where the etudiant with the given ID is not found
    	return "redirect:/etudiants/etudiant";
    }
}


}