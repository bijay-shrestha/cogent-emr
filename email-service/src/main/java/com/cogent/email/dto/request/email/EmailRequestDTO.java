package com.cogent.email.dto.request.email;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author smriti on 2019-08-26
 */
@Getter
@Setter
public class EmailRequestDTO implements Serializable {

    private String subject;

    private String receiverEmailAddress;

    private String templateName;

    private String paramValue;
}
