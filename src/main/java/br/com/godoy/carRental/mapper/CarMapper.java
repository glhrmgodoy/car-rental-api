package br.com.godoy.carRental.mapper;

import br.com.godoy.carRental.dto.request.CarRequest;
import br.com.godoy.carRental.dto.response.CarResponse;
import br.com.godoy.carRental.model.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    @Mapping(target = "category", ignore = true)
    Car toEntity(CarRequest request);

    @Mapping(source = "category.name", target = "categoryName")
    CarResponse toResponse(Car car);
}
