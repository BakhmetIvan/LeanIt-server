package mate.leanitserver.dto.resource;

import lombok.Data;

@Data
public class ResourceFullResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String content;
    private String externalUrl;
}
