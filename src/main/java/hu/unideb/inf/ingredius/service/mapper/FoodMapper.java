package hu.unideb.inf.ingredius.service.mapper;

import hu.unideb.inf.ingredius.data.model.Allergen;
import hu.unideb.inf.ingredius.data.model.Category;
import hu.unideb.inf.ingredius.data.model.Food;
import hu.unideb.inf.ingredius.data.dto.AllergenDTO;
import hu.unideb.inf.ingredius.data.dto.CategoryDTO;
import hu.unideb.inf.ingredius.data.dto.FoodDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FoodMapper {

    public Food toEntity(FoodDTO dto, Category category, Set<Allergen> allergens) {
        Food entity = new Food();

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        entity.setCategory(category);
        entity.setAllergens(allergens);

        return entity;
    }

    public Category toEntity(CategoryDTO dto) {
        Category entity = new Category();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public Allergen toEntity(AllergenDTO dto) {
        Allergen entity = new Allergen();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setSeverity(dto.getSeverity());
        return entity;
    }

    public FoodDTO toDto(Food entity) {
        FoodDTO dto = new FoodDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getId());
        }

        if (entity.getAllergens() != null) {
            dto.setAllergenIds(entity.getAllergens().stream()
                    .map(Allergen::getId)
                    .collect(Collectors.toSet()));

            dto.setAllergens(entity.getAllergens().stream()
                    .map(this::toAllergenDto)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }


    public AllergenDTO toAllergenDto(Allergen entity) {
        AllergenDTO dto = new AllergenDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSeverity(entity.getSeverity());
        return dto;
    }

    public CategoryDTO toCategoryDto(Category entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}