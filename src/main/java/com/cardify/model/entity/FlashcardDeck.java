package com.cardify.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "flashcard_decks")
public class FlashcardDeck extends BaseEntity {

    @Column(name = "title", length = 64)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "cover_id")
    private CloudFile cover;

    @Setter(AccessLevel.PRIVATE)
    @OrderBy("createdAt desc")
    @OneToMany(mappedBy = "flashcardDeck", cascade = CascadeType.ALL)
    private List<Flashcard> flashcards = new ArrayList<>();

    public void addFlashcard(Flashcard flashcard) {
        flashcard.setFlashcardDeck(this);
        flashcards.add(flashcard);
    }
}
