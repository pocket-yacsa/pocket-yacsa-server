package pocketyacsa.server.medicine.repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pocketyacsa.server.medicine.domain.entity.DetectionLog;

@Repository
public interface DetectionLogRepository extends JpaRepository<DetectionLog, Integer> {

  int countByMemberId(int memberId);

  List<DetectionLog> findByMemberId(int memberId, PageRequest pageable);
}
