package hu.unideb.inf.ingredius.controller;

import hu.unideb.inf.ingredius.data.dto.AllergenDTO;
import hu.unideb.inf.ingredius.service.AllergenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<AllergenDTO>> getAllAllergens() {
        List<AllergenDTO> allergens = allergenService.findAll();
        return ResponseEntity.ok(allergens);
    }

    // POST /api/allergens
    @PostMapping
    public ResponseEntity<AllergenDTO> createAllergen(@Valid @RequestBody AllergenDTO allergenDto) {
        allergenDto.setId(null);
        AllergenDTO savedAllergen = allergenService.save(allergenDto);
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
