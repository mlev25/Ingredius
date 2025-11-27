package hu.unideb.inf.ingredius.data.dto;

import hu.unideb.inf.ingredius.data.model.util.Categories;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    @NotNull(message = "Kategória név megadása kötelező (ENUN érték).")
    private Categories name;

    @Size(max = 100, message = "A leírás maximum 100 karakter lehet.")
    private String description;
}
