package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctor.*;
import com.cogent.admin.dto.response.doctor.DoctorUpdateResponseDTO;
import com.cogent.admin.feign.dto.response.fileserver.FileServerResponseDTO;
import com.cogent.persistence.model.*;

import java.util.HashMap;
import java.util.Map;

import static com.cogent.admin.constants.StatusConstants.ACTIVE;
import static com.cogent.admin.constants.StatusConstants.NO;
import static com.cogent.admin.constants.StringConstant.COMMA_SEPARATED;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-09-29
 */
public class DoctorUtils {

    public static Doctor convertDTOToDoctor(DoctorRequestDTO requestDTO,
                                            Gender gender,
                                            Country country) {
        Doctor doctor = new Doctor();
        doctor.setName(toUpperCase(requestDTO.getName()));
        doctor.setCode(String.valueOf(RandomGenerator.generateRandomNumber(3)));
        doctor.setMobileNumber(requestDTO.getMobileNumber());
        doctor.setDateOfBirth(requestDTO.getDateOfBirth());
        doctor.setNepaliDateOfBirth(requestDTO.getNepaliDateOfBirth());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setNmcNumber(requestDTO.getNmcNumber());
        doctor.setJoinedDate(requestDTO.getJoinedDate());
        doctor.setNepaliJoinedDate(requestDTO.getNepaliJoinedDate());
        doctor.setStatus(requestDTO.getStatus());

        parseToDoctor(doctor, gender, country);
        return doctor;
    }

    private static void parseToDoctor(Doctor doctor,
                                      Gender gender,
                                      Country country) {
        doctor.setGender(gender);
        doctor.setCountry(country);
    }

    public static DoctorSpecialization parseToDoctorSpecialization(Long doctorId,
                                                                   Long specializationId) {
        DoctorSpecialization doctorSpecialization =
                convertToDoctorSpecialization(doctorId, specializationId);
        doctorSpecialization.setStatus(ACTIVE);
        return doctorSpecialization;
    }

    private static DoctorSpecialization convertToDoctorSpecialization(Long doctorId,
                                                                      Long specializationId) {
        DoctorSpecialization doctorSpecialization = new DoctorSpecialization();
        doctorSpecialization.setDoctorId(doctorId);
        doctorSpecialization.setSpecializationId(specializationId);
        return doctorSpecialization;
    }

    public static DoctorDepartment parseToDoctorDepartment(Long doctorId,
                                                           Long departmentId) {
        DoctorDepartment doctorDepartment = convertToDoctorDepartment(doctorId, departmentId);
        doctorDepartment.setStatus(ACTIVE);
        return doctorDepartment;
    }

    private static DoctorDepartment convertToDoctorDepartment(Long doctorId,
                                                              Long departmentId) {
        DoctorDepartment doctorDepartment = new DoctorDepartment();
        doctorDepartment.setDoctor(doctorId);
        doctorDepartment.setDepartment(departmentId);
        return doctorDepartment;
    }

    public static DoctorScheduleType parseToDoctorScheduleType(Long doctorId,
                                                               Long doctorTypeId) {
        DoctorScheduleType doctorDepartment =
                convertToDoctorScheduleType(doctorId, doctorTypeId);
        doctorDepartment.setStatus(ACTIVE);
        return doctorDepartment;
    }

    private static DoctorScheduleType convertToDoctorScheduleType(Long doctorId,
                                                                  Long doctorTypeId) {
        DoctorScheduleType doctorScheduleType = new DoctorScheduleType();
        doctorScheduleType.setDoctorId(doctorId);
        doctorScheduleType.setDoctorTypeId(doctorTypeId);
        return doctorScheduleType;
    }

    public static DoctorQualification parseToDoctorQualification(Long doctorId,
                                                                 Long qualificationId) {
        DoctorQualification doctorQualification =
                convertToDoctorQualification(doctorId, qualificationId);
        doctorQualification.setStatus(ACTIVE);
        return doctorQualification;
    }

