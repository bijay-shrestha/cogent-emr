package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.title.TitleRequestDTO;
import com.cogent.admin.dto.request.title.TitleSearchRequestDTO;
import com.cogent.admin.dto.request.title.TitleUpdateRequestDTO;
import com.cogent.admin.service.TitleService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.TitleConstants.BASE_TITLE;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.title.TitleRequestUtils.*;
import static com.cogent.admin.dto.title.TitleResponseUtils.fetchTitleForDropDown;
import static com.cogent.admin.dto.title.TitleResponseUtils.fetchTitleResponseDTO;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TitleControllerTest {

    @InjectMocks
    private TitleController titleController;

    @Mock
    private TitleService titleService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(titleController).build();
        BASE_URL = API_V1 + BASE_TITLE;

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

        doNothing().when(titleService).save(any(TitleRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getTitleRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

             verify(titleService, times(1)).save(any(TitleRequestDTO.class));
        verifyNoMoreInteractions(titleService);
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

        doNothing().when(titleService).update(any(TitleUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getTitleUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(titleService, times(1)).update(any(TitleUpdateRequestDTO.class));
        verifyNoMoreInteractions(titleService);
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

        doNothing().when(titleService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(titleService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(titleService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(titleService.search(any(TitleSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchTitleResponseDTO()));

        mockMvc.perform(put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getTitleSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchTitleResponseDTO().size())))
                .andReturn();


        verify(titleService, times(1)).search
                (any(TitleSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(titleService);
    }

    @Test
    public void fetchSurnameForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(titleService.fetchTitleForDropDown())
                .willReturn(fetchTitleForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchTitleForDropDown().size())))
                .andReturn();

        verify(titleService, times(1)).fetchTitleForDropDown();
        verifyNoMoreInteractions(titleService);
    }

}
