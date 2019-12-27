package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.bed.BedRequestDTO;
import com.cogent.admin.dto.request.bed.BedUpdateRequestDTO;
import com.cogent.persistence.model.Bed;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Sauravi Thapa 11/1/19
 */
public class BedUtils {
    public static Function<BedRequestDTO, Bed> parseToBed = (requestDTO) -> {
        Bed bed = new Bed();
        bed.setName(toUpperCase(requestDTO.getName()));
        bed.setCode(toUpperCase(requestDTO.getCode()));
        bed.setStatus(requestDTO.getStatus());
        return bed;
    };

    public static BiFunction<Bed, DeleteRequestDTO, Bed> deleteBed = (bedToDelete, deleteRequestDTO) -> {
        bedToDelete.setStatus(deleteRequestDTO.getStatus());
        bedToDelete.setRemarks(deleteRequestDTO.getRemarks());

        return bedToDelete;
    };


    public static BiFunction<Bed, BedUpdateRequestDTO, Bed> updateBed = (bedToUpdate, updateRequestDTO) -> {
        bedToUpdate.setName(toUpperCase(updateRequestDTO.getName()));
        bedToUpdate.setCode(updateRequestDTO.getCode());
        bedToUpdate.setStatus(updateRequestDTO.getStatus());
        bedToUpdate.setRemarks(updateRequestDTO.getRemarks());

        return bedToUpdate;
    };


}
