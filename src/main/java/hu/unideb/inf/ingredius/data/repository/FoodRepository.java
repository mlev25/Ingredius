package hu.unideb.inf.ingredius.data.repository;

import hu.unideb.inf.ingredius.data.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
