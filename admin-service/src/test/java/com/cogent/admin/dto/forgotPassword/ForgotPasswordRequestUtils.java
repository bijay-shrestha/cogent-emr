package com.cogent.admin.dto.forgotPassword;

import com.cogent.admin.dto.request.forgotPassword.ForgotPasswordRequestDTO;

/**
 * @author smriti on 2019-09-19
 */
public class ForgotPasswordRequestUtils {

    public static String getUsernameOrEmail(){
        return "cogent";
    }

    public static String getResetCode(){
        return "1234567";
    }

    public static ForgotPasswordRequestDTO getForgotPasswordRequestDTO(){
       ForgotPasswordRequestDTO requestDTO = new ForgotPasswordRequestDTO();
       requestDTO.setPassword("cogent");
       requestDTO.setUsername("cogent");
       return requestDTO;
    }

}
