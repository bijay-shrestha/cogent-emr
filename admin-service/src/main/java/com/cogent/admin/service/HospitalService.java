package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalSearchRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalUpdateRequestDTO;
import com.cogent.admin.dto.response.hospital.HospitalDropdownDTO;
import com.cogent.admin.dto.response.hospital.HospitalResponseDTO;
import com.cogent.persistence.model.Hospital;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Rupak
 */
public interface HospitalService {
    void save(HospitalRequestDTO hospitalRequestDTO);

    List<HospitalDropdownDTO> hospitalDropdown();

    List<HospitalResponseDTO> searchHospital(
            HospitalSearchRequestDTO hospitalSearchRequestDTO,
            Pageable pageable);

    void deleteHospital(DeleteRequestDTO deleteRequestDTO);

    void updateHospital(HospitalUpdateRequestDTO updateRequestDTO);

    Hospital fetchHospital(Long id);

    List<HospitalDropdownDTO> fetchHospitalForDropDown();
}
