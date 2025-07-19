package package1.classe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import package1.niveau.Niveau;
import package1.niveau.NiveauRepository;

@Controller
@RequestMapping("/classes")
public class ClasseController {
    @Autowired
    private ClasseRepository classeRepository;
     @Autowired
     private NiveauRepository niveauRepository;

    @GetMapping
    public String getAllClasses(Model model) {
        List<Classe> classes = classeRepository.findAll();
        model.addAttribute("classes", classes);
        return "classe/liste_classes";
    }

    @GetMapping("/ajouter")
    public String afficherFormulaireAjoutClasse(Model model) {
        Classe classe = new Classe();
        List<Niveau> niveaux = niveauRepository.findAll();
        model.addAttribute("classe", classe);
        model.addAttribute("niveaux", niveaux);
        return "classe/ajout-classe";
    }
    
    @PostMapping("/ajouter")
    public String ajouterClasse(@ModelAttribute("classe") Classe classe) {
        classeRepository.save(classe);
        return "redirect:/classes";
    }
    
    // Autres méthodes du contrôleur pour les opérations CRUD sur Classe
}


