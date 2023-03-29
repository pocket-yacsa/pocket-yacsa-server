package pocketyacsa.server.medicine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pocketyacsa.server.common.exception.BadRequestException;
import pocketyacsa.server.medicine.entity.Medicine;
import pocketyacsa.server.medicine.entity.dto.MedicineDto;
import pocketyacsa.server.medicine.repository.MedicineRepository;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

  @Mock
  MedicineRepository medicineRepository;

  @InjectMocks
  MedicineService medicineService;

  Medicine medicine;
  MedicineDto medicineDto;
  List<String> ingredientList;

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

    ingredientList = List.of("apple", "banana", "strawberry");

    medicineDto = MedicineDto.builder()
        .id(medicine.getId())
        .code(medicine.getCode())
        .name(medicine.getName())
        .company(medicine.getCompany())
        .ingredient(ingredientList)
        .image(medicine.getImage())
        .effect(medicine.getEffect())
        .usages(medicine.getUsages())
        .precautions(medicine.getPrecautions())
        .build();
  }

  @Test
  public void getMedicineById_Success() {
    Mockito.when(medicineRepository.findById(1)).thenReturn(Optional.ofNullable(medicine));

    Medicine result = medicineService.getMedicineById(1);

    assertEquals(result, medicine);
  }

  @Test
  public void getMedicineById_NotExist() {
    Mockito.when(medicineRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> medicineService.getMedicineById(1));
  }

  @Test
  public void getMedicineByCode_Success() {
    Mockito.when(medicineRepository.findByCode("1234512345"))
        .thenReturn(Optional.ofNullable(medicine));

    Medicine result = medicineService.getMedicineByCode("1234512345");

    assertEquals(result, medicine);
  }

  @Test
  public void getMedicineByCode_NotExist() {
    Mockito.when(medicineRepository.findByCode("1234512345")).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> medicineService.getMedicineByCode("1234512345"));
  }

  @Test
  public void getIngredientList_Success() {
    List<String> result = medicineService.getIngredientList(medicine.getIngredient());

    assertEquals(result, ingredientList);
  }

  @Test
  public void getMedicineDtoById_Success() {
    Mockito.when(medicineRepository.findById(1)).thenReturn(Optional.ofNullable(medicine));

    MedicineDto result = medicineService.getMedicineDtoById(1);

    assertEquals(result, medicineDto);
  }

  @Test
  public void getMedicineDtoById_NotExist() {
    Mockito.when(medicineRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> medicineService.getMedicineDtoById(1));
  }

  @Test
  public void getMedicineDtoByCode_Success() {
    Mockito.when(medicineRepository.findByCode("1234512345"))
        .thenReturn(Optional.ofNullable(medicine));

    MedicineDto result = medicineService.getMedicineDtoByCode("1234512345");

    assertEquals(result, medicineDto);
  }

  @Test
  public void getMedicineDtoByCode_NotExist() {
    Mockito.when(medicineRepository.findByCode("1234512345")).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class,
        () -> medicineService.getMedicineDtoByCode("1234512345"));
  }

}