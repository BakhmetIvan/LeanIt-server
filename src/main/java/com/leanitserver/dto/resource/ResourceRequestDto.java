package com.leanitserver.dto.resource;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ResourceRequestDto {
    @NotBlank(message = "Title can't be blank")
    @Length(max = 255, message = "Title can't be longer than 255")
    private String title;
    private String imageUrl;
    @NotBlank(message = "Description can't be blank")
    private String description;
    @NotBlank(message = "External Url can't be blank")
    private String href;
}
