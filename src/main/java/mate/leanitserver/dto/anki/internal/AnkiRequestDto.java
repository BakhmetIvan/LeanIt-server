package mate.leanitserver.dto.anki.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AnkiRequestDto {
    @NotBlank(message = "Image url can't be blank")
    private String imageUrl;
    @NotBlank(message = "Front text can't be blank")
    @Length(max = 255, message = "Front can't be longer than 255")
    private String front;
    @NotBlank(message = "Back text can't be blank")
    private String back;
    @NotNull(message = "Video id can't be null")
    @Positive(message = "Video id should be positive")
    private Long videoId;
}
