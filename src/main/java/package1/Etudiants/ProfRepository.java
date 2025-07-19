package package1.Etudiants;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfRepository extends JpaRepository<Professeurs, Integer> {

	 Professeurs findByNom(String nom);

	Optional<Professeurs> findByEmail(String email);
}
