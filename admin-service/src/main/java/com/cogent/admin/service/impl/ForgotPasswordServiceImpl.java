package com.cogent.admin.service.impl;

import com.cogent.admin.constants.ExpirationTimeProperties;
import com.cogent.admin.dto.request.forgotPassword.ForgotPasswordRequestDTO;
import com.cogent.admin.exception.BadRequestException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.feign.dto.request.email.EmailRequestDTO;
import com.cogent.admin.feign.service.EmailService;
import com.cogent.admin.repository.AdminRepository;
import com.cogent.admin.repository.ForgotPasswordRepository;
import com.cogent.admin.service.ForgotPasswordService;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.ForgotPasswordVerification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.function.Supplier;

import static com.cogent.admin.constants.ErrorMessageConstants.AdminServiceMessages.ADMIN_NOT_ACTIVE;
import static com.cogent.admin.constants.ErrorMessageConstants.ForgotPasswordMessages.RESET_CODE_EXPIRED;
import static com.cogent.admin.constants.StatusConstants.ACTIVE;
import static com.cogent.admin.constants.StatusConstants.INACTIVE;
import static com.cogent.admin.log.constants.AdminLog.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.ForgotPasswordUtils.convertToForgotPasswordVerification;
import static com.cogent.admin.utils.ForgotPasswordUtils.parseToEmailRequestDTO;
import static java.util.Objects.isNull;

/**
 * @author smriti on 2019-09-20
 */
@Service
@Transactional
@Slf4j
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final AdminRepository adminRepository;

    private final ExpirationTimeProperties expirationTimeProperties;

    private final ForgotPasswordRepository verificationRepository;

    private final EmailService emailService;

    public ForgotPasswordServiceImpl(AdminRepository adminRepository,
                                     ExpirationTimeProperties expirationTimeProperties,
                                     ForgotPasswordRepository verificationRepository,
                                     EmailService emailService) {
        this.adminRepository = adminRepository;
        this.expirationTimeProperties = expirationTimeProperties;
        this.verificationRepository = verificationRepository;
        this.emailService = emailService;
    }

    @Override
    public void forgotPassword(String username) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FORGOT_PASSWORD_PROCESS_STARTED);

        Admin admin = adminRepository.fetchAdminByUsernameOrEmail(username);

        validateAdmin(admin, username);

        ForgotPasswordVerification forgotPasswordVerification = verificationRepository.findByAdminId(admin.getId());

        convertToForgotPasswordVerification(admin,
                expirationTimeProperties.getForgotPassword(),
                isNull(forgotPasswordVerification) ? new ForgotPasswordVerification() : forgotPasswordVerification);

        save(forgotPasswordVerification);

        EmailRequestDTO emailRequestDTO = parseToEmailRequestDTO(admin.getEmail(),
                admin.getUsername(), forgotPasswordVerification.getResetCode());

        emailService.sendEmail(emailRequestDTO);

        log.info(FORGOT_PASSWORD_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void verify(String resetCode) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(VERIFY_CODE_PROCESS_STARTED);

        Object expirationTime = verificationRepository.findByResetCode(resetCode);
        validateExpirationTime(expirationTime);

        log.info(VERIFY_CODE_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void updatePassword(ForgotPasswordRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PASSWORD_PROCESS_STARTED);

        Admin admin = adminRepository.fetchAdminByUsernameOrEmail(requestDTO.getUsername());
        updateAdminPassword(requestDTO, admin);
        updateForgotPasswordVerification(admin.getId());

        log.info(UPDATING_PASSWORD_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));
    }

    public void updateAdminPassword(ForgotPasswordRequestDTO requestDTO, Admin admin) {
        admin.setPassword(BCrypt.hashpw(requestDTO.getPassword(), BCrypt.gensalt()));
        adminRepository.save(admin);
    }

    public void updateForgotPasswordVerification(Long adminId) {
        ForgotPasswordVerification forgotPasswordVerification = verificationRepository.findByAdminId(adminId);
        forgotPasswordVerification.setStatus(INACTIVE);
        save(forgotPasswordVerification);
    }

    private void validateExpirationTime(Object expirationTime) {
        if (((Date) expirationTime).before(new Date())) throw RESET_CODE_HAS_EXPIRED.get();
    }

    public void save(ForgotPasswordVerification forgotPasswordVerification) {
        verificationRepository.save(forgotPasswordVerification);
    }

    private void validateAdmin(Admin admin, String username) {
        if (!admin.getStatus().equals(ACTIVE))
            throw new NoContentFoundException(Admin.class + " '" + username + "' " + ADMIN_NOT_ACTIVE,
                    "username/email", username);
    }

    private Supplier<BadRequestException> RESET_CODE_HAS_EXPIRED = () ->
            new BadRequestException(RESET_CODE_EXPIRED);
}
