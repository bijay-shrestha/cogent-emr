package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.admin.*;
import com.cogent.admin.dto.response.admin.AdminDetailResponseDTO;
import com.cogent.admin.dto.response.files.FileUploadResponseDTO;
import com.cogent.admin.exception.BadRequestException;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.exception.OperationUnsuccessfulException;
import com.cogent.admin.feign.dto.request.email.EmailRequestDTO;
import com.cogent.admin.feign.service.EmailService;
import com.cogent.admin.repository.*;
import com.cogent.admin.service.AdminCategoryService;
import com.cogent.admin.service.AdminService;
import com.cogent.admin.service.ProfileService;
import com.cogent.admin.service.StorageService;
import com.cogent.admin.utils.AdminUtils;
import com.cogent.admin.validator.LoginValidator;
import com.cogent.persistence.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.cogent.admin.constants.ErrorMessageConstants.AdminServiceMessages.*;
import static com.cogent.admin.constants.StatusConstants.INACTIVE;
import static com.cogent.admin.constants.StatusConstants.YES;
import static com.cogent.admin.constants.StringConstant.FORWARD_SLASH;
import static com.cogent.admin.exception.utils.ValidationUtils.validateConstraintViolation;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.AdminLog.*;
import static com.cogent.admin.utils.AdminUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static java.lang.reflect.Array.get;

/**
 * @author smriti on 2019-08-05
 */
