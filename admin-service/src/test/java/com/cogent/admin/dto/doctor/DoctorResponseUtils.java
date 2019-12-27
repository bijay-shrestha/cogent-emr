package com.cogent.admin.dto.doctor;

import com.cogent.admin.dto.response.doctor.DoctorDetailResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorDropdownDTO;
import com.cogent.admin.dto.response.doctor.DoctorMinimalResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorUpdateResponseDTO;
import com.cogent.persistence.model.Doctor;
import com.cogent.persistence.model.DoctorAvatar;
import com.cogent.persistence.model.DoctorSignature;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.cogent.admin.dto.country.CountryResponseUtils.fetchCountry;
import static com.cogent.admin.dto.gender.GenderResponseUtils.fetchGender;

/**
 * @author smriti on 2019-09-27
 */
public class DoctorResponseUtils {
    public static Doctor getDoctor() {
        return new Doctor(1L, "DR.SANJEEV UPRETI", "123", "9841111111",
                new Date(), "2052-06-18", "sanjeev.upreti@gmail.com",
                "1234", new Date(), "2076-07-26", null,
                null, fetchGender(), fetchCountry(),
                'Y', null);
    }

    public static DoctorAvatar getDoctorAvatar() {
        DoctorAvatar doctorAvatar = new DoctorAvatar();
        parseDoctorAvatarProperties(doctorAvatar);
        doctorAvatar.setFileUri("http://localhost:8081/files/test-cogent/test.png");
        return doctorAvatar;
    }

    public static DoctorSignature getDoctorSignature() {
        DoctorSignature doctorSignature = new DoctorSignature();
        parseDoctorSignatureProperties(doctorSignature);
        doctorSignature.setFileUri("http://localhost:8081/files/test-cogent/sign.png");
        return doctorSignature;
    }

    private static void parseDoctorAvatarProperties(DoctorAvatar DoctorAvatar) {
        DoctorAvatar.setDoctorId(getDoctor());
        DoctorAvatar.setFileType("image/png");
        DoctorAvatar.setFileSize(100L);
        DoctorAvatar.setIsDefaultImage('N');
    }

    private static void parseDoctorSignatureProperties(DoctorSignature DoctorSignature) {
        DoctorSignature.setDoctorId(getDoctor());
        DoctorSignature.setFileType("image/png");
        DoctorSignature.setFileSize(100L);
        DoctorSignature.setIsDefaultImage('N');
    }

    public static Doctor getUpdatedDoctor() {
        return new Doctor(1L, "DR.SANJEEV UPRETI", "123", "9841111111",
                new Date(), "2052-06-18", "sanjeev.upreti@gmail.com",
                "1234", new Date(), "2076-07-26", null,
                null, fetchGender(), fetchCountry(),
                'N', "yes! Inactive it");
    }

    public static List<DoctorMinimalResponseDTO> fetchDoctorMinimalResponseDTO() {
        return Arrays.asList(DoctorMinimalResponseDTO.builder()
                        .doctorId(BigInteger.valueOf(1L))
                        .doctorName("DR.SANJEEV UPRETI")
                        .mobileNumber("9841111111")
                        .status('Y')
                        .avatarUri("http://localhost:8081/files/Doctor-cogent/test.png")
                        .isAvatarDefault('N')
                        .specializationName("Physician")
                        .build(),
                DoctorMinimalResponseDTO.builder()
                        .doctorId(BigInteger.valueOf(2L))
                        .doctorName("DR.DANIEL SHRESTHA")
                        .mobileNumber("9842222222")
                        .status('Y')
                        .avatarUri("http://localhost:8081/files/Doctor-cogent/cogent.png")
                        .specializationName("Surgeon")
                        .isAvatarDefault('N')
                        .build()
        );
    }

    public static List<DoctorDropdownDTO> fetchDoctorForDropDown() {
        return Arrays.asList(DoctorDropdownDTO.builder()
                        .value(1L)
                        .label("DR.SANJEEV UPRETI")
                        .fileUri("http://localhost:8081/files/Doctor-cogent/test.png")
                        .build(),
                DoctorDropdownDTO.builder()
                        .value(2L)
                        .label("DR.DANIEL SHRESTHA")
                        .fileUri("http://localhost:8081/files/Doctor-cogent/cogent.png")
                        .build()
        );
    }

    public static DoctorDetailResponseDTO fetchDoctorDetailResponseDTO() {
        return DoctorDetailResponseDTO.builder()
                .doctorId(BigInteger.valueOf(1L))
                .doctorName("DR.SANJEEV UPRETI")
                .mobileNumber("9841111111")
                .status('Y')
                .remarks(null)
                .avatarUri("http://localhost:8081/files/Doctor-cogent/test.png")
                .isAvatarDefault('N')
                .specializationName("Physician")
                .departmentName("OPD, ICU")
                .build();
    }

    public static DoctorUpdateResponseDTO fetchDoctorUpdateResponseDTO() {
        return DoctorUpdateResponseDTO.builder()
                .doctorId(BigInteger.valueOf(1L))
                .doctorName("DR.SANJEEV UPRETI")
                .mobileNumber("9841111111")
                .status('Y')
                .remarks(null)
                .avatarUri("http://localhost:8081/files/Doctor-cogent/test.png")
                .isAvatarDefault('N')
                .doctorSpecializationResponseDTOs(new HashMap<>(1, 2))
                .doctorDepartmentResponseDTOs(new HashMap<>(1, 2))
                .doctorQualificationResponseDTOs(new HashMap<>(1, 2))
                .doctorScheduleTypeResponseDTOs(new HashMap<>(1, 2))
                .build();
    }
}
