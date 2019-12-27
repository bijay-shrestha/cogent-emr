package com.cogent.admin.feign.controller;

import com.cogent.admin.feign.dto.request.appointment.AdminAppointmentRequestDTO;
import com.cogent.admin.feign.service.AdminAppointmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.MicroServiceConstants.AppointmentMicroService.FETCH_ADMIN_DETAILS;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.feign.dto.request.appointment.AdminAppointmentRequestUtils.getAdminAppointmentRequestDTO;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * @author smriti on 2019-10-26
 */
public class AppointmentControllerTest {

    @InjectMocks
    private AppointmentController appointmentController;

    @Mock
    private AdminAppointmentService adminAppointmentService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
        BASE_URL = API_V1;
    }

    @Test
    public void fetchAdminDetails() throws Exception {
        String URL = BASE_URL + FETCH_ADMIN_DETAILS;

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getAdminAppointmentRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(adminAppointmentService, times(1)).fetchAdminDetails(any(AdminAppointmentRequestDTO.class));
        verifyNoMoreInteractions(adminAppointmentService);
    }
}
