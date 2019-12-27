package com.cogent.admin.dto.response.registeredBank;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RegisteredBankResponseDTO implements Serializable {

    private String name;

    private String code;

    private String contact;

    private String address;

    private String swiftCode;

    private Character status;

    private String remarks;
}

