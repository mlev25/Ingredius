package hu.unideb.inf.ingredius.controller;

import hu.unideb.inf.ingredius.data.dto.AllergenDto;
import hu.unideb.inf.ingredius.service.AllergenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/allergens")
public class AllergenController {
    private final AllergenService allergenService;

    public AllergenController(AllergenService allergenService) {
        this.allergenService = allergenService;
    }

    //GET /api/allergens
    @GetMapping
    public ResponseEntity<List<AllergenDto>> getAllAllergens() {
        List<AllergenDto> allergens = allergenService.findAll();
        return ResponseEntity.ok(allergens);
    }

    // POST /api/allergens
    @PostMapping
    public ResponseEntity<AllergenDto> createAllergen(@Valid @RequestBody AllergenDto allergenDto) {
        allergenDto.setId(null);
        AllergenDto savedAllergen = allergenService.save(allergenDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAllergen);
    }

    // DELETE /api/allergends/id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllergen(@PathVariable Long id) {
        if (allergenService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        allergenService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
