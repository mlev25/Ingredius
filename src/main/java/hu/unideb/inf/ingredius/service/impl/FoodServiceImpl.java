package hu.unideb.inf.ingredius.service.impl;

import hu.unideb.inf.ingredius.data.dto.FoodDTO;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final AllergenRepository allergenRepository;
    private final FoodMapper mapper;

    public FoodServiceImpl(FoodRepository foodRepository, CategoryRepository categoryRepository, AllergenRepository allergenRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
        this.allergenRepository = allergenRepository;
        this.mapper = foodMapper;
    }

    @Override
    public FoodDTO save(FoodDTO foodDto) {
        Category category = categoryRepository.findById(foodDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategoria nem talalhato ezzel az ID-val: " + foodDto.getCategoryId()));

        Set<Allergen> allergens = foodDto.getAllergenIds() == null || foodDto.getAllergenIds().isEmpty()
                ? Collections.emptySet()
                : new HashSet<>(allergenRepository.findAllById(foodDto.getAllergenIds()));

        Food foodEntity = mapper.toEntity(foodDto, category, allergens);

        Food savedEntity = foodRepository.save(foodEntity);

        return mapper.toDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodDTO> findAll() {
        return foodRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FoodDTO> findById(Long id) {
        return foodRepository.findById(id).map(mapper::toDto);    }

    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public Optional<FoodDTO> updateAllergens(Long foodId, Set<Long> allergenIds) {
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
    public Optional<FoodDTO> updateCategory(Long foodId, Long categoryId) {
        return foodRepository.findById(foodId).flatMap(food -> {
            return categoryRepository.findById(categoryId).map(category -> {
                food.setCategory(category);
                Food updatedFood = foodRepository.save(food);
                return mapper.toDto(updatedFood);
            });
        });
    }
}