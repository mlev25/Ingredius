package hu.unideb.inf.ingredius.controller;

import hu.unideb.inf.ingredius.data.dto.FoodDTO;
import hu.unideb.inf.ingredius.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    //POST /api/foods
    @PostMapping
    public ResponseEntity<FoodDTO> createFood(@Valid @RequestBody FoodDTO foodDto) {
        foodDto.setId(null);
        FoodDTO savedFood = foodService.save(foodDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFood);
    }

    // GET /api/foods
    @GetMapping
    public ResponseEntity<List<FoodDTO>> getAllFoods() {
        return ResponseEntity.ok(foodService.findAll());
    }

    // GET /api/foods/id
    @GetMapping("/{id}")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable Long id) {
        return foodService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /api/foods/id
    @PutMapping("/{id}")
    public ResponseEntity<FoodDTO> updateFood(@PathVariable Long id, @Valid @RequestBody FoodDTO foodDto) {
        if (foodService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        foodDto.setId(id);

        FoodDTO updatedFood = foodService.save(foodDto);

        return ResponseEntity.ok(updatedFood);
    }

    // DELETE /api/foods/id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        if (foodService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        foodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}