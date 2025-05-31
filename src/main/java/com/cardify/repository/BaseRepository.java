package com.cardify.repository;

import com.cardify.model.entity.BaseEntity_;
import com.cardify.model.entity.User_;
import com.cardify.model.exception.NotFoundException;
import com.cardify.security.CardifyContextHolder;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

    String SEARCH_QUERY_TEMPLATE = "%%%s%%";

    default T findOneById(String id) {
        Specification<T> specification = (root, query, cb) -> cb.equal(root.get(BaseEntity_.ID), id);
        return findOne(specification.and(createdByCurrentUser())).orElseThrow(() -> new NotFoundException("Resource not found"));
    }

    @NotNull
    default Page<T> findAllOrdered(Specification<T> specification, @NotNull Pageable pageable) {
        return findAll(specification.and(createdByCurrentUser()).and(ordered()), pageable);
    }

    private Specification<T> createdByCurrentUser() {
        return (root, query, cb) ->
                cb.equal(root.get(BaseEntity_.CREATED_BY).get(User_.ID), CardifyContextHolder.getUserId());
    }

    private Specification<T> ordered() {
        return (root, query, cb) -> {
            assert query != null;
            query.orderBy(cb.desc(root.get(BaseEntity_.CREATED_AT)));
            return null;
        };
    }
}
