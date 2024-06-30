package mate.leanitserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.resource.ResourceFullResponseDto;
import mate.leanitserver.dto.resource.ResourceShortResponseDto;
import mate.leanitserver.service.ResourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
@Tag(name = "Resources management", description = "Endpoint for resources operations")
public class ResourceController {
    private final ResourceService resourceService;

    @GetMapping
    @Operation(summary = "Get all resources",
            description = "Retrieves all resources from the database")
    public Page<ResourceShortResponseDto> findAll(@PageableDefault Pageable pageable) {
        return resourceService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get resource by id",
            description = "Retrieves a resource by id from the database")
    public ResourceFullResponseDto findById(@PathVariable @Positive Long id) {
        return resourceService.findById(id);
    }
}
