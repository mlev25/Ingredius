package hu.unideb.inf.ingredius.controller;

import hu.unideb.inf.ingredius.data.dto.CategoryDTO;
import hu.unideb.inf.ingredius.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /api/categories
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    // POST /api/categories
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
        try {
            categoryDto.setId(null);
            CategoryDTO savedCategory = categoryService.save(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

//    // PUT /api/categories/{id}
//    @PutMapping("/{id}")
//    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDto) {
//        if (categoryService.findById(id).isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        categoryDto.setId(id);
//        CategoryDTO updatedCategory = categoryService.save(categoryDto);
//        return ResponseEntity.ok(updatedCategory);
//    }

//    // DELETE /api/categories/{id}
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
//        if (categoryService.findById(id).isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        categoryService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }

}
