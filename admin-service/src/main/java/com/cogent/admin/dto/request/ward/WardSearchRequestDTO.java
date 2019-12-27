package com.cogent.admin.dto.request.ward;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WardSearchRequestDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;
}
