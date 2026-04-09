package br.com.godoy.carRental.mapper;

import br.com.godoy.carRental.dto.request.UserRequest;
import br.com.godoy.carRental.dto.response.UserResponse;
import br.com.godoy.carRental.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(UserRequest request);

    UserResponse toResponse(User user);
}
