package com.cardify.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "flashcards")
public class Flashcard extends BaseEntity {

    @Column(name = "front_term", length = 64)
    private String frontTerm;

    @OneToOne
    @JoinColumn(name = "front_image_id")
    private CloudFile frontImage;

    @Column(name = "back_term", length = 64)
    private String backTerm;

    @Column(name = "back_context")
    private String backContext;

    @OneToOne
    @JoinColumn(name = "back_image_id")
    private CloudFile backImage;

    @ManyToOne
    @JoinColumn(name = "flashcard_deck_id")
    private FlashcardDeck flashcardDeck;

    @Column(name = "is_learned")
    private Boolean isLearned;
}
