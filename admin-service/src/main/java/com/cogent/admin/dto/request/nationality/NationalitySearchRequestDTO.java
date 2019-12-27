package com.cogent.admin.dto.request.nationality;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NationalitySearchRequestDTO implements Serializable {

    private Long id;

    private String name;

    private Character status;
}
