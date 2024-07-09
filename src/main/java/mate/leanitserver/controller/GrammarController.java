package mate.leanitserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.grammar.GrammarFullResponseDto;
import mate.leanitserver.dto.grammar.GrammarRequestDto;
import mate.leanitserver.dto.grammar.GrammarShortResponseDto;
import mate.leanitserver.service.GrammarService;
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
@RequestMapping("/grammars")
@Tag(name = "Grammar management", description = "Endpoint for grammar operations")
public class GrammarController {
    private final GrammarService grammarService;

    @GetMapping
    @Operation(summary = "Get all grammars",
            description = "Retrieves all grammars from the database")
    public Page<GrammarShortResponseDto> findAll(@PageableDefault Pageable pageable) {
        return grammarService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get grammar by id",
            description = "Retrieves a grammar by id from the database")
    public GrammarFullResponseDto findById(@PathVariable @Positive Long id) {
        return grammarService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Save a new grammar",
            description = "Allows a user to save a new grammar")
    public GrammarFullResponseDto save(@Valid @RequestBody GrammarRequestDto requestDto) {
        return grammarService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update the grammar by id",
            description = "Allows a user to update the grammar")
    public GrammarFullResponseDto update(@PathVariable @Positive Long id,
                                       @Valid @RequestBody GrammarRequestDto requestDto) {
        return grammarService.update(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a grammar",
            description = "Allows an admin to delete a grammar by id")
    public void delete(@PathVariable @Positive Long id) {
        grammarService.delete(id);
    }
}
