package com.cogent.contextserver.config;
import com.cogent.contextserver.constant.Action;
import com.cogent.contextserver.model.File;
import com.cogent.contextserver.model.FileHistory;
import com.cogent.contextserver.utils.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import static com.cogent.contextserver.constant.Action.DELETED;
import static com.cogent.contextserver.constant.Action.INSERTED;
import static com.cogent.contextserver.constant.Action.UPDATED;
import static javax.transaction.Transactional.TxType.MANDATORY;

public class FileEntityListener {

    @PrePersist
    public void prePersist(File target) {
        perform(target, INSERTED);
    }

    @PreUpdate
    public void preUpdate(File target) {
        perform(target, UPDATED);
    }

    @PreRemove
    public void preRemove(File target) {
        perform(target, DELETED);
    }

    @Transactional(MANDATORY)
    void perform(File target, Action action) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(new FileHistory(target, action));
    }

}