package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.unit.UnitRequestDTO;
import com.cogent.admin.dto.request.unit.UnitUpdateRequestDTO;
import com.cogent.persistence.model.Unit;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Sauravi Thapa 10/2/19
 */
public class UnitUtils {

    public static Function<UnitRequestDTO, Unit> parseToUnit =
            (requestDTO) -> {
                Unit unit = new Unit();
                unit.setName(toUpperCase(requestDTO.getName()));
                unit.setCode(toUpperCase(requestDTO.getCode()));
                unit.setStatus(requestDTO.getStatus());
                return unit;
            };

    public static BiFunction<Unit, DeleteRequestDTO, Unit> deleteUnit = (unitToDelete, deleteRequestDTO) -> {
        unitToDelete.setStatus(deleteRequestDTO.getStatus());
        unitToDelete.setRemarks(deleteRequestDTO.getRemarks());

        return unitToDelete;
    };

    public static Unit updateToUnit(Unit unitToUpdate, UnitUpdateRequestDTO updateRequestDTO) {
        unitToUpdate.setName(toUpperCase(updateRequestDTO.getName()));
        unitToUpdate.setCode(toUpperCase(updateRequestDTO.getCode()));
        unitToUpdate.setStatus(updateRequestDTO.getStatus());
        unitToUpdate.setRemarks(updateRequestDTO.getRemarks());

        return unitToUpdate;
    }


}
