package package1.niveau;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/niveaux")
public class NiveauController {
    @Autowired
    private NiveauRepository niveauRepository;

    @GetMapping
    public String getAllNiveaux(Model model) {
        List<Niveau> niveaux = niveauRepository.findAll();
        model.addAttribute("niveaux", niveaux);
        return "liste_niveaux";
    }

    // Autres méthodes du contrôleur pour les opérations CRUD (Création, Lecture, Mise à jour, Suppression) sur Niveau
}
