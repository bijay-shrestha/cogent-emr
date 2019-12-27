package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeSearchRequestDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeUpdateRequestDTO;
import com.cogent.admin.service.PatientTypeService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.PatientTypeConstants.BASE_PATIENT_TYPE;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.request.patientType.PatientTypeRequestUtils.*;
import static com.cogent.admin.dto.request.patientType.PatientTypeResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author smriti on 2019-09-26
 */
public class PatientTypeControllerTest {

    @InjectMocks
    private PatientTypeController patientTypeController;

    @Mock
    private PatientTypeService patientTypeService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientTypeController).build();
        BASE_URL = API_V1 + BASE_PATIENT_TYPE;
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
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForSave())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(patientTypeService).save(any(PatientTypeRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getPatientTypeRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(patientTypeService, times(1))
                .save(any(PatientTypeRequestDTO.class));
        verifyNoMoreInteractions(patientTypeService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForUpdate() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForUpdate())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        doNothing().when(patientTypeService).update(any(PatientTypeUpdateRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .content(writeObjectToJson(getPatientTypeUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(patientTypeService, times(1))
                .update(any(PatientTypeUpdateRequestDTO.class));
        verifyNoMoreInteractions(patientTypeService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForDelete() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForDelete())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        doNothing().when(patientTypeService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(patientTypeService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(patientTypeService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(patientTypeService.search(any(PatientTypeSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchPatientTypeMinimalResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getPatientTypeSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchPatientTypeMinimalResponseDTO().size())))
                .andReturn();

        verify(patientTypeService, times(1)).search
                (any(PatientTypeSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(patientTypeService);
    }

    @Test
    public void fetchPatientTypeForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(patientTypeService.fetchActivePatientTypeForDropdown())
                .willReturn(fetchPatientTypeForDropDown());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchPatientTypeForDropDown().size())))
                .andReturn();

        verify(patientTypeService, times(1)).fetchActivePatientTypeForDropdown();
        verifyNoMoreInteractions(patientTypeService);
    }

    @Test
    public void fetchPatientTypeDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(patientTypeService.fetchDetailsById(id)).willReturn(fetchPatientTypeResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(patientTypeService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(patientTypeService);
    }
}
