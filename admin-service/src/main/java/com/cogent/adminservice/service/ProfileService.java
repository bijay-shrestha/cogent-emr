package com.cogent.adminservice.service;

import com.cogent.persistence.model.Profile;

public interface ProfileService {
    Profile findProfileById(Long profileId);
}
