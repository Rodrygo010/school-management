package package1.matiere;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import package1.Etudiants.Etudiant;
import package1.Etudiants.Professeurs;
import package1.Note.Note;
import package1.Note.NoteRepository;
import package1.absence.Absence;


@Controller
@RequestMapping("/matiere")
public class MatiereController {

    @Autowired
    private MatiereRepository matiererepo;

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/matiere")
    public String matiere(Model model) {
        List<Matiére> ListMatiere = matiererepo.findAll();
        model.addAttribute("ListMatiere", ListMatiere);
        return "matiere";
    }

    @GetMapping("/add-matiere")
    public String addmatiere(Model model) {
        return "add-matiere";
    }

    @PostMapping("/add-matiere")
    public String AjouterEtudiant(@RequestParam String matiére) throws IOException {
        Matiére matiere = new Matiére();
        matiere.setMatiére(matiére);
        matiererepo.save(matiere);
        return "redirect:/matiere/matiere";
    }

    @GetMapping("/delete-matiere/{id}")
    public String supprimer(@PathVariable("id") Integer id) {
        Optional<Matiére> matiereOptional = matiererepo.findById(id);
        if (matiereOptional.isPresent()) {
            Matiére matiere = matiereOptional.get();

            // Dissocier les notes associées
            for (Note note : matiere.getNotes()) {
                note.setMatiere(null);
            }
            matiere.getNotes().clear();

            // Dissocier les absences associées
            for (Absence absence : matiere.getAbsences()) {
                absence.setMatieres(null);
            }
            matiere.getAbsences().clear();

            // Dissocier les professeurs associés
            for (Professeurs prof : matiere.getProfesseurs()) {
                prof.getMatieres().remove(matiere);
            }
            matiere.getProfesseurs().clear();

            matiererepo.save(matiere);
            matiererepo.delete(matiere);
        }
        return "redirect:/matiere/matiere";
    }
    

    @GetMapping("/edit-matiere/{id}")
    public String afficherFormulaireEdition(@PathVariable int id, Model model) {
        Optional<Matiére> matiereOptional = matiererepo.findById(id);
        if (matiereOptional.isPresent()) {
            Matiére matiere = matiereOptional.get();
            model.addAttribute("matiere", matiere);
            return "edit-matiere";
        } else {
            // Handle the case where the etudiant with the given ID is not found
            return "redirect:/matiere/matiere";
        }
    }

    @PostMapping("/edit-matiere/{id}")
    public String editerEtudiant(@PathVariable int id,
                                 @RequestParam String matiére,
                                 RedirectAttributes redirectAttributes) throws IOException {
        Optional<Matiére> matiereOptional = matiererepo.findById(id);
        if (matiereOptional.isPresent()) {
            Matiére Matiere = matiereOptional.get();
            Matiere.setMatiére(matiére);
            matiererepo.save(Matiere);
            return "redirect:/matiere/matiere";
        } else {
            // Handle the case where the etudiant with the given ID is not found
            return "redirect:/matiere/matiere";
        }
    }
}

	   

