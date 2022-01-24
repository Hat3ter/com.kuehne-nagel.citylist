package com.solbegsoft.citylist.utils;

import com.solbegsoft.citylist.models.dtos.UserDetailDto;
import com.solbegsoft.citylist.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Converter for {@link User} and {@link UserDetailDto}
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
