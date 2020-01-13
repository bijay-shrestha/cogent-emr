package com.cogent.email.service;

import com.cogent.email.dto.request.email.EmailRequestDTO;

public interface EmailService {

    void sendEmail(EmailRequestDTO emailRequestDTO);

}
