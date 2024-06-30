package mate.leanitserver.service;

import mate.leanitserver.dto.resource.ResourceFullResponseDto;
import mate.leanitserver.dto.resource.ResourceShortResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceService {
    Page<ResourceShortResponseDto> findAll(Pageable pageable);

    ResourceFullResponseDto findById(Long id);
}
