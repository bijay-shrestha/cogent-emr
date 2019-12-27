package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.service.ServiceRequestDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.request.service.ServiceUpdateRequestDTO;
import com.cogent.admin.dto.response.service.ServiceMinimalResponseDTO;
import com.cogent.admin.dto.response.service.ServiceResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 11/18/19
 */
public interface ServiceService {

    void createService(ServiceRequestDTO serviceRequestDTO);

    void deleteService(DeleteRequestDTO deleteRequestDTO);

    void updateService(ServiceUpdateRequestDTO requestDTO);

    List<ServiceMinimalResponseDTO> searchService(
            ServiceSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    ServiceResponseDTO fetchServiceDetails(Long id);

    List<DropDownResponseDTO> fetchDropDownList(Long departmentId);

    List<DropDownResponseDTO> fetchActiveDropDownList(Long departmentId);




}
