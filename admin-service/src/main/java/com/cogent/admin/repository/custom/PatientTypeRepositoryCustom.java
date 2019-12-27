package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeSearchRequestDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 2019-09-26
 */
@Repository
@Qualifier("PatientTypeRepositoryCustom")
public interface PatientTypeRepositoryCustom {
    Long fetchPatientTypeByName(String name);

    Long fetchPatientTypeByIdAndName(Long id, String name);

    List<PatientTypeMinimalResponseDTO> search(PatientTypeSearchRequestDTO searchRequestDTO,
                                               Pageable pageable);

    List<DropDownResponseDTO> fetchActivePatientTypeForDropdown();

    PatientTypeResponseDTO fetchDetailsById(Long id);
}
