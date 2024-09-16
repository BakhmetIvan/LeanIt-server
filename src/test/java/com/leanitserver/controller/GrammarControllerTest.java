package com.leanitserver.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leanitserver.dto.grammar.GrammarFullResponseDto;
import com.leanitserver.dto.grammar.GrammarRequestDto;
import com.leanitserver.dto.grammar.GrammarShortResponseDto;
import com.leanitserver.testdata.GrammarTestData;
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
        "classpath:database/grammar/add-grammar.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/grammar/remove-grammar.sql",
        "classpath:database/article_type/remove-article-types.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrammarControllerTest {
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
    @DisplayName("Save a new grammar with valid data should return the GrammarFullResponseDto")
    public void saveGrammar_validRequest_shouldReturnGrammarFullResponseDto() throws Exception {
        GrammarRequestDto requestDto = GrammarTestData.createGrammarRequestDto();
        GrammarFullResponseDto expectedGrammar = GrammarTestData.createGrammarFullResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/grammar")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        GrammarFullResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), GrammarFullResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expectedGrammar, actual, "id");
    }

    @Test
    @DisplayName("Find all grammars should return the page of GrammarShortResponseDto")
    public void findAllGrammars_givenGrammarsInDb_ShouldReturnPageOfGrammarDto() throws Exception {
        List<GrammarShortResponseDto> expectedGrammars = new ArrayList<>();
        expectedGrammars.add(GrammarTestData.createGrammarShortResponseDto());

        MvcResult result = mockMvc.perform(
                        get("/grammar")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode contentNode = jsonNode.get("content");
        GrammarShortResponseDto[] actualGrammars =
                objectMapper.treeToValue(contentNode, GrammarShortResponseDto[].class);
        Assertions.assertNotNull(actualGrammars);
        Assertions.assertEquals(expectedGrammars.size(), actualGrammars.length);
        EqualsBuilder.reflectionEquals(expectedGrammars.get(0), actualGrammars[0], "id");
    }

    @Test
    @DisplayName("Find video by valid id should return GrammarFullResponseDto")
    public void findGrammarById_withValidId_shouldReturnGrammarDto() throws Exception {
        Long id = 1L;
        GrammarFullResponseDto expectedGrammar = GrammarTestData.createGrammarFullResponseDto();

        MvcResult result = mockMvc.perform(
                        get("/grammar/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GrammarFullResponseDto actualGrammar = objectMapper.readValue(
                result.getResponse().getContentAsString(), GrammarFullResponseDto.class
        );
        Assertions.assertNotNull(actualGrammar);
        EqualsBuilder.reflectionEquals(expectedGrammar, actualGrammar, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update the grammar by valid data should return updated GrammarFullResponseDto")
    public void updateGrammar_withValidData_shouldUpdateGrammar() throws Exception {
        Long id = 1L;
        GrammarRequestDto requestDto = GrammarTestData.createGrammarRequestDto();
        requestDto.setTitle("Updated");
        GrammarFullResponseDto expectedGrammar = GrammarTestData.createGrammarFullResponseDto();
        expectedGrammar.setTitle("Updated");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(
                        put("/grammar/{id}", id)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GrammarFullResponseDto actualGrammar = objectMapper.readValue(
                result.getResponse().getContentAsString(), GrammarFullResponseDto.class);
        Assertions.assertNotNull(actualGrammar);
        EqualsBuilder.reflectionEquals(expectedGrammar, actualGrammar, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete grammar by valid id")
    public void deleteGrammar_validId_shouldDeleteGrammar() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/grammar/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
