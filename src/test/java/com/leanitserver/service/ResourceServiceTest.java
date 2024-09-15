package com.leanitserver.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.leanitserver.dto.resource.ResourceRequestDto;
import com.leanitserver.dto.resource.ResourceResponseDto;
import com.leanitserver.exception.EntityNotFoundException;
import com.leanitserver.mapper.ResourceMapper;
import com.leanitserver.model.ArticleType;
import com.leanitserver.model.Resource;
import com.leanitserver.model.User;
import com.leanitserver.repository.ArticleTypeRepository;
import com.leanitserver.repository.ResourceRepository;
import com.leanitserver.service.impl.ResourceServiceImpl;
import com.leanitserver.testdata.ResourceTestData;
import com.leanitserver.testdata.UserTestData;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ResourceServiceTest {
    public static final String RESOURCE_NOT_FOUND_EXCEPTION = "Can't find resource by id = %d";
    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private ResourceMapper resourceMapper;
    @Mock
    private ArticleTypeRepository articleTypeRepository;
    @InjectMocks
    private ResourceServiceImpl resourceService;

    @Test
    @DisplayName("Save resource with valid data should return ResourceResponseDto")
    public void saveResource_withValidData_ShouldReturnResourceResponseDto() {
        ResourceRequestDto requestDto = ResourceTestData.createResourceRequestDto();
        Resource resource = ResourceTestData.createDefaultResource();
        ArticleType articleType = new ArticleType();
        articleType.setName(ArticleType.ArticleName.RESOURCES);
        ResourceResponseDto expectedResource = ResourceTestData.createResourceResponseDto();
        User user = UserTestData.createDefaultUser();

        when(resourceMapper.toModel(requestDto)).thenReturn(resource);
        when(articleTypeRepository.findByName(ArticleType.ArticleName.RESOURCES))
                .thenReturn(Optional.of(articleType));
        when(resourceRepository.save(resource)).thenReturn(resource);
        when(resourceMapper.toDto(resource)).thenReturn(expectedResource);

        ResourceResponseDto actualResource = resourceService.save(requestDto, user);

        Assertions.assertNotNull(actualResource);
        Assertions.assertEquals(expectedResource, actualResource);
        verify(resourceMapper, times(1)).toModel(requestDto);
        verify(articleTypeRepository, times(1)).findByName(ArticleType.ArticleName.RESOURCES);
        verify(resourceRepository, times(1)).save(resource);
        verify(resourceMapper, times(1)).toDto(resource);
    }

    @Test
    @DisplayName("Find resource by id should return ResourceResponseDto")
    public void findById_withValidId_ShouldReturnResourceResponseDto() {
        Long id = 1L;
        Resource resource = ResourceTestData.createDefaultResource();
        ResourceResponseDto expectedResource = ResourceTestData.createResourceResponseDto();

        when(resourceRepository.findById(id)).thenReturn(Optional.of(resource));
        when(resourceMapper.toDto(resource)).thenReturn(expectedResource);

        ResourceResponseDto actualResource = resourceService.findById(id);

        Assertions.assertNotNull(actualResource);
        Assertions.assertEquals(expectedResource, actualResource);
        verify(resourceRepository, times(1)).findById(id);
        verify(resourceMapper, times(1)).toDto(resource);
    }

    @Test
    @DisplayName("Find resource by invalid id should throw EntityNotFoundException")
    public void findById_withInvalidId_ShouldThrowEntityNotFoundException() {
        Long id = 100L;

        when(resourceRepository.findById(id)).thenReturn(Optional.empty());

        String actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> resourceService.findById(id)).getMessage();
        String expected = String.format(RESOURCE_NOT_FOUND_EXCEPTION, id);

        Assertions.assertEquals(expected, actual);
        verify(resourceRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Update resource with valid data should return updated ResourceResponseDto")
    public void updateResource_withValidData_ShouldReturnUpdatedResourceResponseDto() {
        Long id = 1L;
        ResourceRequestDto requestDto = ResourceTestData.createResourceRequestDto();
        Resource resource = ResourceTestData.createDefaultResource();
        ResourceResponseDto expectedResource = ResourceTestData.createResourceResponseDto();
        User user = UserTestData.createDefaultUser();

        when(resourceRepository.findByIdAndUser(id, user)).thenReturn(Optional.of(resource));
        when(resourceRepository.save(resource)).thenReturn(resource);
        when(resourceMapper.toDto(resource)).thenReturn(expectedResource);

        ResourceResponseDto actualResource = resourceService.update(id, requestDto, user);

        Assertions.assertNotNull(actualResource);
        Assertions.assertEquals(expectedResource, actualResource);
        verify(resourceRepository, times(1)).findByIdAndUser(id, user);
        verify(resourceRepository, times(1)).save(resource);
        verify(resourceMapper, times(1)).toDto(resource);
    }

    @Test
    @DisplayName("Delete resource by valid id should successfully delete resource")
    public void deleteResource_withValidId_ShouldDeleteResource() {
        Long id = 1L;
        Resource resource = ResourceTestData.createDefaultResource();

        when(resourceRepository.findById(id)).thenReturn(Optional.of(resource));

        resourceService.delete(id);

        verify(resourceRepository, times(1)).findById(id);
        verify(resourceRepository, times(1)).delete(resource);
    }

    @Test
    @DisplayName("Find all resources should return Page of ResourceResponseDto")
    public void findAll_shouldReturnPageOfResourceResponseDto() {
        Pageable pageable = Pageable.ofSize(10);
        Resource resource = ResourceTestData.createDefaultResource();
        ResourceResponseDto expectedResource = ResourceTestData.createResourceResponseDto();
        Page<Resource> resourcePage = new PageImpl<>(Collections.singletonList(resource));

        when(resourceRepository.findAll(pageable)).thenReturn(resourcePage);
        when(resourceMapper.toDto(resource)).thenReturn(expectedResource);

        Page<ResourceResponseDto> actualPage = resourceService.findAll(pageable);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(1, actualPage.getTotalElements());
        Assertions.assertEquals(expectedResource, actualPage.getContent().get(0));
        verify(resourceRepository, times(1)).findAll(pageable);
        verify(resourceMapper, times(1)).toDto(resource);
    }

    @Test
    @DisplayName("Find all resources by user should return Page of ResourceResponseDto")
    public void findAllByUser_shouldReturnPageOfResourceResponseDto() {
        Pageable pageable = Pageable.ofSize(10);
        User user = UserTestData.createDefaultUser();
        Resource resource = ResourceTestData.createDefaultResource();
        ResourceResponseDto expectedResource = ResourceTestData.createResourceResponseDto();
        Page<Resource> resourcePage = new PageImpl<>(Collections.singletonList(resource));

        when(resourceRepository.findAllByUser(user, pageable)).thenReturn(resourcePage);
        when(resourceMapper.toDto(resource)).thenReturn(expectedResource);

        Page<ResourceResponseDto> actualPage = resourceService.findAllByUser(user, pageable);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(1, actualPage.getTotalElements());
        Assertions.assertEquals(expectedResource, actualPage.getContent().get(0));
        verify(resourceRepository, times(1)).findAllByUser(user, pageable);
        verify(resourceMapper, times(1)).toDto(resource);
    }
}
