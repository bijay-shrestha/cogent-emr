package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityUpdateRequestDTO;
import com.cogent.admin.dto.response.nationality.NationalityDropDownResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityMinimalResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityResponseDTO;
import com.cogent.persistence.model.Nationality;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NationalityService {

    void createNationality(NationalityRequestDTO nationalityRequestDTO);

    void deleteNationality(DeleteRequestDTO deleteRequestDTO);

    void updateNationality(NationalityUpdateRequestDTO updateRequestDTO);

    List<NationalityMinimalResponseDTO> searchNationality(NationalitySearchRequestDTO searchRequestDTO,
                                                          Pageable pageable);

    NationalityResponseDTO fetchNatioanlityDetails(Long id);

    Nationality fetchNatioanlity(Long id);

    List<NationalityDropDownResponseDTO> fetchActiveDropDownList();

    List<NationalityDropDownResponseDTO> fetchDropDownList();


}
