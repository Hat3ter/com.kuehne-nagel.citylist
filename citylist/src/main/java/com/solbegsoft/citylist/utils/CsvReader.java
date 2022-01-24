package com.solbegsoft.citylist.utils;

import com.opencsv.CSVReader;
import com.solbegsoft.citylist.models.entities.City;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvReader {

    /**
     * Read from file csv cities, should placed in resources.
     * First line skipped!
     *
     * @param reader reader
     * @return list raw cities
     */
    @SneakyThrows
    public static List<List<String>> read(Reader reader) {


        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(reader)) {
            //clear header
            String[] values = csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }

    /**
     * Convert list raw string city in city list
     *
     * @param rawFromCsv raw city
     * @return list citites
     */
    public static List<City> convertToCityList(List<List<String>> rawFromCsv) {

        return rawFromCsv.stream()
                .map(CsvReader::createCityFromStrings)
                .collect(Collectors.toList());
    }

    /**
     * Create city
     *
     * @param rawCityInStringList city in string list
     * @return {@link City}
     */
    private static City createCityFromStrings(List<String> rawCityInStringList) {
        City city = new City();
        city.setName(rawCityInStringList.get(1));
        city.setPhotoPath(rawCityInStringList.get(2));
        return city;
    }
}
