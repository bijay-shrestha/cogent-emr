package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedMinimalResponseDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedResponseDTO;
import com.cogent.persistence.model.AssignBed;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 11/8/19
 */

@Repository
@Qualifier("assignBedRepositoryCustom")
public interface AssignBedRepositoryCustom {

    AssignBed fetchAssignBedById(Long id);

    List<AssignBedMinimalResponseDTO> searchAssignedBed(
            AssignBedSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    AssignBedResponseDTO fetchAssignBedDetails(Long id);

    Optional<List<DropDownResponseDTO>> dropDownList(Long wardId);

    Optional<List<DropDownResponseDTO>> activeDropDownList(Long wardId);

    Long checkIfBedExixts(Long bedId,Long wardId,Long unitId);
}
