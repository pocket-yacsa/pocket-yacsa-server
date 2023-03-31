package pocketyacsa.server.medicine.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pocketyacsa.server.medicine.domain.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

  Optional<Medicine> findByCode(String code);
}
