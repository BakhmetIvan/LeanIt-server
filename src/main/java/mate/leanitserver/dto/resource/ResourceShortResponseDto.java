package mate.leanitserver.dto.resource;

import lombok.Data;

@Data
public class ResourceShortResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private String type;
}
