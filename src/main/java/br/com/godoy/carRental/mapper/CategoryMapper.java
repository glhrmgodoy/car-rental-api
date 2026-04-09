package br.com.godoy.carRental.mapper;

import br.com.godoy.carRental.dto.request.CategoryRequest;
import br.com.godoy.carRental.dto.response.CategoryResponse;
import br.com.godoy.carRental.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);
}
