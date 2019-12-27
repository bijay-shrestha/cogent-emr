package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationSearchRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationUpdateRequestDTO;
import com.cogent.admin.service.QualificationService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.QualificationConstants.BASE_QUALIFICATION;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.request.qualification.QualificationRequestUtils.*;
import static com.cogent.admin.dto.request.qualification.QualificationResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author smriti on 12/11/2019
 */
public class QualificationControllerTest {

    @InjectMocks
    private QualificationController qualificationController;

    @Mock
    private QualificationService qualificationService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(qualificationController).build();
        BASE_URL = API_V1 + BASE_QUALIFICATION;
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(qualificationService).save(any(QualificationRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getQualificationRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(qualificationService, times(1)).save(any(QualificationRequestDTO.class));
        verifyNoMoreInteractions(qualificationService);
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        doNothing().when(qualificationService).update(any(QualificationUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getQualificationUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(qualificationService, times(1)).update(any(QualificationUpdateRequestDTO.class));
        verifyNoMoreInteractions(qualificationService);
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        doNothing().when(qualificationService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(qualificationService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(qualificationService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(qualificationService.search(any(QualificationSearchRequestDTO.class), any(Pageable.class)))
                .willReturn(fetchMinimalQualificationList());

        mockMvc.perform(put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getQualificationSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchMinimalQualificationList().size())))
                .andReturn();

        verify(qualificationService, times(1)).search
                (any(QualificationSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(qualificationService);
    }

    @Test
    public void fetchQualificationForDropdown() throws Exception {
        String URL = BASE_URL  + DROPDOWN + ACTIVE;

        given(qualificationService.fetchActiveQualificationForDropDown())
                .willReturn(fetchQualificationListForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchQualificationListForDropDown().size())))
                .andReturn();

        verify(qualificationService, times(1)).fetchActiveQualificationForDropDown();
        verifyNoMoreInteractions(qualificationService);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS +ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(qualificationService.fetchDetailsById(id)).willReturn(fetchQualificationDetailById());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(qualificationService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(qualificationService);
    }
}
