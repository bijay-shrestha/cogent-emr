package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 11/21/19
 */

@Repository
@Qualifier("doctorCategoryRepositoryCustom")
public interface DoctorCategoryRepositoryCustom {

    List<Object[]> fetchDoctorCategoryByNameOrCode(String name, String code);

    List<Object[]> checkIfDoctorCategoryNameAndCodeExists( Long id,  String name,String code);

    Optional<List<DropDownResponseDTO>> fetchDropDownList();

    Optional<List<DropDownResponseDTO>> fetchActiveDropDownList();

    DoctorCategoryResponseDTO fetchDoctorCategoryDetails(Long id);

    List<DoctorCategoryMinimalResponseDTO> searchDoctorCategory(
            DoctorCategorySearchRequestDTO requestDTO,
            Pageable pageable);
}
