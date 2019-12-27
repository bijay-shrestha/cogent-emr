package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.district.DistrictRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesUpdateRequestDTO;
import com.cogent.persistence.model.District;
import com.cogent.persistence.model.Provinces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class DistrictUtils {
    public static  BiFunction<DistrictRequestDTO, Provinces, District> parseToDistrict =
            (districtRequestDTO, provinces) -> {
                District district =new District();
                district.setName(toUpperCase(districtRequestDTO.getName()));
                district.setStatus(districtRequestDTO.getStatus());
                district.setCreatedDate(new Date());
                district.setCreatedById(1L);
                district.setProvinces(provinces);
                return district;
            };

    public static  BiFunction<District, DeleteRequestDTO, District> updateDistrict =
            (district, deleteRequestDTO) -> {
                district.setStatus(deleteRequestDTO.getStatus());
                district.setRemarks(deleteRequestDTO.getRemarks());
                district.setLastModifiedDate(new Date());
                district.setModifiedById(1L);
                return district;
            };

    public static  BiFunction<List<District>, ProvincesUpdateRequestDTO, List<District>> parseToUpdateListOfDistrict =
            (districts, provincesUpdateRequestDTO) -> {
                List<District> districtList = new ArrayList<>();
                districts.forEach(district -> {
                    district.setStatus(provincesUpdateRequestDTO.getStatus());
                    district.setRemarks(provincesUpdateRequestDTO.getRemarks());
                    district.setLastModifiedDate(new Date());
                    district.setModifiedById(1L);
                    districtList.add(district);
                });
                return districtList;
            };

    public static final BiFunction<DeleteRequestDTO, List<DeleteRequestDTO>,
            List<DeleteRequestDTO>> distictDeleteRequestDTO =

            (departmentDeleteRequestDTO, districtDeleteRequestDTOS) -> {
                districtDeleteRequestDTOS.forEach(subDepartmentUpdateRequestDTO -> {
                    subDepartmentUpdateRequestDTO.setStatus(departmentDeleteRequestDTO.getStatus());
                    subDepartmentUpdateRequestDTO.setRemarks(departmentDeleteRequestDTO.getRemarks());

                });
                return districtDeleteRequestDTOS;
            };

}
