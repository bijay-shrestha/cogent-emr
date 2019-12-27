package com.cogent.admin.dto.doctorcategory;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryResponseDTO;
import com.cogent.persistence.model.DoctorCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoctorCategoryTestUtils {
    public static DoctorCategoryRequestDTO getDoctorCategoryRequestDTO() {
        return DoctorCategoryRequestDTO.builder()
                .name("M1")
                .code("m1")
                .status('Y')
                .build();
    }


    public static DoctorCategoryUpdateRequestDTO getDoctorCategoryUpdateRequestDTO() {
        return DoctorCategoryUpdateRequestDTO.builder()
                .id(1L)
                .name("M1")
                .code("m1")
                .status('Y')
                .remarks("Update")
                .build();
    }

    public static DoctorCategory getDoctorCategoryInfo() {
        DoctorCategory doctorCategory = new DoctorCategory();
        doctorCategory.setId(1L);
        doctorCategory.setName("M1");
        doctorCategory.setCode("M1");
        doctorCategory.setStatus('Y');

        return doctorCategory;
    }

    public static DoctorCategory getDoctorCategoryInfoToUpdate() {
        DoctorCategory doctorCategory = new DoctorCategory();
        doctorCategory.setId(1L);
        doctorCategory.setName("M1");
        doctorCategory.setCode("M1");
        doctorCategory.setStatus('Y');
        doctorCategory.setRemarks("Update");
        return doctorCategory;
    }

    public static DoctorCategorySearchRequestDTO getDoctorCategorySearchRequestDTO() {
        return DoctorCategorySearchRequestDTO.builder()
                .id(1L)
                .name("M1")
                .code("m1")
                .status('Y')
                .build();
    }

    public static DoctorCategoryMinimalResponseDTO getMinimalResponseDTO() {
        return DoctorCategoryMinimalResponseDTO.builder()
                .id(1L)
                .name("M1")
                .code("m1")
                .status('Y')
                .build();
    }

    public static List<DoctorCategoryMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getMinimalResponseDTO());
    }


    public static DoctorCategoryResponseDTO getDoctorCategoryResponseDTO() {
        return DoctorCategoryResponseDTO.builder()
                .remarks("Test")
                .build();
    }


    public static DropDownResponseDTO dropDownResponseDTO() {
        return DropDownResponseDTO.builder()
                .value(1L)
                .label("M1")
                .build();
    }

    public static List<DropDownResponseDTO> dropDownResponseDTOS() {
        return Arrays.asList(dropDownResponseDTO());
    }

    public static DeleteRequestDTO getDeleteRequestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("Delete Doctor Category").build();
    }

    public static DoctorCategory getDoctorCategoryInfoToDelete() {
        DoctorCategory doctorCategory = new DoctorCategory();
        doctorCategory.setId(1L);
        doctorCategory.setName("M1");
        doctorCategory.setCode("M1");
        doctorCategory.setStatus('D');
        doctorCategory.setRemarks("Delete Doctor Category");
        return doctorCategory;
    }



    public static List<Object[]> getDoctorCategoryObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "M1";
        object[1] = "m1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getDoctorCategoryObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "M1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getDoctorCategoryObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "m1";
        objects.add(object);
        return objects;
    }
}
