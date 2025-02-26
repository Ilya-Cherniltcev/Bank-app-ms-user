package com.org.bankmsuser.mapper;

import com.org.bankmsuser.dto.UserDto;
import com.org.bankmsuser.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUserEntity(UserDto userDto);
}
