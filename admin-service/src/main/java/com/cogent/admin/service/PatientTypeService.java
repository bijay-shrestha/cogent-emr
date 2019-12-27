package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeSearchRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeUpdateRequestDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeResponseDTO;
import com.cogent.persistence.model.PatientType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author smriti on 2019-09-26
 */
public interface PatientTypeService {
    void save(PatientTypeRequestDTO requestDTO);

    void update(PatientTypeUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<PatientTypeMinimalResponseDTO> search(PatientTypeSearchRequestDTO searchRequestDTO,
                                               Pageable pageable);

    List<DropDownResponseDTO> fetchActivePatientTypeForDropdown();

    PatientTypeResponseDTO fetchDetailsById(Long id);

    PatientType fetchPatientTypeById(Long id);
}
