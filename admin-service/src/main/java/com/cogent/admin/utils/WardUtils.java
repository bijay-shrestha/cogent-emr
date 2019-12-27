package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ward.WardRequestDTO;
import com.cogent.admin.dto.request.ward.WardUpdateRequestDTO;
import com.cogent.admin.dto.response.ward.WardMinimalResponseDTO;
import com.cogent.admin.dto.response.ward.WardResponseDTO;
import com.cogent.persistence.model.Ward;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Sauravi Thapa 10/2/19
 */
public class WardUtils {


    private static final Integer NAME = 0;
    private static final Integer CODE = 1;
    private static final Integer STATUS = 2;
    private static final Integer REMARKS = 3;
    private static final Integer HAS_UNIT = 4;
    private static final Integer UNITNAMES = 5;

    public static Ward parseToWard(WardRequestDTO requestDTO) {
        Ward ward = new Ward();

        ward.setName(toUpperCase(requestDTO.getName()));
        ward.setCode(toUpperCase(requestDTO.getCode()));
        ward.setStatus(requestDTO.getStatus());
        ward.setHas_Unit(requestDTO.getHas_Unit());

        return ward;
    }

    public static BiFunction<Ward, DeleteRequestDTO, Ward> deleteWard = (wardToDelete, deleteRequestDTO) -> {
        wardToDelete.setStatus(deleteRequestDTO.getStatus());
        wardToDelete.setRemarks(deleteRequestDTO.getRemarks());

        return wardToDelete;
    };

    public static BiFunction<Ward, WardUpdateRequestDTO, Ward> updateWard = (wardToUpdate, updateRequestDTO) -> {
        wardToUpdate.setName(toUpperCase(updateRequestDTO.getName()));
        wardToUpdate.setCode(updateRequestDTO.getCode());
        wardToUpdate.setStatus(updateRequestDTO.getStatus());
        wardToUpdate.setRemarks(updateRequestDTO.getRemarks());
        wardToUpdate.setHas_Unit(updateRequestDTO.getHas_Unit());

        return wardToUpdate;
    };


    public static WardResponseDTO parseObjectToWardResponseDTO(List<Object[]> objects) {

        WardResponseDTO responseDTO = new WardResponseDTO();
        for (Object[] object : objects) {
            responseDTO.setName(object[NAME].toString());
            responseDTO.setCode(object[CODE].toString());
            responseDTO.setStatus(object[STATUS].toString().charAt(0));
            responseDTO.setRemarks((object[REMARKS] == null) ? null : object[REMARKS].toString());
            responseDTO.setHas_Unit(Boolean.valueOf(object[HAS_UNIT].toString()));
            responseDTO.setUnitNames(object[UNITNAMES].toString());
        }
        return responseDTO;
    }

    public static List<WardMinimalResponseDTO> parseObjectListToWardMinimalResponseDTO(List<Object[]> objects) {

        List<WardMinimalResponseDTO> responseDTO = new ArrayList<>();
        for (Object[] object : objects) {
            WardMinimalResponseDTO minimalResponseDTO = new WardMinimalResponseDTO();
            minimalResponseDTO.setName(object[NAME].toString());
            minimalResponseDTO.setCode(object[CODE].toString());
            minimalResponseDTO.setStatus(object[STATUS].toString().charAt(0));
            minimalResponseDTO.setRemarks((object[REMARKS] == null) ? null : object[REMARKS].toString());
            minimalResponseDTO.setHas_Unit(Boolean.valueOf(object[HAS_UNIT].toString()));
            minimalResponseDTO.setUnitNames(object[UNITNAMES].toString());
            responseDTO.add(minimalResponseDTO);
        }
        return responseDTO;
    }

}
