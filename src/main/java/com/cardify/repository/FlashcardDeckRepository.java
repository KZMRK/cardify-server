package com.cardify.repository;

import com.cardify.model.entity.FlashcardDeck;
import com.cardify.model.entity.FlashcardDeck_;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface FlashcardDeckRepository extends BaseRepository<FlashcardDeck> {

    default Page<FlashcardDeck> findAllOrderedBySearch(String search, Pageable pageable) {
        Specification<FlashcardDeck> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(search)) {
                String searchTemplate = SEARCH_QUERY_TEMPLATE.formatted(StringUtils.lowerCase(search));
                predicates.add(cb.like(cb.lower(root.get(FlashcardDeck_.TITLE)), searchTemplate));
            }
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
        return findAllOrdered(specification, pageable);
    }
}
