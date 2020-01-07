package com.cogent.admin.feign.service;

import com.cogent.admin.feign.dto.request.patient.PatientRequestDTO;
import com.cogent.admin.feign.dto.response.patient.PatientResponseDTO;

/**
 * @author Sauravi Thapa 9/29/19
 */
public interface PatientService {
    PatientResponseDTO getPatientDetails(PatientRequestDTO requestDTO);
}
