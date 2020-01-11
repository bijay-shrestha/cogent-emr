package com.cogent.admin.utils;

import com.cogent.admin.feign.dto.request.email.EmailRequestDTO;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.ForgotPasswordVerification;

import java.util.Date;

import static com.cogent.admin.constants.EmailConstants.SUBJECT_FOR_FORGOT_PASSWORD;
import static com.cogent.admin.constants.EmailTemplates.FORGOT_PASSWORD;
import static com.cogent.admin.constants.StatusConstants.ACTIVE;
import static com.cogent.admin.constants.StringConstant.COMMA_SEPARATED;

/**
 * @author smriti on 2019-09-20
 */
public class ForgotPasswordUtils {

    public static ForgotPasswordVerification convertToForgotPasswordVerification(
            Admin admin,
            int expirationTime,
            ForgotPasswordVerification verification) {

        verification.setResetCode(String.valueOf(RandomNumberGenerator.generateRandomNumber(7)));
        verification.setExpirationDate(calculateExpirationDate(expirationTime));
        verification.setAdmin(admin);
        verification.setStatus(ACTIVE);
        return verification;
    }

    public static EmailRequestDTO parseToEmailRequestDTO(String emailAddress,
                                                         String username,
                                                         String resetCode) {
        return EmailRequestDTO.builder()
                .receiverEmailAddress(emailAddress)
                .subject(SUBJECT_FOR_FORGOT_PASSWORD)
                .templateName(FORGOT_PASSWORD)
                .paramValue(username + COMMA_SEPARATED + resetCode)
                .build();
    }

    private static Date calculateExpirationDate(int expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }
}
