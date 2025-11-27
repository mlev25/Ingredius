package hu.unideb.inf.ingredius.service;

import hu.unideb.inf.ingredius.data.dto.AllergenDTO;

import java.util.List;
import java.util.Optional;

public interface AllergenService {
    AllergenDTO save(AllergenDTO allergenDto);

    List<AllergenDTO> findAll();

    Optional<AllergenDTO> findById(Long id);

    void deleteById(Long id);

    Optional<AllergenDTO> findByName(String name);
}
