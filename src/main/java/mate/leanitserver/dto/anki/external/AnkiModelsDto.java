package mate.leanitserver.dto.anki.external;

import java.util.List;
import lombok.Data;

@Data
public class AnkiModelsDto {
    private List<String> result;
    private String error;
}
