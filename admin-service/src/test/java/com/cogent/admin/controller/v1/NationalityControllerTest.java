package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.service.NationalityService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.NationalityConstants.BASE_NATIONALITY;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.nationality.NationalityTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NationalityControllerTest {
    @Mock
    private NationalityService nationalityService;

    @InjectMocks
    private NationalityController nationalityController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(nationalityController).build();
    }

    @Test
    public void provinces() throws Exception {
        create();
        delete();
        update();
        search();
        fetchNationality();
        fetchNationalityDetails();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_NATIONALITY;

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getNationalityRequestDTO())))
                .andExpect(status().isCreated());

        verify(nationalityService).createNationality(getNationalityRequestDTO());
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_NATIONALITY;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getDeleteRequestDTO())))
                .andExpect(status().isOk());

        verify(nationalityService).deleteNationality(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_NATIONALITY;

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(nationalityUpdateRequestDTO())))
                .andExpect(status().isOk());

        verify(nationalityService).updateNationality(nationalityUpdateRequestDTO());
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_NATIONALITY + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getNationalitySeacrhRequestDTO())))
                .andExpect(status().isOk());

        verify(nationalityService).searchNationality(getNationalitySeacrhRequestDTO(), pageable);
    }

    @Test
    public void fetchNationality() throws Exception {
        String URL = API_V1 + BASE_NATIONALITY + ID_PATH_VARIABLE_BASE;

        given(nationalityService.fetchNatioanlity(1L))
                .willReturn(getNationalityInfo());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(nationalityService).fetchNatioanlity(1L);
    }

    @Test
    public void fetchNationalityDetails() throws Exception {
        String URL = API_V1 + BASE_NATIONALITY + DETAILS + ID_PATH_VARIABLE_BASE;

        given(nationalityService.fetchNatioanlityDetails(1L))
                .willReturn(getNationalityResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(nationalityService).fetchNatioanlityDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_NATIONALITY + DROPDOWN;

        given(nationalityService.fetchDropDownList())
                .willReturn(getDropDownList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(getDropDownList().size())));


        verify(nationalityService).fetchDropDownList();
    }

    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_NATIONALITY + DROPDOWN + ACTIVE ;

        given(nationalityService.fetchActiveDropDownList())
                .willReturn(getDropDownList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(getDropDownList().size())));


        verify(nationalityService).fetchActiveDropDownList();
    }

}
