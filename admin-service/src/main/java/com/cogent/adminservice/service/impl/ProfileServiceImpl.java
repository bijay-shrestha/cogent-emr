package com.cogent.adminservice.service.impl;

import com.cogent.adminservice.repository.ProfileRepository;
import com.cogent.adminservice.service.ProfileService;
import com.cogent.persistence.model.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile findProfileById(Long id) {
        return profileRepository.findProfileById(id);
    }
}
