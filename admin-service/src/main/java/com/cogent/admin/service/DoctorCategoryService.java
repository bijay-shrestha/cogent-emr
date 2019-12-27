package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 11/21/19
 */
public interface DoctorCategoryService {

    void createDoctorCategory(DoctorCategoryRequestDTO requestDTO);

    void deleteDoctorCategory(DeleteRequestDTO requestDTO);

    void updateDoctorCategory(DoctorCategoryUpdateRequestDTO requestDTO);

    List<DoctorCategoryMinimalResponseDTO> searchDoctorCategory(
            DoctorCategorySearchRequestDTO requestDTO,
            Pageable pageable);

    DoctorCategoryResponseDTO fetchDoctorCategoryDetails(Long id);

    List<DropDownResponseDTO> doctorCategoryDropdown();

    List<DropDownResponseDTO> activeDoctorCategoryDropdown();
}
