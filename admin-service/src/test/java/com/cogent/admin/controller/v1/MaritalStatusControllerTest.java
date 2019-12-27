package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.CommonRequestUtils;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import com.cogent.admin.dto.request.maritalStatus.MaritalStatusUpdateRequestDTO;
import com.cogent.admin.service.MaritalStatusService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.MaritalStatusConstants.BASE_MARITAL_STATUS;
import static com.cogent.admin.dto.maritalStatus.MaritalStatusResponseUtils.*;
import static com.cogent.admin.dto.maritalStatus.MaritalStatusTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MaritalStatusControllerTest {

    @InjectMocks
    private MaritalStatusController maritalStatusController;

    @Mock
    private MaritalStatusService maritalStatusService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(maritalStatusController).build();
        BASE_URL = API_V1 + BASE_MARITAL_STATUS;

    }

    @Test
    public void saveTest() throws Exception {
        Should_Throw_Exception_When_InputIsValidForSave();
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
    public void Should_Throw_Exception_When_InputIsValidForSave() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForSave())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();


    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(maritalStatusService).save(any(MaritalStatusRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getMaritalStatusRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(maritalStatusService, Mockito.times(1)).save(any(MaritalStatusRequestDTO.class));
        verifyNoMoreInteractions(maritalStatusService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForUpdate() throws Exception {

        String URL = BASE_URL;

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForUpdate())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        doNothing().when(maritalStatusService).update(any(MaritalStatusUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getMaritalStatusUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(maritalStatusService, times(1)).update(any(MaritalStatusUpdateRequestDTO.class));
        verifyNoMoreInteractions(maritalStatusService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForDelete() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForUpdate())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        doNothing().when(maritalStatusService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(CommonRequestUtils.getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(maritalStatusService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(maritalStatusService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(maritalStatusService.search(any(MaritalStatusSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchMaritalStatusResponseDTO()));

        mockMvc.perform(put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getMaritalStatusSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchMaritalStatusResponseDTO().size())))
                .andReturn();


        verify(maritalStatusService, times(1)).search
                (any(MaritalStatusSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(maritalStatusService);
    }

    @Test
    public void fetchMaritalStatusForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(maritalStatusService.fetchMaritalStatusForDropDown())
                .willReturn(fetchMaritalStatusForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchMaritalStatusForDropDown().size())))
                .andReturn();

        verify(maritalStatusService, times(1)).fetchMaritalStatusForDropDown();
        verifyNoMoreInteractions(maritalStatusService);
    }

    @Test
    public void fetchEthnicity() throws Exception {
        String URL = BASE_URL+ ID_PATH_VARIABLE_BASE;

        given(maritalStatusService.fetchMaritalStatus(1L))
                .willReturn(getMaritalStatus());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(maritalStatusService).fetchMaritalStatus(1L);
    }

}
