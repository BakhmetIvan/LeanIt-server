package com.leanitserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "videos")
@SQLDelete(sql = "UPDATE videos SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted = FALSE")
public class Video extends Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String videoUrl;
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "videos_anki_cards",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "anki_card_id")
    )
    private List<AnkiCard> ankiCards;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private ArticleType type;

    @Override
    public String getType() {
        return type.getName().getValue();
    }
}
