package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorSearchRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorUpdateRequestDTO;
import com.cogent.admin.service.DoctorService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.DoctorConstants.*;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.doctor.DoctorRequestUtils.*;
import static com.cogent.admin.dto.files.MultipartFileUtils.getMockMultipartFile;
import static com.cogent.admin.dto.doctor.DoctorResponseUtils.*;
import static com.cogent.admin.utils.FileUtils.configureMultipartForPutMethod;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author smriti on 2019-09-27
 */
public class DoctorControllerTest {
    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
        BASE_URL = API_V1 + BASE_DOCTOR;
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.multipart(URL)
                .param("file", writeObjectToJson(getMockMultipartFile().getBytes()))
                .param("request", writeObjectToJson(getDoctorRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(doctorService, times(1))
                .save(any(DoctorRequestDTO.class), any(), any());
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        MockMultipartHttpServletRequestBuilder builder = configureMultipartForPutMethod(URL);

        mockMvc.perform(builder
                .file(getMockMultipartFile())
                .param("request", writeObjectToJson(getDoctorUpdateRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(doctorService, times(1))
                .update(any(DoctorUpdateRequestDTO.class), any(), any());
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(doctorService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(doctorService.search(any(DoctorSearchRequestDTO.class), any(Pageable.class)))
                .willReturn((fetchDoctorMinimalResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getDoctorSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.hasSize(fetchDoctorMinimalResponseDTO().size())))
                .andReturn();

        verify(doctorService, times(1)).search
                (any(DoctorSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void activeDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(doctorService.fetchDoctorForDropdown()).willReturn(fetchDoctorForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchDoctorForDropDown().size())))
                .andReturn();

        verify(doctorService, times(1)).fetchDoctorForDropdown();
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(doctorService.fetchDetailsById(id)).willReturn(fetchDoctorDetailResponseDTO());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(doctorService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void fetchDoctorBySpecializationId() throws Exception {
        String URL = BASE_URL + SPECIALIZATION_ID_PATH_VARIABLE_BASE;
        Long specializationId = 1L;

        given(doctorService.fetchDoctorBySpecializationId(specializationId))
                .willReturn(fetchDoctorForDropDown());

        mockMvc.perform(get(URL, specializationId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchDoctorForDropDown().size())))
                .andReturn();

        verify(doctorService, times(1)).fetchDoctorBySpecializationId(specializationId);
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void fetchDetailsForUpdate() throws Exception {
        String URL = BASE_URL + UPDATE_DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(doctorService.fetchDetailsForUpdate(id)).willReturn(fetchDoctorUpdateResponseDTO());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(doctorService, times(1)).fetchDetailsForUpdate(id);
        verifyNoMoreInteractions(doctorService);
    }
}
