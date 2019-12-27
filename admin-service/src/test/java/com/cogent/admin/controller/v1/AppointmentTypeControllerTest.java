package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeUpdateRequestDTO;
import com.cogent.admin.service.AppointmentTypeService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.AppointmentTypeConstants.BASE_APPOINTMENT_TYPE;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.appointmentType.AppointmentTypeRequestUtils.*;
import static com.cogent.admin.dto.appointmentType.AppointmentTypeResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author smriti on 2019-09-26
 */
public class AppointmentTypeControllerTest {

    @InjectMocks
    private AppointmentTypeController appointmentTypeController;

    @Mock
    private AppointmentTypeService appointmentTypeService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentTypeController).build();
        BASE_URL = API_V1 + BASE_APPOINTMENT_TYPE;
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

        doNothing().when(appointmentTypeService).save(any(AppointmentTypeRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(APPLICATION_JSON)
                .content(writeObjectToJson(getAppointmentTypeRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(appointmentTypeService, times(1))
                .save(any(AppointmentTypeRequestDTO.class));
        verifyNoMoreInteractions(appointmentTypeService);
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

        doNothing().when(appointmentTypeService).update(any(AppointmentTypeUpdateRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .content(writeObjectToJson(getAppointmentTypeUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(appointmentTypeService, times(1))
                .update(any(AppointmentTypeUpdateRequestDTO.class));
        verifyNoMoreInteractions(appointmentTypeService);
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

        doNothing().when(appointmentTypeService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(appointmentTypeService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(appointmentTypeService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(appointmentTypeService.search(any(AppointmentTypeSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchAppointmentTypeMinimalResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(APPLICATION_JSON)
                .content(writeObjectToJson(getAppointmentTypeSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchAppointmentTypeMinimalResponseDTO().size())))
                .andReturn();

        verify(appointmentTypeService, times(1)).search
                (any(AppointmentTypeSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(appointmentTypeService);
    }

    @Test
    public void fetchAppointmentTypeForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(appointmentTypeService.fetchAppointmentTypeForDropdown())
                .willReturn(fetchAppointmentTypeForDropDown());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchAppointmentTypeForDropDown().size())))
                .andReturn();

        verify(appointmentTypeService, times(1)).fetchAppointmentTypeForDropdown();
        verifyNoMoreInteractions(appointmentTypeService);
    }

    @Test
    public void fetchAppointmentTypeDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(appointmentTypeService.fetchDetailsById(id)).willReturn(fetchAppointmentTypeResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, id)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(appointmentTypeService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(appointmentTypeService);
    }
}
