package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryDropdownDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 2019-08-11
 */
@Repository
@Qualifier("adminCategoryRepositoryCustom")
public interface AdminCategoryRepositoryCustom {

    List findAdminCategoryByNameOrCode(String name, String code);

    List findAdminCategoryByIdAndNameOrCode(Long id, String name, String code);

    List<AdminCategoryMinimalResponseDTO> search(AdminCategorySearchRequestDTO searchRequestDTO,
                                                 Pageable pageable);

    List<AdminCategoryDropdownDTO> fetchActiveAdminCategoryForDropDown();

    AdminCategoryResponseDTO fetchDetailsById(Long id);

    List getAdminCategoryForExcel();
}
