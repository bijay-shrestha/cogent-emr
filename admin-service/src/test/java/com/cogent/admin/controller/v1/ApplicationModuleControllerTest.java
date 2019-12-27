package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleSearchRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleUpdateRequestDTO;
import com.cogent.admin.service.ApplicationModuleService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.ApplicationModuleConstants.BASE_APPLICATION_MODULE;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.applicationModules.ApplicationModuleRequestUtils.*;
import static com.cogent.admin.dto.applicationModules.ApplicationModuleResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author smriti ON 25/12/2019
 */

public class ApplicationModuleControllerTest {

    @InjectMocks
    private ApplicationModuleController applicationModuleController;

    @Mock
    private ApplicationModuleService applicationModuleService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationModuleController).build();
        BASE_URL = API_V1 + BASE_APPLICATION_MODULE;
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(applicationModuleService).save(any(ApplicationModuleRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getApplicationModuleRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(applicationModuleService, times(1)).save(any(ApplicationModuleRequestDTO.class));
        verifyNoMoreInteractions(applicationModuleService);
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        doNothing().when(applicationModuleService).update(any(ApplicationModuleUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getApplicationModuleRequestDTOForUpdate()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(applicationModuleService, times(1)).update(any(ApplicationModuleUpdateRequestDTO.class));
        verifyNoMoreInteractions(applicationModuleService);
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        doNothing().when(applicationModuleService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(applicationModuleService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(applicationModuleService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(applicationModuleService.search(any(ApplicationModuleSearchRequestDTO.class), any(Pageable.class)))
                .willReturn(fetchMinimalApplicationModuleList());

        mockMvc.perform(put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getApplicationModuleRequestDTOForSearch())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchMinimalApplicationModuleList().size())))
                .andReturn();

        verify(applicationModuleService, times(1)).search
                (any(ApplicationModuleSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(applicationModuleService);
    }

    @Test
    public void fetchApplicationModuleForDropdown() throws Exception {
        String URL = BASE_URL  + DROPDOWN + ACTIVE;

        given(applicationModuleService.fetchActiveApplicationModuleForDropDown()).willReturn(fetchApplicationModuleListForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchApplicationModuleListForDropDown().size())))
                .andReturn();

        verify(applicationModuleService, times(1)).fetchActiveApplicationModuleForDropDown();
        verifyNoMoreInteractions(applicationModuleService);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS +ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(applicationModuleService.fetchDetailsById(id)).willReturn(fetchApplicationModuleDetailById());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(applicationModuleService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(applicationModuleService);
    }
}
