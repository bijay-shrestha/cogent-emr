package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationSearchRequestDTO;
import com.cogent.admin.dto.request.specialization.SpecializationUpdateRequestDTO;
import com.cogent.admin.service.SpecializationService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.DoctorConstants.DOCTOR_ID_PATH_VARIABLE_BASE;
import static com.cogent.admin.constants.WebResourceKeyConstants.SpecializationConstants.BASE_SPECIALIZATION;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.doctor.DoctorResponseUtils.fetchDoctorForDropDown;
import static com.cogent.admin.dto.specialization.SpecializationRequestUtils.*;
import static com.cogent.admin.dto.specialization.SpecializationResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author smriti on 2019-09-25
 */
public class SpecializationControllerTest {
    @InjectMocks
    private SpecializationController specializationController;

    @Mock
    private SpecializationService specializationService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(specializationController).build();
        BASE_URL = API_V1 + BASE_SPECIALIZATION;
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

        doNothing().when(specializationService).save(any(SpecializationRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getSpecializationRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(specializationService, times(1))
                .save(any(SpecializationRequestDTO.class));
        verifyNoMoreInteractions(specializationService);
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

        doNothing().when(specializationService).update(any(SpecializationUpdateRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .content(writeObjectToJson(getSpecializationUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(specializationService, times(1))
                .update(any(SpecializationUpdateRequestDTO.class));
        verifyNoMoreInteractions(specializationService);
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

        doNothing().when(specializationService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(specializationService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(specializationService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(specializationService.search(any(SpecializationSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchSpecializationMinimalResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getSpecializationSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchSpecializationMinimalResponseDTO().size())))
                .andReturn();

        verify(specializationService, times(1)).search
                (any(SpecializationSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(specializationService);
    }

    @Test
    public void fetchSpecializationForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(specializationService.fetchActiveSpecializationForDropDown())
                .willReturn(fetchSpecializationForDropDown());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchSpecializationForDropDown().size())))
                .andReturn();

        verify(specializationService, times(1)).fetchActiveSpecializationForDropDown();
        verifyNoMoreInteractions(specializationService);
    }

    @Test
    public void fetchSpecializationDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(specializationService.fetchDetailsById(id)).willReturn(fetchSpecializationResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(specializationService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(specializationService);
    }

    @Test
    public void fetchSpecializationByDoctorId() throws Exception {
        String URL = BASE_URL + DOCTOR_ID_PATH_VARIABLE_BASE;
        Long DoctorId = 1L;

        given(specializationService.fetchSpecializationByDoctorId(DoctorId))
                .willReturn(fetchSpecializationForDropDown());

        mockMvc.perform(get(URL, DoctorId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchDoctorForDropDown().size())))
                .andReturn();

        verify(specializationService, times(1)).fetchSpecializationByDoctorId(DoctorId);
        verifyNoMoreInteractions(specializationService);
    }
}
