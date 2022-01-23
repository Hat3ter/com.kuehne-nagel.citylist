package com.solbegsoft.sitylist.utils;

import com.solbegsoft.sitylist.models.dtos.UserDetailDto;
import com.solbegsoft.sitylist.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Converter for {@link User} and {@link UserDetailDto}
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * Instance
     */
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * Convert {@link User} and {@link UserDetailDto}
     *
     * @param user {@link User}
     * @return {@link UserDetailDto}
     */
    UserDetailDto convert(User user);
}
