package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 08/11/2019
 */
@Repository
@Qualifier("doctorTypeRepositoryCustom")
public interface DoctorTypeRepositoryCustom {

    List<DropDownResponseDTO> fetchActiveDoctorType();
}
