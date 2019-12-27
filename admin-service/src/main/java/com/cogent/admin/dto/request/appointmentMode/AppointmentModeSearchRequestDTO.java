package com.cogent.admin.dto.request.appointmentMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author smriti on 2019-10-10
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentModeSearchRequestDTO implements Serializable {

    private String name;

    private Character status;
}
