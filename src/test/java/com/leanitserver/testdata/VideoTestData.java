package com.leanitserver.testdata;

import com.leanitserver.dto.video.VideoFullResponseDto;
import com.leanitserver.dto.video.VideoRequestDto;
import com.leanitserver.dto.video.VideoShortResponseDto;
import com.leanitserver.model.Video;
import java.util.List;

public class VideoTestData {
    public static VideoRequestDto createVideoRequestDto() {
        VideoRequestDto requestDto = new VideoRequestDto();
        requestDto.setTitle("Video");
        requestDto.setDescription("Video description");
        requestDto.setVideoUrl("Video Url");
        requestDto.setHref("Video href");
        requestDto.setImageUrl("Video image url");
        return requestDto;
    }

    public static VideoFullResponseDto createVideoFullResponseDto() {
        VideoFullResponseDto responseDto = new VideoFullResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Video");
        responseDto.setDescription("Video description");
        responseDto.setVideoUrl("Video Url");
        responseDto.setHref("Video href");
        responseDto.setImageUrl("Video image url");
        responseDto.setAnkiCards(List.of(AnkiTestData.createAnkiResponseDto()));
        return responseDto;
    }

    public static Video createDefaultVideo() {
        Video video = new Video();
        video.setId(1L);
        video.setTitle("Video");
        video.setDescription("Video description");
        video.setVideoUrl("Video Url");
        video.setHref("Video href");
        video.setImageUrl("Video image url");
        video.setAnkiCards(List.of(AnkiTestData.createDefaultAnkiCard()));
        video.setType(ArticleTestData.createVideoArticleType());
        return video;
    }

    public static VideoShortResponseDto createVideoShortResponseDto() {
        VideoShortResponseDto responseDto = new VideoShortResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Video");
        responseDto.setDescription("Video description");
        responseDto.setImageUrl("Video image url");
        responseDto.setType("VIDEO");
        return responseDto;
    }
}
