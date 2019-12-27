package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.specialization.SpecializationRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationSearchRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationUpdateRequestDTO;
import com.cogent.admin.dto.response.specialization.SpecializationMinimalResponseDTO;
import com.cogent.admin.dto.response.specialization.SpecializationResponseDTO;
import com.cogent.persistence.model.Specialization;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author smriti on 2019-08-11
 */
public interface SpecializationService {
    void save(SpecializationRequestDTO requestDTO);

    void update(SpecializationUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<SpecializationMinimalResponseDTO> search(SpecializationSearchRequestDTO searchRequestDTO,
                                                  Pageable pageable);

    List<DropDownResponseDTO> fetchActiveSpecializationForDropDown();

    SpecializationResponseDTO fetchDetailsById(Long id);

    List<DropDownResponseDTO> fetchSpecializationByDoctorId(Long DoctorId);

    Specialization fetchActiveSpecializationById(Long specializationId);
}
