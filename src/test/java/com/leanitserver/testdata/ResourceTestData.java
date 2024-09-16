package com.leanitserver.testdata;

import com.leanitserver.dto.resource.ResourceRequestDto;
import com.leanitserver.dto.resource.ResourceResponseDto;
import com.leanitserver.model.Resource;

public class ResourceTestData {
    public static Resource createDefaultResource() {
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setTitle("Resource title");
        resource.setImageUrl("Resource image url");
        resource.setDescription("Resource description");
        resource.setHref("Resource href");
        resource.setUser(UserTestData.createDefaultUser());
        resource.setType(ArticleTestData.createResourcesArticleType());
        return resource;
    }

    public static ResourceRequestDto createResourceRequestDto() {
        ResourceRequestDto requestDto = new ResourceRequestDto();
        requestDto.setTitle("Resource title");
        requestDto.setImageUrl("Resource image url");
        requestDto.setDescription("Resource description");
        requestDto.setHref("Resource href");
        return requestDto;
    }

    public static ResourceResponseDto createResourceResponseDto() {
        ResourceResponseDto responseDto = new ResourceResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Resource title");
        responseDto.setImageUrl("Resource image url");
        responseDto.setDescription("Resource description");
        responseDto.setHref("Resource href");
        responseDto.setType("RESOURCES");
        return responseDto;
    }
}
