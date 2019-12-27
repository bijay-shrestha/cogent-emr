package com.cogent.admin.controller.v1;

import com.cogent.admin.service.DoctorTypeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.DoctorTypeConstants.BASE_DOCTOR_TYPE;
import static com.cogent.admin.dto.doctorType.DoctorTypeResponseUtils.fetchActiveDoctorTypes;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author smriti on 08/11/2019
 */
public class DoctorTypeControllerTest {

    @InjectMocks
    private DoctorTypeController doctorTypeController;

    @Mock
    private DoctorTypeService doctorTypeService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorTypeController).build();
        BASE_URL = API_V1 + BASE_DOCTOR_TYPE;
    }

    @Test
    public void fetchActiveDoctorType() throws Exception {
        String URL = BASE_URL;

        given(doctorTypeService.fetchActiveDoctorType()).willReturn(fetchActiveDoctorTypes());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(doctorTypeService, times(1)).fetchActiveDoctorType();
        verifyNoMoreInteractions(doctorTypeService);
    }
}
