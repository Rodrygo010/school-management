package package1.paiement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long>{

	List<Paiement> findByEtudiantId(int etudiantId);

}
