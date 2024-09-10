package com.leanitserver.service;

import com.leanitserver.dto.resource.ResourceRequestDto;
import com.leanitserver.dto.resource.ResourceResponseDto;
import com.leanitserver.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceService {
    Page<ResourceResponseDto> findAll(Pageable pageable);

    ResourceResponseDto findById(Long id);

    Page<ResourceResponseDto> findAllByUser(User user, Pageable pageable);

    ResourceResponseDto save(ResourceRequestDto requestDto, User user);

    ResourceResponseDto update(Long id, ResourceRequestDto requestDto, User user);

    void delete(Long id);
}
