package hu.unideb.inf.ingredius.service.impl;

import hu.unideb.inf.ingredius.data.dto.AllergenDto;
import hu.unideb.inf.ingredius.data.model.Allergen;
import hu.unideb.inf.ingredius.data.repository.AllergenRepository;
import hu.unideb.inf.ingredius.service.AllergenService;
import hu.unideb.inf.ingredius.service.mapper.FoodMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AllergenServiceImpl implements AllergenService {
    private final AllergenRepository allergenRepository;
    private final FoodMapper mapper;

    public AllergenServiceImpl(AllergenRepository allergenRepository, FoodMapper mapper) {
        this.allergenRepository = allergenRepository;
        this.mapper = mapper;
    }

    @Override
    public AllergenDto save(AllergenDto allergenDto) {
        Allergen entity = mapper.toEntity(allergenDto);
        if (allergenDto.getId() != null) {
            entity.setId(allergenDto.getId());
        }

        Allergen savedEntity = allergenRepository.save(entity);

        return mapper.toAllergenDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllergenDto> findAll() {
        return allergenRepository.findAll().stream()
                .map(mapper::toAllergenDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AllergenDto> findById(Long id) {
        return allergenRepository.findById(id)
                .map(mapper::toAllergenDto);
    }

    @Override
    public void deleteById(Long id) {
        allergenRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AllergenDto> findByName(String name) {
        return allergenRepository.findByName(name)
                .map(mapper::toAllergenDto);
    }
}