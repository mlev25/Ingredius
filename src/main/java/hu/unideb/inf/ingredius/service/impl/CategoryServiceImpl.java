package hu.unideb.inf.ingredius.service.impl;

import hu.unideb.inf.ingredius.data.dto.CategoryDTO;
import hu.unideb.inf.ingredius.data.model.Category;
import hu.unideb.inf.ingredius.data.repository.CategoryRepository;
import hu.unideb.inf.ingredius.service.CategoryService;
import hu.unideb.inf.ingredius.service.mapper.FoodMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FoodMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, FoodMapper foodMapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = foodMapper;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDto) {
        Category entity = mapper.toEntity(categoryDto);
        if (categoryDto.getId() != null) entity.setId(categoryDto.getId());

        Category savedEntity = categoryRepository.save(entity);

        return mapper.toCategoryDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(mapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id)
                .map(mapper::toCategoryDto);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(mapper::toCategoryDto);
    }
}
