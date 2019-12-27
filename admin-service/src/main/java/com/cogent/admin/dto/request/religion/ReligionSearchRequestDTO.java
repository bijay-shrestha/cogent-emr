package com.cogent.admin.dto.request.religion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReligionSearchRequestDTO implements Serializable {

    private String name;

    private Character status;

}
