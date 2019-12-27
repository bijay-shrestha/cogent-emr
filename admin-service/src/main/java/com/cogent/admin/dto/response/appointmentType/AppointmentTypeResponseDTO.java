package com.cogent.admin.dto.response.appointmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author smriti on 2019-09-26
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentTypeResponseDTO implements Serializable {
    private String remarks;
}
