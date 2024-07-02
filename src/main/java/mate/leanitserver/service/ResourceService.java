package mate.leanitserver.service;

import mate.leanitserver.dto.resource.ResourceFullResponseDto;
import mate.leanitserver.dto.resource.ResourceRequestDto;
import mate.leanitserver.dto.resource.ResourceShortResponseDto;
import mate.leanitserver.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceService {
    Page<ResourceShortResponseDto> findAll(Pageable pageable);

    ResourceFullResponseDto findById(Long id);

    ResourceFullResponseDto save(ResourceRequestDto requestDto, User user);

    ResourceFullResponseDto update(Long id, ResourceRequestDto requestDto);
}
