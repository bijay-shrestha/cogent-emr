package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.hospital.HospitalSearchRequestDTO;
import com.cogent.admin.dto.response.hospital.HospitalDropdownDTO;
import com.cogent.admin.dto.response.hospital.HospitalResponseDTO;
import com.cogent.persistence.model.Hospital;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rupak
 */
@Repository
@Qualifier("municipalityRepositoryCustom")
public interface HospitalRepositoryCustom {

    Long fetchHospitalByName(String name);

    Hospital findHospital(Long id);

    Long findHospitalByIdAndName(Long id, String name);

    List<HospitalResponseDTO> search(HospitalSearchRequestDTO searchRequestDTO, Pageable pageable);

    List<HospitalDropdownDTO> fetchActiveHospitalForDropDown();

    HospitalResponseDTO fetchDetailsById(Long id);

    Hospital fetchHospitalById(Long id);

    List<HospitalDropdownDTO> fetchHospitalForDropDown();
}
