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
import com.leanitserver.dto.video.VideoFullResponseDto;
import com.leanitserver.dto.video.VideoRequestDto;
import com.leanitserver.dto.video.VideoShortResponseDto;
import com.leanitserver.testdata.VideoTestData;
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
        "classpath:database/video/add-videos.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/video/remove-videos.sql",
        "classpath:database/article_type/remove-article-types.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VideoControllerTest {
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
    @DisplayName("Save a new video with valid data should return the VideoFullResponseDto")
    public void saveVideo_validRequest_shouldReturnVideoFullResponseDto() throws Exception {
        VideoRequestDto requestDto = VideoTestData.createVideoRequestDto();
        VideoFullResponseDto expectedVideo = VideoTestData.createVideoFullResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/video")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        VideoFullResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), VideoFullResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expectedVideo, actual, "id");
    }

    @Test
    @DisplayName("Find all videos should return the page of VideoShortResponseDto")
    public void findAllVideos_givenVideoInDb_ShouldReturnPageOfVideoDto() throws Exception {
        List<VideoShortResponseDto> expectedVideos = new ArrayList<>();
        expectedVideos.add(VideoTestData.createVideoShortResponseDto());

        MvcResult result = mockMvc.perform(
                        get("/video")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode contentNode = jsonNode.get("content");
        VideoShortResponseDto[] actualVideos =
                objectMapper.treeToValue(contentNode, VideoShortResponseDto[].class);
        Assertions.assertNotNull(actualVideos);
        Assertions.assertEquals(expectedVideos.size(), actualVideos.length);
        EqualsBuilder.reflectionEquals(expectedVideos.get(0), actualVideos[0], "id");
    }

    @Test
    @DisplayName("Find video by valid id should return VideoFullResponseDto")
    public void findVideoById_withValidId_shouldReturnVideoDto() throws Exception {
        Long id = 1L;
        VideoFullResponseDto expectedVideo = VideoTestData.createVideoFullResponseDto();

        MvcResult result = mockMvc.perform(
                        get("/video/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        VideoFullResponseDto actualVideo = objectMapper.readValue(
                result.getResponse().getContentAsString(), VideoFullResponseDto.class
        );
        Assertions.assertNotNull(actualVideo);
        EqualsBuilder.reflectionEquals(expectedVideo, actualVideo, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update the video by valid data should return updated VideoFullResponseDto")
    public void updateVideo_withValidData_shouldUpdateVideo() throws Exception {
        Long id = 1L;
        VideoRequestDto requestDto = VideoTestData.createVideoRequestDto();
        requestDto.setTitle("Updated");
        VideoFullResponseDto expectedVideo = VideoTestData.createVideoFullResponseDto();
        expectedVideo.setTitle("Updated");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(
                        put("/video/{id}", id)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GrammarFullResponseDto actualVideo = objectMapper.readValue(
                result.getResponse().getContentAsString(), GrammarFullResponseDto.class);
        Assertions.assertNotNull(actualVideo);
        EqualsBuilder.reflectionEquals(expectedVideo, actualVideo, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete video by valid id")
    public void deleteVideo_validId_shouldDeleteVideo() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/video/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
