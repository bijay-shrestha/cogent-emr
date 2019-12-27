package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.response.service.ServiceMinimalResponseDTO;
import com.cogent.admin.dto.response.service.ServiceResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 11/18/19
 */
@Repository
@Qualifier("serviceRepositoryCustom")
public interface ServiceRepositoryCustom {
    List<Object[]> fetchServiceByNameOrCode(String name, String code);

    List<Object[]> checkIfServiceNameAndCodeExists(Long id, String name, String code);

    List<ServiceMinimalResponseDTO> searchService(
            ServiceSearchRequestDTO departmentSearchRequestDTO, Pageable pageable);

    ServiceResponseDTO fetchServiceDetails(Long id);

    Optional<List<DropDownResponseDTO>> fetchDropDownList(Long departmentId);

    Optional<List<DropDownResponseDTO>> fetchActiveDropDownList(Long departmentId);

}
