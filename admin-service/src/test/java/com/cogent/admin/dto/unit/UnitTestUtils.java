package com.cogent.admin.dto.unit;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.unit.UnitDeleteRequestDTO;
import com.cogent.admin.dto.request.unit.UnitRequestDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.request.unit.UnitUpdateRequestDTO;
import com.cogent.admin.dto.response.unit.UnitMinimalResponseDTO;
import com.cogent.admin.dto.response.unit.UnitResponseDTO;
import com.cogent.persistence.model.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sauravi Thapa 10/13/19
 */
public class UnitTestUtils {

    public static UnitRequestDTO getUnitRequestDTO() {
        return UnitRequestDTO.builder()
                .name("Unit-1")
                .code("Unit-1")
                .status('Y')
                .build();
    }

    public static UnitDeleteRequestDTO getUnitDeleteRequestDTO() {
        return UnitDeleteRequestDTO.builder()
                .id(1L)
                .status('Y')
                .wardId(1L)
                .remarks("test")
                .build();
    }


    public static List<Object[]> getUnitObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Unit-1";
        object[1] = "Unit-1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getUnitObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "Unit-1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getUnitObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Unit-1";
        objects.add(object);
        return objects;
    }

    public static Unit getUnitInfo() {
        Unit Unit = new Unit();
        Unit.setId(1L);
        Unit.setName("Unit-1");
        Unit.setCode("Unit-1");
        Unit.setStatus('Y');

        return Unit;
    }

    public static UnitUpdateRequestDTO getUpdateUnitRequestDTO() {
        return UnitUpdateRequestDTO.builder()
                .id(1L)
                .name("Unit-2")
                .code("wrd-2")
                .status('Y')
                .remarks("I want to update re")
                .build();
    }

    public static UnitSearchRequestDTO getSearchUnitRequestDTO() {
        return UnitSearchRequestDTO.builder()
                .id(1L)
                .name("Unit-1")
                .code("Unit-1")
                .status('Y')
                .build();
    }

    public static List<UnitMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(UnitMinimalResponseDTO
                .builder()
                .name("Unit-1")
                .code("Unit-1")
                .status('Y')
                .build());
    }

    public static UnitResponseDTO getUnitResponseDTO() {
        return UnitResponseDTO.builder()
                .name("Unit-1")
                .code("Unit-1")
                .status('Y')
                .remarks("test")
                .build();
    }

    public static List<DropDownResponseDTO> getDropDownInfo() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("Unit-1")
                .build());

    }

}
