package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.religion.ReligionRequestUtils;
import com.cogent.admin.dto.request.religion.ReligionRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionSearchRequestDTO;
import com.cogent.admin.dto.request.religion.ReligionUpdateRequestDTO;
import com.cogent.admin.service.ReligionService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.ReligionConstants.BASE_RELIGION;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.religion.ReligionRequestUtils.*;
import static com.cogent.admin.dto.religion.ReligionResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReligionControllerTest {
    @InjectMocks
    private ReligionController religionController;

    @Mock
    private ReligionService religionService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(religionController).build();
        BASE_URL = API_V1 + BASE_RELIGION;

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

        doNothing().when(religionService).save(any(ReligionRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getReligionRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(religionService, times(1)).save(any(ReligionRequestDTO.class));
        verifyNoMoreInteractions(religionService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForUpdate() throws Exception {

        String URL = BASE_URL;

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(ReligionRequestUtils.getInvalidInputForUpdate())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        doNothing().when(religionService).update(any(ReligionUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getReligionUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(religionService, times(1)).update(any(ReligionUpdateRequestDTO.class));
        verifyNoMoreInteractions(religionService);
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

        doNothing().when(religionService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(religionService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(religionService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(religionService.search(any(ReligionSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchReligionResponseDTO()));

        mockMvc.perform(put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getReligionSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchReligionResponseDTO().size())))
                .andReturn();


        verify(religionService, times(1)).search
                (any(ReligionSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(religionService);
    }

    @Test
    public void fetchReligionForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(religionService.fetchReligionForDropDown())
                .willReturn(fetchReligionForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchReligionForDropDown().size())))
                .andReturn();

        verify(religionService, times(1)).fetchReligionForDropDown();
        verifyNoMoreInteractions(religionService);
    }

    @Test
    public void fetchEthnicity() throws Exception {
        String URL = BASE_URL+ ID_PATH_VARIABLE_BASE;

        given(religionService.fetchReligion(1L))
                .willReturn(getReligion());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(religionService).fetchReligion(1L);
    }


}
