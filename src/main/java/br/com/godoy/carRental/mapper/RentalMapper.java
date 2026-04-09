package br.com.godoy.carRental.mapper;

import br.com.godoy.carRental.dto.request.RentalRequest;
import br.com.godoy.carRental.dto.response.RentalResponse;
import br.com.godoy.carRental.model.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RentalMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "car", ignore = true)
    Rental toEntity(RentalRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "car.id", target = "carId")
    @Mapping(source = "car.brand", target = "carBrand")
    @Mapping(source = "car.model", target = "carModel")
    RentalResponse toResponse(Rental rental);
}
