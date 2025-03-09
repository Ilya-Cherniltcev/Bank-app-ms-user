package com.org.bankmsuser.mapper;

import com.org.bankmsuser.dto.UserCreateDto;
import com.org.bankmsuser.dto.UserDto;
import com.org.bankmsuser.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUserEntity(UserDto userDto);
    User toUserEntity(UserCreateDto userCreateDto);
    UserDto toUserDto(User user);
    List<UserDto> toUserDtoList(List<User> users);
}
