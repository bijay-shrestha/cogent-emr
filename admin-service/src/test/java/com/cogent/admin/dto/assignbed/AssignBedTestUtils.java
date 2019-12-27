package com.cogent.admin.dto.assignbed;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedUpdateRequestDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedMinimalResponseDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedResponseDTO;
import com.cogent.persistence.model.AssignBed;

import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.bed.BedTestUtils.getBedInfo;
import static com.cogent.admin.dto.unit.UnitTestUtils.getUnitInfo;
import static com.cogent.admin.dto.ward.WardTestUtils.getWardInfo;

/**
 * @author Sauravi Thapa 11/7/19
 */
public class AssignBedTestUtils {
    public static AssignBedRequestDTO getAssignBedRequestDTO() {
        return AssignBedRequestDTO.builder()
                .bedId(1L)
                .wardId(1L)
                .unitId(1L)
                .status('Y')
                .build();
    }

    public static AssignBedResponseDTO getAssignBedResponseDTO() {
        return AssignBedResponseDTO.builder()
                .bedName("Bed-1")
                .wardName("Ward-1")
                .unitName("Unit-1")
                .status('Y')
                .build();
    }

    public static AssignBedUpdateRequestDTO getAssignBedUpdateRequestDTO() {
        return AssignBedUpdateRequestDTO.builder()
                .id(1L)
                .bedId(1L)
                .wardId(1L)
                .unitId(1L)
                .status('Y')
                .remarks("update")
                .build();
    }

    public static AssignBed getAssignBedToUpdateInfo() {
        AssignBed assignBed=new AssignBed();
        assignBed.setId(1L);
        assignBed.setBed( getBedInfo());
        assignBed.setWard(getWardInfo());
        assignBed.setUnit(getUnitInfo());
        assignBed.setStatus('Y');

        return assignBed;
    }

    public static AssignBed getAssignBedInfo() {
        AssignBed assignBed=new AssignBed();
        assignBed.setBed( getBedInfo());
        assignBed.setWard(getWardInfo());
        assignBed.setUnit(getUnitInfo());
        assignBed.setStatus('Y');

        return assignBed;
    }


    public static List<AssignBedMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(AssignBedMinimalResponseDTO
                .builder()
               .bedName("Bed-1")
                .unitName("Unit-1")
                .wardName("Ward-1")
                .status('Y')
                .remarks("test")
                .totalItems(12)
                .build());
    }

    public static List<DropDownResponseDTO> getDropDownInfo() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("Bed-1")
                .build());

    }

}
