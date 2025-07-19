package package1.Etudiants;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import package1.Note.Note;
import package1.Note.NoteRepository;
import package1.matiere.MatiereRepository;
import package1.matiere.Matiére;

@Controller
@RequestMapping("/professeurs")
public class ProfController {
@Autowired
private ProfRepository profRepo;
@Autowired
private MatiereRepository matiereRepository;
@Autowired
private NoteRepository noteRepository;
@Autowired
public PasswordEncoder passwordEncoder;

    @GetMapping("/professeurs")
	public String AfficherProf(Model model){
		List<Professeurs> prof = profRepo.findAll();
		List<Matiére> listMatieres = matiereRepository.findAll();
		model.addAttribute("prof", prof);
		model.addAttribute("listMatieres", listMatieres);
		return "prof";		
	}
    
   
    @GetMapping("/ajouterprof")
    public String AfficherFormAjout(Model model) {   
    	List<Matiére> listMatieres = matiereRepository.findAll();
        model.addAttribute("listMatieres", listMatieres);
        return "add-prof";
    }
    
    @PostMapping("/ajouterprof")
    public String AjouterEtudiant(@RequestParam String adresse,
                                  @RequestParam String nom,
                                  @RequestParam String prenom,
                                  @RequestParam String email,
                                  @RequestParam String mdp,
                                  @RequestParam String classe,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_naissance,
                                  @RequestParam List<Integer> matieresIds,
                                  @RequestParam int numtel,
                                  @RequestParam String role,
                                  @RequestParam MultipartFile image,
                                  @RequestParam String sex
                                   ) throws IOException {
        Professeurs prof = new Professeurs();
        prof.setAdresse(adresse);
        prof.setNom(nom);
        prof.setPrenom(prenom);
        prof.setEmail(email);
        prof.setMotdepasse(passwordEncoder.encode(mdp));
        prof.setClasse(classe);
        prof.setDatenaissance(date_naissance);
        
        prof.setNumtel(numtel);
        
        prof.setImage(Base64.encodeBase64String(image.getBytes()));
        prof.setSex(sex);
        
        List<Matiére> matieres = matiereRepository.findAllById(matieresIds);
        Set<Matiére> matieresSet = new HashSet<>(matieres);
        prof.setMatieres(matieresSet);

        prof.setRole(role);
        
        profRepo.save(prof);
        
        for (Integer matiereId : matieresIds) {
            Matiére matiere = matiereRepository.findById(matiereId).orElseThrow(() -> new IllegalArgumentException("Invalid matiere Id:" + matiereId));
            matiere.getProfesseurs().add(prof);
            matiereRepository.save(matiere);
        }
        
        return "redirect:/professeurs/professeurs";
    }
    
    @GetMapping("/supprimer/{id}")
    public String deleteProf(@PathVariable int id) {
        // Rechercher le professeur par ID
        Optional<Professeurs> professeurOptional = profRepo.findById(id);
        if (professeurOptional.isPresent()) {
            Professeurs professeur = professeurOptional.get();
            
            // Dissocier les matières du professeur avant la suppression
            professeur.getMatieres().clear();
            profRepo.save(professeur);
            
            // Supprimer le professeur
            profRepo.delete(professeur);
        }
        
    
		return "redirect:/professeurs/professeurs";
    	
    }
    
   
   
    @GetMapping("/edit-prof/{id}")
    public String Editerprof(@PathVariable int id,Model model) {    
        Optional<Professeurs> optprof = profRepo.findById(id);
        List<Matiére> listMatieres = matiereRepository.findAll();
        if (optprof.isPresent()) {
        Professeurs prof = optprof.get();
        model.addAttribute("prof",prof);
        model.addAttribute("listMatieres", listMatieres);
        return"edit-professor";
        }
        else {
            // Handle the case where the etudiant with the given ID is not found
            return "redirect:/professeurs/professeurs";
        }
    }
    
    @PostMapping("/edit-prof/{id}")
    public String Editerprof(@PathVariable int id,
    		                      @RequestParam String adresse,
                                  @RequestParam String nom,
                                  @RequestParam String prenom,
                                  @RequestParam String email,
                                  @RequestParam String motdepasse,
                                  @RequestParam String classe,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date Datenaissance,
                                  @RequestParam String role,
                                  @RequestParam int numtel,
                                  @RequestParam List<Integer> matieresIds,
                                  @RequestParam MultipartFile Image,
                                  @RequestParam String sex,
                                  RedirectAttributes redirectAttributes
                                   ) throws IOException {
            Optional<Professeurs> optprof = profRepo.findById(id);
    	    if (optprof.isPresent()) {
    	        Professeurs prof = optprof.get();
    	        
        prof.setAdresse(adresse);
        prof.setNom(nom);
        prof.setPrenom(prenom);
        prof.setEmail(email);
        prof.setMotdepasse(passwordEncoder.encode(motdepasse));
        prof.setClasse(classe);
        prof.setDatenaissance(Datenaissance);
        
        prof.setNumtel(numtel);
        if (!Image.isEmpty()) {
        	prof.setImage(Base64.encodeBase64String(Image.getBytes()));
        }
        prof.setSex(sex);
        
        List<Matiére> matieres = matiereRepository.findAllById(matieresIds);
        Set<Matiére> matieresSet = new HashSet<>(matieres);
        prof.setMatieres(matieresSet);
        for (Integer matiereId : matieresIds) {
            Matiére matiere = matiereRepository.findById(matiereId).orElseThrow(() -> new IllegalArgumentException("Invalid matiere Id:" + matiereId));
            matiere.getProfesseurs().add(prof);
            matiereRepository.save(matiere);
        }
        
        prof.setRole(role);
        profRepo.save(prof);
        return "redirect:/professeurs/professeurs";
    	    } else {
    	        // Handle the case where the etudiant with the given ID is not found
    	        return "redirect:/professeurs/professeurs";
    	    }
    }
}
