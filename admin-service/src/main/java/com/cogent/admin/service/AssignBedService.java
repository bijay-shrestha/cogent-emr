package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedUpdateRequestDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedMinimalResponseDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 11/7/19
 */
public interface AssignBedService {

    void createAssignBed(AssignBedRequestDTO assignBedRequestDTO);

    void deleteAssignedBed(DeleteRequestDTO deleteRequestDTO);

    List<AssignBedMinimalResponseDTO> searchAssignedBed(
            AssignBedSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    void updateAssignedBed(AssignBedUpdateRequestDTO updateRequestDTO);

    AssignBedResponseDTO fetchAssignedBedDetails(Long id);

    List<DropDownResponseDTO> fetchActiveDropDownList(Long wardId);

    List<DropDownResponseDTO> fetchDropDownList(Long wardId);
}
