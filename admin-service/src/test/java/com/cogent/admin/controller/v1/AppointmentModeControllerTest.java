package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeUpdateRequestDTO;
import com.cogent.admin.service.AppointmentModeService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.AppointmentModeConstants.BASE_APPOINTMENT_MODE;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.appointmentMode.AppointmentModeRequestUtils.*;
import static com.cogent.admin.dto.appointmentMode.AppointmentModeResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author smriti on 2019-10-10
 */
public class AppointmentModeControllerTest {

    @InjectMocks
    private AppointmentModeController appointmentModeController;

    @Mock
    private AppointmentModeService appointmentModeService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentModeController).build();
        BASE_URL = API_V1 + BASE_APPOINTMENT_MODE;
    }

    @Test
    public void saveTest() throws Exception {
        Should_Throw_Exception_When_InputIsInvalidForSave();
        save();
    }

    @Test
    public void updateTest() throws Exception {
        Should_Throw_Exception_When_InputIsInvalidForUpdate();
        update();
    }

    @Test
    public void deleteTest() throws Exception {
        Should_Throw_Exception_When_InputIsInvalidForDelete();
        delete();
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForSave() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(post(URL)
                .contentType(APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForSave())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(appointmentModeService).save(any(AppointmentModeRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(APPLICATION_JSON)
                .content(writeObjectToJson(getAppointmentModeRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(appointmentModeService, times(1))
                .save(any(AppointmentModeRequestDTO.class));
        verifyNoMoreInteractions(appointmentModeService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForUpdate() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForUpdate())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        doNothing().when(appointmentModeService).update(any(AppointmentModeUpdateRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .content(writeObjectToJson(getAppointmentModeUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(appointmentModeService, times(1))
                .update(any(AppointmentModeUpdateRequestDTO.class));
        verifyNoMoreInteractions(appointmentModeService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForDelete() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForDelete())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        doNothing().when(appointmentModeService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(appointmentModeService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(appointmentModeService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(appointmentModeService.search(any(AppointmentModeSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchAppointmentModeMinimalResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(APPLICATION_JSON)
                .content(writeObjectToJson(getAppointmentModeSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchAppointmentModeMinimalResponseDTO().size())))
                .andReturn();

        verify(appointmentModeService, times(1)).search
                (any(AppointmentModeSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(appointmentModeService);
    }

    @Test
    public void fetchAppointmentModeForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(appointmentModeService.fetchAppointmentModeForDropdown())
                .willReturn(fetchAppointmentModeForDropDown());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchAppointmentModeForDropDown().size())))
                .andReturn();

        verify(appointmentModeService, times(1)).fetchAppointmentModeForDropdown();
        verifyNoMoreInteractions(appointmentModeService);
    }

    @Test
    public void fetchAppointmentModeDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(appointmentModeService.fetchDetailsById(id)).willReturn(fetchAppointmentModeResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, id)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(appointmentModeService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(appointmentModeService);
    }
}
