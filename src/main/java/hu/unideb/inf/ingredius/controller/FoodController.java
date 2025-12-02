package hu.unideb.inf.ingredius.controller;

import hu.unideb.inf.ingredius.data.dto.FoodDto;
import hu.unideb.inf.ingredius.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<FoodDto> createFood(@Valid @RequestBody FoodDto foodDto) {
        try {
            foodDto.setId(null);
            FoodDto savedFood = foodService.save(foodDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFood);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // GET /api/foods
    @GetMapping
    public ResponseEntity<List<FoodDto>> getAllFoods() {
        return ResponseEntity.ok(foodService.findAll());
    }

    // GET /api/foods/id
    @GetMapping("/{id}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable Long id) {
        return foodService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /api/foods/id
    @PutMapping("/{id}")
    public ResponseEntity<FoodDto> updateFood(@PathVariable Long id, @Valid @RequestBody FoodDto foodDto) {
        if (foodService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        foodDto.setId(id);

        FoodDto updatedFood = foodService.save(foodDto);

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