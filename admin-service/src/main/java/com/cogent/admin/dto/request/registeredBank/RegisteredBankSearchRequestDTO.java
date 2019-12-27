package com.cogent.admin.dto.request.registeredBank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBankSearchRequestDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private String contact;

    private String address;

    private String swiftCode;

    private Character status;
}
