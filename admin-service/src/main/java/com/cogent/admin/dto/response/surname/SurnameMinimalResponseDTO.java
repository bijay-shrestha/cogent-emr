package com.cogent.admin.dto.response.surname;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 2019-10-16
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SurnameMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String ethnicityName;

    private Character status;

    private int totalItems;
}
