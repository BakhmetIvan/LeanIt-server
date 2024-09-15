package com.leanitserver.controller;

import com.leanitserver.dto.anki.internal.AnkiRequestDto;
import com.leanitserver.dto.anki.internal.AnkiResponseDto;
import com.leanitserver.service.AnkiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/anki")
@Tag(name = "Anki cards management", description = "Endpoints for anki operations")
public class AnkiController {
    private final AnkiService ankiService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save a new anki card",
            description = "Allows an admin to save a new anki card")
    public AnkiResponseDto save(@RequestBody @Valid AnkiRequestDto requestDto) {
        return ankiService.save(requestDto);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add anki card to deck",
            description = "Allows a user to add a new anki card to a deck")
    public void addAnkiCardToDeck(@PathVariable @Positive Long id) {
        ankiService.addCardToDeck(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update the anki card by id",
            description = "Allows an admin to update the anki card")
    public AnkiResponseDto update(@PathVariable @Positive Long id,
                                          @RequestBody @Valid AnkiRequestDto requestDto) {
        return ankiService.update(id, requestDto);
    }

    @GetMapping
    @Operation(summary = "Get all anki cards",
            description = "Retrieves all anki cards from the database")
    public List<AnkiResponseDto> findAll(@PageableDefault Pageable pageable) {
        return ankiService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get anki card by id",
            description = "Retrieves a anki card by id from the database")
    public AnkiResponseDto findById(@PathVariable @Positive Long id) {
        return ankiService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete anki card",
            description = "Allows an admin to delete a anki card by id")
    public void delete(@PathVariable @Positive Long id) {
        ankiService.delete(id);
    }
}
