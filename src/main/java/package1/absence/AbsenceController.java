package package1.absence;

import java.sql.Date;
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
@RequestMapping("/absences")
public class AbsenceController {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @GetMapping("/{etudiantId}/absences")
    public String getAbsencesByEtudiantId(@PathVariable("etudiantId") int etudiantId, Model model) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant == null) {
            return "redirect:/";
        }

        List<Absence> absences = absenceRepository.findByEtudiantId(etudiantId);
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("absences", absences);

        return "absence/afficher";
    }

    @GetMapping("/etudiant/{etudiantId}/ajouter-absence")
    public String showAddAbsenceForm(@PathVariable("etudiantId") int etudiantId, Model model) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant == null) {
            return "redirect:/";
        }
        List<Matiére> matieres = matiereRepository.findAll(); // Get the list of matieres
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("matieres", matieres); // Add the list of matieres to the model
        return "absence/addAbsence";
    }

    @PostMapping("/etudiant/{etudiantId}/ajouter-absence")
    public String addAbsence(@PathVariable("etudiantId") int etudiantId, @RequestParam("matiereIds") List<Integer> matiereIds, @RequestParam Date dateAbsence, @RequestParam String reason, @RequestParam int heures) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant == null) {
            return "redirect:/";
        }
        
        List<Matiére> matieres = matiereRepository.findAllById(matiereIds);
        Absence absence = new Absence();
        absence.setEtudiant(etudiant);
        absence.setReason(reason);
        absence.setMatieres(matieres);
        absence.setDateAbsence(dateAbsence);
        absence.setHeures(heures);
        absenceRepository.save(absence);
        return "redirect:/absences/" + etudiantId + "/absences";
    }
    
    @GetMapping("/etudiant/{etudiantId}/modifier-absence/{absenceId}")
    public String showUpdateAbsenceForm(@PathVariable("etudiantId") int etudiantId, @PathVariable("absenceId") Long absenceId, Model model) {
        Absence absence = absenceRepository.findById(absenceId).orElse(null);
        if (absence == null) {
            return "redirect:/absences/" + etudiantId + "/absences";
        }
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant == null) {
            return "redirect:/etudiants";
        }
        List<Matiére> matieres = matiereRepository.findAll(); // Get the list of matieres
        model.addAttribute("absence", absence);
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("matieres", matieres); // Add the list of matieres to the model
        return "absence/updateAbsence";
    }


    @PostMapping("/etudiant/{etudiantId}/modifier-absence/{absenceId}")
    public String updateAbsence(@PathVariable("etudiantId") int etudiantId, @PathVariable("absenceId") Long absenceId, @RequestParam("matiereIds") List<Integer> matiereIds, @RequestParam Date dateAbsence, @RequestParam String reason, @RequestParam int heures) {
        Absence absence = absenceRepository.findById(absenceId).orElse(null);
        if (absence == null) {
            return "redirect:/absences/" + etudiantId + "/absences";
        }

        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant == null) {
            return "redirect:/etudiants";
        }
        
        List<Matiére> matieres = matiereRepository.findAllById(matiereIds);
        absence.setReason(reason);
        absence.setMatieres(matieres);
        absence.setDateAbsence(dateAbsence);
        absence.setHeures(heures);
        absenceRepository.save(absence);
        return "redirect:/absences/" + etudiantId + "/absences";
    }

    @PostMapping("/{absenceId}/supprimer")
    public String deleteNote(@PathVariable("absenceId") Long absenceId) {
    	Absence absence = absenceRepository.findById(absenceId).orElse(null);
        if (absence != null) {
            absenceRepository.delete(absence);
        }
        return "redirect:/absences/" + absence.getEtudiant().getId() + "/absences";
    }

}