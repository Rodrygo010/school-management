package package1.paiement;

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



@Controller
@RequestMapping("/paiements")
public class PaiementController {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @GetMapping("/paiements")
    public String getPaiements(Model model) {
        model.addAttribute("paiements", paiementRepository.findAll());
        model.addAttribute("etudiants", etudiantRepository.findAll());
        return "paiement/paiement";
    }
    
    @GetMapping("/ajouterpaiement")
    public String addPaiements(Model model) {
        model.addAttribute("etudiants", etudiantRepository.findAll());
        return "paiement/ajouter-paiement";
    }

    @PostMapping("/ajouterpaiement")
    public String ajouterPaiement(@RequestParam int etudiantId,
                                  @RequestParam double montant,
                                  @RequestParam Date datePaiement) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant != null) {
            Paiement paiement = new Paiement();
            paiement.setEtudiant(etudiant);
            paiement.setMontant(montant);
            paiement.setDatePaiement(datePaiement);

            etudiant.setTotalPaiement(etudiant.getTotalPaiement() - montant);
            etudiantRepository.save(etudiant);
            paiementRepository.save(paiement);
        }
        return "redirect:/paiements/paiements";
    }
    
    @GetMapping("/etudiant")
    public String getPaiementsParEtudiant(@RequestParam("etudiantId") int etudiantId, Model model) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant != null) {
            List<Paiement> paiements = paiementRepository.findByEtudiantId(etudiantId);
            model.addAttribute("etudiant", etudiant);
            model.addAttribute("paiements", paiements);
        }
        return "paiement/paiements-etudiant";
    }

}

