package com.leanitserver.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leanitserver.dto.anki.internal.AnkiRequestDto;
import com.leanitserver.dto.anki.internal.AnkiResponseDto;
import com.leanitserver.testdata.AnkiTestData;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@Sql(scripts = {
        "classpath:database/article_type/add-article-types.sql",
        "classpath:database/video/add-videos.sql",
        "classpath:database/anki/add-anki.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/anki/remove-anki.sql",
        "classpath:database/video/remove-videos.sql",
        "classpath:database/article_type/remove-article-types.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnkiControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Save a new anki card with valid data should return an AnkiResponseDto")
    public void saveAnki_validRequest_shouldReturnAnkiResponseDto() throws Exception {
        AnkiRequestDto requestDto = AnkiTestData.createAnkiRequestDto();
        AnkiResponseDto expectedAnki = AnkiTestData.createAnkiResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/anki")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        AnkiResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), AnkiResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expectedAnki, actual, "id");
    }

    @Test
    @DisplayName("Find all anki cards should return the list of AnkiResponseDto")
    public void findAllAnki_givenAnkiInDb_ShouldReturnListOfAnkiDto() throws Exception {
        List<AnkiResponseDto> expectedAnki = new ArrayList<>();
        expectedAnki.add(AnkiTestData.createAnkiResponseDto());

        MvcResult result = mockMvc.perform(
                        get("/anki")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        AnkiResponseDto[] actualAnki =
                objectMapper.treeToValue(jsonNode, AnkiResponseDto[].class);
        Assertions.assertNotNull(actualAnki);
        Assertions.assertEquals(expectedAnki.size(), actualAnki.length);
        EqualsBuilder.reflectionEquals(expectedAnki.get(0), actualAnki[0], "id");
    }

    @Test
    @DisplayName("Find anki card by valid id should return the AnkiResponseDto")
    public void findAnkiById_withValidId_shouldReturnAnkiDto() throws Exception {
        Long id = 1L;
        AnkiResponseDto expectedAnki = AnkiTestData.createAnkiResponseDto();

        MvcResult result = mockMvc.perform(
                        get("/anki/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        AnkiResponseDto actualAnki = objectMapper.readValue(
                result.getResponse().getContentAsString(), AnkiResponseDto.class
        );
        Assertions.assertNotNull(actualAnki);
        EqualsBuilder.reflectionEquals(expectedAnki, actualAnki, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update anki card with valid data should return the updated AnkiResponseDto")
    public void updateAnki_withValidData_shouldUpdateAnki() throws Exception {
        Long id = 1L;
        AnkiRequestDto requestDto = AnkiTestData.createUpdateAnkiRequestDto();
        AnkiResponseDto expectedAnki = AnkiTestData.createUpdatedAnkiResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(
                        put("/anki/{id}", id)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        AnkiResponseDto actualAnki = objectMapper.readValue(
                result.getResponse().getContentAsString(), AnkiResponseDto.class);
        Assertions.assertNotNull(actualAnki);
        EqualsBuilder.reflectionEquals(expectedAnki, actualAnki, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete anki card by valid id should delete anki")
    public void deleteAnki_validId_shouldDeleteAnki() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/anki/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
