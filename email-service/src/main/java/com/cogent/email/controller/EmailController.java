package com.cogent.email.controller;

import com.cogent.email.dto.request.email.EmailRequestDTO;
import com.cogent.email.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.email.constants.SwaggerConstants.EmailConstant.BASE_API_VALUE;
import static com.cogent.email.constants.SwaggerConstants.EmailConstant.SEND_EMAIL_OPERATION;
import static com.cogent.email.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.email.constants.WebResourceKeyConstants.EmailService.SEND_EMAIL;

@RestController
@RequestMapping(API_V1)
@Api(BASE_API_VALUE)
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(SEND_EMAIL)
    @ApiOperation(SEND_EMAIL_OPERATION)
    public void sendEmail(@RequestBody EmailRequestDTO requestDTO) {
        emailService.sendEmail(requestDTO);
    }

}
