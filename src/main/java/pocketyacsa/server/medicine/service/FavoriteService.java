package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.common.utility.Constant.pageSize;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.FAVORITE_ALREADY_EXIST;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.FAVORITE_NOT_EXIST;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.FAVORITE_NO_PERMISSION;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.FAVORITE_PAGE_OUT_OF_RANGE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.entity.Favorite;
import pocketyacsa.server.medicine.domain.entity.Medicine;
import pocketyacsa.server.medicine.domain.response.FavoriteRes;
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
    Favorite favorite = getFavoriteById(id);
    Member loginMember = memberService.getLoginMember();

    if (favorite.getMember().getId() != loginMember.getId()) {
      throw new BadRequestException(FAVORITE_NO_PERMISSION.getErrorResponse());
    }

    repository.deleteById(favorite.getId());
  }

  /**
   * 특정 page의 favorite 응답들을 반환합니다.
   *
   * @param page 페이지 수
   * @return 특정 page의 favorite 응답목록
   */
  public List<FavoriteRes> getFavoritesByPage(int page) {
    Member loginMember = memberService.getLoginMember();
    int totalSize = repository.countByMemberId(loginMember.getId());
    int totalPages = (int) Math.ceil((double) totalSize / pageSize);

    if (page < 1 || page > totalPages) {
      throw new BadRequestException(FAVORITE_PAGE_OUT_OF_RANGE.getErrorResponse());
    }

    List<Favorite> favorites = repository.findByMemberId(loginMember.getId(),
        PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));

    List<FavoriteRes> favoriteResList = favorites.stream()
        .map(favorite -> FavoriteRes.builder()
            .id(favorite.getId())
            .memberId(favorite.getMember().getId())
            .medicineName(favorite.getMedicine().getName())
            .medicineCompany(favorite.getMedicine().getCompany())
            .createdAt(favorite.getCreatedAt())
            .build())
        .collect(Collectors.toList());

    return favoriteResList;
  }
}
