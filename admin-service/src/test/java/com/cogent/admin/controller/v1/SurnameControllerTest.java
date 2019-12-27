package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameUpdateRequestDTO;
import com.cogent.admin.service.SurnameService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.SurnameConstants.BASE_SURNAME;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.surname.SurnameRequestUtils.*;
import static com.cogent.admin.dto.surname.SurnameResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author smriti on 2019-09-12
 */
public class SurnameControllerTest {

    @InjectMocks
    private SurnameController surnameController;

    @Mock
    private SurnameService surnameService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(surnameController).build();
        BASE_URL = API_V1 + BASE_SURNAME;
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

        doNothing().when(surnameService).save(any(SurnameRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getSurnameRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(surnameService, times(1)).save(any(SurnameRequestDTO.class));
        verifyNoMoreInteractions(surnameService);
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

        doNothing().when(surnameService).update(any(SurnameUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getSurnameUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(surnameService, times(1)).update(any(SurnameUpdateRequestDTO.class));
        verifyNoMoreInteractions(surnameService);
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

        doNothing().when(surnameService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(surnameService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(surnameService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(surnameService.search(any(SurnameSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchSurnameMinimalResponseDTO()));

        mockMvc.perform(put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getSurnameSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchSurnameMinimalResponseDTO().size())))
                .andReturn();

        verify(surnameService, times(1)).search
                (any(SurnameSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(surnameService);
    }

    @Test
    public void fetchSurnameForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(surnameService.fetchActiveSurnameForDropDown())
                .willReturn(fetchSurnameForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchSurnameForDropDown().size())))
                .andReturn();

        verify(surnameService, times(1)).fetchActiveSurnameForDropDown();
        verifyNoMoreInteractions(surnameService);
    }

    @Test
    public void fetchEthnicity() throws Exception {
        String URL = BASE_URL + ID_PATH_VARIABLE_BASE;

        given(surnameService.fetchSurname(1L))
                .willReturn(getSurname());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(surnameService).fetchSurname(1L);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(surnameService.fetchSurnameDetails(id)).willReturn(fetchSurnameResponseDTO());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(surnameService, times(1)).fetchSurnameDetails(id);
        verifyNoMoreInteractions(surnameService);
    }
}
