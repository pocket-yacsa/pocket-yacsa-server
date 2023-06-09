package pocketyacsa.server.medicine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pocketyacsa.server.common.utility.Constant.PAGE_SIZE;
import static pocketyacsa.server.common.utility.SortDirection.DESCENDING;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.entity.Favorite;
import pocketyacsa.server.medicine.domain.entity.Medicine;
import pocketyacsa.server.medicine.domain.response.FavoritePageRes;
import pocketyacsa.server.medicine.domain.response.FavoriteRes;
import pocketyacsa.server.medicine.repository.FavoriteRepository;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

  @Mock
  FavoriteRepository favoriteRepository;

  @Mock
  MemberService memberService;

  @Mock
  MedicineService medicineService;

  @InjectMocks
  FavoriteService favoriteService;

  Medicine medicine;

  Member member;

  Favorite favorite;

  FavoriteRes favoriteRes;

  LocalDateTime time = LocalDateTime.of(2023, 1, 1, 1, 1);

  @BeforeEach
  public void setUp() {
    medicine = Medicine.builder()
        .id(1)
        .code("1234512345")
        .name("medicine")
        .company("jetbrains")
        .ingredient("apple/banana/strawberry")
        .image("www.naver.com")
        .effect("make you stronger")
        .usages("once a week")
        .precautions("warning for pregnant")
        .build();

    member = Member.builder()
        .id(1)
        .name("hong")
        .email("hong@yacsa.com")
        .picture("picture-url")
        .deleted(false)
        .build();

    favorite = Favorite.builder()
        .id(1)
        .member(member)
        .medicine(medicine)
        .build();

    favoriteRes = FavoriteRes.builder()
        .id(1)
        .medicineName(medicine.getName())
        .medicineCompany(medicine.getCompany())
        .medicineImage(medicine.getImage())
        .createdAt(LocalDateTime.of(1, 1, 1, 1, 1))
        .build();
  }

  @Test
  public void save_Success() {
    Favorite favoriteBeforeSave = Favorite.builder().member(member).medicine(medicine).build();

    when(memberService.getLoginMember()).thenReturn(member);
    when(medicineService.getMedicineById(1)).thenReturn(medicine);
    when(favoriteRepository.existsByMemberIdAndMedicineId(1, 1)).thenReturn(false);

    favoriteService.save(1);

    verify(favoriteRepository).save(favoriteBeforeSave);
  }

  @Test
  public void save_AlreadyExist() {
    when(memberService.getLoginMember()).thenReturn(member);
    when(medicineService.getMedicineById(1)).thenReturn(medicine);
    when(favoriteRepository.existsByMemberIdAndMedicineId(1, 1)).thenReturn(true);

    Assertions.assertThrows(BadRequestException.class, () -> favoriteService.save(1));
  }

  @Test
  public void getFavoriteById_Success() {
    when(favoriteRepository.findById(1)).thenReturn(Optional.ofNullable(favorite));

    Favorite result = favoriteService.getFavoriteById(1);

    assertEquals(result, favorite);
  }

  @Test
  public void existsByMemberAndMedicineId_ReturnTrue() {
    when(favoriteRepository.existsByMemberIdAndMedicineId(1, 1)).thenReturn(true);

    boolean result = favoriteService.existsByMemberIdAndMedicineId(1, 1);

    assertEquals(result, true);
  }

  @Test
  public void existsByMemberAndMedicineId_ReturnFalse() {
    when(favoriteRepository.existsByMemberIdAndMedicineId(1, 1)).thenReturn(false);

    boolean result = favoriteService.existsByMemberIdAndMedicineId(1, 1);

    assertEquals(result, false);
  }

  @Test
  public void delete_Success() {
    when(favoriteRepository.findById(1)).thenReturn(Optional.ofNullable(favorite));
    when(memberService.getLoginMember()).thenReturn(member);

    favoriteService.delete(1);

    verify(favoriteRepository).deleteById(1);
  }

  @Test
  public void delete_NotExistFavorite() {
    when(favoriteRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> favoriteService.delete(1));
  }

  @Test
  public void delete_NotMyFavorite() {
    Member otherMember = Member.builder()
        .id(2)
        .name("kim")
        .email("kim@yacsa.com")
        .picture("picture-url")
        .deleted(false).build();

    Favorite otherFavorite = Favorite.builder()
        .id(2)
        .member(otherMember)
        .medicine(medicine)
        .build();

    when(favoriteRepository.findById(2)).thenReturn(Optional.ofNullable(otherFavorite));
    when(memberService.getLoginMember()).thenReturn(member);

    assertThrows(BadRequestException.class, () -> favoriteService.delete(2));
  }

  @Test
  public void deleteAll_Success() {
    when(memberService.getLoginMember()).thenReturn(member);
    when(favoriteRepository.countByMemberId(member.getId())).thenReturn(10);

    favoriteService.deleteAll();

    verify(favoriteRepository).deleteByMemberId(member.getId());
  }

  @Test
  public void deleteAll_NotExist() {
    when(memberService.getLoginMember()).thenReturn(member);
    when(favoriteRepository.countByMemberId(member.getId())).thenReturn(0);

    assertThrows(BadRequestException.class, () -> favoriteService.deleteAll());
  }

  @Test
  public void getFavoritesByPage_ReturnFirstPage() {
    int page = 1;
    int count = PAGE_SIZE + 1;
    LocalDateTime time = LocalDateTime.of(2023, 1, 1, 1, 1);
    List<Favorite> favorites = new ArrayList<>();
    List<FavoriteRes> favoriteReses = new ArrayList<>();
    for (int i = 1; i <= count; i++) {
      Medicine med = Medicine.builder().id(i).code(Integer.toString(i)).name("a").company("a")
          .ingredient("a").image("a").effect("a").usages("a").precautions("a").build();
      Favorite fav = Favorite.builder().id(i).member(member).medicine(med).build();
      fav.setCreatedAt(time);
      fav.setUpdatedAt(time);
      favorites.add(fav);
    }
    for (int i = 1; i <= PAGE_SIZE; i++) {
      favoriteReses.add(
          FavoriteRes.builder().id(i).medicineId(i).medicineName("a")
              .medicineCompany("a").medicineImage("a").createdAt(time).build());
    }

    FavoritePageRes favoritePageRes = FavoritePageRes.builder()
        .memberId(member.getId())
        .total(count)
        .totalPage((int) Math.ceil((double) count / PAGE_SIZE))
        .page(page)
        .lastPage(false)
        .favorites(favoriteReses)
        .build();

    when(memberService.getLoginMember()).thenReturn(member);
    when(favoriteRepository.countByMemberId(member.getId())).thenReturn(count);
    when(favoriteRepository.findByMemberId(eq(member.getId()), eq(
        PageRequest.of(page - 1, PAGE_SIZE, Sort.by("createdAt").descending()))))
        .thenReturn(favorites.subList(0, PAGE_SIZE));

    FavoritePageRes result = favoriteService.getFavoritesByPageSorted(page, DESCENDING);

    assertEquals(result, favoritePageRes);
  }

  @Test
  public void getFavoritesByPage_ReturnLastPage() {
    int page = 2;
    int count = PAGE_SIZE * 2 - 1;
    List<Favorite> favorites = new ArrayList<>();
    List<FavoriteRes> favoriteReses = new ArrayList<>();

    for (int i = 1; i <= count; i++) {
      Medicine med = Medicine.builder().id(i).code(Integer.toString(i)).name("a").company("a")
          .ingredient("a").image("a").effect("a").usages("a").precautions("a").build();
      Favorite fav = Favorite.builder().id(i).member(member).medicine(med).build();
      fav.setCreatedAt(time);
      fav.setUpdatedAt(time);
      favorites.add(fav);
    }
    for (int i = PAGE_SIZE + 1; i <= count; i++) {
      favoriteReses.add(
          FavoriteRes.builder().id(i).medicineId(i).medicineName("a")
              .medicineCompany("a").medicineImage("a").createdAt(time).build());
    }

    FavoritePageRes favoritePageRes = FavoritePageRes.builder()
        .memberId(member.getId())
        .total(count)
        .totalPage((int) Math.ceil((double) count / PAGE_SIZE))
        .page(page)
        .lastPage(true)
        .favorites(favoriteReses)
        .build();

    when(memberService.getLoginMember()).thenReturn(member);
    when(favoriteRepository.countByMemberId(member.getId())).thenReturn(count);
    when(favoriteRepository.findByMemberId(eq(member.getId()), eq(
        PageRequest.of(page - 1, PAGE_SIZE, Sort.by("createdAt").descending()))))
        .thenReturn(favorites.subList(PAGE_SIZE, count));

    FavoritePageRes result = favoriteService.getFavoritesByPageSorted(page, DESCENDING);

    assertEquals(result, favoritePageRes);
  }

  @Test
  public void getFavoritesByPage_ReturnPageZero() {
    int page = 0;
    int count = PAGE_SIZE;

    when(memberService.getLoginMember()).thenReturn(member);
    when(favoriteRepository.countByMemberId(member.getId())).thenReturn(count);

    assertThrows(BadRequestException.class,
        () -> favoriteService.getFavoritesByPageSorted(page, DESCENDING));
  }

  @Test
  public void getFavoritesByPage_ReturnLastPageNext() {
    int page = 2;
    int count = PAGE_SIZE;

    when(memberService.getLoginMember()).thenReturn(member);
    when(favoriteRepository.countByMemberId(member.getId())).thenReturn(count);

    assertThrows(BadRequestException.class,
        () -> favoriteService.getFavoritesByPageSorted(page, DESCENDING));
  }

  @Test
  public void getFavoritesByPage_DataNotExist() {
    int page = 1;

    when(memberService.getLoginMember()).thenReturn(member);
    when(favoriteRepository.countByMemberId(member.getId())).thenReturn(0);

    assertThrows(BadRequestException.class,
        () -> favoriteService.getFavoritesByPageSorted(page, DESCENDING));
  }

  @Test
  public void getFavoriteCount_Success() {
    when(memberService.getLoginMember()).thenReturn(member);
    when(favoriteRepository.countByMemberId(member.getId())).thenReturn(10);

    int result = favoriteService.getFavoriteCount();

    assertEquals(result, 10);
  }
}

