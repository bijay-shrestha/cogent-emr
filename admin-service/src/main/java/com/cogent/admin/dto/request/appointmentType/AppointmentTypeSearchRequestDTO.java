package com.cogent.admin.dto.request.appointmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author smriti on 2019-09-26
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentTypeSearchRequestDTO implements Serializable {

    private String name;

    private Character status;
}
