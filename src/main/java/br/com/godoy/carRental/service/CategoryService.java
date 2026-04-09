package br.com.godoy.carRental.service;

import br.com.godoy.carRental.dto.request.CategoryRequest;
import br.com.godoy.carRental.dto.response.CategoryResponse;
import br.com.godoy.carRental.exception.NotFoundException;
import br.com.godoy.carRental.mapper.CategoryMapper;
import br.com.godoy.carRental.model.entity.Category;
import br.com.godoy.carRental.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);

        return categoryMapper.toResponse(saved);
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse findById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        return categoryMapper.toResponse(category);
    }

    public CategoryResponse update(UUID id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        category.setName(request.name());
        category.setDescription(request.description());

        Category update = categoryRepository.save(category);

        return categoryMapper.toResponse(update);
    }

    public void delete(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        category.setActive(false);
        categoryRepository.save(category);
    }
}
