package com.cogent.adminservice.service.impl;

import com.cogent.adminservice.repository.ProfileMenuRepository;
import com.cogent.adminservice.service.ProfileMenuService;
import com.cogent.persistence.model.ProfileMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class ProfileMenuServiceImpl implements ProfileMenuService {

    private ProfileMenuRepository profileMenuRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ProfileMenuServiceImpl(ProfileMenuRepository profileMenuRepository) {
        this.profileMenuRepository = profileMenuRepository;
    }

    @Override
    public ProfileMenu getProfileMenuByUsername(String t) {

        return null;

    }
}
