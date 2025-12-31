package package1.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class AdminController {

	@Autowired
	private AdminRepository adminrepo;
	@Autowired
    public PasswordEncoder passwordEncoder;
	
	 @GetMapping("/ajoutAdmin")
	    public String AfficherFormAjout(Model model) { 
	    	
	        return "add-admin";
	    }
	    
	    @PostMapping("/ajoutAdmin")
	    public String AjouterEtudiant(@RequestParam String nom,
	    		                      @RequestParam String prenom,
	                                  @RequestParam String username,
	                                  @RequestParam String password,
	                                  @RequestParam String role) throws IOException {
	        Admin admin = new Admin();
	        admin.setNom(nom);
	        admin.setPrenom(prenom);
	        admin.setUsername(username);
	        admin.setRole(role);
	        admin.setPassword(passwordEncoder.encode(password));

	        	        
	        adminrepo.save(admin);
	        return "redirect:/etudiant";
	    }
	    
	    

}
