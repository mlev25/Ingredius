package hu.unideb.inf.ingredius.service.impl;

import hu.unideb.inf.ingredius.data.dto.AllergenDTO;
import hu.unideb.inf.ingredius.data.model.Allergen;
import hu.unideb.inf.ingredius.data.model.util.Severities;
import hu.unideb.inf.ingredius.data.repository.AllergenRepository;
import hu.unideb.inf.ingredius.service.mapper.FoodMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AllergenServiceImplTest {

    @InjectMocks
    private AllergenServiceImpl allergenService;

    @Mock
    private AllergenRepository allergenRepository;

    @Mock
    private FoodMapper mapper;

    private Allergen testAllergen;
    private AllergenDTO testAllergenDto;

    @BeforeEach
    void setUp() {
        testAllergen = new Allergen();
        testAllergen.setId(1L);
        testAllergen.setName("Mogyoró");
        testAllergen.setSeverity(Severities.HIGH);

        testAllergenDto = new AllergenDTO();
        testAllergenDto.setId(1L);
        testAllergenDto.setName("Mogyoró");
        testAllergenDto.setSeverity(Severities.HIGH);
    }

    @Test
    void save_shouldReturnSavedDto() {
        // GIVEN
        when(mapper.toEntity(testAllergenDto)).thenReturn(testAllergen);
        when(allergenRepository.save(testAllergen)).thenReturn(testAllergen);
        when(mapper.toAllergenDto(testAllergen)).thenReturn(testAllergenDto);

        // WHEN
        AllergenDTO result = allergenService.save(testAllergenDto);

        // THEN
        assertNotNull(result);
        assertEquals("Mogyoró", result.getName());
        verify(allergenRepository, times(1)).save(testAllergen);
    }

    @Test
    void findById_shouldReturnAllergenDto_whenFound() {
        // GIVEN
        when(allergenRepository.findById(1L)).thenReturn(Optional.of(testAllergen));
        when(mapper.toAllergenDto(testAllergen)).thenReturn(testAllergenDto);

        // WHEN
        Optional<AllergenDTO> result = allergenService.findById(1L);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(testAllergenDto, result.get());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenNotFound() {
        // GIVEN
        when(allergenRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Optional<AllergenDTO> result = allergenService.findById(99L);

        // THEN
        assertFalse(result.isPresent());
        verify(mapper, never()).toAllergenDto(any());
    }

    @Test
    void findAll_shouldReturnListOfAllergenDtos() {
        // GIVEN
        Allergen anotherAllergen = new Allergen();
        anotherAllergen.setName("Tej");
        AllergenDTO anotherAllergenDto = new AllergenDTO();
        anotherAllergenDto.setName("Tej");

        List<Allergen> allergenList = Arrays.asList(testAllergen, anotherAllergen);
        when(allergenRepository.findAll()).thenReturn(allergenList);

        when(mapper.toAllergenDto(testAllergen)).thenReturn(testAllergenDto);
        when(mapper.toAllergenDto(anotherAllergen)).thenReturn(anotherAllergenDto);

        // WHEN
        List<AllergenDTO> result = allergenService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(allergenRepository, times(1)).findAll();
    }
}