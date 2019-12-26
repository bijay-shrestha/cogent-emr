package com.cogent.adminservice.service.impl;

import com.example.demo.model.ProfileMenu;
import com.example.demo.repository.ProfileMenuRepository;
import com.example.demo.service.ProfileMenuService;
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
