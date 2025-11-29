package hu.unideb.inf.ingredius.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class FoodDto {
    private Long id;

    @NotBlank(message = "A nev nem lehet ures")
    @Size(min = 2, max = 50, message = "A nevnek 2 és 50 karakter kozott kell lennie.")
    private String name;

    @NotBlank(message = "A leiras nem lehet ures.")
    @Size(max = 255, message = "A leiras maximum 255 karakter lehet.")
    private String description;

    @NotNull(message = "Kategória azonosítója kötelező.")
    private Long categoryId;

    private Set<Long> allergenIds;

    private Set<AllergenDto> allergens;
}