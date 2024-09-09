package com.example.hospitalmanagement.utilities;

import com.example.hospitalmanagement.DTO.UserRequestDTO;
import com.example.hospitalmanagement.DTO.UserResponseDTO;
import com.example.hospitalmanagement.model.UsersEntity;

public class UsersMapper {


    // Convert UsersEntity to UserResponseDTO
    public static UserResponseDTO toResponseDTO(UsersEntity usersEntity) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(usersEntity.getId());
        responseDTO.setUsername(usersEntity.getUsername());
        return responseDTO;
    }


    // Convert UserRequestDTO to UsersEntity
    public static UsersEntity toEntity(UserRequestDTO dto) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(dto.getUsername());
        usersEntity.setPassword(dto.getPassword());
        return usersEntity;
    }
}

