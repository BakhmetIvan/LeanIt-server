package com.leanitserver.testdata;

import com.leanitserver.dto.search.SearchResponseDto;
import com.leanitserver.model.Grammar;
import com.leanitserver.model.Resource;
import com.leanitserver.model.Video;
import java.util.ArrayList;
import java.util.List;

public class SearchTestData {
    public static List<SearchResponseDto> createListOfResponseObjects() {
        Grammar grammar = GrammarTestData.createDefaultGrammar();
        SearchResponseDto grammarSearch = new SearchResponseDto();
        grammarSearch.setId(grammar.getId());
        grammarSearch.setTitle(grammar.getTitle());
        grammarSearch.setDescription(grammar.getDescription());
        grammarSearch.setImageUrl(grammar.getImageUrl());
        grammarSearch.setHref(grammar.getHref());
        Resource resource = ResourceTestData.createDefaultResource();
        SearchResponseDto resourceSearch = new SearchResponseDto();
        resourceSearch.setId(resource.getId());
        resourceSearch.setTitle(resource.getTitle());
        resourceSearch.setDescription(resource.getDescription());
        resourceSearch.setImageUrl(resource.getImageUrl());
        resourceSearch.setHref(resource.getHref());
        Video video = VideoTestData.createDefaultVideo();
        SearchResponseDto videoSearch = new SearchResponseDto();
        videoSearch.setId(video.getId());
        videoSearch.setTitle(video.getTitle());
        videoSearch.setDescription(video.getDescription());
        videoSearch.setImageUrl(video.getImageUrl());
        videoSearch.setHref(video.getHref());
        List<SearchResponseDto> searchResponseList = new ArrayList<>();
        searchResponseList.add(grammarSearch);
        searchResponseList.add(resourceSearch);
        searchResponseList.add(videoSearch);
        return searchResponseList;
    }
}
