package com.leanitserver.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leanitserver.dto.resource.ResourceRequestDto;
import com.leanitserver.dto.resource.ResourceResponseDto;
import com.leanitserver.testdata.ResourceTestData;
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
import org.springframework.security.test.context.support.WithUserDetails;
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
        "classpath:database/resource/add-resources.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/resource/remove-resources.sql",
        "classpath:database/article_type/remove-article-types.sql",
        "classpath:database/user/remove-users.sql",
        "classpath:database/user/remove-roles.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceControllerTest {
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

    @WithUserDetails("admin@gmail.com")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Save a new resource with valid data should return the ResourceResponseDto")
    public void saveResource_validRequest_shouldReturnResourceDto() throws Exception {
        ResourceRequestDto requestDto = ResourceTestData.createResourceRequestDto();
        ResourceResponseDto expectedResource = ResourceTestData.createResourceResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/resources")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        ResourceResponseDto actualResource = objectMapper.readValue(result.getResponse()
                .getContentAsString(), ResourceResponseDto.class);
        Assertions.assertNotNull(actualResource);
        EqualsBuilder.reflectionEquals(expectedResource, actualResource, "id");
    }

    @Test
    @DisplayName("Find all resources should return the page of ResourceResponseDto")
    public void findAllResources_givenResourceInDb_ShouldReturnPageOfResourceDto()
            throws Exception {
        List<ResourceResponseDto> expectedResource = new ArrayList<>();
        expectedResource.add(ResourceTestData.createResourceResponseDto());

        MvcResult result = mockMvc.perform(
                        get("/resources")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode contentNode = jsonNode.get("content");
        ResourceResponseDto[] actualResources =
                objectMapper.treeToValue(contentNode, ResourceResponseDto[].class);
        Assertions.assertNotNull(actualResources);
        Assertions.assertEquals(expectedResource.size(), actualResources.length);
        EqualsBuilder.reflectionEquals(expectedResource.get(0), actualResources[0], "id");
    }

    @Test
    @DisplayName("Find resource by valid id should return ResourceResponseDto")
    public void findResourceById_withValidId_shouldReturnResourceDto() throws Exception {
        Long id = 1L;
        ResourceResponseDto expectedResource = ResourceTestData.createResourceResponseDto();

        MvcResult result = mockMvc.perform(
                        get("/resources/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ResourceResponseDto actualResource = objectMapper.readValue(
                result.getResponse().getContentAsString(), ResourceResponseDto.class
        );
        Assertions.assertNotNull(actualResource);
        EqualsBuilder.reflectionEquals(expectedResource, actualResource, "id");
    }

    @WithUserDetails("john@gmail.com")
    @WithMockUser(username = "John")
    @Test
    @DisplayName("Update the resource by valid data should return updated ResourceResponseDto")
    public void updateResource_withValidData_shouldUpdateResource() throws Exception {
        Long id = 1L;
        ResourceRequestDto requestDto = ResourceTestData.createResourceRequestDto();
        requestDto.setTitle("Updated");
        ResourceResponseDto expectedResource = ResourceTestData.createResourceResponseDto();
        expectedResource.setTitle("Updated");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(
                        put("/resources/{id}", id)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ResourceResponseDto actualResource = objectMapper.readValue(
                result.getResponse().getContentAsString(), ResourceResponseDto.class);
        Assertions.assertNotNull(actualResource);
        EqualsBuilder.reflectionEquals(expectedResource, actualResource, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete resource by valid id")
    public void deleteResource_validId_shouldDeleteResource() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/resources/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
