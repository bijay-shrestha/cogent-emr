package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.request.discountscheme.DiscountSchemeSearchRequestDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeMinimalResponseDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DepartmentDiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.ServiceDiscountDropDownResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 11/11/19
 */

@Repository
@Qualifier("discountSchemeRepositoryCustom")
public interface DiscountSchemeRepositoryCustom {

    List<Object[]> fetchDiscountSchemeByNameOrCode(String name, String code);

    List<Object[]> checkIfDiscountSchemeNameAndCodeExists(Long id, String name, String code);

    List<DiscountSchemeMinimalResponseDTO> searchDiscountScheme(
            DiscountSchemeSearchRequestDTO searchRequestDTO, Pageable pageable);

    Optional<List<DiscountDropDownResponseDTO>> dropDownListWithNetDiscount();

    Optional<List<DiscountDropDownResponseDTO>> activeDropDownListWithNetDiscount();

    Optional<List<DepartmentDiscountDropDownResponseDTO>> dropDownListByDepartmentId(Long departmentId);

    Optional<List<DepartmentDiscountDropDownResponseDTO>> activeDropDownListByDepartmentId(Long departmentId);

    Optional<List<ServiceDiscountDropDownResponseDTO>> dropDownListByServiceId(Long serviceId);

    Optional<List<ServiceDiscountDropDownResponseDTO>> activeDropDownListByServiceId(Long serviceId);

    DiscountSchemeResponseDTO fetchDiscountSchemeDetails(Long id);
}
