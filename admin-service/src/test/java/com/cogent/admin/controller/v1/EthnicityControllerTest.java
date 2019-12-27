package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityRequestDTO;
import com.cogent.admin.dto.ethnicity.EthnicityTestUtils;
import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityUpdateRequestDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityDropDownResponseDTO;
import com.cogent.admin.service.EthnicityService;
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
import java.util.List;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.EthnicityConstants.BASE_ETHNICITY;
import static com.cogent.admin.dto.ethnicity.EthnicityTestUtils.getEthnicityInfo;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi  on 2019-08-25
 */

public class EthnicityControllerTest {
    @Mock
    private EthnicityService ethnicityService;

    @InjectMocks
    private EthnicityController ethnicityController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ethnicityController).build();
    }

    @Test
    public void ethnicity() throws Exception {
        create();
        delete();
        update();
        search();
        fetchEthnicity();
        fetchEthnicityDetails();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_ETHNICITY;

        EthnicityRequestDTO ethnicityRequestDTO = EthnicityTestUtils
                .getEthnicityRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(ethnicityRequestDTO)))
                .andExpect(status().isCreated());

        verify(ethnicityService).createEthnicity(ethnicityRequestDTO);
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_ETHNICITY;

        DeleteRequestDTO deleteRequestDTO = EthnicityTestUtils
                .getDeleteReuqestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(ethnicityService).deleteEthnicity(any(DeleteRequestDTO.class));
    }


    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_ETHNICITY;

        EthnicityUpdateRequestDTO ethnicityUpdateRequestDTO = EthnicityTestUtils
                .getUpdateEthnicityRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(ethnicityUpdateRequestDTO)))
                .andExpect(status().isOk());


        verify(ethnicityService).updateEthnicity(any(EthnicityUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_ETHNICITY + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        EthnicitySearchRequestDTO ethnicitySearchRequestDTO = EthnicityTestUtils
                .getEthnicitySeacrhRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(ethnicitySearchRequestDTO)))
                .andExpect(status().isOk());


        verify(ethnicityService).searchEthnicity(ethnicitySearchRequestDTO, pageable);
    }

    @Test
    public void fetchEthnicity() throws Exception {
        String URL = API_V1 + BASE_ETHNICITY + ID_PATH_VARIABLE_BASE;

        given(ethnicityService.fetchEthnicity(1L))
                .willReturn(getEthnicityInfo());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(ethnicityService).fetchEthnicity(1L);
    }

    @Test
    public void fetchEthnicityDetails() throws Exception {
        String URL = API_V1 + BASE_ETHNICITY + DETAILS + ID_PATH_VARIABLE_BASE;

        given(ethnicityService.fetchEthnicityDetails(1L))
                .willReturn(EthnicityTestUtils
                        .getEthnicityResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(ethnicityService).fetchEthnicityDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_ETHNICITY + DROPDOWN;
        List<EthnicityDropDownResponseDTO> ethnicityDropDownResponseDTO = EthnicityTestUtils
                .getDropDownInfo();


        given(ethnicityService.fetchDropDownList())
                .willReturn(ethnicityDropDownResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(ethnicityDropDownResponseDTO.size())));


        verify(ethnicityService).fetchDropDownList();
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_ETHNICITY + DROPDOWN + ACTIVE;
        List<EthnicityDropDownResponseDTO> ethnicityDropDownResponseDTO = EthnicityTestUtils
                .getDropDownInfo();


        given(ethnicityService.fetchActiveDropDownList())
                .willReturn(ethnicityDropDownResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(ethnicityDropDownResponseDTO.size())));


        verify(ethnicityService).fetchActiveDropDownList();
    }


}
