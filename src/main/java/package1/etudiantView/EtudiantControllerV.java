package package1.etudiantView;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import package1.Etudiants.Etudiant;
import package1.Etudiants.EtudiantRepository;
import package1.Note.Note;
import package1.Note.NoteRepository;
import package1.absence.Absence;
import package1.absence.AbsenceRepository;
import package1.cours.Cours;
import package1.cours.CoursRepository;
import package1.paiement.Paiement;
import package1.paiement.PaiementRepository;



@Controller
@RequestMapping("/etudiant")
public class EtudiantControllerV {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private AbsenceRepository absenceRepository;
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private PaiementRepository paiementrepository;
    
    @Autowired
    private CoursRepository coursrepo;
    
    
    @GetMapping("/absences")
    public String viewAbsences(Authentication authentication, Model model) {
        String email = authentication.getName();
        Etudiant etudiant = etudiantRepository.findByemail(email).orElse(null);
        if (etudiant == null) {
            return "redirect:/login"; // Redirect to login if the student is not found
        }

        List<Absence> absences = absenceRepository.findByEtudiantId(etudiant.getId());
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("absences", absences);
        return "etudiant/absence-etudiant";
    }

    @GetMapping("/notes")
    public String viewNotes(Authentication authentication, Model model) {
        String email = authentication.getName();
        Etudiant etudiant = etudiantRepository.findByemail(email).orElse(null);
        if (etudiant == null) {
            return "redirect:/login"; // Redirect to login if the student is not found
        }

        List<Note> notes = noteRepository.findByEtudiantId(etudiant.getId());
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("notes", notes);
        return "etudiant/notes-etudiant";
    }
    
    @GetMapping("/paiements")
    public String viewPaiements(Authentication authentication, Model model) {
        String email = authentication.getName();
        Etudiant etudiant = etudiantRepository.findByemail(email).orElse(null);
        if (etudiant == null) {
            return "redirect:/login"; // Redirect to login if the student is not found
        }

        List<Paiement> paiements = paiementrepository.findByEtudiantId(etudiant.getId());
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("paiements", paiements);
        return "etudiant/paiement-etudiant";
    }
    
    @GetMapping("/cours")
	public String cours(Model model) {
		List<Cours> listcours = coursrepo.findAll();
		model.addAttribute("listcours",listcours);
		return "courses";
	}
	
	@GetMapping("/cours/{coursId}")
	public String AboutCours(@PathVariable("coursId") int coursId,Model model) {
		Optional<Cours> optcours = coursrepo.findById(coursId);
		if(optcours.isPresent()){
			Cours cours = optcours.get();
			 model.addAttribute("cours", cours);
			 return"about-course"; 
	        } else {
	        	return"about-course"; 
	        }
		
	}
}

