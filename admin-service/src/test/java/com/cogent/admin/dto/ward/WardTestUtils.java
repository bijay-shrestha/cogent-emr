package com.cogent.admin.dto.ward;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.ward.WardRequestDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.request.ward.WardUpdateRequestDTO;
import com.cogent.admin.dto.response.ward.WardMinimalResponseDTO;
import com.cogent.admin.dto.response.ward.WardResponseDTO;
import com.cogent.persistence.model.Ward;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Sauravi Thapa 10/2/19
 */
public class WardTestUtils {

    public static WardRequestDTO getWardRequestDTO() {
        return WardRequestDTO.builder()
                .name("Surgical")
                .code("SRG")
                .status('Y')
                .has_Unit(TRUE)
                .build();
    }

    public static List<Object[]> getWardObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Surgical";
        object[1] = "SRG";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getWardObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "SRG";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getWardObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Surgical";
        objects.add(object);
        return objects;
    }

    public static Ward getWardInfo() {
        Ward ward = new Ward();
        ward.setId(1L);
        ward.setName("Surgical");
        ward.setCode("SRG");
        ward.setStatus('Y');

        return ward;
    }

    public static Ward getWardWithHasUnitTrue() {
        Ward ward = new Ward();
        ward.setId(1L);
        ward.setName("Surgical");
        ward.setCode("SRG");
        ward.setStatus('Y');
        ward.setHas_Unit(TRUE);

        return ward;
    }

    public static Ward getWardWithHasUnitFalse() {
        Ward ward = new Ward();
        ward.setId(1L);
        ward.setName("Surgical");
        ward.setCode("SRG");
        ward.setStatus('Y');
        ward.setHas_Unit(FALSE);

        return ward;
    }

    public static WardUpdateRequestDTO getUpdateWardRequestDTO() {
        return WardUpdateRequestDTO.builder()
                .id(1L)
                .name("Ward-2")
                .code("wrd-2")
                .status('Y')
                .has_Unit(TRUE)
                .remarks("I want to update re")
                .build();
    }

    public static WardSearchRequestDTO getSearchWardRequestDTO() {
        return WardSearchRequestDTO.builder()
                .id(1L)
                .name("Ward-2")
                .code("wrd-2")
                .status('Y')
                .build();
    }

    public static List<WardMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(WardMinimalResponseDTO
                .builder()
                .name("Ward-2")
                .code("wrd-2")
                .status('Y')
                .has_Unit(TRUE)
                .build());
    }

    public static  WardResponseDTO getWardResponseDTO(){
        return WardResponseDTO.builder()
                .name("Ward-2")
                .code("WRD-2")
                .status('Y')
                .remarks("test")
                .has_Unit(TRUE)
                .build();
    }

    public static List<DropDownResponseDTO> getDropDownInfo() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("Ward-2")
                .build());

    }



}
