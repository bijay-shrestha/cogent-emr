package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupUpdateRequestDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupMinimalResponseDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupResponseDTO;

import java.util.List;

/**
 * @author smriti on 2019-11-04
 */
public interface FollowUpSetupService {
    void save(FollowUpSetupRequestDTO requestDTO);

    void update(FollowUpSetupUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<FollowUpSetupMinimalResponseDTO> fetchFollowUpSetup();

    FollowUpSetupResponseDTO fetchDetailsById(Long id);
}
