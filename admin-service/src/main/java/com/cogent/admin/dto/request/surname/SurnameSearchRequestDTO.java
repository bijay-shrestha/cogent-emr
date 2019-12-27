package com.cogent.admin.dto.request.surname;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author smriti on 2019-09-12
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurnameSearchRequestDTO implements Serializable {

    private String name;

    private Character status;

    private Long ethnicityId;
}
