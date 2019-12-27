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
public class SurnameDropdownDTO implements Serializable {

    private Long value;

    private String label;
}
