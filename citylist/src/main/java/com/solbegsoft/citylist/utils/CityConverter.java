package com.solbegsoft.citylist.utils;

import com.solbegsoft.citylist.models.dtos.CityDto;
import com.solbegsoft.citylist.models.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Converter for {@link City} and {@link CityDto}
 */
@Mapper(componentModel = "spring")
public interface CityConverter {

    /**
     * Instance
     */
    CityConverter INSTANCE = Mappers.getMapper(CityConverter.class);

    /**
     * Convert
     *
     * @param city {@link City}
     * @return {@link CityDto}
     */
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "photoPath", target = "photoPath")
    })
    CityDto convert(City city);
}
