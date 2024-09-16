package com.leanitserver.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leanitserver.dto.resource.ResourceResponseDto;
import com.leanitserver.dto.user.UserResponseDto;
import com.leanitserver.dto.user.UserUpdateInfoDto;
import com.leanitserver.testdata.ResourceTestData;
import com.leanitserver.testdata.UserTestData;
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
public class UserControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithUserDetails("john@gmail.com")
    @WithMockUser(username = "John")
    @Test
    @DisplayName("Get info for current user")
    public void getInfo_shouldReturnUserResponseDto() throws Exception {
        UserResponseDto expectedUser = UserTestData.createUserResponseDto();

        MvcResult result = mockMvc.perform(get("/profile")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto actualUser = objectMapper.readValue(
                result.getResponse().getContentAsString(), UserResponseDto.class);

        Assertions.assertNotNull(actualUser);
        EqualsBuilder.reflectionEquals(expectedUser, actualUser, "id");
    }

    @WithUserDetails("john@gmail.com")
    @WithMockUser(username = "John")
    @Test
    @DisplayName("Get resources that has been added by current user. "
            + "Should return the page of ResourceResponseDto")
    public void getResources_shouldReturnPageOfResourceResponseDto() throws Exception {
        List<ResourceResponseDto> expectedResources = new ArrayList<>();
        expectedResources.add(ResourceTestData.createResourceResponseDto());

        MvcResult result = mockMvc.perform(get("/profile/my-resources")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode contentNode = jsonNode.get("content");
        ResourceResponseDto[] actualResources =
                objectMapper.treeToValue(contentNode, ResourceResponseDto[].class);

        Assertions.assertNotNull(actualResources);
        Assertions.assertEquals(expectedResources.size(), actualResources.length);
        EqualsBuilder.reflectionEquals(expectedResources.get(0), actualResources[0], "id");
    }

    @WithUserDetails("john@gmail.com")
    @WithMockUser(username = "John")
    @Test
    @DisplayName("Update info for current user")
    public void updateInfo_shouldReturnUpdatedUserResponseDto() throws Exception {
        UserUpdateInfoDto updateInfoDto = UserTestData.createValidUpdateInfoDto();
        UserResponseDto expectedUser = UserTestData.createUserResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(updateInfoDto);

        MvcResult result = mockMvc.perform(put("/profile/update-info")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto actualUser = objectMapper.readValue(
                result.getResponse().getContentAsString(), UserResponseDto.class);

        Assertions.assertNotNull(actualUser);
        EqualsBuilder.reflectionEquals(expectedUser, actualUser, "id");
    }
}
