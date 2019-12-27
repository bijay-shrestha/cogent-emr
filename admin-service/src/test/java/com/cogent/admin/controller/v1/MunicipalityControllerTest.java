package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityUpdateRequestDTO;
import com.cogent.admin.service.MunicipalityService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.MunicipalityConstants.BASE_MUNICIPALITY;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.municipality.MunicipalityResponseUtils.*;
import static com.cogent.admin.dto.municipality.MunicipalityTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author smriti on 2019-09-15
 */
public class MunicipalityControllerTest {

    @InjectMocks
    private MunicipalityController municipalityController;

    @Mock
    private MunicipalityService municipalityService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(municipalityController).build();
        BASE_URL = API_V1 + BASE_MUNICIPALITY;
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

        doNothing().when(municipalityService).save(any(MunicipalityRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getMunicipalityRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(municipalityService, times(1)).save(any(MunicipalityRequestDTO.class));
        verifyNoMoreInteractions(municipalityService);
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

        doNothing().when(municipalityService).update(any(MunicipalityUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getMunicipalityUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(municipalityService, times(1)).update(any(MunicipalityUpdateRequestDTO.class));
        verifyNoMoreInteractions(municipalityService);
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

        doNothing().when(municipalityService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(municipalityService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(municipalityService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(municipalityService.search(any(MunicipalitySearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchMunicipalityMinimalResponseDTO()));

        mockMvc.perform(put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getMunicipalitySearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchMunicipalityMinimalResponseDTO().size())))
                .andReturn();

        verify(municipalityService, times(1)).search
                (any(MunicipalitySearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(municipalityService);
    }

    @Test
    public void fetchMunicipalityForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(municipalityService.fetchActiveMunicipalityForDropDown())
                .willReturn(fetchMunicipalityForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchMunicipalityForDropDown().size())))
                .andReturn();

        verify(municipalityService, times(1)).fetchActiveMunicipalityForDropDown();
        verifyNoMoreInteractions(municipalityService);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(municipalityService.fetchDetailsById(id)).willReturn(fetchMunicipalityResponseDTO());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(municipalityService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(municipalityService);
    }
}
