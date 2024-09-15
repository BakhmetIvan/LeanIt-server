package com.leanitserver.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leanitserver.dto.search.SearchResponseDto;
import com.leanitserver.testdata.SearchTestData;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@Sql(scripts = {
        "classpath:database/user/add-roles.sql",
        "classpath:database/user/add-users.sql",
        "classpath:database/article_type/add-article-types.sql",
        "classpath:database/resource/add-resources.sql",
        "classpath:database/grammar/add-grammar.sql",
        "classpath:database/video/add-videos.sql",
        "classpath:database/anki/add-anki.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/anki/remove-anki.sql",
        "classpath:database/video/remove-videos.sql",
        "classpath:database/grammar/remove-grammar.sql",
        "classpath:database/resource/remove-resources.sql",
        "classpath:database/article_type/remove-article-types.sql",
        "classpath:database/user/remove-users.sql",
        "classpath:database/user/remove-roles.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchControllerTest {
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

    @Test
    @DisplayName("Search articles by title should return list of SearchResponseDto")
    public void searchByTitle_notEmptyString_shouldReturnListOfSearchDto() throws Exception {
        List<SearchResponseDto> expectedSearch = SearchTestData.createListOfResponseObjects();

        MvcResult result = mockMvc.perform(
                        get("/search")
                                .param("title", "Title")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        SearchResponseDto[] actualSearch =
                objectMapper.treeToValue(jsonNode, SearchResponseDto[].class);
        Assertions.assertNotNull(actualSearch);
        Assertions.assertEquals(expectedSearch.size(), actualSearch.length);
        EqualsBuilder.reflectionEquals(expectedSearch.get(0), actualSearch[0], "id");
        EqualsBuilder.reflectionEquals(expectedSearch.get(1), actualSearch[1], "id");
        EqualsBuilder.reflectionEquals(expectedSearch.get(2), actualSearch[2], "id");
    }
}
