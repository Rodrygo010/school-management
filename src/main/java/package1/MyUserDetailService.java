package package1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import package1.Etudiants.Etudiant;
import package1.Etudiants.EtudiantRepository;
import package1.Etudiants.ProfRepository;
import package1.Etudiants.Professeurs;
import package1.admin.Admin;
import package1.admin.AdminRepository;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService 
{

    @Autowired
    private EtudiantRepository repository;

    @Autowired
    private AdminRepository adminrepository;
    
    @Autowired
    private ProfRepository profRepository;
    
    @Override
    public UserDetails loadUserByUsername(@RequestParam("email") String email) throws UsernameNotFoundException {
        Optional<Etudiant> user = repository.findByemail(email);
        if (user.isPresent()) {
            Etudiant userObj = user.get();
            return User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getMotdepasse())
                    .roles("ETUDIANT")
                    .build();
        } 
        
        Optional<Admin> admin = adminrepository.findByUsername(email);
        if (admin.isPresent()) {
            Admin adminObj = admin.get();
            return User.builder()
                    .username(adminObj.getUsername())
                    .password(adminObj.getPassword())
                    .roles("ADMIN")
                    .build();
        } 
        
        Optional<Professeurs> professeur  = profRepository.findByEmail(email);
        if (professeur .isPresent()) {
            Professeurs prof  = professeur.get();
            return User.builder()
                .username(prof .getEmail())
                .password(prof .getMotdepasse())
                .roles("PROF")
                .build();
        }

        
        
        
            throw new UsernameNotFoundException(email);
        
    }
  


    
}
