package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeUpdateRequestDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeMinimalResponseDTO;
import com.cogent.admin.service.impl.ServiceChargeServiceImpl;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.ServiceChargeConstants.BASE_SERVICE_CHARGE;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.servicecharge.ServiceChargeTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi Thapa 11/18/19
 */
public class ServiceChargeControllerTest {
    @Mock
    private ServiceChargeServiceImpl service;

    @InjectMocks
    private ServiceChargeController controller;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void ethnicity() throws Exception {
        create();
        delete();
        update();
        search();
        fetchServiceChargeDetail();
        dropDownList();
        activeDropDownList();
    }


    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_SERVICE_CHARGE;

        ServiceChargeRequestDTO requestDTO = getServiceChargeRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(requestDTO)))
                .andExpect(status().isCreated());

        verify(service).createServiceCharge(requestDTO);
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_SERVICE_CHARGE;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getDeleteRequestDTO())))
                .andExpect(status().isOk());

        verify(service).deleteServiceCharge(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_SERVICE_CHARGE;

        ServiceChargeUpdateRequestDTO requestDTO = getServiceChargeUpdateRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(requestDTO)))
                .andExpect(status().isOk());

        verify(service).updateServiceCharge(requestDTO);
    }


    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_SERVICE_CHARGE + SEARCH;

        ServiceChargeSearchRequestDTO searchRequestDTO = new ServiceChargeSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        List<ServiceChargeMinimalResponseDTO> minimalResponseDTOS =getMinimalResponseDTOList();

        given(service.searchServiceCharge(searchRequestDTO, pageable))
                .willReturn(minimalResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(searchRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        verify(service).searchServiceCharge(searchRequestDTO, pageable);
    }

    @Test
    public void fetchServiceChargeDetail() throws Exception {
        String URL = API_V1 + BASE_SERVICE_CHARGE + DETAILS + ID_PATH_VARIABLE_BASE;

        given(service.fetchServiceChargeDetailsById(1L))
                .willReturn(getServiceChargeResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(service).fetchServiceChargeDetailsById(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_SERVICE_CHARGE + DROPDOWN;

        List<ServiceChargeDropDownResponseDTO> dropDownInfo = getDropDownResponseDTOS();

        given(service.fetchDropDownList())
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL,1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchDropDownList();
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_SERVICE_CHARGE + DROPDOWN + ACTIVE ;

        List<ServiceChargeDropDownResponseDTO> dropDownInfo = getDropDownResponseDTOS();

        given(service.fetchActiveDropDownList())
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL,1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchActiveDropDownList();
    }
}

