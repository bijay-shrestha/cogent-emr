package com.cogent.admin.dto.drug;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.drug.DrugRequestDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.request.drug.DrugUpdateRequestDTO;
import com.cogent.admin.dto.response.drug.DrugMinimalResponseDTO;
import com.cogent.admin.dto.response.drug.DrugResponseDTO;
import com.cogent.persistence.model.Drug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrugTestUtils {
    public static DrugRequestDTO getDrugRequestDTO() {
        return DrugRequestDTO.builder()
                .name("Emergency")
                .code("EMR")
                .status('Y')
                .build();
    }


    public static DrugUpdateRequestDTO getDrugUpdateRequestDTO() {
        return DrugUpdateRequestDTO.builder()
                .id(1L)
                .name("Emergency")
                .code("EMR")
                .status('Y')
                .remarks("Update")
                .build();
    }

    public static Drug getDrugInfoToUpdate() {
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setName("Emergency");
        drug.setCode("EMR");
        drug.setStatus('Y');
        drug.setRemarks("Update");

        return drug;
    }

    public static Drug getDrugInfoToDelete() {
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setName("Emergency");
        drug.setCode("EMR");
        drug.setStatus('D');
        drug.setRemarks("Delete Drug");

        return drug;
    }

    public static Drug getDrugInfo() {
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setName("Emergency");
        drug.setCode("EMR");
        drug.setStatus('Y');

        return drug;
    }

    public static DrugSearchRequestDTO getSearchRequestDTO() {
        return DrugSearchRequestDTO.builder()
                .id(1L)
                .name("Emergency")
                .code("EMR")
                .status('Y')
                .build();
    }

    public static DrugMinimalResponseDTO getMinimalResponseDTO() {
        return DrugMinimalResponseDTO.builder()
                .id(1L)
                .name("Emergency")
                .code("EMR")
                .status('Y')
                .build();
    }

    public static List<DrugMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getMinimalResponseDTO());
    }


    public static DrugResponseDTO getDrugResponseDTO() {
        return DrugResponseDTO.builder()
                .remarks("Test")
                .build();
    }


    public static DropDownResponseDTO dropDownResponseDTO() {
        return DropDownResponseDTO.builder()
                .value(1L)
                .label("Emergency")
                .build();
    }

    public static List<DropDownResponseDTO> dropDownResponseDTOS() {
        return Arrays.asList(dropDownResponseDTO());
    }

    public static DeleteRequestDTO getDeleteRequestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("Delete Drug").build();
    }


    public static List<Object[]> getDrugObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Emergency";
        object[1] = "EMR";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getDrugObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Emergency";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getDrugObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "EMR";
        objects.add(object);
        return objects;
    }
}
