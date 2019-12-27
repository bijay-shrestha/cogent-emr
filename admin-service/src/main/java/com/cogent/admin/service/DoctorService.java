package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorSearchRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorUpdateRequestDTO;
import com.cogent.admin.dto.response.doctor.DoctorDetailResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorDropdownDTO;
import com.cogent.admin.dto.response.doctor.DoctorMinimalResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorUpdateResponseDTO;
import com.cogent.persistence.model.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author smriti on 2019-09-29
 */
public interface DoctorService {

    String save(DoctorRequestDTO requestDTO, MultipartFile avatar, MultipartFile signature);

    void update(DoctorUpdateRequestDTO requestDTO, MultipartFile avatar, MultipartFile signature);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<DoctorMinimalResponseDTO> search(DoctorSearchRequestDTO searchRequestDTO,
                                          Pageable pageable);

    List<DoctorDropdownDTO> fetchDoctorForDropdown();

    DoctorDetailResponseDTO fetchDetailsById(Long id);

    Doctor fetchDoctorById(Long id);

    List<DoctorDropdownDTO> fetchDoctorBySpecializationId(Long specializationId);

    DoctorUpdateResponseDTO fetchDetailsForUpdate(Long id);
}
