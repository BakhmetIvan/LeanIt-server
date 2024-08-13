package mate.leanitserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mate.leanitserver.repository.StringListConverter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "grammars")
@SQLDelete(sql = "UPDATE grammars SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted = FALSE")
public class Grammar extends Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String videoUrl;
    @Column(nullable = false)
    private String mainSubTitle;
    private String secondTitle;
    private String thirdTitle;
    private String thirdSubTitle;
    private String fourthTitle;
    private String fourthSubTitle;
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> underTitleList;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private ArticleType type;

    @Override
    public String getType() {
        return type.getName().getValue();
    }
}
