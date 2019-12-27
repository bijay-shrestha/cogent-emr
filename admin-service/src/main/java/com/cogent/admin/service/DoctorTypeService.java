package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.DoctorType;

import java.util.List;

/**
 * @author smriti on 08/11/2019
 */
public interface DoctorTypeService {
    List<DropDownResponseDTO> fetchActiveDoctorType();

    DoctorType fetchDoctorTypeById(Long id);
}
