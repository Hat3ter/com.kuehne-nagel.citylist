package com.solbegsoft.sitylist.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solbegsoft.sitylist.models.entities.City;
import com.solbegsoft.sitylist.models.requests.CityRequest;
import com.solbegsoft.sitylist.repositories.CityRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CityController}
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CityControllerTest {

    /**
     * @see CityRepository
     */
    @Autowired
    private CityRepository repository;

    /**
     * @see ObjectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test {@link CityController#findCities(Integer, Integer)}
     *
     * @throws Exception e
     */
    @Test
    public void testFindCities() throws Exception {

        mockMvc.perform(
                get("/api/v1/cities")
                        .param("page", "0")
                        .param("size", "2")
        ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data", Matchers.empty()));

        repository.save(create("A"));
        repository.save(create("B"));
        repository.save(create("C"));

        mockMvc.perform(
                get("/api/v1/cities")
                        .param("page", "0")
                        .param("size", "2")
        ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data[0].name").value("A"))
                .andExpect(jsonPath("$.data[1].name").value("B"))
                .andExpect(jsonPath("$.data[2]").doesNotExist());
    }

    /**
     * Test {@link CityController#findByName(String)}
     *
     * @throws Exception e
     */
    @Test
    public void testFindByName() throws Exception {

        mockMvc.perform(
                get("/api/v1/cities/{name}", "Tokyo")
        ).andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.data.message")
                        .value("City with name Tokyo not exists"));

        repository.save(create("Tokyo"));

        mockMvc.perform(
                get("/api/v1/cities/{name}", "Tokyo")
        ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.name").value("Tokyo"))
                .andExpect(jsonPath("$.data.photoPath").value("some photo path"));
    }

    /**
     * Test {@link CityController#patchCity(CityRequest)}
     *
     * @throws Exception e
     */
    @Test
    public void testPatchCity() throws Exception {

        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ALLOW_EDIT"));
        var authenticationToken = new UsernamePasswordAuthenticationToken(null, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        CityRequest request = new CityRequest();
        request.setId(UUID.randomUUID());
        request.setName("Tokyo");
        request.setPhotoPath("http://www.www.com");

        mockMvc.perform(
                patch("/api/v1/cities")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.data.message")
                        .value(String.format("City with id %s not exists", request.getId())));

        City city = new City();
        city.setId(request.getId());
        city.setName("some name");
        city.setPhotoPath(request.getPhotoPath());
        city = repository.save(city);
        request.setId(city.getId());

        Assertions.assertNotEquals(request.getName(), city.getName());

        mockMvc.perform(
                patch("/api/v1/cities")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.name").value(request.getName()))
                .andExpect(jsonPath("$.data.photoPath").value(request.getPhotoPath()));
    }


    /**
     * Create city
     *
     * @param name city name
     * @return {@link City}
     */
    private City create(String name) {

        City city = new City();
        city.setId(UUID.randomUUID());
        city.setName(name);
        city.setPhotoPath("some photo path");
        return city;
    }
}