package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.common.utility.Constant.KEYWORD_SIZE;
import static pocketyacsa.server.common.utility.Constant.PAGE_SIZE;
import static pocketyacsa.server.common.utility.Constant.RECENT_KEYWORD_SIZE;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.KEYWORD_NOT_EXIST;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.PAGE_OUT_OF_RANGE;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.SEARCH_LOG_NOT_EXIST;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.SEARCH_RESULT_NOT_EXIST;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.response.MedicineSearch;
import pocketyacsa.server.medicine.domain.response.MedicineSearchPageRes;
import pocketyacsa.server.medicine.repository.MedicineSearchRepository;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class MedicineSearchService {

  private final MedicineSearchRepository repository;
  private final MemberService memberService;
  private final StringRedisTemplate redisTemplate;

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
    int totalPages = (int) Math.ceil((double) totalSize / PAGE_SIZE);

    if (totalSize == 0) {
      throw new BadRequestException(SEARCH_RESULT_NOT_EXIST.getErrorResponse());
    }
    if (page < 1 || page > totalPages) {
      throw new BadRequestException(PAGE_OUT_OF_RANGE.getErrorResponse());
    }

    List<MedicineSearch> searchResults = repository.findByName(name,
        PageRequest.of(page - 1, PAGE_SIZE));

    boolean lastPage = (page == totalPages);

    MedicineSearchPageRes response = MedicineSearchPageRes.builder().total(totalSize)
        .totalPage(totalPages).page(page)
        .lastPage(lastPage).medicineSearchList(searchResults).build();

    return response;
  }

  /**
   * 특정 name의 medicine 검색결과의 첫번째 페이지를 반환합니다. 이 때 검색기록에 추가됩니다.
   *
   * @param name 의약품 검색어
   * @return name으로 검색한 첫번째 결과
   */
  public MedicineSearchPageRes getMedicineSearchAtFist(String name) {
    if (name.isEmpty()) {
      throw new BadRequestException(KEYWORD_NOT_EXIST.getErrorResponse());
    }

    saveRecentSearchLog(name);

    int totalSize = repository.countByName(name);
    int totalPages = (int) Math.ceil((double) totalSize / PAGE_SIZE);

    if (totalSize == 0) {
      throw new BadRequestException(SEARCH_RESULT_NOT_EXIST.getErrorResponse());
    }

    List<MedicineSearch> searchResults = repository.findByName(name,
        PageRequest.of(0, PAGE_SIZE));

    boolean lastPage = (totalPages == 1);

    MedicineSearchPageRes response = MedicineSearchPageRes.builder().total(totalSize)
        .totalPage(totalPages).page(1)
        .lastPage(lastPage).medicineSearchList(searchResults).build();

    return response;
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
        PageRequest.of(0, KEYWORD_SIZE));

    List<String> medicineNames = searchResults.stream().map(MedicineSearch::getName)
        .collect(Collectors.toList());

    return medicineNames;
  }

  /**
   * 검색기록에 검색어를 추가합니다.
   *
   * @param name 검색기록에 추가할 검색어
   */
  public void saveRecentSearchLog(String name) {
    Member loginMember = memberService.getLoginMember();
    String key = searchLogKey(loginMember.getId());
    String value = name;

    Long size = redisTemplate.opsForList().size(key);
    if (size == (long) RECENT_KEYWORD_SIZE) {
      redisTemplate.opsForList().rightPop(key);
    }

    redisTemplate.opsForList().leftPush(key, value);
  }

  /**
   * 최근 검색기록을 RECENT_KEYWORD_SIZE만큼 반환합니다.
   *
   * @return 최근 검색기록들
   */
  public List<String> getRecentSearchLogs() {
    Member loginMember = memberService.getLoginMember();
    String key = searchLogKey(loginMember.getId());
    List<String> logs = redisTemplate.opsForList().
        range(key, 0, RECENT_KEYWORD_SIZE);

    return logs;
  }

  /**
   * 특정 인덱스의 검색기록을 삭제합니다.
   *
   * @param index 검색기록의 index
   */
  public void deleteRecentSearchLog(int index) {
    Member loginMember = memberService.getLoginMember();
    String key = searchLogKey(loginMember.getId());
    String value = redisTemplate.opsForList().index(key, index);
    long count = redisTemplate.opsForList().remove(key, index, value);

    if (count == 0) {
      throw new BadRequestException(SEARCH_LOG_NOT_EXIST.getErrorResponse());
    }
  }

  /**
   * 전체 검색기록을 삭제합니다.
   */
  public void deleteRecentSearchLogs() {
    Member loginMember = memberService.getLoginMember();
    String key = searchLogKey(loginMember.getId());
    Boolean isDeleted = redisTemplate.delete(key);

    if (!isDeleted) {
      throw new BadRequestException(SEARCH_LOG_NOT_EXIST.getErrorResponse());
    }
  }

  /**
   * redis에 저장할 사용자의 검색기록 key를 생성합니다.
   *
   * @param memberId member의 id
   * @return redis에 저장할 검색기록 key
   */
  private String searchLogKey(int memberId) {
    return memberId + " SearchLog";
  }
}
