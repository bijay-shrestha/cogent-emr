package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerUpdateRequestDTO;
import com.cogent.admin.service.ReferrerService;
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

import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.ReferrerConstants.BASE_REFERRER;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.referrer.ReferrerTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * @author Rupak
 */
public class ReferrerControllerTest {

    @InjectMocks
    private ReferrerController titleController;

    @Mock
    private ReferrerService titleService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(titleController).build();
        BASE_URL = API_V1 + BASE_REFERRER;

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
    public void update() throws Exception {

        String URL = BASE_URL;

        doNothing().when(titleService).update(any(ReferrerUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getReferrerUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(titleService, times(1)).update(any(ReferrerUpdateRequestDTO.class));
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
    public void deleteTest() throws Exception {
        Should_Throw_Exception_When_InputIsInvalidForDelete();
        delete();

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
    public void Should_Throw_Exception_When_InputIsInvalidForDelete() throws Exception {

        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForUpdate())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }

    @Test
    public void save() throws Exception {

        String URL = BASE_URL;

        doNothing().when(titleService).save(any(ReferrerRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getReferrerRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(titleService, times(1)).save(any(ReferrerRequestDTO.class));
        verifyNoMoreInteractions(titleService);


    }

    @Test
    public void Should_Throw_Exception_When_InputIsValidForSave() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForSave())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

}
