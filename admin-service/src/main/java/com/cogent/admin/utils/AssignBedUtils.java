package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedUpdateRequestDTO;
import com.cogent.persistence.model.AssignBed;
import com.cogent.persistence.model.Bed;
import com.cogent.persistence.model.Unit;
import com.cogent.persistence.model.Ward;

import java.util.function.BiFunction;

/**
 * @author Sauravi Thapa 11/7/19
 */
public class AssignBedUtils {


    public static AssignBed parseToAssignedBed(AssignBedRequestDTO requestDTO, Bed bed, Ward ward, Unit unit){
        AssignBed assignBed = new AssignBed();
        assignBed.setBed(bed);
        assignBed.setWard(ward);
        assignBed.setUnit((unit != null) ? unit : null);
        assignBed.setStatus(requestDTO.getStatus());

        return assignBed;

    }

    public static BiFunction<AssignBed, DeleteRequestDTO, AssignBed> deleteAssignedBed = (bedToDelete, deleteRequestDTO) -> {
        bedToDelete.setStatus(deleteRequestDTO.getStatus());
        bedToDelete.setRemarks(deleteRequestDTO.getRemarks());

        return bedToDelete;
    };


    public static AssignBed updateAssignedBed(AssignBed assignBed, AssignBedUpdateRequestDTO requestDTO, Bed bed, Ward ward, Unit unit){
        assignBed.setBed(bed);
        assignBed.setWard(ward);
        assignBed.setUnit((unit != null) ? unit : null);
        assignBed.setStatus(requestDTO.getStatus());
        assignBed.setRemarks(requestDTO.getRemarks());

        return assignBed;

    }


}
