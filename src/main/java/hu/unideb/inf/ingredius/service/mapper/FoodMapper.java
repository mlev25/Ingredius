package hu.unideb.inf.ingredius.service.mapper;

import hu.unideb.inf.ingredius.data.model.Allergen;
import hu.unideb.inf.ingredius.data.model.Category;
import hu.unideb.inf.ingredius.data.model.Food;
import hu.unideb.inf.ingredius.data.dto.AllergenDto;
import hu.unideb.inf.ingredius.data.dto.CategoryDto;
import hu.unideb.inf.ingredius.data.dto.FoodDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FoodMapper {

    public Food toEntity(FoodDto dto, Category category, Set<Allergen> allergens) {
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

    public Category toEntity(CategoryDto dto) {
        Category entity = new Category();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public Allergen toEntity(AllergenDto dto) {
        Allergen entity = new Allergen();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setSeverity(dto.getSeverity());
        return entity;
    }

    public FoodDto toDto(Food entity) {
        FoodDto dto = new FoodDto();

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


    public AllergenDto toAllergenDto(Allergen entity) {
        AllergenDto dto = new AllergenDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSeverity(entity.getSeverity());
        return dto;
    }

    public CategoryDto toCategoryDto(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}