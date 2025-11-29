package hu.unideb.inf.ingredius.service.impl;

import hu.unideb.inf.ingredius.data.dto.CategoryDto;
import hu.unideb.inf.ingredius.data.model.Category;
import hu.unideb.inf.ingredius.data.model.util.Categories;
import hu.unideb.inf.ingredius.data.repository.CategoryRepository;
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
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private FoodMapper mapper;

    private Category testCategory;
    private CategoryDto testCategoryDto;

    @BeforeEach
    void setUp(){
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName(Categories.DAIRY);
        testCategory.setDescription("Tejtermék");

        testCategoryDto = new CategoryDto();
        testCategoryDto.setId(1L);
        testCategoryDto.setName(Categories.DAIRY);
        testCategoryDto.setDescription("Tejtermék");
    }

    //---------TESZTEK--------//

    @Test
    void save_shouldReturnSavedDto() {
        // GIVEN
        when(mapper.toEntity(testCategoryDto)).thenReturn(testCategory);
        when(categoryRepository.save(testCategory)).thenReturn(testCategory);
        when(mapper.toCategoryDto(testCategory)).thenReturn(testCategoryDto);

        // WHEN
        CategoryDto result = categoryService.save(testCategoryDto);

        // THEN
        assertNotNull(result);
        assertEquals(Categories.DAIRY, result.getName());
        verify(categoryRepository, times(1)).save(testCategory);
    }

    @Test
    void findById_shouldReturnCategoryDto_whenFound() {
        // GIVEN
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(mapper.toCategoryDto(testCategory)).thenReturn(testCategoryDto);

        // WHEN
        Optional<CategoryDto> result = categoryService.findById(1L);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(testCategoryDto, result.get());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenNotFound() {
        // GIVEN
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Optional<CategoryDto> result = categoryService.findById(99L);

        // THEN
        assertFalse(result.isPresent());
        verify(mapper, never()).toCategoryDto(any());
    }

    @Test
    void findAll_shouldReturnListOfCategoryDtos() {
        // GIVEN
        Category anotherCategory = new Category();
        CategoryDto anotherCategoryDto = new CategoryDto();

        List<Category> categoryList = Arrays.asList(testCategory, anotherCategory);
        when(categoryRepository.findAll()).thenReturn(categoryList);

        when(mapper.toCategoryDto(testCategory)).thenReturn(testCategoryDto);
        when(mapper.toCategoryDto(anotherCategory)).thenReturn(anotherCategoryDto);

        // WHEN
        List<CategoryDto> result = categoryService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        // GIVEN & WHEN
        categoryService.deleteById(1L);

        // THEN
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
