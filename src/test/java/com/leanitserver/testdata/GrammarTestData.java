package com.leanitserver.testdata;

import com.leanitserver.dto.grammar.GrammarFullResponseDto;
import com.leanitserver.dto.grammar.GrammarRequestDto;
import com.leanitserver.dto.grammar.GrammarShortResponseDto;
import com.leanitserver.model.Grammar;
import java.util.Arrays;

public class GrammarTestData {
    public static Grammar createDefaultGrammar() {
        Grammar grammar = new Grammar();
        grammar.setId(1L);
        grammar.setTitle("Grammar title");
        grammar.setImageUrl("Grammar image url");
        grammar.setDescription("Grammar description");
        grammar.setVideoUrl("Grammar video url");
        grammar.setMainSubTitle("Grammar main sub title");
        grammar.setSecondTitle("Grammar second title");
        grammar.setThirdTitle("Grammar third title");
        grammar.setThirdSubTitle("Grammar third sub title");
        grammar.setFourthTitle("Grammar fourth title");
        grammar.setFourthSubTitle("Grammar fourth sub title");
        grammar.setUnderTitleList(Arrays.asList("First under title", "Second under title"));
        grammar.setHref("Grammar href");
        grammar.setType(ArticleTestData.createGrammarArticleType());
        return grammar;
    }

    public static GrammarRequestDto createGrammarRequestDto() {
        GrammarRequestDto requestDto = new GrammarRequestDto();
        requestDto.setTitle("Grammar title");
        requestDto.setImageUrl("Grammar image url");
        requestDto.setDescription("Grammar description");
        requestDto.setVideoUrl("Grammar video url");
        requestDto.setMainSubTitle("Grammar main sub title");
        requestDto.setSecondTitle("Grammar second title");
        requestDto.setThirdTitle("Grammar third title");
        requestDto.setThirdSubTitle("Grammar third sub title");
        requestDto.setFourthTitle("Grammar fourth title");
        requestDto.setFourthSubTitle("Grammar fourth sub title");
        requestDto.setUnderTitleList(Arrays.asList("First under title", "Second under title"));
        requestDto.setHref("Grammar href");
        return requestDto;
    }

    public static GrammarFullResponseDto createGrammarFullResponseDto() {
        GrammarFullResponseDto responseDto = new GrammarFullResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Grammar title");
        responseDto.setImageUrl("Grammar image url");
        responseDto.setDescription("Grammar description");
        responseDto.setVideoUrl("Grammar video url");
        responseDto.setMainSubTitle("Grammar main sub title");
        responseDto.setSecondTitle("Grammar second title");
        responseDto.setThirdTitle("Grammar third title");
        responseDto.setThirdSubTitle("Grammar third sub title");
        responseDto.setFourthTitle("Grammar fourth title");
        responseDto.setFourthSubTitle("Grammar fourth sub title");
        responseDto.setUnderTitleList(Arrays.asList("First under title", "Second under title"));
        responseDto.setHref("Grammar href");
        return responseDto;
    }

    public static GrammarShortResponseDto createGrammarShortResponseDto() {
        GrammarShortResponseDto responseDto = new GrammarShortResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Grammar title");
        responseDto.setImageUrl("Grammar image url");
        responseDto.setDescription("Grammar description");
        responseDto.setType("GRAMMAR");
        return responseDto;
    }
}
