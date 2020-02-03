package com.cogent.persistence.listener;

import com.cogent.persistence.config.Action;
import com.cogent.persistence.history.AdminHistory;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.util.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import static com.cogent.persistence.config.Action.DELETED;
import static com.cogent.persistence.config.Action.INSERTED;
import static com.cogent.persistence.config.Action.UPDATED;
import static javax.transaction.Transactional.TxType.MANDATORY;

public class AdminEntityListener {

    @PrePersist
    public void prePersist(Admin target) {
        perform(target, INSERTED);
    }

    @PreUpdate
    public void preUpdate(Admin target) {
        perform(target, UPDATED);
    }

    @PreRemove
    public void preRemove(Admin target) {
        perform(target, DELETED);
    }

    @Transactional(MANDATORY)
    public void perform(Admin target, Action action) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(new AdminHistory(target, action));
    }
}

