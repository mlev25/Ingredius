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

    @Mock
    private Category testCategory;

    private CategoryDto testCategoryDto;

    @BeforeEach
    void setUp(){
        testCategoryDto = new CategoryDto();
        testCategoryDto.setId(1L);
        testCategoryDto.setName(Categories.DAIRY);
        testCategoryDto.setDescription("Tejtermék");
    }

    @Test
    void save_shouldHandleExistingId_forUpdate() {
        // GIVEN
        doNothing().when(testCategory).setId(1L);

        when(mapper.toEntity(testCategoryDto)).thenReturn(testCategory);
        when(categoryRepository.save(testCategory)).thenReturn(testCategory);
        when(mapper.toCategoryDto(testCategory)).thenReturn(testCategoryDto);

        // WHEN
        CategoryDto result = categoryService.save(testCategoryDto);

        // THEN
        assertNotNull(result);
        assertEquals(Categories.DAIRY, result.getName());
        verify(categoryRepository, times(1)).save(testCategory);

        verify(testCategory, times(1)).setId(1L);
    }

    @Test
    void save_shouldHandleNullId_forCreation() {
        // GIVEN
        CategoryDto createDto = new CategoryDto();
        createDto.setName(Categories.SPICES);
        createDto.setDescription("Fűszerek");

        Category createdEntityWithId = mock(Category.class);

        when(mapper.toEntity(createDto)).thenReturn(testCategory);
        when(categoryRepository.save(testCategory)).thenReturn(createdEntityWithId);
        when(mapper.toCategoryDto(createdEntityWithId)).thenReturn(createDto);

        // WHEN
        CategoryDto result = categoryService.save(createDto);

        // THEN
        assertNotNull(result);
        assertEquals(Categories.SPICES, result.getName());
        verify(categoryRepository, times(1)).save(testCategory);

        // Ellenőrizzük, hogy az if blokk nem futott le (branch coverage)
        verify(testCategory, never()).setId(any());
    }

    // --- FIND MŰVELETEK ---

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
        Category anotherCategory = mock(Category.class);
        CategoryDto anotherCategoryDto = new CategoryDto();
        anotherCategoryDto.setName(Categories.VEGGIES);

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
    void findByName_shouldReturnCategoryDto_whenFound() {
        // GIVEN
        when(categoryRepository.findByName("DAIRY")).thenReturn(Optional.of(testCategory));
        when(mapper.toCategoryDto(testCategory)).thenReturn(testCategoryDto);

        // WHEN
        Optional<CategoryDto> result = categoryService.findByName("DAIRY");

        // THEN
        assertTrue(result.isPresent());
        assertEquals(testCategoryDto, result.get());
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        // GIVEN & WHEN
        categoryService.deleteById(1L);

        // THEN
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}