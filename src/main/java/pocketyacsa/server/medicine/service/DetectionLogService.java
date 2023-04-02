package pocketyacsa.server.medicine.service;

import static pocketyacsa.server.common.utility.Constant.pageSize;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.DETECTION_LOG_NOT_EXIST;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.DETECTION_LOG_NO_PERMISSION;
import static pocketyacsa.server.medicine.exception.MedicineErrorResponse.PAGE_OUT_OF_RANGE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.entity.DetectionLog;
import pocketyacsa.server.medicine.domain.entity.Medicine;
import pocketyacsa.server.medicine.domain.response.DetectionLogPageRes;
import pocketyacsa.server.medicine.domain.response.DetectionLogRes;
import pocketyacsa.server.medicine.repository.DetectionLogRepository;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class DetectionLogService {

  private final DetectionLogRepository repository;
  private final MemberService memberService;
  private final MedicineService medicineService;

  /**
   * 로그인한 사용자가 촬영하고 정보를 조회한 의약품을 저장합니다.
   *
   * @param medicineId 촬영하고 정보를 조회한 medicine의 id
   */
  public void save(int medicineId) {
    Member member = memberService.getLoginMember();
    Medicine medicine = medicineService.getMedicineById(medicineId);

    DetectionLog detectionLog = DetectionLog.builder()
        .member(member)
        .medicine(medicine)
        .build();

    repository.save(detectionLog);
  }

  /**
   * 특정 id의 detectionLog를 반환합니다.
   *
   * @param id detectionLog의 id
   * @return 특정 id의 detectionLog
   */
  public DetectionLog getDetectionLogById(int id) {
    Optional<DetectionLog> detectionLog = repository.findById(id);
    if (detectionLog.isEmpty()) {
      throw new BadRequestException(DETECTION_LOG_NOT_EXIST.getErrorResponse());
    }

    return detectionLog.get();
  }

  /**
   * detectionLog를 삭제합니다.
   *
   * @param id 삭제할 detectionLog의 id
   */
  public void delete(int id) {
    DetectionLog detectionLog = getDetectionLogById(id);
    Member loginMember = memberService.getLoginMember();

    if (detectionLog.getMember().getId() != loginMember.getId()) {
      throw new BadRequestException(DETECTION_LOG_NO_PERMISSION.getErrorResponse());
    }

    repository.deleteById(detectionLog.getId());
  }

  /**
   * 로그인한 사용자의 detectionLog를 모두 삭제합니다.
   */
  @Transactional
  public void deleteAll() {
    Member loginMember = memberService.getLoginMember();
    int count = repository.countByMemberId(loginMember.getId());

    if (count == 0) {
      throw new BadRequestException(DETECTION_LOG_NOT_EXIST.getErrorResponse());
    }

    repository.deleteByMemberId(loginMember.getId());
  }

  /**
   * 특정 page의 detectionLog의 응답들을 반환합니다.
   *
   * @param page 페이지 수
   * @return 특정 page의 detectionLog 응답목록
   */
  public DetectionLogPageRes getDetectionLogsByPage(int page) {
    Member loginMember = memberService.getLoginMember();
    int totalSize = repository.countByMemberId(loginMember.getId());
    int totalPages = (int) Math.ceil((double) totalSize / pageSize);

    if (totalSize == 0) {
      throw new BadRequestException(DETECTION_LOG_NOT_EXIST.getErrorResponse());
    }
    if (page < 1 || page > totalPages) {
      throw new BadRequestException(PAGE_OUT_OF_RANGE.getErrorResponse());
    }

    List<DetectionLog> detectionLogs = repository.findByMemberId(loginMember.getId(),
        PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));

    List<DetectionLogRes> detectionLogResList = detectionLogs.stream()
        .map(detectionLog -> DetectionLogRes.builder()
            .id(detectionLog.getId())
            .medicineId(detectionLog.getMedicine().getId())
            .medicineName(detectionLog.getMedicine().getName())
            .medicineCompany(detectionLog.getMedicine().getCompany())
            .medicineImage(detectionLog.getMedicine().getImage())
            .createdAt(detectionLog.getCreatedAt())
            .build())
        .collect(Collectors.toList());

    boolean isLastPage = (page == totalPages) ? true : false;

    DetectionLogPageRes detectionLogPageRes = DetectionLogPageRes.builder()
        .memberId(loginMember.getId())
        .total(totalSize)
        .page(page)
        .lastPage(isLastPage)
        .detectionLogs(detectionLogResList)
        .build();

    return detectionLogPageRes;
  }

  /**
   * 로그인한 사용자의 detectionLog의 개수를 반환합니다.
   *
   * @return 로그인한 사용자의 detectionLog의 개수
   */
  public int getDetectionLogCount() {
    Member loginMember = memberService.getLoginMember();
    return repository.countByMemberId(loginMember.getId());
  }
}
