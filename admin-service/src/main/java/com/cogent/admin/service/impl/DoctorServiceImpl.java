package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctor.*;
import com.cogent.admin.dto.response.doctor.DoctorDetailResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorDropdownDTO;
import com.cogent.admin.dto.response.doctor.DoctorMinimalResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorUpdateResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.feign.dto.response.fileserver.FileServerResponseDTO;
import com.cogent.admin.feign.service.FileServerService;
import com.cogent.admin.repository.*;
import com.cogent.admin.service.*;
import com.cogent.persistence.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_AND_MOBILE_NUMBER_DUPLICATION_MESSAGE;
import static com.cogent.admin.constants.StatusConstants.YES;
import static com.cogent.admin.constants.StringConstant.FORWARD_SLASH;
import static com.cogent.admin.constants.StringConstant.SPACE;
import static com.cogent.admin.exception.utils.ValidationUtils.validateConstraintViolation;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.DoctorLog.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.DoctorUtils.*;

/**
 * @author smriti on 2019-09-29
 */
@Service
@Transactional
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final DoctorAvatarRepository doctorAvatarRepository;

    private final DoctorSpecializationRepository doctorSpecializationRepository;

    private final SpecializationService specializationService;

    private final DepartmentRepository departmentRepository;

    private final DoctorDepartmentRepository doctorDepartmentRepository;

    private final Validator validator;

    private final FileServerService fileServerService;

    private final GenderService genderService;

    private final CountryService countryService;

    private final QualificationService qualificationService;

    private final DoctorTypeService doctorTypeService;

    private final DoctorScheduleTypeRepository doctorScheduleTypeRepository;

    private final DoctorQualificationRepository doctorQualificationRepository;

    private final DoctorSignatureRepository doctorSignatureRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             DoctorAvatarRepository doctorAvatarRepository,
                             DoctorSpecializationRepository doctorSpecializationRepository,
                             SpecializationService specializationService,
                             DepartmentRepository departmentRepository,
                             DoctorDepartmentRepository doctorDepartmentRepository,
                             Validator validator,
                             FileServerService fileServerService,
                             GenderService genderService,
                             CountryService countryService,
                             QualificationService qualificationService,
                             DoctorTypeService doctorTypeService,
                             DoctorScheduleTypeRepository doctorScheduleTypeRepository,
                             DoctorQualificationRepository doctorQualificationRepository,
                             DoctorSignatureRepository doctorSignatureRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorAvatarRepository = doctorAvatarRepository;
        this.doctorSpecializationRepository = doctorSpecializationRepository;
        this.specializationService = specializationService;
        this.departmentRepository = departmentRepository;
        this.doctorDepartmentRepository = doctorDepartmentRepository;
        this.validator = validator;
        this.fileServerService = fileServerService;
        this.genderService = genderService;
        this.countryService = countryService;
        this.qualificationService = qualificationService;
        this.doctorTypeService = doctorTypeService;
        this.doctorScheduleTypeRepository = doctorScheduleTypeRepository;
        this.doctorQualificationRepository = doctorQualificationRepository;
        this.doctorSignatureRepository = doctorSignatureRepository;
    }

    @Override
    public String save(@Valid DoctorRequestDTO requestDTO, MultipartFile avatar, MultipartFile signature) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DOCTOR);

        validateConstraintViolation(validator.validate(requestDTO));

        Long doctorCount = doctorRepository.validateDoctorDuplicity(requestDTO.getName(), requestDTO.getMobileNumber());

        validateDoctor(doctorCount, requestDTO.getName(), requestDTO.getMobileNumber());

        Doctor doctor = convertDTOToDoctor(requestDTO,
                fetchGenderById(requestDTO.getGenderId()),
                fetchCountryById(requestDTO.getCountryId()));

        saveDoctor(doctor);

        saveDoctorSpecialization(doctor.getId(), requestDTO.getSpecializationIds());

        saveDoctorDepartment(doctor.getId(), requestDTO.getDepartmentIds());

        saveDoctorScheduleType(doctor.getId(), requestDTO.getDoctorTypeIds());

        saveDoctorQualifications(doctor.getId(), requestDTO.getQualificationIds());

        saveDoctorAvatar(doctor, avatar);

        saveDoctorSignature(doctor, signature);

        log.info(SAVING_PROCESS_COMPLETED, DOCTOR, getDifferenceBetweenTwoTime(startTime));

        return doctor.getCode();
    }

    @Override
    public void update(@Valid DoctorUpdateRequestDTO requestDTO,
                       MultipartFile avatar, MultipartFile signature) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DOCTOR);

        validateConstraintViolation(validator.validate(requestDTO));

        Doctor doctor = findById(requestDTO.getUpdateDTO().getId());

        Long doctorCount = doctorRepository.validateDoctorDuplicityForUpdate(
                requestDTO.getUpdateDTO().getId(),
                requestDTO.getUpdateDTO().getName(),
                requestDTO.getUpdateDTO().getMobileNumber());

        validateDoctor(doctorCount,
                requestDTO.getUpdateDTO().getName(),
                requestDTO.getUpdateDTO().getMobileNumber());

        convertToUpdatedDoctor(
                requestDTO.getUpdateDTO(),
                doctor,
                fetchGenderById(requestDTO.getUpdateDTO().getGenderId()),
                fetchCountryById(requestDTO.getUpdateDTO().getCountryId()));

        updateDoctorSpecialization(doctor.getId(), requestDTO.getSpecializationUpdateRequestDTOS());

        updateDoctorDepartment(doctor.getId(), requestDTO.getDoctorDepartmentUpdateDTOS());

        updateDoctorScheduleType(doctor.getId(), requestDTO.getDoctorScheduleTypeUpdateDTOS());

        updateDoctorQualification(doctor.getId(), requestDTO.getDoctorQualificationUpdateDTOS());

        updateDoctorAvatar(doctor, avatar);

        updateDoctorSignature(doctor, signature);

        log.info(UPDATING_PROCESS_COMPLETED, DOCTOR, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, DOCTOR);

        Doctor doctor = findById(deleteRequestDTO.getId());

        convertToDeletedDoctor(doctor, deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, DOCTOR, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<DoctorMinimalResponseDTO> search(DoctorSearchRequestDTO searchRequestDTO,
                                                 Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, DOCTOR);

        List<DoctorMinimalResponseDTO> responseDTOS = doctorRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, DOCTOR, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<DoctorDropdownDTO> fetchDoctorForDropdown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, DOCTOR);

        List<DoctorDropdownDTO> responseDTOS = doctorRepository.fetchDoctorForDropdown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, DOCTOR, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public DoctorDetailResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, DOCTOR);

        DoctorDetailResponseDTO responseDTO = doctorRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, DOCTOR, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public DoctorUpdateResponseDTO fetchDetailsForUpdate(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, DOCTOR);

        DoctorUpdateResponseDTO responseDTO = doctorRepository.fetchDetailsForUpdate(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, DOCTOR, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }


    @Override
    public Doctor fetchDoctorById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED, DOCTOR);

        Doctor doctor = doctorRepository.findActiveDoctorById(id)
                .orElseThrow(() -> new NoContentFoundException(Doctor.class, "id", id.toString()));

        log.info(FETCHING_PROCESS_COMPLETED, doctor, getDifferenceBetweenTwoTime(startTime));

        return doctor;
    }

    @Override
    public List<DoctorDropdownDTO> fetchDoctorBySpecializationId(Long specializationId) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, DOCTOR);

        List<DoctorDropdownDTO> responseDTOS =
                doctorRepository.fetchDoctorBySpecializationId(specializationId);

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, DOCTOR, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    private Gender fetchGenderById(Long genderId) {
        return genderService.fetchGenderById(genderId);
    }

    private Country fetchCountryById(Long countryId) {
        return countryService.fetchCountryById(countryId);
    }

    private void findSpecializationById(Long specializationId) {
        specializationService.fetchActiveSpecializationById(specializationId);
    }

    private void findDepartmentById(Long departmentId) {
        departmentRepository.findActiveDepartmentById(departmentId).orElseThrow(() ->
                new NoContentFoundException(Department.class, "departmentId", departmentId.toString()));
    }

    private void fetchDoctorTypeById(Long doctorTypeId) {
        doctorTypeService.fetchDoctorTypeById(doctorTypeId);
    }

    private void fetchQualificationById(Long qualificationId) {
        qualificationService.fetchQualificationById(qualificationId);
    }

    private void saveDoctorSpecialization(Long doctorId, List<Long> specializationIds) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DOCTOR_SPECIALIZATION);

        List<DoctorSpecialization> doctorSpecializations = specializationIds.stream()
                .map(specializationId -> {
                    /*VALIDATE IF THE SPECIALIZATION IS ACTIVE*/
                    findSpecializationById(specializationId);
                    return parseToDoctorSpecialization(doctorId, specializationId);
                }).collect(Collectors.toList());

        saveDoctorSpecialization(doctorSpecializations);

        log.info(SAVING_PROCESS_COMPLETED, DOCTOR_SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));
    }

    private void saveDoctorDepartment(Long doctorId, List<Long> departmentIds) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DOCTOR_DEPARTMENT);

        List<DoctorDepartment> doctorDepartments = departmentIds.stream()
                .map(departmentId -> {
                    /*VALIDATE IF DEPARTMENT IS ACTIVE*/
                    findDepartmentById(departmentId);
                    return parseToDoctorDepartment(doctorId, departmentId);
                }).collect(Collectors.toList());

        saveDoctorDepartment(doctorDepartments);

        log.info(SAVING_PROCESS_COMPLETED, DOCTOR_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));
    }

    private void saveDoctorScheduleType(Long doctorId, List<Long> doctorTypeIds) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DOCTOR_SCHEDULE_TYPE);

        List<DoctorScheduleType> doctorScheduleTypes = doctorTypeIds.stream()
                .map(doctorTypeId -> {
                    /*VALIDATE IF Doctor TYPE IS ACTIVE*/
                    fetchDoctorTypeById(doctorTypeId);
                    return parseToDoctorScheduleType(doctorId, doctorTypeId);
                }).collect(Collectors.toList());

        saveDoctorScheduleType(doctorScheduleTypes);

        log.info(SAVING_PROCESS_COMPLETED, DOCTOR_SCHEDULE_TYPE, getDifferenceBetweenTwoTime(startTime));
    }

    private void saveDoctorQualifications(Long doctorId, List<Long> qualificationIds) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DOCTOR_QUALIFICATION);

        List<DoctorQualification> doctorQualifications = qualificationIds.stream()
                .map(qualificationId -> {
                    /*VALIDATE IF QUALIFICATION IS ACTIVE*/
                    fetchQualificationById(qualificationId);
                    return parseToDoctorQualification(doctorId, qualificationId);
                }).collect(Collectors.toList());

        saveDoctorQualification(doctorQualifications);

        log.info(SAVING_PROCESS_COMPLETED, DOCTOR_QUALIFICATION, getDifferenceBetweenTwoTime(startTime));
    }

    private void saveDoctorAvatar(Doctor doctor, MultipartFile file) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DOCTOR_AVATAR);

        if (!Objects.isNull(file)) {
            List<FileServerResponseDTO> responseList = uploadFiles(doctor, new MultipartFile[]{file});
            saveDoctorAvatar(convertFileToDoctorAvatar(responseList.get(0), doctor));
        }

        log.info(SAVING_PROCESS_COMPLETED, DOCTOR_AVATAR, getDifferenceBetweenTwoTime(startTime));
    }

    private void saveDoctorSignature(Doctor doctor, MultipartFile signature) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DOCTOR_SIGNATURE);

        if (!Objects.isNull(signature)) {
            List<FileServerResponseDTO> responseList = uploadFiles(doctor, new MultipartFile[]{signature});
            saveDoctorSignature(convertFileToDoctorSignature(responseList.get(0), doctor));
        }

        log.info(SAVING_PROCESS_COMPLETED, DOCTOR_SIGNATURE, getDifferenceBetweenTwoTime(startTime));
    }

    private List<FileServerResponseDTO> uploadFiles(Doctor Doctor, MultipartFile[] file) {
        String subDirectoryLocation = Doctor.getClass().getSimpleName()
                + FORWARD_SLASH + Doctor.getName() + SPACE + Doctor.getMobileNumber();

        return fileServerService.uploadFiles(file, subDirectoryLocation);
    }

    private void updateDoctorSpecialization(Long doctorId,
                                            List<DoctorSpecializationUpdateDTO> specializationUpdateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DOCTOR_SPECIALIZATION);

        List<DoctorSpecialization> doctorSpecializations = specializationUpdateRequestDTO.stream()
                .map(requestDTO -> {
                    /*VALIDATE IF THE SPECIALIZATION IS ACTIVE*/
                    findSpecializationById(requestDTO.getSpecializationId());

                    return parseToUpdatedDoctorSpecialization(doctorId, requestDTO);
                }).collect(Collectors.toList());

        saveDoctorSpecialization(doctorSpecializations);

        log.info(UPDATING_PROCESS_COMPLETED, DOCTOR_SPECIALIZATION, getDifferenceBetweenTwoTime(startTime));
    }

    private void updateDoctorDepartment(Long doctorId,
                                        List<DoctorDepartmentUpdateDTO> doctorDepartmentUpdateDTOS) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DOCTOR_DEPARTMENT);

        List<DoctorDepartment> doctorDepartments = doctorDepartmentUpdateDTOS.stream()
                .map(requestDTO -> {
                    /*VALIDATE IF DEPARTMENT IS ACTIVE*/
                    findDepartmentById(requestDTO.getDepartmentId());
                    return parseToUpdatedDoctorDepartment(doctorId, requestDTO);
                }).collect(Collectors.toList());

        saveDoctorDepartment(doctorDepartments);

        log.info(UPDATING_PROCESS_COMPLETED, DOCTOR_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));
    }

    private void updateDoctorScheduleType(Long doctorId,
                                          List<DoctorScheduleTypeUpdateDTO> doctorScheduleTypeUpdateDTOS) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DOCTOR_SCHEDULE_TYPE);

        List<DoctorScheduleType> doctorScheduleTypes = doctorScheduleTypeUpdateDTOS.stream()
                .map(requestDTO -> {
                    /*VALIDATE IF THE DOCTOR TYPE IS ACTIVE*/
                    fetchDoctorTypeById(requestDTO.getDoctorTypeId());
                    return parseToUpdatedDoctorScheduleType(doctorId, requestDTO);
                }).collect(Collectors.toList());

        saveDoctorScheduleType(doctorScheduleTypes);

        log.info(UPDATING_PROCESS_COMPLETED, DOCTOR_SCHEDULE_TYPE, getDifferenceBetweenTwoTime(startTime));
    }

    private void updateDoctorQualification(Long doctorId,
                                           List<DoctorQualificationUpdateDTO> doctorQualificationUpdateDTOS) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DOCTOR_QUALIFICATION);

        List<DoctorQualification> doctorQualifications = doctorQualificationUpdateDTOS.stream()
                .map(requestDTO -> {
                    /*VALIDATE IF QUALIFICATION IS ACTIVE*/
                    fetchQualificationById(requestDTO.getQualificationId());
                    return parseToUpdatedDoctorQualification(doctorId, requestDTO);
                }).collect(Collectors.toList());

        saveDoctorQualification(doctorQualifications);

        log.info(UPDATING_PROCESS_COMPLETED, DOCTOR_QUALIFICATION, getDifferenceBetweenTwoTime(startTime));
    }

    public void updateDoctorAvatar(Doctor doctor, MultipartFile file) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DOCTOR_AVATAR);

        DoctorAvatar doctorAvatar = doctorAvatarRepository.findByDoctorId(doctor.getId());

        if (Objects.isNull(doctorAvatar)) saveDoctorAvatar(doctor, file);
        else updateDoctorAvatar(doctor, doctorAvatar, file);

        log.info(UPDATING_PROCESS_COMPLETED, DOCTOR_AVATAR, getDifferenceBetweenTwoTime(startTime));
    }

    public void updateDoctorSignature(Doctor doctor, MultipartFile file) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DOCTOR_AVATAR);

        DoctorSignature doctorSignature = doctorSignatureRepository.findByDoctorId(doctor.getId());

        if (Objects.isNull(doctorSignature)) saveDoctorSignature(doctor, file);
        else updateDoctorSignature(doctor, doctorSignature, file);

        log.info(UPDATING_PROCESS_COMPLETED, DOCTOR_AVATAR, getDifferenceBetweenTwoTime(startTime));
    }

    private void updateDoctorAvatar(Doctor doctor,
                                    DoctorAvatar doctorAvatar,
                                    MultipartFile files) {
        if (!Objects.isNull(files)) {
            List<FileServerResponseDTO> responseList = uploadFiles(doctor, new MultipartFile[]{files});
            setAvatarFileProperties(responseList.get(0), doctorAvatar);
        } else doctorAvatar.setIsDefaultImage(YES);

        saveDoctorAvatar(doctorAvatar);
    }

    private void updateDoctorSignature(Doctor doctor,
                                       DoctorSignature doctorSignature,
                                       MultipartFile files) {
        if (!Objects.isNull(files)) {
            List<FileServerResponseDTO> responseList = uploadFiles(doctor, new MultipartFile[]{files});
            setSignatureFileProperties(responseList.get(0), doctorSignature);
        } else doctorSignature.setIsDefaultImage(YES);

        saveDoctorSignature(doctorSignature);
    }

    private void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    private void saveDoctorSpecialization(List<DoctorSpecialization> doctorSpecialization) {
        doctorSpecializationRepository.saveAll(doctorSpecialization);
    }

    private void saveDoctorDepartment(List<DoctorDepartment> doctorDepartments) {
        doctorDepartmentRepository.saveAll(doctorDepartments);
    }

    private void saveDoctorScheduleType(List<DoctorScheduleType> doctorScheduleTypes) {
        doctorScheduleTypeRepository.saveAll(doctorScheduleTypes);
    }

    private void saveDoctorQualification(List<DoctorQualification> doctorQualifications) {
        doctorQualificationRepository.saveAll(doctorQualifications);
    }

    private void saveDoctorAvatar(DoctorAvatar doctorAvatar) {
        doctorAvatarRepository.save(doctorAvatar);
    }

    private void saveDoctorSignature(DoctorSignature doctorSignature) {
        doctorSignatureRepository.save(doctorSignature);
    }

    public Doctor findById(Long doctorId) {
        return doctorRepository.findDoctorById(doctorId)
                .orElseThrow(() -> DOCTOR_WITH_GIVEN_ID_NOT_FOUND.apply(doctorId));
    }

    private Function<Long, NoContentFoundException> DOCTOR_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Doctor.class, "id", id.toString());
    };

    private void validateDoctor(Long doctorCount, String name, String mobileNumber) {

        if (doctorCount.intValue() > 0)
            throw new DataDuplicationException(
                    String.format(NAME_AND_MOBILE_NUMBER_DUPLICATION_MESSAGE, Doctor.class, name, mobileNumber),
                    "name", name, "mobileNumber", mobileNumber
            );
    }
}