@Service
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final Validator validator;

    private final AdminRepository adminRepository;

    private final AdminProfileRepository adminProfileRepository;

    private final MacAddressInfoRepository macAddressInfoRepository;

    private final AdminMetaInfoRepository adminMetaInfoRepository;

    private final AdminAvatarRepository adminAvatarRepository;

    private final AdminConfirmationTokenRepository confirmationTokenRepository;

    private final ProfileService profileService;

    private final AdminCategoryService adminCategoryService;

    private final StorageService storageService;

    private final EmailService emailService;

    private final AdminApplicationModuleRepository adminApplicationModuleRepository;

    public AdminServiceImpl(Validator validator,
                            AdminRepository adminRepository,
                            AdminProfileRepository adminProfileRepository,
                            MacAddressInfoRepository macAddressInfoRepository,
                            AdminMetaInfoRepository adminMetaInfoRepository,
                            AdminAvatarRepository adminAvatarRepository,
                            AdminConfirmationTokenRepository confirmationTokenRepository,
                            ProfileService profileService,
                            AdminCategoryService adminCategoryService,
                            StorageService storageService,
                            EmailService emailService,
                            AdminApplicationModuleRepository adminApplicationModuleRepository) {
        this.validator = validator;
        this.adminRepository = adminRepository;
        this.adminProfileRepository = adminProfileRepository;
        this.macAddressInfoRepository = macAddressInfoRepository;
        this.adminMetaInfoRepository = adminMetaInfoRepository;
        this.adminAvatarRepository = adminAvatarRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.profileService = profileService;
        this.adminCategoryService = adminCategoryService;
        this.storageService = storageService;
        this.emailService = emailService;
        this.adminApplicationModuleRepository = adminApplicationModuleRepository;
    }

    @Override
    public void save(@Valid AdminRequestDTO adminRequestDTO, MultipartFile files) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, ADMIN);

        validateConstraintViolation(validator.validate(adminRequestDTO));

        List admins = adminRepository.fetchAdminForValidation(adminRequestDTO.getUsername(),
                adminRequestDTO.getEmail(), adminRequestDTO.getMobileNumber());

        validateAdminDuplicity(admins, adminRequestDTO.getUsername(), adminRequestDTO.getEmail(),
                adminRequestDTO.getMobileNumber());

        Admin admin = save(adminRequestDTO);

        saveAdminApplicationModule(admin.getId(), adminRequestDTO.getApplicationModuleIds());

        saveAdminProfile(admin, adminRequestDTO.getProfileIds());

        saveAdminAvatar(admin, files);

        saveMacAddressInfo(admin, adminRequestDTO.getMacAddressInfoRequestDTOS());

        saveAdminMetaInfo(admin);

        AdminConfirmationToken adminConfirmationToken =
                saveAdminConfirmationToken(parseInAdminConfirmationToken(admin));

        sendEmail(adminRequestDTO, adminConfirmationToken.getConfirmationToken());

        log.info(SAVING_PROCESS_COMPLETED, ADMIN, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List fetchActiveAdminsForDropdown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, ADMIN);

        List responseDTOS = adminRepository.fetchActiveAdminsForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, ADMIN, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List search(AdminSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, ADMIN);

        List responseDTOS = adminRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_STARTED, ADMIN, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public AdminDetailResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, ADMIN);

        AdminDetailResponseDTO responseDTO = adminRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, ADMIN, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, ADMIN);

        Admin admin = findById(deleteRequestDTO.getId());

        save(convertAdminToDeleted(admin, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, ADMIN, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void changePassword(UpdatePasswordRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PASSWORD_PROCESS_STARTED);

        Admin admin = findById(requestDTO.getId());

        validatePassword(admin, requestDTO);

        save(updateAdminPassword(requestDTO, admin));

        log.info(UPDATING_PASSWORD_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void updateAvatar(MultipartFile files, Long adminId) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, ADMIN_AVATAR);

        Admin admin = findById(adminId);

        updateAvatar(admin, files);

        log.info(UPDATING_PROCESS_STARTED, ADMIN_AVATAR, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(AdminUpdateRequestDTO updateRequestDTO, MultipartFile files) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, ADMIN);

        validateConstraintViolation(validator.validate(updateRequestDTO));

        Admin admin = findById(updateRequestDTO.getId());

        List admins = adminRepository.fetchAdmin(updateRequestDTO);

        validateAdminDuplicity(admins, updateRequestDTO.getEmail(),
                updateRequestDTO.getMobileNumber());

        EmailRequestDTO emailRequestDTO = parseUpdatedInfo(updateRequestDTO, admin);

        update(updateRequestDTO, admin);

        updateAdminApplicationModule(admin.getId(), updateRequestDTO.getAdminApplicationModuleUpdateRequestDTOS());

        updateAdminProfile(admin.getId(), updateRequestDTO.getAdminProfileUpdateRequestDTOS());

        updateAvatar(admin, files);

        updateMacAddressInfo(updateRequestDTO, admin);

        saveAdminMetaInfo(admin);

        emailService.sendEmail(emailRequestDTO);

        log.info(UPDATING_PROCESS_COMPLETED, ADMIN, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void verifyConfirmationToken(String token) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(VERIFY_CONFIRMATION_TOKEN_PROCESS_STARTED);

        Object status = confirmationTokenRepository.findByConfirmationToken(token);
        validateStatus(status);

        log.info(VERIFY_CONFIRMATION_TOKEN_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void savePassword(PasswordRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PASSWORD_PROCESS_STARTED);

        AdminConfirmationToken adminConfirmationToken =
                confirmationTokenRepository.findAdminConfirmationTokenByToken(requestDTO.getToken())
                        .orElseThrow(() -> CONFIRMATION_TOKEN_NOT_FOUND.apply(requestDTO.getToken()));

        save(saveAdminPassword(requestDTO, adminConfirmationToken));
        adminConfirmationToken.setStatus(INACTIVE);

        log.info(SAVING_PASSWORD_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));
    }

    private void validateStatus(Object status) {
        if (status.equals(INACTIVE)) throw ADMIN_ALREADY_REGISTERED.get();
    }

    private void validateAdminDuplicity(List<Object[]> adminList, String requestUsername, String requestEmail,
                                        String requestMobileNumber) {

        final int USERNAME = 0;
        final int EMAIL = 1;
        final int MOBILE_NUMBER = 2;

        adminList.forEach(admin -> {
            boolean isUsernameExists = requestUsername.equalsIgnoreCase((String) get(admin, USERNAME));
            boolean isEmailExists = requestEmail.equalsIgnoreCase((String) get(admin, EMAIL));
            boolean isMobileNumberExists = requestMobileNumber.equalsIgnoreCase((String) get(admin, MOBILE_NUMBER));

            if (isUsernameExists && isEmailExists && isMobileNumberExists)
                throw ADMIN_DUPLICATION.get();

            validateUsername(isUsernameExists);
            validateEmail(isEmailExists);
            validateMobileNumber(isMobileNumberExists);
        });
    }

    public void validateUsername(boolean isUsernameExists) {
        if (isUsernameExists)
            throw new DataDuplicationException(
                    Admin.class, USERNAME_DUPLICATION_MESSAGE, USERNAME_DUPLICATION_DEBUG_MESSAGE);
    }

    private void validateEmail(boolean isEmailExists) {
        if (isEmailExists)
            throw new DataDuplicationException(Admin.class, EMAIL_DUPLICATION_MESSAGE, EMAIL_DUPLICATION_DEBUG_MESSAGE);
    }

    public void validateMobileNumber(boolean isMobileNumberExists) {
        if (isMobileNumberExists)
            throw new DataDuplicationException
                    (Admin.class, MOBILE_NUMBER_DUPLICATION_MESSAGE, MOBILE_NUMBER_DUPLICATION_DEBUG_MESSAGE);
    }

    public Admin save(AdminRequestDTO adminRequestDTO) {

        AdminCategory adminCategory = adminCategoryService.fetchActiveAdminCategoryById
                (adminRequestDTO.getAdminCategoryId());

        return save(convertAdminRequestDTOToAdmin(adminRequestDTO, adminCategory));
    }

    private void saveAdminApplicationModule(Long adminId, List<Long> applicationModuleIds) {
        List<AdminApplicationModule> adminApplicationModules = applicationModuleIds.stream()
                .map(applicationModuleId -> parseToAdminApplicationModule(adminId, applicationModuleId))
                .collect(Collectors.toList());

        saveAdminApplicationModule(adminApplicationModules);
    }

    private void saveAdminProfile(Admin admin, List<Long> profileIds) {
        List<AdminProfile> adminProfiles = profileIds.stream()
                .map(profileId -> {
                    /*VALIDATE IF PROFILE IS ACTIVE*/
                    fetchProfileById(profileId);
                    return parseToAdminProfile(admin.getId(), profileId);
                }).collect(Collectors.toList());

        saveAdminProfile(adminProfiles);
    }

    private void saveAdminAvatar(Admin admin, MultipartFile files) {
        if (!Objects.isNull(files)) {
            List<FileUploadResponseDTO> responseList = uploadFiles(admin, new MultipartFile[]{files});
            saveAdminAvatar(convertFileToAdminAvatar(responseList.get(0), admin));
        }
    }

    private List<FileUploadResponseDTO> uploadFiles(Admin admin, MultipartFile[] files) {
        String subDirectory = admin.getClass().getSimpleName() + FORWARD_SLASH + admin.getUsername();
        return storageService.uploadFiles(files, subDirectory);
    }

    private void updateAdminAvatar(Admin admin, AdminAvatar adminAvatar, MultipartFile files) {
        if (!Objects.isNull(files)) {
            List<FileUploadResponseDTO> responseList = uploadFiles(admin, new MultipartFile[]{files});
            AdminUtils.setFileProperties(responseList.get(0), adminAvatar);
        } else {
            adminAvatar.setIsDefaultImage(YES);
        }
        saveAdminAvatar(adminAvatar);
    }

    private void saveAdminApplicationModule(List<AdminApplicationModule> adminApplicationModules) {
        adminApplicationModuleRepository.saveAll(adminApplicationModules);
    }

    private void saveAdminProfile(List<AdminProfile> adminProfile) {
        adminProfileRepository.saveAll(adminProfile);
    }

    private void saveAdminAvatar(AdminAvatar adminAvatar) {
        adminAvatarRepository.save(adminAvatar);
    }

    public void saveMacAddressInfo(Admin admin, List<String> macAddresses) {
        if (admin.getHasMacBinding().equals(YES)) {
            validateMacAddressInfoSize.accept(macAddresses);

            List<MacAddressInfo> macAddressInfos = macAddresses
                    .stream().map(macAddress -> convertToMACAddressInfo(macAddress, admin))
                    .collect(Collectors.toList());

            saveMacAddressInfo(macAddressInfos);
        }
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public void saveMacAddressInfo(List<MacAddressInfo> macAddressInfos) {
        macAddressInfoRepository.saveAll(macAddressInfos);
    }

    public void saveAdminMetaInfo(Admin admin) {
        AdminMetaInfo adminMetaInfo = adminMetaInfoRepository.findAdminMetaInfoByAdminId(admin.getId());

        if (Objects.isNull(adminMetaInfo))
            saveAdminMetaInfo(parseInAdminMetaInfo(admin, new AdminMetaInfo()));

        else saveAdminMetaInfo(parseInAdminMetaInfo(admin, adminMetaInfo));
    }

    public void saveAdminMetaInfo(AdminMetaInfo adminMetaInfo) {
        adminMetaInfoRepository.save(adminMetaInfo);
    }

    public AdminConfirmationToken saveAdminConfirmationToken(AdminConfirmationToken adminConfirmationToken) {
        return confirmationTokenRepository.save(adminConfirmationToken);
    }

    public void sendEmail(AdminRequestDTO adminRequestDTO,
                          String confirmationToken) {

        EmailRequestDTO emailRequestDTO = convertAdminRequestToEmailRequestDTO(
                adminRequestDTO, confirmationToken);
        emailService.sendEmail(emailRequestDTO);
    }

    public Admin findById(Long adminId) {
        return adminRepository.findAdminById(adminId)
                .orElseThrow(() -> ADMIN_WITH_GIVEN_ID_NOT_FOUND.apply(adminId));
    }

    private void validateAdminDuplicity(List<Object[]> adminList, String requestEmail,
                                        String requestMobileNumber) {

        final int EMAIL = 0;
        final int MOBILE_NUMBER = 1;

        adminList.forEach(admin -> {
            boolean isEmailExists = requestEmail.equalsIgnoreCase((String) get(admin, EMAIL));
            boolean isMobileNumberExists = requestMobileNumber.equalsIgnoreCase((String) get(admin, MOBILE_NUMBER));

            if (isEmailExists && isMobileNumberExists)
                throw ADMIN_DUPLICATION.get();

            validateEmail(isEmailExists);
            validateMobileNumber(isMobileNumberExists);
        });
    }

    private void updateAdminApplicationModule(Long adminId,
                                              List<AdminApplicationModuleUpdateRequestDTO> updateRequestDTOS) {

        List<AdminApplicationModule> adminApplicationModules = updateRequestDTOS.stream()
                .map(requestDTO -> parseToUpdatedAdminApplicationModule(adminId, requestDTO))
                .collect(Collectors.toList());

        saveAdminApplicationModule(adminApplicationModules);
    }

    private void updateAdminProfile(Long adminId,
                                    List<AdminProfileUpdateRequestDTO> adminProfileUpdateRequestDTOS) {

        List<AdminProfile> adminProfiles = adminProfileUpdateRequestDTOS.stream()
                .map(requestDTO -> {
                    /*VALIDATE IF PROFILE IS ACTIVE*/
                    fetchProfileById(requestDTO.getProfileId());
                    return parseToUpdatedAdminProfile(adminId, requestDTO);
                }).collect(Collectors.toList());

        saveAdminProfile(adminProfiles);
    }

    private void fetchProfileById(Long profileId) {
        profileService.fetchActiveProfileById(profileId);
    }

    public void updateAvatar(Admin admin, MultipartFile files) {
        AdminAvatar adminAvatar = adminAvatarRepository.findAdminAvatarByAdminId(admin.getId());

        if (Objects.isNull(adminAvatar)) saveAdminAvatar(admin, files);
        else updateAdminAvatar(admin, adminAvatar, files);
    }

    public void update(AdminUpdateRequestDTO adminRequestDTO, Admin admin) {

        AdminCategory adminCategory = adminCategoryService.fetchActiveAdminCategoryById
                (adminRequestDTO.getAdminCategoryId());

        convertAdminUpdateRequestDTOToAdmin(admin, adminRequestDTO, adminCategory);
    }

    public void updateMacAddressInfo(AdminUpdateRequestDTO adminRequestDTO, Admin admin) {

        List<MacAddressInfo> macAddressInfos = convertToUpdatedMACAddressInfo(
                adminRequestDTO.getMacAddressInfoUpdateRequestDTOS(), admin);

        saveMacAddressInfo(macAddressInfos);
    }

    public EmailRequestDTO parseUpdatedInfo(AdminUpdateRequestDTO adminRequestDTO, Admin admin) {
        return parseToEmailRequestDTO(admin.getUsername(), adminRequestDTO,
                parseUpdatedValues(admin, adminRequestDTO), parseUpdatedMacAddress(adminRequestDTO));
    }

    private Consumer<List<String>> validateMacAddressInfoSize = (macInfos) -> {
        if (ObjectUtils.isEmpty(macInfos))
            throw new NoContentFoundException(MacAddressInfo.class);
    };

    private void validatePassword(Admin admin, UpdatePasswordRequestDTO requestDTO) {

        if (!LoginValidator.checkPassword(requestDTO.getOldPassword(), admin.getPassword()))
            throw new OperationUnsuccessfulException(PASSWORD_MISMATCH_MESSAGE);

        if (LoginValidator.checkPassword(requestDTO.getNewPassword(), admin.getPassword()))
            throw new DataDuplicationException(DUPLICATE_PASSWORD_MESSAGE);
    }

    private Supplier<DataDuplicationException> ADMIN_DUPLICATION = () -> new DataDuplicationException(
            Admin.class, ADMIN_DUPLICATION_MESSAGE, ADMIN_DUPLICATION_DEBUG_MESSAGE);

    private Function<Long, NoContentFoundException> ADMIN_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Admin.class, "id", id.toString());
    };

    private Supplier<BadRequestException> ADMIN_ALREADY_REGISTERED = () -> new BadRequestException(ADMIN_REGISTERED);

    private Function<String, NoContentFoundException> CONFIRMATION_TOKEN_NOT_FOUND = (confirmationToken) -> {
        throw new NoContentFoundException(INVALID_CONFIRMATION_TOKEN, "confirmationToken", confirmationToken);
    };

}
