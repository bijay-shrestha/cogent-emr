package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleSearchRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleUpdateRequestDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDetailResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDropdownResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleMinimalResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author smriti ON 24/12/2019
 */

public interface ApplicationModuleService {

    void save(ApplicationModuleRequestDTO requestDTO);

    void update(ApplicationModuleUpdateRequestDTO updateRequestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<ApplicationModuleMinimalResponseDTO> search(ApplicationModuleSearchRequestDTO searchRequestDTO,
                                                     Pageable pageable);

    ApplicationModuleDetailResponseDTO fetchDetailsById(Long id);

    List<ApplicationModuleDropdownResponseDTO> fetchActiveApplicationModuleForDropDown();
}
