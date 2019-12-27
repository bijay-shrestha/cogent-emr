package com.cogent.admin.feign.service.impl;

import com.cogent.admin.feign.dto.request.appointment.AdminAppointmentRequestDTO;
import com.cogent.admin.feign.dto.response.appointment.AdminAppointmentResponseDTO;
import com.cogent.admin.feign.service.AdminAppointmentService;
import com.cogent.admin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cogent.admin.feign.utils.AdminAppointmentUtils.parseToAppointmentResponseDTO;
import static com.cogent.admin.log.CommonLogConstant.FETCHING_DETAIL_PROCESS_COMPLETED;
import static com.cogent.admin.log.CommonLogConstant.FETCHING_DETAIL_PROCESS_STARTED;
import static com.cogent.admin.log.constants.AdminLog.ADMIN;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author smriti on 2019-10-23
 */
@Service
@Transactional
@Slf4j
public class AdminAppointmentServiceImpl implements AdminAppointmentService {

    private final PatientTypeService patientTypeService;

    private final AppointmentTypeService appointmentTypeService;

    private final AppointmentModeService appointmentModeService;

    private final DoctorService doctorService;

    private final BillTypeService billTypeService;

    private final SpecializationService specializationService;

    public AdminAppointmentServiceImpl(PatientTypeService patientTypeService,
                                       AppointmentTypeService appointmentTypeService,
                                       AppointmentModeService appointmentModeService,
                                       DoctorService doctorService,
                                       BillTypeService billTypeService,
                                       SpecializationService specializationService) {
        this.patientTypeService = patientTypeService;
        this.appointmentTypeService = appointmentTypeService;
        this.appointmentModeService = appointmentModeService;
        this.doctorService = doctorService;
        this.billTypeService = billTypeService;
        this.specializationService = specializationService;
    }

    @Override
    public AdminAppointmentResponseDTO fetchAdminDetails(AdminAppointmentRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, ADMIN);

        AdminAppointmentResponseDTO responseDTO = parseToAppointmentResponseDTO(
                appointmentTypeService.fetchAppointmentTypeById(requestDTO.getAppointmentTypeId()),
                appointmentModeService.fetchAppointmentModeById(requestDTO.getAppointmentModeId()),
                patientTypeService.fetchPatientTypeById(requestDTO.getPatientTypeId()),
                doctorService.fetchDoctorById(requestDTO.getDoctorId()),
                specializationService.fetchActiveSpecializationById(requestDTO.getSpecializationId()),
                billTypeService.fetchBillTypeById(requestDTO.getBillTypeId()));

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, ADMIN, getDifferenceBetweenTwoTime(startTime));
        return responseDTO;
    }
}
