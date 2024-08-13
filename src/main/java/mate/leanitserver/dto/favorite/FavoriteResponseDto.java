package mate.leanitserver.dto.favorite;

import lombok.Data;

@Data
public class FavoriteResponseDto {
    private Long id;
    private Long favoriteId;
    private String title;
    private String description;
    private String href;
    private String type;
    private String imageUrl;
}
