package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterSearchRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterUpdateRequestDTO;
import com.cogent.admin.service.DoctorDutyRosterService;
import com.cogent.admin.utils.ObjectToJSONUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.DoctorDutyRosterConstants.BASE_DOCTOR_DUTY_ROSTER;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.doctorDutyRoster.DoctorDutyRosterRequestUtils.*;
import static com.cogent.admin.dto.doctorDutyRoster.DoctorDutyRosterResponseUtils.fetchDoctorDutyRosterDetailResponseDTO;
import static com.cogent.admin.dto.doctorDutyRoster.DoctorDutyRosterResponseUtils.fetchDoctorDutyRosterMinimalResponseDTO;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author smriti on 26/11/2019
 */
public class DoctorDutyRosterControllerTest {

    @InjectMocks
    private DoctorDutyRosterController doctorDutyRosterController;

    @Mock
    private DoctorDutyRosterService doctorDutyRosterService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorDutyRosterController).build();
        BASE_URL = API_V1 + BASE_DOCTOR_DUTY_ROSTER;
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getDoctorDutyRosterRequestDTO())))
                .andExpect(status().isCreated()).andReturn();

        verify(doctorDutyRosterService, times(1))
                .save(any(DoctorDutyRosterRequestDTO.class));

        verifyNoMoreInteractions(doctorDutyRosterService);
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        DoctorDutyRosterUpdateRequestDTO updateRequestDTO = getDoctorDutyRosterUpdateRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToJSONUtils.writeObjectToJson(updateRequestDTO)))
                .andExpect(status().isOk());

        verify(doctorDutyRosterService, times(1))
                .update(any(DoctorDutyRosterUpdateRequestDTO.class));
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        verify(doctorDutyRosterService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(doctorDutyRosterService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(doctorDutyRosterService.search(any(DoctorDutyRosterSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchDoctorDutyRosterMinimalResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getDoctorDutyRosterSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.hasSize(fetchDoctorDutyRosterMinimalResponseDTO().size())))
                .andReturn();

        verify(doctorDutyRosterService, times(1)).search
                (any(DoctorDutyRosterSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(doctorDutyRosterService);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(doctorDutyRosterService.fetchDetailsById(id))
                .willReturn(fetchDoctorDutyRosterDetailResponseDTO());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(doctorDutyRosterService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(doctorDutyRosterService);
    }
}
