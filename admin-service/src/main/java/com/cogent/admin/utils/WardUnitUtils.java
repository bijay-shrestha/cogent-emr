package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ward.WardUnitDeleteRequestDTO;
import com.cogent.persistence.model.Unit;
import com.cogent.persistence.model.Ward;
import com.cogent.persistence.model.Ward_Unit;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Sauravi Thapa 10/20/19
 */
public class WardUnitUtils {
    public static Ward_Unit parseToWardUnit(Ward ward, Unit unit, Character status) {
        Ward_Unit wardUnit = new Ward_Unit();
        wardUnit.setWard(ward);
        wardUnit.setUnit(unit);
        wardUnit.setStatus(status);

        return wardUnit;
    }

    public static BiFunction<List<Ward_Unit>, DeleteRequestDTO, List<Ward_Unit>> deleteWardUnitList = (listToDelete, deleteRequestDTO) -> {
        listToDelete.forEach(wardToDelete -> {
            wardToDelete.setStatus(deleteRequestDTO.getStatus());
            wardToDelete.setRemarks(deleteRequestDTO.getRemarks());
        });
        return listToDelete;
    };


    public static BiFunction<Ward_Unit, WardUnitDeleteRequestDTO, Ward_Unit> deleteWardUnit = (wardUnit, requestDTO) -> {

        wardUnit.setStatus(requestDTO.getStatus());
        wardUnit.setRemarks(requestDTO.getRemarks());

        return wardUnit;
    };


}
