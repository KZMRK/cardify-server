package com.cardify.model.entity.listener;

import com.cardify.model.entity.BaseEntity;
import com.cardify.model.entity.User;
import com.cardify.security.CardifyContextHolder;
import jakarta.persistence.PrePersist;

public class BaseEntityListener {

    @PrePersist
    public void setCreatedBy(BaseEntity baseEntity) {
        User user = new User();
        user.setId(CardifyContextHolder.getUserId());
        baseEntity.setCreatedBy(user);
    }
}