    private static DoctorQualification convertToDoctorQualification(Long doctorId,
                                                                    Long qualificationId) {
        DoctorQualification doctorQualification = new DoctorQualification();
        doctorQualification.setDoctorId(doctorId);
        doctorQualification.setQualificationId(qualificationId);
        return doctorQualification;
    }

    public static DoctorAvatar convertFileToDoctorAvatar(FileServerResponseDTO fileServerResponseDTO,
                                                         Doctor doctor) {

        DoctorAvatar doctorAvatar = new DoctorAvatar();
        setAvatarFileProperties(fileServerResponseDTO, doctorAvatar);
        doctorAvatar.setDoctorId(doctor);
        return doctorAvatar;
    }

    public static DoctorSignature convertFileToDoctorSignature(FileServerResponseDTO fileServerResponseDTO,
                                                               Doctor doctor) {

        DoctorSignature doctorSignature = new DoctorSignature();
        setSignatureFileProperties(fileServerResponseDTO, doctorSignature);
        doctorSignature.setDoctorId(doctor);
        return doctorSignature;
    }

    public static void setSignatureFileProperties(FileServerResponseDTO fileServerResponseDTO,
                                                  DoctorSignature doctorSignature) {
        doctorSignature.setFileSize(fileServerResponseDTO.getFileSize());
        doctorSignature.setFileUri(fileServerResponseDTO.getFileUri());
        doctorSignature.setFileType(fileServerResponseDTO.getFileType());
        doctorSignature.setIsDefaultImage(NO);
    }

    public static void setAvatarFileProperties(FileServerResponseDTO fileServerResponseDTO,
                                               DoctorAvatar doctorAvatar) {
        doctorAvatar.setFileSize(fileServerResponseDTO.getFileSize());
        doctorAvatar.setFileUri(fileServerResponseDTO.getFileUri());
        doctorAvatar.setFileType(fileServerResponseDTO.getFileType());
        doctorAvatar.setIsDefaultImage(NO);
    }

    public static void convertToUpdatedDoctor(DoctorUpdateDTO requestDTO,
                                              Doctor doctor,
                                              Gender gender,
                                              Country country) {

        doctor.setName(toUpperCase(requestDTO.getName()));
        doctor.setMobileNumber(requestDTO.getMobileNumber());
        doctor.setDateOfBirth(requestDTO.getDateOfBirth());
        doctor.setNepaliDateOfBirth(requestDTO.getNepaliDateOfBirth());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setNmcNumber(requestDTO.getNmcNumber());
        doctor.setJoinedDate(requestDTO.getJoinedDate());
        doctor.setNepaliJoinedDate(requestDTO.getNepaliJoinedDate());
        doctor.setStatus(requestDTO.getStatus());
        doctor.setRemarks(requestDTO.getRemarks());
        doctor.setResignationDate(requestDTO.getResignationDate());
        doctor.setNepaliResignationDate(requestDTO.getNepaliResignationDate());
        parseToDoctor(doctor, gender, country);
    }

    public static DoctorSpecialization parseToUpdatedDoctorSpecialization(
            Long doctorId,
            DoctorSpecializationUpdateDTO updateRequestDTO) {

        DoctorSpecialization doctorSpecialization =
                convertToDoctorSpecialization(doctorId, updateRequestDTO.getSpecializationId());
        doctorSpecialization.setId(updateRequestDTO.getDoctorSpecializationId());
        doctorSpecialization.setStatus(updateRequestDTO.getStatus());
        return doctorSpecialization;
    }

    public static DoctorDepartment parseToUpdatedDoctorDepartment(
            Long doctorId,
            DoctorDepartmentUpdateDTO updateRequestDTO) {

        DoctorDepartment doctorDepartment =
                convertToDoctorDepartment(doctorId, updateRequestDTO.getDepartmentId());
        doctorDepartment.setId(updateRequestDTO.getDoctorDepartmentId());
        doctorDepartment.setStatus(updateRequestDTO.getStatus());
        return doctorDepartment;
    }

