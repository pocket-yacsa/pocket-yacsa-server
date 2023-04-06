package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.common.utility.Constant.pageSize;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.KEYWORD_NOT_EXIST;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.PAGE_OUT_OF_RANGE;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.SEARCH_RESULT_NOT_EXIST;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.entity.MedicineSearch;
import pocketyacsa.server.medicine.domain.response.MedicineSearchPageRes;
import pocketyacsa.server.medicine.repository.MedicineSearchRepository;
import pocketyacsa.server.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class MedicineSearchService {

  private final MedicineSearchRepository repository;
  private final MemberService memberService;
  private final RedisTemplate redisTemplate;

  /**
   * 특정 name의 medicine 검색결과를 반환합니다. page를 넘겨줌으로써 특정 페이지의 정보로 제공됩니다.
   *
   * @param name 의약품 검색어
   * @param page 검색결과 페이지
   * @return 특정 page의 name으로 검색한 결과
   */
  public MedicineSearchPageRes getMedicineSearchesByNameAndPage(String name, int page) {
    if (name.isEmpty()) {
      throw new BadRequestException(KEYWORD_NOT_EXIST.getErrorResponse());
    }
    int totalSize = repository.countByName(name);
    int totalPages = (int) Math.ceil((double) totalSize / pageSize);

    if (totalSize == 0) {
      throw new BadRequestException(SEARCH_RESULT_NOT_EXIST.getErrorResponse());
    }
    if (page < 1 || page > totalPages) {
      throw new BadRequestException(PAGE_OUT_OF_RANGE.getErrorResponse());
    }

    List<MedicineSearch> searchResults = repository.findByName(name,
        PageRequest.of(page - 1, pageSize));

    boolean lastPage = (page == totalPages);

    return MedicineSearchPageRes.builder()
        .total(totalSize)
        .totalPage(totalPages)
        .page(page)
        .lastPage(lastPage)
        .medicineSearchList(searchResults)
        .build();
  }

  /**
   * 특정 검색어와 연관도가 높은 검색어를 최대 10개까지 반환합니다.
   *
   * @param name 의약품 검색어
   * @return 연관도가 높은 검색어들
   */
  public List<String> getMedicineNamesByKeyword(String name) {
    if (name.isEmpty()) {
      throw new BadRequestException(KEYWORD_NOT_EXIST.getErrorResponse());
    }
    List<MedicineSearch> searchResults = repository.findByName(name,
        PageRequest.of(0, 10));

    List<String> medicineNames = searchResults.stream()
        .map(MedicineSearch::getName)
        .collect(Collectors.toList());

    return medicineNames;
  }
}
