package com.cardify.model.entity.listener;

import com.cardify.model.entity.BaseEntity;
import com.cardify.model.entity.User;
import com.cardify.security.CardifyContextHolder;
import jakarta.persistence.PrePersist;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseEntityListener {

    @PrePersist
    public void setCreatedBy(BaseEntity baseEntity) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return;
        }
        User user = new User();
        user.setId(CardifyContextHolder.getUserId());
        baseEntity.setCreatedBy(user);
    }
}
