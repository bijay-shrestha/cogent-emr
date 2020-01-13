package com.cogent.admin.dto.forgotPassword;

import com.cogent.admin.utils.RandomNumberGenerator;
import com.cogent.persistence.model.ForgotPasswordVerification;
import org.joda.time.DateTime;

import java.util.Date;

import static com.cogent.admin.dto.admin.AdminResponseUtils.getAdmin;

/**
 * @author smriti on 2019-09-19
 */
public class ForgotPasswordResponseUtils {

    public static ForgotPasswordVerification getForgotPasswordVerification() {
        ForgotPasswordVerification verification = new ForgotPasswordVerification();
        verification.setId(1L);
        verification.setAdmin(getAdmin());
        verification.setStatus('Y');
        verification.setResetCode(RandomNumberGenerator.generateRandomNumber(7));
        verification.setExpirationDate(new Date(new Date().getTime() + 86400000));
        return verification;
    }

    public static Date fetchExpiredTime() {
        return new DateTime(new Date()).minusDays(1).toDate();
    }
}
