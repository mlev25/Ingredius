package hu.unideb.inf.ingredius.service;

import hu.unideb.inf.ingredius.data.dto.FoodDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FoodService {
    FoodDto save(FoodDto foodDto);

    List<FoodDto> findAll();

    Optional<FoodDto> findById(Long id);

    void deleteById(Long id);

    Optional<FoodDto> updateAllergens(Long foodId, Set<Long> allergenIds);

    Optional<FoodDto> updateCategory(Long foodId, Long categoryId);
}
