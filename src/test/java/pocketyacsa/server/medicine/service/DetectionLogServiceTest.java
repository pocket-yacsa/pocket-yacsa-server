package pocketyacsa.server.medicine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pocketyacsa.server.common.utility.Constant.pageSize;
import static pocketyacsa.server.common.utility.SortDirection.DESCENDING;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.domain.entity.DetectionLog;
import pocketyacsa.server.medicine.domain.entity.Medicine;
import pocketyacsa.server.medicine.domain.response.DetectionLogPageRes;
import pocketyacsa.server.medicine.domain.response.DetectionLogRes;
import pocketyacsa.server.medicine.repository.DetectionLogRepository;
import pocketyacsa.server.member.entity.Member;
import pocketyacsa.server.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
class DetectionLogServiceTest {

  @Mock
  DetectionLogRepository detectionLogRepository;

  @Mock
  MemberService memberService;

  @Mock
  MedicineService medicineService;

  @InjectMocks
  DetectionLogService detectionLogService;

  Medicine medicine;

  Member member;

  DetectionLog detectionLog;

  DetectionLogRes detectionLogRes;

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

    detectionLog = DetectionLog.builder()
        .id(1)
        .member(member)
        .medicine(medicine)
        .build();

    detectionLogRes = DetectionLogRes.builder()
        .id(1)
        .medicineName(medicine.getName())
        .medicineCompany(medicine.getCompany())
        .createdAt(LocalDateTime.of(1, 1, 1, 1, 1))
        .build();
  }

  @Test
  public void save_Success() {
    DetectionLog detectionLogBeforeSave =
        DetectionLog.builder().member(member).medicine(medicine).build();

    when(memberService.getLoginMember()).thenReturn(member);
    when(medicineService.getMedicineById(1)).thenReturn(medicine);

    detectionLogService.save(1);

    verify(detectionLogRepository).save(detectionLogBeforeSave);
  }

  @Test
  public void getDetectionLogById_Success() {
    when(detectionLogRepository.findById(1)).thenReturn(Optional.ofNullable(detectionLog));

    DetectionLog result = detectionLogService.getDetectionLogById(1);

    assertEquals(result, detectionLog);
  }

  @Test
  public void delete_Success() {
    when(detectionLogRepository.findById(1)).thenReturn(Optional.ofNullable(detectionLog));
    when(memberService.getLoginMember()).thenReturn(member);

    detectionLogService.delete(1);

    verify(detectionLogRepository).deleteById(1);
  }

  @Test
  public void delete_NotExistDetectionLog() {
    when(detectionLogRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> detectionLogService.delete(1));
  }

  @Test
  public void deleteAll_Success() {
    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogRepository.countByMemberId(member.getId())).thenReturn(10);

    detectionLogService.deleteAll();

    verify(detectionLogRepository).deleteByMemberId(member.getId());
  }

  @Test
  public void deleteAll_NotExist() {
    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogRepository.countByMemberId(member.getId())).thenReturn(0);

    assertThrows(BadRequestException.class, () -> detectionLogService.deleteAll());
  }

  @Test
  public void delete_NotMyDetectionLog() {
    Member otherMember = Member.builder()
        .id(2)
        .name("kim")
        .email("kim@yacsa.com")
        .picture("picture-url")
        .deleted(false).build();

    DetectionLog otherDetectionLogs = DetectionLog.builder()
        .id(2)
        .member(otherMember)
        .medicine(medicine)
        .build();

    when(detectionLogRepository.findById(2)).thenReturn(Optional.ofNullable(otherDetectionLogs));
    when(memberService.getLoginMember()).thenReturn(member);

    assertThrows(BadRequestException.class, () -> detectionLogService.delete(2));
  }

  @Test
  public void getDetectionLogsByPage_ReturnFirstPage() {
    int page = 1;
    int count = pageSize + 1;
    LocalDateTime time = LocalDateTime.of(2023, 1, 1, 1, 1);
    List<DetectionLog> detectionLogs = new ArrayList<>();
    List<DetectionLogRes> detectionLogResList = new ArrayList<>();
    for (int i = 1; i <= count; i++) {
      Medicine med = Medicine.builder().id(i).code(Integer.toString(i)).name("a").company("a")
          .ingredient("a").image("a").effect("a").usages("a").precautions("a").build();
      DetectionLog detLog = DetectionLog.builder().id(i).member(member).medicine(med).build();
      detLog.setCreatedAt(time);
      detLog.setUpdatedAt(time);
      detectionLogs.add(detLog);
    }
    for (int i = 1; i <= pageSize; i++) {
      detectionLogResList.add(
          DetectionLogRes.builder().id(i).medicineId(i).medicineName("a")
              .medicineCompany("a").medicineImage("a").createdAt(time).build());
    }

    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogRepository.countByMemberId(member.getId())).thenReturn(count);
    when(detectionLogRepository.findByMemberId(eq(member.getId()), eq(
        PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()))))
        .thenReturn(detectionLogs.subList(0, pageSize));

    DetectionLogPageRes detectionLogPageRes = DetectionLogPageRes.builder()
        .memberId(member.getId())
        .total(count)
        .totalPage((int) Math.ceil((double) count / pageSize))
        .page(page)
        .lastPage(false)
        .detectionLogs(detectionLogResList)
        .build();

    DetectionLogPageRes result = detectionLogService.getDetectionLogsByPageSorted(page, DESCENDING);

    assertEquals(result, detectionLogPageRes);
  }

  @Test
  public void getDetectionLogsByPage_ReturnLastPage() {
    int page = 2;
    int count = pageSize * 2 - 1;
    List<DetectionLog> detectionLogs = new ArrayList<>();
    List<DetectionLogRes> detectionLogResList = new ArrayList<>();

    for (int i = 1; i <= count; i++) {
      Medicine med = Medicine.builder().id(i).code(Integer.toString(i)).name("a").company("a")
          .ingredient("a").image("a").effect("a").usages("a").precautions("a").build();
      DetectionLog detLog = DetectionLog.builder().id(i).member(member).medicine(med).build();
      detLog.setCreatedAt(time);
      detLog.setUpdatedAt(time);
      detectionLogs.add(detLog);
    }
    for (int i = 7; i <= count; i++) {
      detectionLogResList.add(
          DetectionLogRes.builder().id(i).medicineId(i).medicineName("a")
              .medicineCompany("a").medicineImage("a").createdAt(time).build());
    }

    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogRepository.countByMemberId(member.getId())).thenReturn(count);
    when(detectionLogRepository.findByMemberId(eq(member.getId()), eq(
        PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()))))
        .thenReturn(detectionLogs.subList(pageSize, count));

    DetectionLogPageRes detectionLogPageRes = DetectionLogPageRes.builder()
        .memberId(member.getId())
        .total(count)
        .totalPage((int) Math.ceil((double) count / pageSize))
        .page(page)
        .lastPage(true)
        .detectionLogs(detectionLogResList)
        .build();

    DetectionLogPageRes result = detectionLogService.getDetectionLogsByPageSorted(page, DESCENDING);

    assertEquals(result, detectionLogPageRes);

  }

  @Test
  public void getDetectionLogsByPage_ReturnPageZero() {
    int page = 0;
    int count = pageSize;
    List<DetectionLog> detectionLogs = new ArrayList<>();

    for (int i = 1; i <= count; i++) {
      Medicine med = Medicine.builder().id(i).code(Integer.toString(i)).name("a").company("a")
          .ingredient("a").image("a").effect("a").usages("a").precautions("a").build();
      DetectionLog detLog = detectionLog.builder().id(i).member(member).medicine(med).build();
      detLog.setCreatedAt(time);
      detLog.setUpdatedAt(time);
      detectionLogs.add(detLog);
    }

    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogRepository.countByMemberId(member.getId())).thenReturn(count);

    assertThrows(BadRequestException.class,
        () -> detectionLogService.getDetectionLogsByPageSorted(page, DESCENDING));
  }

  @Test
  public void getDetectionLogsByPage_ReturnLastPageNext() {
    int page = 2;
    int count = pageSize;
    List<DetectionLog> detectionLogs = new ArrayList<>();

    for (int i = 1; i <= count; i++) {
      Medicine med = Medicine.builder().id(i).code(Integer.toString(i)).name("a").company("a")
          .ingredient("a").image("a").effect("a").usages("a").precautions("a").build();
      DetectionLog detLog = DetectionLog.builder().id(i).member(member).medicine(med).build();
      detLog.setCreatedAt(time);
      detLog.setUpdatedAt(time);
      detectionLogs.add(detLog);
    }

    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogRepository.countByMemberId(member.getId())).thenReturn(count);

    assertThrows(BadRequestException.class,
        () -> detectionLogService.getDetectionLogsByPageSorted(page, DESCENDING));
  }

  @Test
  public void getDetectionLogsByPage_DataNotExist() {
    int page = 1;

    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogRepository.countByMemberId(member.getId())).thenReturn(0);

    assertThrows(BadRequestException.class,
        () -> detectionLogService.getDetectionLogsByPageSorted(page, DESCENDING));
  }

  @Test
  public void getDetectionLogCount_Success() {
    when(memberService.getLoginMember()).thenReturn(member);
    when(detectionLogRepository.countByMemberId(member.getId())).thenReturn(10);

    int result = detectionLogService.getDetectionLogCount();

    assertEquals(result, 10);
  }
}