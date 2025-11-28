package hu.unideb.inf.ingredius.data.repository;

import hu.unideb.inf.ingredius.data.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllergenRepository extends JpaRepository<Allergen,Long> {
    Optional<Allergen> findByName(String name);


}
