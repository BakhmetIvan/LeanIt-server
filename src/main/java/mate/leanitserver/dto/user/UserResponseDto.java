package mate.leanitserver.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private Integer imageId;
}
