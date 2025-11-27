package hu.unideb.inf.ingredius.service;

import hu.unideb.inf.ingredius.data.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDTO save(CategoryDTO categoryDTO );

    List<CategoryDTO> findAll();

    Optional<CategoryDTO> findById(Long id);

    void deleteById(Long id);

    Optional<CategoryDTO> findByName(String name);
}
