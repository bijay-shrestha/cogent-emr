package com.cogent.admin.dto.response.surname;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 2019-09-12
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SurnameResponseDTO implements Serializable {

    private String name;

    private Character status;

    private String remarks;

    private String ethnicityName;
}
