package hu.unideb.inf.ingredius.service;

import hu.unideb.inf.ingredius.data.dto.AllergenDto;

import java.util.List;
import java.util.Optional;

public interface AllergenService {
    AllergenDto save(AllergenDto allergenDto);

    List<AllergenDto> findAll();

    Optional<AllergenDto> findById(Long id);

    void deleteById(Long id);

    Optional<AllergenDto> findByName(String name);
}
