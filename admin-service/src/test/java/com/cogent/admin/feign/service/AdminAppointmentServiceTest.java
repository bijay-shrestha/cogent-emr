package com.cogent.admin.feign.service;

import com.cogent.admin.feign.dto.request.appointment.AdminAppointmentRequestDTO;
import com.cogent.admin.feign.service.impl.AdminAppointmentServiceImpl;
import com.cogent.admin.service.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.cogent.admin.dto.appointmentMode.AppointmentModeResponseUtils.getAppointmentMode;
import static com.cogent.admin.dto.appointmentType.AppointmentTypeResponseUtils.getAppointmentType;
import static com.cogent.admin.dto.billType.BillTypeResponseUtils.getBillType;
import static com.cogent.admin.dto.doctor.DoctorResponseUtils.getDoctor;
import static com.cogent.admin.dto.request.patientType.PatientTypeResponseUtils.getPatientType;
import static com.cogent.admin.feign.dto.request.appointment.AdminAppointmentRequestUtils.getAdminAppointmentRequestDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.BDDMockito.given;

/**
 * @author smriti on 2019-10-26
 */
public class AdminAppointmentServiceTest {

    @InjectMocks
    private AdminAppointmentServiceImpl appointmentService;

    @Mock
    private PatientTypeService patientTypeService;

    @Mock
    private AppointmentTypeService appointmentTypeService;

    @Mock
    private AppointmentModeService appointmentModeService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private BillTypeService billTypeService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchAdminDetails() {
        AdminAppointmentRequestDTO requestDTO = getAdminAppointmentRequestDTO();

        given(appointmentTypeService.fetchAppointmentTypeById(requestDTO.getAppointmentTypeId()))
                .willReturn(getAppointmentType());

        given(patientTypeService.fetchPatientTypeById(requestDTO.getPatientTypeId()))
                .willReturn(getPatientType());

        given(doctorService.fetchDoctorById(requestDTO.getDoctorId()))
                .willReturn(getDoctor());

        given(appointmentModeService.fetchAppointmentModeById(requestDTO.getAppointmentModeId()))
                .willReturn(getAppointmentMode());

        given(billTypeService.fetchBillTypeById(requestDTO.getBillTypeId()))
                .willReturn(getBillType());

        assertThat(appointmentService.fetchAdminDetails(requestDTO), hasProperty("appointmentType"));
    }

}
