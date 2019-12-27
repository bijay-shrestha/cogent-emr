package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.request.hospital.HospitalRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalUpdateRequestDTO;
import com.cogent.admin.service.HospitalService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.HospitalConstants.BASE_HOSPITAL;
import static com.cogent.admin.dto.hospital.HospitalTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * @author Rupak
 */
public class HospitalControllerTest {

    @InjectMocks
    private HospitalController titleController;

    @Mock
    private HospitalService hospitalService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(titleController).build();
        BASE_URL = API_V1 + BASE_HOSPITAL;

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
    public void Should_Throw_Exception_When_InputIsValidForSave() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForSave())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(hospitalService).save(any(HospitalRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getHospitalRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(hospitalService, Mockito.times(1)).save(any(HospitalRequestDTO.class));
        verifyNoMoreInteractions(hospitalService);
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

        doNothing().when(hospitalService).updateHospital(any(HospitalUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getHospitalUpdateRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(hospitalService, times(1)).updateHospital(any(HospitalUpdateRequestDTO.class));
        verifyNoMoreInteractions(hospitalService);
    }

}
