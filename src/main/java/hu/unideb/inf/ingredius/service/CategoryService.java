package hu.unideb.inf.ingredius.service;

import hu.unideb.inf.ingredius.data.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);

    List<CategoryDto> findAll();

    Optional<CategoryDto> findById(Long id);

    void deleteById(Long id);

    Optional<CategoryDto> findByName(String name);
}
