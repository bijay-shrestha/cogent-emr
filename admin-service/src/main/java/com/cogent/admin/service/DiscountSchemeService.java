package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeSearchRequestDTO;
import com.cogent.admin.dto.discountscheme.DiscountSchemeUpdateRequestDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeMinimalResponseDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DepartmentDiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.ServiceDiscountDropDownResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 11/11/19
 */
public interface DiscountSchemeService {

    void createDiscountScheme(DiscountSchemeRequestDTO requestDTO);

    void deleteDiscountScheme(DeleteRequestDTO deleteRequestDTO);

    void updateDiscountScheme(DiscountSchemeUpdateRequestDTO requestDTO);

    List<DiscountSchemeMinimalResponseDTO> searchDiscountScheme(DiscountSchemeSearchRequestDTO searchRequestDTO,
                                                                Pageable pageable);

    DiscountSchemeResponseDTO fetchDiscountSchemeDetails(Long id);

    List<DiscountDropDownResponseDTO> fetchActiveDropDownListWithNetDiscount();

    List<DiscountDropDownResponseDTO> fetchDropDownListWithNetDiscount();

    List<DepartmentDiscountDropDownResponseDTO> fetchActiveDropDownListByDepartmentId(Long departmentId);

    List<DepartmentDiscountDropDownResponseDTO> fetchDropDownListByDepartmentId(Long departmentId);

    List<ServiceDiscountDropDownResponseDTO> fetchActiveDropDownListByServiceId(Long serviceId);

    List<ServiceDiscountDropDownResponseDTO> fetchDropDownListByServiceId(Long serviceId);

    void deleteDepartmentDiscount(DeleteRequestDTO requestDTO);

    void deleteServiceDiscount(DeleteRequestDTO requestDTO);
}
