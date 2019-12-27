package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryDropdownDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryResponseDTO;
import com.cogent.persistence.model.AdminCategory;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author smriti on 2019-08-11
 */
public interface AdminCategoryService {
    void save(AdminCategoryRequestDTO requestDTO);

    void update(AdminCategoryUpdateRequestDTO requestDTO);

    void delete(DeleteRequestDTO deleteRequestDTO);

    List<AdminCategoryMinimalResponseDTO> search(AdminCategorySearchRequestDTO searchRequestDTO, Pageable pageable);

    List<AdminCategoryDropdownDTO> fetchActiveAdminCategoryForDropDown();

    AdminCategoryResponseDTO fetchDetailsById(Long id);

    void createExcelInByteArray(HttpServletResponse response) throws IOException;

    AdminCategory fetchActiveAdminCategoryById(Long id);
}