    public static DoctorScheduleType parseToUpdatedDoctorScheduleType(
            Long doctorId,
            DoctorScheduleTypeUpdateDTO updateRequestDTO) {

        DoctorScheduleType doctorScheduleType =
                convertToDoctorScheduleType(doctorId, updateRequestDTO.getDoctorTypeId());
        doctorScheduleType.setId(updateRequestDTO.getDoctorScheduleTypeId());
        doctorScheduleType.setStatus(updateRequestDTO.getStatus());
        return doctorScheduleType;
    }

    public static DoctorQualification parseToUpdatedDoctorQualification(
            Long doctorId,
            DoctorQualificationUpdateDTO updateRequestDTO) {

        DoctorQualification doctorQualification =
                convertToDoctorQualification(doctorId, updateRequestDTO.getQualificationId());
        doctorQualification.setId(updateRequestDTO.getDoctorQualificationId());
        doctorQualification.setStatus(updateRequestDTO.getStatus());
        return doctorQualification;
    }

    public static void convertToDeletedDoctor(Doctor doctor,
                                              DeleteRequestDTO deleteRequestDTO) {
        doctor.setStatus(deleteRequestDTO.getStatus());
        doctor.setRemarks(deleteRequestDTO.getRemarks());
    }

    public static void parseToDoctorUpdateResponseDTO(DoctorUpdateResponseDTO updateResponseDTO) {
        parseToDoctorSpecialization(updateResponseDTO);
        parseToDoctorDepartment(updateResponseDTO);
        parseToDoctorScheduleType(updateResponseDTO);
        parseToDoctorQualification(updateResponseDTO);
    }

    private static void parseToDoctorSpecialization(DoctorUpdateResponseDTO updateResponseDTO) {
        String[] doctorSpecializationId = updateResponseDTO.getDoctorSpecializationId().split(COMMA_SEPARATED);
        String[] specializationIds = updateResponseDTO.getSpecializationId().split(COMMA_SEPARATED);

        Map<Long, Long> map = new HashMap<>();

        for (int i = 0; i < doctorSpecializationId.length; i++) {
            map.put(Long.parseLong(doctorSpecializationId[i]), Long.parseLong(specializationIds[i]));
        }

        updateResponseDTO.setDoctorSpecializationResponseDTOs(map);
    }

    private static void parseToDoctorDepartment(DoctorUpdateResponseDTO updateResponseDTO) {
        String[] doctorDepartmentIds = updateResponseDTO.getDoctorDepartmentId().split(COMMA_SEPARATED);
        String[] departmentIds = updateResponseDTO.getDepartmentId().split(COMMA_SEPARATED);

        Map<Long, Long> map = new HashMap<>();

        for (int i = 0; i < doctorDepartmentIds.length; i++) {
            map.put(Long.parseLong(doctorDepartmentIds[i]), Long.parseLong(departmentIds[i]));
        }

        updateResponseDTO.setDoctorDepartmentResponseDTOs(map);
    }

    private static void parseToDoctorScheduleType(DoctorUpdateResponseDTO updateResponseDTO) {
        String[] doctorScheduleTypeIds = updateResponseDTO.getDoctorScheduleTypeId().split(COMMA_SEPARATED);
        String[] DoctorTypeIds = updateResponseDTO.getDoctorTypeId().split(COMMA_SEPARATED);

        Map<Long, Long> map = new HashMap<>();

        for (int i = 0; i < doctorScheduleTypeIds.length; i++) {
            map.put(Long.parseLong(doctorScheduleTypeIds[i]), Long.parseLong(DoctorTypeIds[i]));
        }

        updateResponseDTO.setDoctorScheduleTypeResponseDTOs(map);
    }

    private static void parseToDoctorQualification(DoctorUpdateResponseDTO updateResponseDTO) {
        String[] doctorQualificationIds = updateResponseDTO.getDoctorQualificationId().split(COMMA_SEPARATED);
        String[] qualificationIds = updateResponseDTO.getQualificationId().split(COMMA_SEPARATED);

        Map<Long, Long> map = new HashMap<>();

        for (int i = 0; i < doctorQualificationIds.length; i++) {
            map.put(Long.parseLong(doctorQualificationIds[i]), Long.parseLong(qualificationIds[i]));
        }

        updateResponseDTO.setDoctorQualificationResponseDTOs(map);
    }
}
