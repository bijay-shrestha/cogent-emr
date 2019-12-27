package com.cogent.admin.dto.request.ethnicity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EthnicitySearchRequestDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;
}
