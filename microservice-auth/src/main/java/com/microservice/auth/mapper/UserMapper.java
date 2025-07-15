package com.microservice.auth.mapper;

import com.microservice.auth.dto.response.UserResponseDTO;
import com.microservice.auth.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserEntity.class})
public interface UserMapper {

    @Mapping(source = "rol", target = "roleName", qualifiedByName = "mapRoleToString")
    UserResponseDTO toResponseDto(UserEntity userEntity);

    @Named("mapRoleToString")
    default String mapRoleToString(com.microservice.auth.model.Role role) {
        return role != null && role.getRoleName() != null ? role.getRoleName().name() : null;
    }


}
