package mate.leanitserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.video.VideoFullResponseDto;
import mate.leanitserver.dto.video.VideoRequestDto;
import mate.leanitserver.dto.video.VideoShortResponseDto;
import mate.leanitserver.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
@Tag(name = "Video management", description = "Endpoint for video operations")
public class VideoController {
    private final VideoService videoService;

    @GetMapping
    @Operation(summary = "Get all videos",
            description = "Retrieves all videos from the database")
    public Page<VideoShortResponseDto> findAll(@PageableDefault Pageable pageable) {
        return videoService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get video by id",
            description = "Retrieves a video by id from the database")
    public VideoFullResponseDto findById(@PathVariable @Positive Long id) {
        return videoService.finById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Save a new video",
            description = "Allows a user to save a new video")
    public VideoFullResponseDto save(@Valid @RequestBody VideoRequestDto requestDto) {
        return videoService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update the video by id",
            description = "Allows a user to update the video")
    public VideoFullResponseDto update(@PathVariable @Positive Long id,
                                          @Valid @RequestBody VideoRequestDto requestDto) {
        return videoService.update(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a video",
            description = "Allows an admin to delete a video by id")
    public void delete(@PathVariable @Positive Long id) {
        videoService.delete(id);
    }
}
