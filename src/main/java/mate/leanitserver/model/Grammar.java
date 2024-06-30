package mate.leanitserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "grammars")
@SQLDelete(sql = "UPDATE grammars SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction(value = "is_deleted = FALSE")
public class Grammar implements Searchable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;
    @Lob
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String externalUrl;
    @ManyToMany
    @JoinTable(
            name = "related_grammars",
            joinColumns = @JoinColumn(name = "grammar_id"),
            inverseJoinColumns = @JoinColumn(name = "related_grammar_id")
    )
    private List<Grammar> relatedTopics;
    @ToString.Exclude
    @Column(nullable = false)
    private boolean isDeleted = false;

    @Override
    public String getType() {
        return "grammars";
    }
}
