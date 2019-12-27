package com.cogent.admin.service;

import com.cogent.admin.dto.request.forgotPassword.ForgotPasswordRequestDTO;

/**
 * @author smriti on 2019-09-20
 */
public interface ForgotPasswordService {
    void forgotPassword(String username);

    void verify(String resetCode);

    void updatePassword(ForgotPasswordRequestDTO requestDTO);
}
