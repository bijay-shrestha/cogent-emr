package com.cogent.admin.dto.request.referrer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferrerSearchRequestDTO implements Serializable {

    private String name;
    private Character status;
}
