package com.leanitserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@MappedSuperclass
public abstract class Article implements Searchable {
    @Column(nullable = false)
    private String title;
    private String imageUrl;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String href;
    @ToString.Exclude
    @Column(nullable = false)
    private boolean isDeleted = false;
}
