package mate.leanitserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.search.SearchResponseDto;
import mate.leanitserver.service.SearchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Tag(name = "Search endpoint", description = "Endpoint for searching among all entities")
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    @Operation(summary = "Find all by title",
            description = "Find all by title among all entities")
    public List<SearchResponseDto> findAllByTitle(@NotBlank String title) {
        return searchService.findAllByTitle(title);
    }
}
