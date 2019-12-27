package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.applicationModules.ApplicationModuleSearchRequestDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDetailResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDropdownResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleMinimalResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti ON 24/12/2019
 */
@Repository
@Qualifier("applicationModuleRepositoryCustom")
public interface ApplicationModuleRepositoryCustom {

    Long fetchApplicationModuleByName(String name);

    Long fetchApplicationModuleByIdAndName(Long id, String name);

    List<ApplicationModuleMinimalResponseDTO> search(ApplicationModuleSearchRequestDTO searchRequestDTO,
                                                     Pageable pageable);

    ApplicationModuleDetailResponseDTO fetchDetailsById(Long id);

    List<ApplicationModuleDropdownResponseDTO> fetchActiveApplicationModuleForDropDown();
}
