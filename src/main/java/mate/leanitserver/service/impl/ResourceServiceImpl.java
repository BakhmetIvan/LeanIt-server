package mate.leanitserver.service.impl;

import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.resource.ResourceFullResponseDto;
import mate.leanitserver.dto.resource.ResourceShortResponseDto;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.mapper.ResourceMapper;
import mate.leanitserver.repository.ResourceRepository;
import mate.leanitserver.service.ResourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    @Override
    public Page<ResourceShortResponseDto> findAll(Pageable pageable) {
        return resourceRepository.findAll(pageable)
                .map(resourceMapper::toShortDto);
    }

    @Override
    public ResourceFullResponseDto findById(Long id) {
        return resourceMapper.toFullDto(resourceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Can't find resource by id = %d", id)))
        );
    }
}
