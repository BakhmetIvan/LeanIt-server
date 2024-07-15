package mate.leanitserver.service.impl;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.resource.ResourceFullResponseDto;
import mate.leanitserver.dto.resource.ResourceRequestDto;
import mate.leanitserver.dto.resource.ResourceShortResponseDto;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.mapper.ResourceMapper;
import mate.leanitserver.model.ArticleType;
import mate.leanitserver.model.Resource;
import mate.leanitserver.model.User;
import mate.leanitserver.repository.ArticleTypeRepository;
import mate.leanitserver.repository.ResourceRepository;
import mate.leanitserver.service.ResourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private static final String NOT_FOUND_RESOURCE_EXCEPTION = "Can't find resource by id = %d";
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final ArticleTypeRepository articleTypeRepository;

    @Override
    public Page<ResourceShortResponseDto> findAll(Pageable pageable) {
        return resourceRepository.findAll(pageable)
                .map(resourceMapper::toShortDto);
    }

    @Override
    public ResourceFullResponseDto findById(Long id) {
        return resourceMapper.toFullDto(resourceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_RESOURCE_EXCEPTION, id)))
        );
    }

    @Override
    public Page<ResourceShortResponseDto> findAllByUser(User user, Pageable pageable) {
        return resourceRepository.findAllByUser(user, pageable)
                .map(resourceMapper::toShortDto);
    }

    @Transactional
    @Override
    public ResourceFullResponseDto save(ResourceRequestDto requestDto, User user) {
        Resource resource = resourceMapper.toModel(requestDto);
        resource.setUser(user);
        resource.setType(articleTypeRepository
                .findByName(ArticleType.ArticleName.RESOURCES)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find article type"))
        );
        return resourceMapper.toFullDto(resourceRepository.save(resource));
    }

    @Transactional
    @Override
    public ResourceFullResponseDto update(Long id, ResourceRequestDto requestDto, User user) {
        Resource resource = resourceRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_RESOURCE_EXCEPTION, id))
        );
        resourceMapper.updateResourceFromDto(resource, requestDto);
        return resourceMapper.toFullDto(resourceRepository.save(resource));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Resource resource = resourceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        NOT_FOUND_RESOURCE_EXCEPTION, id))
        );
        resourceRepository.delete(resource);
    }
}
