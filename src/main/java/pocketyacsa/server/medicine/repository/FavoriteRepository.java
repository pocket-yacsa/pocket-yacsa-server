package pocketyacsa.server.medicine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pocketyacsa.server.medicine.entity.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

  boolean existsByMemberIdAndMedicineId(int memberId, int medicineId);
}
