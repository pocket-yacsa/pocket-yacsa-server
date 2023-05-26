package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.MEDICINE_NOT_EXIST;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.entity.Medicine;
import pocketyacsa.server.medicine.domain.response.MedicineRes;
import pocketyacsa.server.medicine.repository.FavoriteRepository;
import pocketyacsa.server.medicine.repository.MedicineRepository;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class MedicineService {

  private final MemberService memberService;
  private final MedicineRepository repository;
  private final FavoriteRepository favoriteRepository;

  /**
   * 특정 id의 medicine을 반환합니다.
   *
   * @param id medicine의 id
   * @return 특정 id의 medicine
   */
  public Medicine getMedicineById(int id) {
    Optional<Medicine> medicine = repository.findById(id);
    if (medicine.isEmpty()) {
      throw new BadRequestException(MEDICINE_NOT_EXIST.getErrorResponse());
    }

    return medicine.get();
  }

  /**
   * 특정 code의 medicine을 반환합니다.
   *
   * @param code medicine의 code
   * @return 특정 code의 medicine
   */
  public Medicine getMedicineByCode(String code) {
    Optional<Medicine> medicine = repository.findByCode(code);
    if (medicine.isEmpty()) {
      throw new BadRequestException(MEDICINE_NOT_EXIST.getErrorResponse());
    }

    return medicine.get();
  }

  /**
   * '/'로 구분되어 있는 ingredient 문자열을 List 형태로 성분별로 나누어 저장합니다.
   *
   * @param ingredient '/'로 구분되어 있는 ingredient 문자열
   * @return ingredient를 종류별로 저장하는 List
   */
  public List<String> getIngredientList(String ingredient) {
    List<String> list = Arrays.asList(ingredient.split("\\|"));
    return list;
  }

  /**
   * 특정 id의 클라이언트에게 전달할 Medicine 정보를 반환합니다.
   *
   * @param id medicine의 id
   * @return
   */
  public MedicineRes getMedicineResById(int id) {
    Member member = memberService.getLoginMember();
    Medicine medicine = getMedicineById(id);
    boolean isFavorite =
        favoriteRepository.existsByMemberIdAndMedicineId(member.getId(), medicine.getId());

    MedicineRes medicineRes = MedicineRes.builder()
        .id(medicine.getId())
        .code(medicine.getCode())
        .name(medicine.getName())
        .company(medicine.getCompany())
        .ingredient(getIngredientList(medicine.getIngredient()))
        .image(medicine.getImage())
        .effect(getHtmlString("/EE", medicine.getCode()))
        .usages(getHtmlString("/UD", medicine.getCode()))
        .precautions(getHtmlString("/NB", medicine.getCode()))
        .isFavorite(isFavorite)
        .build();

    return medicineRes;
  }

  /**
   * 의약품 html 다운로드 링크에서 받은 html파일을 String으로 반환합니다.
   *
   * @param suffix url code 뒷부분
   * @param code   medicine의 code
   * @return 의약품 정보의 html 문자열
   */
  public String getHtmlString(String suffix, String code) {
    try {
      String htmlURL =
          "https://nedrug.mfds.go.kr/pbp/cmn/html/drb/" + code + suffix; // 다운로드할 HTML 파일의 링크

      URL url = new URL(htmlURL);
      InputStream inputStream = new BufferedInputStream(url.openStream());

      // HTML 파일의 내용을 읽어옴
      byte[] htmlBytes = FileCopyUtils.copyToByteArray(inputStream);
      String htmlContent = new String(htmlBytes, "UTF-8");

      return htmlContent;
    } catch (Exception e) {
      return "";
    }
  }
}
