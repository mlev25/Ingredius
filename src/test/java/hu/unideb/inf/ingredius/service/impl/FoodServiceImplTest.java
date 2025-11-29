package hu.unideb.inf.ingredius.service.impl;

import hu.unideb.inf.ingredius.data.dto.FoodDto;
import hu.unideb.inf.ingredius.data.model.Allergen;
import hu.unideb.inf.ingredius.data.model.Category;
import hu.unideb.inf.ingredius.data.model.Food;
import hu.unideb.inf.ingredius.data.model.util.Categories;
import hu.unideb.inf.ingredius.data.repository.AllergenRepository;
import hu.unideb.inf.ingredius.data.repository.CategoryRepository;
import hu.unideb.inf.ingredius.data.repository.FoodRepository;
import hu.unideb.inf.ingredius.service.mapper.FoodMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodServiceImplTest {
    @InjectMocks
    private FoodServiceImpl foodService;

    @Mock
    private FoodRepository foodRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AllergenRepository allergenRepository;
    @Mock
    private FoodMapper mapper;

    private Category dairyCategory;
    private Allergen glutenAllergen;
    private Allergen lactoseAllergen;
    private Food inputFoodEntity;
    private FoodDto inputFoodDto;
    private Set<Long> allergenIds;
    private List<Allergen> allergenList;
    private Set<Allergen> allergenSet;

    @BeforeEach
    void setUp() {
        dairyCategory = new Category();
        dairyCategory.setId(2L);
        dairyCategory.setName(Categories.DAIRY);

        glutenAllergen = new Allergen();
        glutenAllergen.setId(1L);
        lactoseAllergen = new Allergen();
        lactoseAllergen.setId(3L);

        allergenList = Arrays.asList(glutenAllergen, lactoseAllergen);
        allergenSet = new HashSet<>(allergenList);
        allergenIds = new HashSet<>(Arrays.asList(1L, 3L));

        inputFoodDto = new FoodDto();
        inputFoodDto.setName("Joghurt");
        inputFoodDto.setCategoryId(2L);
        inputFoodDto.setAllergenIds(allergenIds);
        inputFoodDto.setId(null);

        inputFoodEntity = new Food();
        inputFoodEntity.setName("Joghurt");
        inputFoodEntity.setCategory(dairyCategory);
        inputFoodEntity.setAllergens(allergenSet);
        inputFoodEntity.setId(10L);
    }

    @Test
    void save_shouldCreateNewFoodAndReturnDto() {
        // GIVEN
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(dairyCategory));
        when(allergenRepository.findAllById(allergenIds)).thenReturn(allergenList);
        when(mapper.toEntity(inputFoodDto, dairyCategory, allergenSet)).thenReturn(inputFoodEntity);
        when(foodRepository.save(inputFoodEntity)).thenReturn(inputFoodEntity);
        FoodDto expectedDto = new FoodDto();
        expectedDto.setId(10L);
        when(mapper.toDto(inputFoodEntity)).thenReturn(expectedDto);

        // WHEN
        FoodDto result = foodService.save(inputFoodDto);

        // THEN
        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(categoryRepository, times(1)).findById(2L);
        verify(foodRepository, times(1)).save(inputFoodEntity);
    }

    @Test
    void save_shouldThrowException_whenCategoryNotFound() {
        // GIVEN
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> foodService.save(inputFoodDto));
        verify(foodRepository, never()).save(any());
    }

    @Test
    void findById_shouldReturnFoodDto_whenFound() {
        // GIVEN
        when(foodRepository.findById(10L)).thenReturn(Optional.of(inputFoodEntity));
        FoodDto expectedDto = new FoodDto();
        expectedDto.setId(10L);
        when(mapper.toDto(inputFoodEntity)).thenReturn(expectedDto);

        // WHEN
        Optional<FoodDto> result = foodService.findById(10L);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(10L, result.get().getId());
    }

    @Test
    void findAll_shouldReturnListOfFoodDtos() {
        // GIVEN
        Food food1 = inputFoodEntity;
        Food food2 = new Food();
        food2.setId(11L);
        List<Food> foodList = Arrays.asList(food1, food2);

        when(foodRepository.findAll()).thenReturn(foodList);

        when(mapper.toDto(food1)).thenReturn(new FoodDto());
        when(mapper.toDto(food2)).thenReturn(new FoodDto());

        // WHEN
        List<FoodDto> result = foodService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(foodRepository, times(1)).findAll();
        verify(mapper, times(2)).toDto(any(Food.class));
    }

    @Test
    void updateCategory_shouldUpdateCategoryAndReturnDto() {
        // GIVEN
        Long foodId = 10L;
        Long newCategoryId = 3L;
        Category newCategory = new Category();
        newCategory.setId(newCategoryId);
        newCategory.setName(Categories.FRUITS);

        Food existingFood = new Food();
        existingFood.setId(foodId);
        existingFood.setName("Régi étel");

        Food updatedFood = new Food();
        updatedFood.setId(foodId);
        updatedFood.setName("Régi étel");
        updatedFood.setCategory(newCategory);

        FoodDto expectedDto = new FoodDto();
        expectedDto.setId(foodId);
        expectedDto.setCategoryId(newCategoryId);

        when(foodRepository.findById(foodId)).thenReturn(Optional.of(existingFood));
        when(categoryRepository.findById(newCategoryId)).thenReturn(Optional.of(newCategory));
        when(foodRepository.save(any(Food.class))).thenReturn(updatedFood);
        when(mapper.toDto(updatedFood)).thenReturn(expectedDto);

        // WHEN
        Optional<FoodDto> result = foodService.updateCategory(foodId, newCategoryId);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(newCategoryId, result.get().getCategoryId());
        verify(foodRepository, times(1)).save(existingFood);
    }
}