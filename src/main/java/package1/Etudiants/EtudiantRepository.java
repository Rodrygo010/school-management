package package1.Etudiants;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface EtudiantRepository extends JpaRepository<Etudiant,Integer> {

    Optional<Etudiant> findByemail(String email);

	
   

}
