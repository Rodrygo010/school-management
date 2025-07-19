package package1.cours;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import package1.Etudiants.ProfRepository;
import package1.Etudiants.Professeurs;
import package1.classe.Classe;
import package1.classe.ClasseRepository;

@Controller
@RequestMapping("/cours")
public class CoursController {
	@Autowired
	public CoursRepository coursrepo;
	@Autowired
	public ProfRepository profrepo;
	@Autowired
	public ClasseRepository classeRepository;

	
	@GetMapping("/cours")
	public String cours(Model model) {
		List<Cours> listcours = coursrepo.findAll();
		model.addAttribute("listcours",listcours);
		return "cours";
	}
	
	
	@GetMapping("/cours/{coursId}")
	public String AboutCours(@PathVariable("coursId") int coursId,Model model) {
		Optional<Cours> optcours = coursrepo.findById(coursId);
		if(optcours.isPresent()){
			Cours cours = optcours.get();
			 model.addAttribute("cours", cours);
			 return"about-courses"; 
	        } else {
	        	return"about-courses"; 
	        }
		
	}
	
	
	@GetMapping("/ajouter-cours")
	public String addcours(Authentication authentication, Model model) {
	    String email = authentication.getName();
	    Professeurs prof = profrepo.findByEmail(email).orElse(null);
	   

	    model.addAttribute("prof", prof);

	    boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	    if (isAdmin) {
	        List<Professeurs> ListProfs = profrepo.findAll();
	        model.addAttribute("ListProfs", ListProfs);
	    }

	    model.addAttribute("isAdmin", isAdmin);
	    model.addAttribute("classes", classeRepository.findAll());
	    model.addAttribute("cours", new Cours());
	    return "add-cours";
	}

	@PostMapping("/ajouter-cours")
	public String addcours(
	        @RequestParam String titre,
	        @RequestParam MultipartFile imagecours,
	        @RequestParam MultipartFile videocours,
	        @RequestParam String coursinfo,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date datecours,
	        @RequestParam Long classeId,
	        @RequestParam(required = false) String nomprof, // This is optional for admin
	        Authentication authentication,
	        RedirectAttributes redirectAttributes) {

	    String email = authentication.getName();
	    Professeurs prof = profrepo.findByEmail(email).orElse(null);

	    Cours cours = new Cours();
	    cours.setTitre(titre);
	    cours.setDatecours(datecours);
	    cours.setCoursInformation(coursinfo);

	    // Check if the user is admin
	    boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	    if (isAdmin) {
	        if (nomprof != null) { // Only set if not null (admin)
	            Professeurs selectedProf = profrepo.findByNom(nomprof);
	            cours.setProfesseurs(selectedProf);
	        }
	    } else {
	        // If not admin, set the professor based on the authenticated user
	        if (prof != null) {
	            cours.setProfesseurs(prof);
	        }
	    }

	    Classe classe = classeRepository.findById(classeId).orElse(null);
	    if (classe != null) {
	        cours.setClasse(classe);
	    }

	    try {
	        if (!imagecours.isEmpty()) {
	            cours.setImageCours(Base64.encodeBase64String(imagecours.getBytes()));
	        }
	        if (!videocours.isEmpty()) {
	            cours.setVedioCours(Base64.encodeBase64String(videocours.getBytes()));
	        }
	    } catch (IOException e) {
	        // Handle the exception
	        // Add proper error handling here
	    }

	    coursrepo.save(cours);
	    return "redirect:/cours/cours";
	}

	
	@GetMapping("/delet-cours/{coursId}")
	public String supprimer(@PathVariable("coursId") Integer coursId) {
		
		coursrepo.deleteById(coursId);
		
		 return "redirect:/cours/cours"; 
	}

		
	
	    

	@GetMapping("/edit-cours/{coursId}")
	public String afficherFormulaireEdition(@PathVariable int coursId, Authentication authentication, Model model) {
	    Optional<Cours> coursopt = coursrepo.findById(coursId);
	    String email = authentication.getName();
	    Professeurs prof = profrepo.findByEmail(email).orElse(null);

	    if (coursopt.isPresent()) {
	        Cours cours = coursopt.get();
	        model.addAttribute("cours", cours);

	        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	        if (isAdmin) {
	            List<Professeurs> ListProfs = profrepo.findAll();
	            model.addAttribute("ListProfs", ListProfs);
	        }

	        model.addAttribute("isAdmin", isAdmin);
	        model.addAttribute("prof", prof);
	        model.addAttribute("classes", classeRepository.findAll());
	        return "edit-courses";
	    } else {
	        // Handle the case where the course with the given ID is not found
	        return "redirect:/cours/cours";
	    }
	}

	@PostMapping("/edit-cours/{coursId}")
	public String editercours(
	        @PathVariable int coursId,
	        @RequestParam String titre,
	        @RequestParam MultipartFile imageCours,
	        @RequestParam MultipartFile vedioCours,
	        @RequestParam String CoursInformation,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date datecours,
	        @RequestParam Long classeId,
	        @RequestParam(required = false) String nomprof,
	        Authentication authentication,
	        RedirectAttributes redirectAttributes) throws IOException {

	    Optional<Cours> Coursopt = coursrepo.findById(coursId);

	    if (Coursopt.isPresent()) {
	        Cours cours = Coursopt.get();
	        cours.setTitre(titre);
	        cours.setDatecours(datecours);
	        cours.setCoursInformation(CoursInformation);

	        // Check if the user is admin
	        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	        if (isAdmin) {
	            if (nomprof != null) { // Only set if not null (admin)
	                Professeurs selectedProf = profrepo.findByNom(nomprof);
	                cours.setProfesseurs(selectedProf);
	            }
	        } else {
	            // If not admin, set the professor based on the authenticated user
	            String email = authentication.getName();
	            Professeurs prof = profrepo.findByEmail(email).orElse(null);
	            if (prof != null) {
	                cours.setProfesseurs(prof);
	            }
	        }

	        Classe classe = classeRepository.findById(classeId).orElse(null);
	        if (classe != null) {
	            cours.setClasse(classe);
	        }

	        if (!imageCours.isEmpty()) {
	            cours.setImageCours(Base64.encodeBase64String(imageCours.getBytes()));
	        }

	        if (!vedioCours.isEmpty()) {
	            cours.setVedioCours(Base64.encodeBase64String(vedioCours.getBytes()));
	        }

	        coursrepo.save(cours);
	        return "redirect:/cours/cours";
	    } else {
	        return "redirect:/cours/cours";
	    }
	}



	
}
