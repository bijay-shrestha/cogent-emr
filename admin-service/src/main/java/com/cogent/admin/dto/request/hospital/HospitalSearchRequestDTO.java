package com.cogent.admin.dto.request.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalSearchRequestDTO implements Serializable {

    private String name;

    private Character status;


}
