package hu.unideb.inf.ingredius.data.repository;

import hu.unideb.inf.ingredius.data.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
}
