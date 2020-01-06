package com.cogent.admin.dto.provinces;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.district.DistrictTestUtils;
import com.cogent.admin.dto.request.provinces.ProvincesRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesUpdateRequestDTO;
import com.cogent.admin.dto.response.provinces.ProvincesDropDownResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesMinimalResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesResponseDTO;
import com.cogent.persistence.model.District;
import com.cogent.persistence.model.Provinces;

import java.util.Arrays;
import java.util.List;

public class ProvincesTestUtils {
    public static ProvincesRequestDTO getRequestDTO() {
        return ProvincesRequestDTO.builder()
                .name("Province No. 1")
                .status('Y')
                .build();
    }

    public static ProvincesDropDownResponseDTO dropDownResponseDTO() {
        return ProvincesDropDownResponseDTO.builder()
                .value(1L)
                .label("Province No.1")
                .build();
    }

    public static List<ProvincesDropDownResponseDTO> dropDownResponseDTOS() {
        return Arrays.asList(dropDownResponseDTO());
    }


    public static ProvincesMinimalResponseDTO getMinimalResponseDTO() {
        return ProvincesMinimalResponseDTO.builder()
                .id(1L)
                .name("Province No.1")
                .status('Y')
                .build();
    }

    public static List<ProvincesMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getMinimalResponseDTO());
    }

    public static ProvincesResponseDTO provincesResponseDTO() {
        return ProvincesResponseDTO.builder()
                .remarks("Test")
                .build();
    }

    public static DeleteRequestDTO getDeleteRequestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("deleteDrug").build();
    }

    public static Provinces getProvincesInfo() {
        Provinces provinces = new Provinces();
        provinces.setId(1L);
        provinces.setName("Provinces No.1");
        provinces.setStatus('Y');
        return provinces;
    }

    public static List<DeleteRequestDTO> getDeleteRequestDTOS(){
        return Arrays.asList(getDeleteRequestDTO());
    }

    public static ProvincesUpdateRequestDTO getProvincesUpdateRequestDTO() {
        return ProvincesUpdateRequestDTO.builder()
                .id(1L)
                .name("Provinces No.1")
                .status('Y')
                .remarks("Update")
                .build();
    }

    public static List<District> districtList() {
        List<District> subDepartmentList = Arrays
                .asList(DistrictTestUtils.getDistrictInfo());
        return subDepartmentList;
    }
}
