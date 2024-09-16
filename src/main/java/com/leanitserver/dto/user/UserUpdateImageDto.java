package com.leanitserver.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserUpdateImageDto {
    @NotNull(message = "Image id can't be null")
    @Positive(message = "Image id should be positive")
    private Integer imageId;
}
