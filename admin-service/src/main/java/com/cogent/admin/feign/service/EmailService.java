package com.cogent.admin.feign.service;

import com.cogent.admin.feign.dto.request.email.EmailRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.cogent.admin.constants.MicroServiceConstants.API_V1;
import static com.cogent.admin.constants.MicroServiceConstants.EmailMicroService.BASE_NAME;
import static com.cogent.admin.constants.MicroServiceConstants.EmailMicroService.SEND_EMAIL;

/**
 * @author smriti on 2019-08-19
 */
@FeignClient(BASE_NAME)
@RequestMapping(API_V1)
public interface EmailService {

    @RequestMapping(SEND_EMAIL)
    void sendEmail(@RequestBody EmailRequestDTO emailRequestDTO);
}
