package com.leanitserver.controller;

import com.leanitserver.dto.resource.ResourceRequestDto;
import com.leanitserver.dto.resource.ResourceResponseDto;
import com.leanitserver.model.User;
import com.leanitserver.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/resources")
@Tag(name = "Resources management", description = "Endpoint for resources operations")
public class ResourceController {
    private final ResourceService resourceService;

    @GetMapping
    @Operation(summary = "Get all resources",
            description = "Retrieves all resources from the database")
    public Page<ResourceResponseDto> findAll(@PageableDefault Pageable pageable) {
        return resourceService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get resource by id",
            description = "Retrieves a resource by id from the database")
    public ResourceResponseDto findById(@PathVariable @Positive Long id) {
        return resourceService.findById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save a new resource",
            description = "Allows a user and admin to save a new resource")
    public ResourceResponseDto save(@Valid @RequestBody ResourceRequestDto requestDto,
                                    Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return resourceService.save(requestDto, user);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update the resource by id",
            description = "Allows a user and admin to update the resource")
    public ResourceResponseDto update(@PathVariable @Positive Long id,
                                      @Valid @RequestBody ResourceRequestDto requestDto,
                                      Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return resourceService.update(id, requestDto, user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a resource",
            description = "Allows an admin to delete a resource by id")
    public void delete(@PathVariable @Positive Long id) {
        resourceService.delete(id);
    }
}
