package com.cogent.admin.dto.response.appointmentMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author smriti on 2019-10-10
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentModeResponseDTO implements Serializable {
    private String remarks;
}
