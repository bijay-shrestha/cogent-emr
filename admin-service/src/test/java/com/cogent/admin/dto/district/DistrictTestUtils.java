package com.cogent.admin.dto.district;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.provinces.ProvincesTestUtils;
import com.cogent.admin.dto.request.district.DistrictRequestDTO;
import com.cogent.admin.dto.request.district.DistrictUpdateRequestDTO;
import com.cogent.admin.dto.response.district.DistrictDropDownResponseDTO;
import com.cogent.admin.dto.response.district.DistrictMinimalResponseDTO;
import com.cogent.admin.dto.response.district.DistrictResponseDTO;
import com.cogent.persistence.model.District;

import java.util.Arrays;
import java.util.List;

public class DistrictTestUtils {
    public static DistrictRequestDTO getDistrictRequestDTO() {
        return DistrictRequestDTO.builder()
                .name("Kathmandu")
                .status('Y')
                .provinceId(3L)
                .build();
    }

    public static DistrictDropDownResponseDTO dropDownResponseDTO() {
        return DistrictDropDownResponseDTO.builder()
                .value(1L)
                .label("Kathmandu")
                .build();
    }

    public static List<DistrictDropDownResponseDTO> dropDownResponseDTOS() {
        return Arrays.asList(dropDownResponseDTO());
    }


    public static DistrictMinimalResponseDTO getMinimalResponseDTO() {
        return DistrictMinimalResponseDTO.builder()
                .id(1L)
                .name("kathmandu")
                .status('Y')
                .build();
    }

    public static List<DistrictMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getMinimalResponseDTO());
    }

    public static DistrictResponseDTO districtResponseDTO() {
        return DistrictResponseDTO.builder()
                .remarks("Test")
                .build();
    }

    public static DeleteRequestDTO getDeleteRequestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("deleteDrug").build();
    }

    public static District getDistrictInfo() {
        District district = new District();
        district.setId(1L);
        district.setName("Provinces No.1");
        district.setStatus('Y');
        district.setProvinces(ProvincesTestUtils.getProvincesInfo());
        return district;
    }

    public static DistrictUpdateRequestDTO getUpdateRequestDTO() {
        return DistrictUpdateRequestDTO.builder()
                .id(1L)
                .provincesId(3L)
                .name("Kathmandu")
                .status('Y')
                .remarks("Update")
                .build();
    }
}
