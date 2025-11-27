package hu.unideb.inf.ingredius.service;

import hu.unideb.inf.ingredius.data.dto.FoodDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FoodService {
    FoodDTO save(FoodDTO foodDto);

    List<FoodDTO> findAll();

    Optional<FoodDTO> findById(Long id);

    void deleteById(Long id);

    Optional<FoodDTO> updateAllergens(Long foodId, Set<Long> allergenIds);

    Optional<FoodDTO> updateCategory(Long foodId, Long categoryId);
}
