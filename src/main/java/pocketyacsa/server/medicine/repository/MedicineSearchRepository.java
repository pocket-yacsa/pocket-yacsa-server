package pocketyacsa.server.medicine.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pocketyacsa.server.medicine.domain.response.MedicineSearch;

public interface MedicineSearchRepository extends ElasticsearchRepository<MedicineSearch, Integer> {

  int countByName(String name);

  public List<MedicineSearch> findByName(String name, Pageable pageable);
}
