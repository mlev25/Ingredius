package hu.unideb.inf.ingredius.data.dto;

import hu.unideb.inf.ingredius.data.model.util.Severities;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AllergenDTO {
    private Long id;

    @NotBlank(message = "Az allergen nem lehet ures!")
    @Size(min = 2, max = 50, message = "A nev 2 es 50 karakter kozotti kell legyen!")
    private String name;

    @NotNull(message = "A sulyossag kotelezo!")
    private Severities severity;
}
