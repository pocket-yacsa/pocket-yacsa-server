package pocketyacsa.server.medicine.repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pocketyacsa.server.medicine.domain.entity.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

  boolean existsByMemberIdAndMedicineId(int memberId, int medicineId);

  int countByMemberId(int memberId);

  List<Favorite> findByMemberId(int memberId, PageRequest pageable);
}
