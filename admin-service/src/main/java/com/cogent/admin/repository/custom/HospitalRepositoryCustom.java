package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.hospital.HospitalSearchRequestDTO;
import com.cogent.admin.dto.response.hospital.HospitalDropDownResponseDTO;
import com.cogent.admin.dto.response.hospital.HospitalResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rupak
 */
@Repository
@Qualifier("hospitalRepositoryCustom")
public interface HospitalRepositoryCustom {

    Long fetchHospitalByName(String name);

    Long findHospitalByIdAndName(Long id, String name);

    List<HospitalResponseDTO> search(HospitalSearchRequestDTO searchRequestDTO, Pageable pageable);

    HospitalResponseDTO fetchDetailsById(Long id);

    List<HospitalDropDownResponseDTO> fetchHospitalForDropDown();
}
