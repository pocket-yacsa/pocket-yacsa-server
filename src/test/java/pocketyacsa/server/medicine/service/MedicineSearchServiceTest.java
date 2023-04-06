package pocketyacsa.server.medicine.service;

import static org.mockito.Mockito.when;
import static pocketyacsa.server.common.utility.Constant.PAGE_SIZE;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.entity.MedicineSearch;
import pocketyacsa.server.medicine.domain.redisValue.SearchLogRedis;
import pocketyacsa.server.medicine.domain.response.MedicineSearchPageRes;
import pocketyacsa.server.medicine.repository.MedicineSearchRepository;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
class MedicineSearchServiceTest {

  @Mock
  MedicineSearchRepository medicineSearchRepository;

  @Mock
  MemberService memberService;

  @Mock
  RedisTemplate<String, SearchLogRedis> redisTemplate;

  @InjectMocks
  MedicineSearchService medicineSearchService;

  Member member;

  @BeforeEach
  public void setUp() {
    member = Member.builder()
        .id(1)
        .name("hong")
        .email("hong@yacsa.com")
        .picture("picture-url")
        .deleted(false)
        .build();
  }

  @Test
  public void getMedicineSearchesByNameAndPage_ReturnFirstPage() {
    String name = "medicine";
    int page = 1;
    int count = PAGE_SIZE + 1;
    List<MedicineSearch> searchResult = new ArrayList<>();

    for (int i = 1; i <= PAGE_SIZE; i++) {
      MedicineSearch medicineSearch = MedicineSearch.builder().id(i).name(name)
          .company("a").image("a").build();

      searchResult.add(medicineSearch);
    }

    List<MedicineSearch> searchResultFirstPage = searchResult.subList(0, PAGE_SIZE);
    MedicineSearchPageRes medicineSearchPageRes = MedicineSearchPageRes.builder().total(count)
        .totalPage((int) Math.ceil((double) count / PAGE_SIZE))
        .page(page)
        .lastPage(false)
        .medicineSearchList(searchResultFirstPage)
        .build();

    when(medicineSearchRepository.countByName(name)).thenReturn(count);
    when(medicineSearchRepository.findByName(name, PageRequest.of(page - 1, PAGE_SIZE)))
        .thenReturn(searchResult);

    MedicineSearchPageRes result =
        medicineSearchService.getMedicineSearchesByNameAndPage(name, page);

    Assertions.assertEquals(result, medicineSearchPageRes);
  }

  @Test
  public void getMedicineSearchesByNameAndPage_ReturnLastPage() {
    String name = "medicine";
    int page = 2;
    int count = PAGE_SIZE + 1;
    List<MedicineSearch> searchResult = new ArrayList<>();

    for (int i = PAGE_SIZE + 1; i <= count; i++) {
      MedicineSearch medicineSearch = MedicineSearch.builder().id(i).name(name)
          .company("a").image("a").build();

      searchResult.add(medicineSearch);
    }

    List<MedicineSearch> searchResultFirstPage = searchResult.subList(0, 1);
    MedicineSearchPageRes medicineSearchPageRes = MedicineSearchPageRes.builder().total(count)
        .totalPage((int) Math.ceil((double) count / PAGE_SIZE))
        .page(page)
        .lastPage(true)
        .medicineSearchList(searchResultFirstPage)
        .build();

    when(medicineSearchRepository.countByName(name)).thenReturn(count);
    when(medicineSearchRepository.findByName(name, PageRequest.of(page - 1, PAGE_SIZE)))
        .thenReturn(searchResult);

    MedicineSearchPageRes result =
        medicineSearchService.getMedicineSearchesByNameAndPage(name, page);

    Assertions.assertEquals(result, medicineSearchPageRes);
  }

  @Test
  public void getMedicineSearchesByNameAndPage_ReturnPageZero() {
    String name = "medicine";
    int page = 0;
    int count = PAGE_SIZE;

    when(medicineSearchRepository.countByName(name)).thenReturn(count);

    Assertions.assertThrows(BadRequestException.class,
        () -> medicineSearchService.getMedicineSearchesByNameAndPage(name, page));
  }

  @Test
  public void getMedicineSearchesByNameAndPage_ReturnLastPageNext() {
    String name = "medicine";
    int page = 2;
    int count = PAGE_SIZE;

    when(medicineSearchRepository.countByName(name)).thenReturn(count);

    Assertions.assertThrows(BadRequestException.class,
        () -> medicineSearchService.getMedicineSearchesByNameAndPage(name, page));
  }

  @Test
  public void getMedicineSearchesByNameAndPage_DataNotExist() {
    String name = "medicine";
    int page = 1;

    when(medicineSearchRepository.countByName(name)).thenReturn(0);

    Assertions.assertThrows(BadRequestException.class,
        () -> medicineSearchService.getMedicineSearchesByNameAndPage(name, page));
  }
}