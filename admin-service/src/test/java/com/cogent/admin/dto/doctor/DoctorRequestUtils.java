package com.cogent.admin.dto.doctor;

import com.cogent.admin.dto.request.doctor.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author smriti on 2019-09-27
 */
public class DoctorRequestUtils {
    public static DoctorRequestDTO getInvalidInputForSave() {
        return DoctorRequestDTO.builder()
                .name("")
                .status('M')
                .mobileNumber("")
                .specializationIds(asList(1L, 2L))
                .departmentIds(asList(1L, 2L))
                .build();
    }

    public static DoctorRequestDTO getDoctorRequestDTO() {
        return DoctorRequestDTO.builder()
                .name("DR.SANJEEV UPRETI")
                .status('Y')
                .mobileNumber("9841111111")
                .specializationIds(asList(1L, 2L))
                .departmentIds(asList(1L, 2L))
                .doctorTypeIds(asList(1L, 2L))
                .qualificationIds(asList(1L, 2L))
                .build();
    }

    private static DoctorUpdateDTO getInvalidDoctorUpdateDTO() {
        return DoctorUpdateDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .mobileNumber("9841111111")
                .build();
    }

    public static DoctorUpdateRequestDTO getInvalidInputForUpdate() {
        return DoctorUpdateRequestDTO.builder()
                .updateDTO(getInvalidDoctorUpdateDTO())
                .specializationUpdateRequestDTOS(new ArrayList<>())
                .build();
    }

    public static DoctorUpdateRequestDTO getDoctorUpdateRequestDTO() {
        return DoctorUpdateRequestDTO.builder()
                .updateDTO(getDoctorUpdateDTO())
                .specializationUpdateRequestDTOS(getDoctorSpecializationUpdateDTO())
                .doctorDepartmentUpdateDTOS(getDoctorDepartmentUpdateDTO())
                .doctorScheduleTypeUpdateDTOS(getDoctorScheduleTypeUpdateDTOS())
                .doctorQualificationUpdateDTOS(getDoctorQualificationUpdateDTOS())
                .build();
    }

    private static DoctorUpdateDTO getDoctorUpdateDTO() {
        return DoctorUpdateDTO.builder()
                .id(1L)
                .name("DR.SANJEEV UPRETI")
                .status('N')
                .mobileNumber("9841111111")
                .dateOfBirth(new Date())
                .nepaliDateOfBirth("2052-06-18")
                .email("sanjeev.upreti@gmail.com")
                .nmcNumber("1234")
                .joinedDate(new Date())
                .nepaliJoinedDate("2076-07-26")
                .resignationDate(null)
                .nepaliResignationDate(null)
                .genderId(1L)
                .countryId(1L)
                .remarks("yes! Inactive it")
                .build();
    }

    private static List<DoctorSpecializationUpdateDTO> getDoctorSpecializationUpdateDTO() {
        return Arrays.asList(
                new DoctorSpecializationUpdateDTO(1L, 1L, 'Y'),
                (new DoctorSpecializationUpdateDTO(null, 2L, 'Y')));
    }

    private static List<DoctorDepartmentUpdateDTO> getDoctorDepartmentUpdateDTO() {
        return Arrays.asList(
                new DoctorDepartmentUpdateDTO(1L, 1L, 'Y'),
                (new DoctorDepartmentUpdateDTO(null, 2L, 'Y')));
    }

    private static List<DoctorScheduleTypeUpdateDTO> getDoctorScheduleTypeUpdateDTOS() {
        return Arrays.asList(
                new DoctorScheduleTypeUpdateDTO(1L, 1L, 'Y'),
                (new DoctorScheduleTypeUpdateDTO(null, 2L, 'Y')));
    }

    private static List<DoctorQualificationUpdateDTO> getDoctorQualificationUpdateDTOS() {
        return Arrays.asList(
                new DoctorQualificationUpdateDTO(1L, 1L, 'Y'),
                (new DoctorQualificationUpdateDTO(null, 2L, 'Y')));
    }

    public static DoctorSearchRequestDTO getDoctorSearchRequestDTO() {
        return DoctorSearchRequestDTO.builder()
                .name("Dr.Sanjeev Upreti")
                .status('Y')
                .mobileNumber("9841111111")
                .specializationId(1L)
                .build();
    }
}
