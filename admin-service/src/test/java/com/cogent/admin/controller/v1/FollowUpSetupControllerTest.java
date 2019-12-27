package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupRequestDTO;
import com.cogent.admin.dto.request.followUpSetup.FollowUpSetupUpdateRequestDTO;
import com.cogent.admin.service.FollowUpSetupService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.FollowUpSetupConstants.BASE_FOLLOW_UP_SETUP;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.followUpSetup.FollowUpSetupRequestUtils.*;
import static com.cogent.admin.dto.followUpSetup.FollowUpSetupResponseUtils.getFollowUpSetupResponseDTO;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author smriti on 2019-11-04
 */
public class FollowUpSetupControllerTest {

    @InjectMocks
    private FollowUpSetupController followUpSetupController;

    @Mock
    private FollowUpSetupService followUpSetupService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(followUpSetupController).build();
        BASE_URL = API_V1 + BASE_FOLLOW_UP_SETUP;
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

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getFollowUpSetupRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(followUpSetupService, times(1))
                .save(any(FollowUpSetupRequestDTO.class));
        verifyNoMoreInteractions(followUpSetupService);
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

        doNothing().when(followUpSetupService).update(any(FollowUpSetupUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getFollowUpSetupUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(followUpSetupService, times(1))
                .update(any(FollowUpSetupUpdateRequestDTO.class));
        verifyNoMoreInteractions(followUpSetupService);
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

        doNothing().when(followUpSetupService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(followUpSetupService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(followUpSetupService);
    }

    @Test
    public void fetchMinimalFollowUpSetup() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(followUpSetupService, times(1)).fetchFollowUpSetup();
        verifyNoMoreInteractions(followUpSetupService);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS +ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(followUpSetupService.fetchDetailsById(id)).willReturn(getFollowUpSetupResponseDTO());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(followUpSetupService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(followUpSetupService);
    }
}
