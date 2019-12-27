package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupMinimalResponseDTO;
import com.cogent.admin.dto.response.followUpSetup.FollowUpSetupResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 2019-11-04
 */
@Repository
@Qualifier("followUpSetupRepositoryCustom")
public interface FollowUpSetupRepositoryCustom {
    List<FollowUpSetupMinimalResponseDTO> fetchFollowUpSetup();

    FollowUpSetupResponseDTO fetchDetailsById(Long id);
}
