package pocketyacsa.server.medicine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.entity.Favorite;
import pocketyacsa.server.medicine.entity.Medicine;
import pocketyacsa.server.medicine.entity.dto.FavoriteDto;
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

  Medicine medicine = Medicine.builder()
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

  Member member = Member.builder()
      .id(1)
      .name("hong")
      .email("hong@yacsa.com")
      .picture("picture-url")
      .deleted(false).build();

  Favorite favorite = Favorite.builder()
      .id(1)
      .member(member)
      .medicine(medicine)
      .build();

  FavoriteDto favoriteDto = FavoriteDto.builder()
      .id(1)
      .memberId(1)
      .medicineId(1)
      .build();

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
  public void getFavoriteById_NotExistFavorite() {
    when(favoriteRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> favoriteService.getFavoriteDtoById(1));
  }

  @Test
  public void getFavoriteDtoById_Success() {
    when(favoriteRepository.findById(1)).thenReturn(Optional.ofNullable(favorite));
    when(memberService.getLoginMember()).thenReturn(member);

    FavoriteDto result = favoriteService.getFavoriteDtoById(1);

    assertEquals(result, favoriteDto);
  }

  @Test
  public void getFavoriteDtoById_NotExistFavorite() {
    when(favoriteRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> favoriteService.getFavoriteDtoById(1));
  }

  @Test
  public void getFavoriteDtoById_NotMyFavorite() {
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

    assertThrows(BadRequestException.class, () -> favoriteService.getFavoriteDtoById(2));
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
}