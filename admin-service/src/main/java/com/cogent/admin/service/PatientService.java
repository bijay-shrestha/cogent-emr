package com.cogent.admin.service;

import com.cogent.admin.dto.request.patient.PatientRequestDTO;
import com.cogent.admin.dto.response.patient.ResponseDTO;

/**
 * @author Sauravi Thapa 9/29/19
 */
public interface PatientService {
    ResponseDTO getPatientDetails(PatientRequestDTO requestDTO);
}
