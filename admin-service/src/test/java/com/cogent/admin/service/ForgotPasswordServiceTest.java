package com.cogent.admin.service;

import com.cogent.admin.constants.ExpirationTimeProperties;
import com.cogent.admin.dto.request.forgotPassword.ForgotPasswordRequestDTO;
import com.cogent.admin.exception.BadRequestException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.feign.service.EmailService;
import com.cogent.admin.repository.AdminRepository;
import com.cogent.admin.repository.ForgotPasswordRepository;
import com.cogent.admin.service.impl.ForgotPasswordServiceImpl;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.ForgotPasswordVerification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.cogent.admin.dto.forgotPassword.ForgotPasswordRequestUtils.*;
import static com.cogent.admin.dto.forgotPassword.ForgotPasswordResponseUtils.fetchExpiredTime;
import static com.cogent.admin.dto.forgotPassword.ForgotPasswordResponseUtils.getForgotPasswordVerification;
import static com.cogent.admin.dto.admin.AdminResponseUtils.getAdmin;
import static com.cogent.admin.dto.admin.AdminResponseUtils.getDeletedAdminInfo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 2019-09-19
 */
public class ForgotPasswordServiceTest {

    @InjectMocks
    private ForgotPasswordServiceImpl forgotPasswordService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ForgotPasswordRepository verificationRepository;

    @Mock
    private ExpirationTimeProperties expirationTimeProperties;

    @Mock
    private EmailService emailService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void forgotPasswordTest() {
        Should_Throw_Exception_When_Admin_Not_Found();
        Should_Throw_Exception_When_Admin_Is_Not_Active();
        Should_Save_ForgotPasswordVerification();
    }

    @Test
    public void validateResetCode() {
        Should_Throw_Exception_When_ResetCode_Is_Invalid();
        Should_Throw_Exception_When_ResetCode_Is_Expired();
    }

    @Test
    public void updatePasswordTest() {
        Should_Throw_Exception_When_AdminNotFound();
        Should_Update_Password();
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_Throw_Exception_When_Admin_Not_Found() {

        String username = getUsernameOrEmail();
        given(adminRepository.fetchAdminByUsernameOrEmail(username)).willThrow(NoContentFoundException.class);

        forgotPasswordService.forgotPassword(username);
    }

    @Test
    public void Should_Throw_Exception_When_Admin_Is_Not_Active() {

        String username = getUsernameOrEmail();

        given(adminRepository.fetchAdminByUsernameOrEmail(username)).willReturn(getDeletedAdminInfo());

        thrown.expect(NoContentFoundException.class);

        forgotPasswordService.forgotPassword(username);
    }

    @Test
    public void Should_Save_ForgotPasswordVerification() {
        String username = getUsernameOrEmail();
        Admin admin = getAdmin();
        given(adminRepository.fetchAdminByUsernameOrEmail(username)).willReturn(admin);

        given(verificationRepository.findByAdminId(admin.getId())).willReturn(getForgotPasswordVerification());

        given(verificationRepository.save(ArgumentMatchers.any(ForgotPasswordVerification.class)))
                .willReturn(getForgotPasswordVerification());

        forgotPasswordService.forgotPassword(username);

        verify(verificationRepository, times(1))
                .save(ArgumentMatchers.any(ForgotPasswordVerification.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_Throw_Exception_When_ResetCode_Is_Invalid() {
        String resetCode = getResetCode();

        given(verificationRepository.findByResetCode(resetCode)).willThrow(NoContentFoundException.class);

        forgotPasswordService.verify(resetCode);
    }

    @Test
    public void Should_Throw_Exception_When_ResetCode_Is_Expired() {
        String resetCode = getResetCode();

        given(verificationRepository.findByResetCode(resetCode)).willReturn(fetchExpiredTime());

        thrown.expect(BadRequestException.class);

        forgotPasswordService.verify(resetCode);
    }

    @Test(expected = NoContentFoundException.class)
    public void Should_Throw_Exception_When_AdminNotFound() {
        ForgotPasswordRequestDTO requestDTO = getForgotPasswordRequestDTO();

        given(adminRepository.fetchAdminByUsernameOrEmail(requestDTO.getUsername()))
                .willThrow(NoContentFoundException.class);

        forgotPasswordService.updatePassword(requestDTO);
    }

    @Test
    public void Should_Update_Password() {
        ForgotPasswordRequestDTO requestDTO = getForgotPasswordRequestDTO();
        Admin admin = getAdmin();

        given(adminRepository.fetchAdminByUsernameOrEmail(requestDTO.getUsername()))
                .willReturn(admin);
        given(verificationRepository.findByAdminId(admin.getId()))
                .willReturn(getForgotPasswordVerification());

        forgotPasswordService.updatePassword(requestDTO);
        verify(adminRepository, times(1)).save(any(Admin.class));
        verify(verificationRepository,times(1)).save(any(ForgotPasswordVerification.class));
    }
}
