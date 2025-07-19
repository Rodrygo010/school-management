package package1.Note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import package1.Etudiants.Etudiant;
import package1.Etudiants.EtudiantRepository;
import package1.matiere.MatiereRepository;
import package1.matiere.Matiére;

@Controller
@RequestMapping("/notes")
public class NoteController {

	    @Autowired
	    private NoteRepository noteRepository;

	    @Autowired
	    private EtudiantRepository etudiantRepository;
	    
	    @Autowired
	    private MatiereRepository matiereRepository;


	    @GetMapping("/{etudiantId}/notes")
	    public String getNotesByEtudiantId(@PathVariable("etudiantId") int etudiantId, Model model) {
	        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
	        if (etudiant == null) {
	            return "redirect:/";
	        }

	        List<Note> notes = noteRepository.findByEtudiantId(etudiantId);
	        List<Matiére> matieres = matiereRepository.findAll();

	        model.addAttribute("etudiant", etudiant);
	        model.addAttribute("notes", notes);
	        model.addAttribute("matieres", matieres);

	        return "note/afficher";
	    }


	    @GetMapping("/etudiant/{etudiantId}/ajouter-note")
	    public String showAddNoteForm(@PathVariable("etudiantId") int etudiantId, Model model) {
	        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
	        if (etudiant == null) {
	            return "redirect:/";
	        }
	        List<Matiére> matieres = matiereRepository.findAll(); // Récupérer la liste des matières
	        model.addAttribute("etudiant", etudiant);
	        model.addAttribute("matieres", matieres); // Ajouter la liste des matières au modèle
	        return "note/addNote";
	    }

	    @PostMapping("/etudiant/{etudiantId}/ajouter-note")
	    public String addNote(@PathVariable("etudiantId") int etudiantId, 
	                          @RequestParam int matiereId, 
	                          
	                          @RequestParam double noteExam, 
	                          @RequestParam double noteControle, 
	                          @RequestParam double noteProf) {
	        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
	        Matiére matiere = matiereRepository.findById(matiereId).orElse(null);
	        if (etudiant == null || matiere == null) {
	            return "redirect:/";
	        }
	        Note note = new Note();
	        note.setMatiere(matiere);
	        
	        note.setNoteExam(noteExam);
	        note.setNoteControle(noteControle);
	        note.setNoteProf(noteProf);
	        note.setEtudiant(etudiant);
	        noteRepository.save(note);
	        return "redirect:/notes/" + etudiantId + "/notes";
	    }
	    
	    @GetMapping("/{noteId}/modifier")
	    public String showUpdateNoteForm(@PathVariable("noteId") Long noteId, Model model) {
	        Note note = noteRepository.findById(noteId).orElse(null);
	        if (note == null) {
	            return "redirect:/";
	        }
	        List<Matiére> matieres = matiereRepository.findAll();
	        model.addAttribute("note", note);
	        model.addAttribute("matieres", matieres);
	        return "note/updateNote";
	    }

	    @PostMapping("/{noteId}/modifier")
	    public String updateNote(@PathVariable("noteId") Long noteId, 
	                             @RequestParam int matiereId, 
	                              
	                             @RequestParam double noteExam, 
	                             @RequestParam double noteControle, 
	                             @RequestParam double noteProf) {
	        Note note = noteRepository.findById(noteId).orElse(null);
	        Matiére matiere = matiereRepository.findById(matiereId).orElse(null);
	        if (note == null || matiere == null) {
	            return "redirect:/";
	        }
	        note.setMatiere(matiere);
	        
	        note.setNoteExam(noteExam);
	        note.setNoteControle(noteControle);
	        note.setNoteProf(noteProf);
	        noteRepository.save(note);
	        return "redirect:/notes/" + note.getEtudiant().getId() + "/notes";
	    }

	    @PostMapping("/{noteId}/supprimer")
	    public String deleteNote(@PathVariable("noteId") Long noteId) {
	        Note note = noteRepository.findById(noteId).orElse(null);
	        if (note != null) {
	            noteRepository.delete(note);
	        }
	        return "redirect:/notes/" + note.getEtudiant().getId() + "/notes";
	    }

	    
	}
	