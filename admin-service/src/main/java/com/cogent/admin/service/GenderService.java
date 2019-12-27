package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.Gender;

import java.util.List;

/**
 * @author smriti on 08/11/2019
 */
public interface GenderService {
    List<DropDownResponseDTO> fetchActiveGender();

    Gender fetchGenderById(Long id);
}
