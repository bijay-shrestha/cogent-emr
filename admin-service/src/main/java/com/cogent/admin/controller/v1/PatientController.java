package com.cogent.admin.controller.v1;


import com.cogent.admin.dto.request.patient.PatientRequestDTO;
import com.cogent.admin.dto.response.patient.ResponseDTO;
import com.cogent.admin.service.PatientService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.DETAILS;
import static com.cogent.admin.constants.WebResourceKeyConstants.PatientConstants.BASE_PATIENT;

/**
 * @author Sauravi Thapa 9/23/19
 */

@RestController
@RequestMapping(API_V1 + BASE_PATIENT)
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PutMapping(DETAILS)
    public ResponseDTO getPatientDetails(@RequestBody PatientRequestDTO requestDTO) {
        return patientService.getPatientDetails(requestDTO);
    }

}
