package mate.leanitserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.grammar.GrammarFullResponseDto;
import mate.leanitserver.dto.grammar.GrammarShortResponseDto;
import mate.leanitserver.service.GrammarService;
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
@RequestMapping("/grammar")
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
}
