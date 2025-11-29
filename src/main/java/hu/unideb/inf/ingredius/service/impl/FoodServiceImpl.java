package hu.unideb.inf.ingredius.service.impl;

import hu.unideb.inf.ingredius.data.dto.FoodDto;
import hu.unideb.inf.ingredius.data.model.Allergen;
import hu.unideb.inf.ingredius.data.model.Category;
import hu.unideb.inf.ingredius.data.model.Food;
import hu.unideb.inf.ingredius.data.repository.AllergenRepository;
import hu.unideb.inf.ingredius.data.repository.CategoryRepository;
import hu.unideb.inf.ingredius.data.repository.FoodRepository;
import hu.unideb.inf.ingredius.service.FoodService;
import hu.unideb.inf.ingredius.service.mapper.FoodMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final AllergenRepository allergenRepository;
    private final FoodMapper mapper;

    public FoodServiceImpl(FoodRepository foodRepository, CategoryRepository categoryRepository,
                           AllergenRepository allergenRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
        this.allergenRepository = allergenRepository;
        this.mapper = foodMapper;
    }

    @Override
    public FoodDto save(FoodDto foodDto) {
        Category category = categoryRepository.findById(foodDto.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Kategoria nem talalhato ezzel az ID-val: " + foodDto.getCategoryId()));

        Set<Allergen> allergens = foodDto.getAllergenIds() == null || foodDto.getAllergenIds().isEmpty()
                ? Collections.emptySet()
                : new HashSet<>(allergenRepository.findAllById(foodDto.getAllergenIds()));

        Food foodEntity = mapper.toEntity(foodDto, category, allergens);

        Food savedEntity = foodRepository.save(foodEntity);

        return mapper.toDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodDto> findAll() {
        return foodRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FoodDto> findById(Long id) {
        return foodRepository.findById(id).map(mapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public Optional<FoodDto> updateAllergens(Long foodId, Set<Long> allergenIds) {
        Optional<Food> foodOpt = foodRepository.findById(foodId);
        if (foodOpt.isEmpty()) {
            return Optional.empty();
        }

        Food food = foodOpt.get();
        Set<Allergen> allergens = new HashSet<>(allergenRepository.findAllById(allergenIds));
        food.setAllergens(allergens);
        Food updatedFood = foodRepository.save(food);

        return Optional.of(mapper.toDto(updatedFood));
    }

    @Override
    public Optional<FoodDto> updateCategory(Long foodId, Long categoryId) {
        return foodRepository.findById(foodId).flatMap(food -> {
            return categoryRepository.findById(categoryId).map(category -> {
                food.setCategory(category);
                Food updatedFood = foodRepository.save(food);
                return mapper.toDto(updatedFood);
            });
        });
    }
}