package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.FAVORITE_ALREADY_EXIST;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.FAVORITE_NOT_EXIST;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.FAVORITE_NO_PERMISSION;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.entity.Favorite;
import pocketyacsa.server.medicine.entity.Medicine;
import pocketyacsa.server.medicine.entity.dto.FavoriteDto;
import pocketyacsa.server.medicine.repository.FavoriteRepository;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final FavoriteRepository repository;
  private final MemberService memberService;
  private final MedicineService medicineService;

  /**
   * 로그인한 사용자가 선택한 의약품을 즐겨찾기에 추가합니다.
   *
   * @param medicineId 즐겨찾기할 medicine의 id
   */
  public void save(int medicineId) {
    Member member = memberService.getLoginMember();
    Medicine medicine = medicineService.getMedicineById(medicineId);

    boolean isExist = existsByMemberIdAndMedicineId(member.getId(), medicine.getId());

    if (isExist) {
      throw new BadRequestException(FAVORITE_ALREADY_EXIST.getErrorResponse());
    }

    Favorite favorite = Favorite.builder()
        .member(member)
        .medicine(medicine)
        .build();

    repository.save(favorite);
  }

  /**
   * 특정 id의 favorite을 반환합니다.
   *
   * @param id favorite의 id
   * @return 특정 id의 favorite
   */
  public Favorite getFavoriteById(int id) {
    Optional<Favorite> favorite = repository.findById(id);
    if (favorite.isEmpty()) {
      throw new BadRequestException(FAVORITE_NOT_EXIST.getErrorResponse());
    }

    return favorite.get();
  }

  /**
   * 특정 id의 클라이언트에게 전송할 favoriteDto를 반환합니다.
   *
   * @param id favorite의 id
   * @return 특정 id의 favoriteDto
   */
  public FavoriteDto getFavoriteDtoById(int id) {
    Favorite favorite = getFavoriteById(id);
    Member loginMember = memberService.getLoginMember();

    if (favorite.getMember().getId() != loginMember.getId()) {
      throw new BadRequestException(FAVORITE_NO_PERMISSION.getErrorResponse());
    }

    FavoriteDto favoriteDto = FavoriteDto.builder()
        .id(favorite.getId())
        .memberId(favorite.getMember().getId())
        .medicine(favorite.getMedicine())
        .build();

    return favoriteDto;
  }

  /**
   * 이미 member가 특정 medicine을 즐겨찾기에 추가했는지 확인한다.
   *
   * @param memberId   member의 id
   * @param medicineId medicine의 id
   * @return 즐겨찾기 추가 여부
   */
  public boolean existsByMemberIdAndMedicineId(int memberId, int medicineId) {
    return repository.existsByMemberIdAndMedicineId(memberId, medicineId);
  }

  /**
   * favorite을 삭제합니다.
   *
   * @param id 삭제할 favorite의 id
   */
  public void delete(int id) {
    FavoriteDto favoriteDto = getFavoriteDtoById(id);

    repository.deleteById(favoriteDto.getId());
  }
}
