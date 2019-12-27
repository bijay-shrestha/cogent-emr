package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.department.DepartmentTestUtils;
import com.cogent.admin.dto.request.service.ServiceRequestDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.request.service.ServiceUpdateRequestDTO;
import com.cogent.admin.dto.response.service.ServiceMinimalResponseDTO;
import com.cogent.admin.service.impl.ServiceServiceImpl;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.ServiceConstants.BASE_SERVICE;
import static com.cogent.admin.dto.service.ServiceTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi Thapa 11/18/19
 */
public class ServiceControllerTest {
    @Mock
    private ServiceServiceImpl service;

    @InjectMocks
    private ServiceController controller;

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
        fetchServiceDetail();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_SERVICE;

        ServiceRequestDTO requestDTO = getServiceRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(requestDTO)))
                .andExpect(status().isCreated());

        verify(service).createService(requestDTO);
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_SERVICE;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(DepartmentTestUtils
                        .getDeleteRequestDTO())))
                .andExpect(status().isOk());

        verify(service).deleteService(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_SERVICE;

        ServiceUpdateRequestDTO requestDTO = getServiceUpdateRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(requestDTO)))
                .andExpect(status().isOk());

        verify(service).updateService(requestDTO);
    }


    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_SERVICE + SEARCH;

        ServiceSearchRequestDTO searchRequestDTO = new ServiceSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        List<ServiceMinimalResponseDTO> minimalResponseDTOS =getMinimalResponseDTOList();

        given(service.searchService(searchRequestDTO, pageable))
                .willReturn(minimalResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(searchRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        verify(service).searchService(searchRequestDTO, pageable);
    }

    @Test
    public void fetchServiceDetail() throws Exception {
        String URL = API_V1 + BASE_SERVICE + DETAILS + ID_PATH_VARIABLE_BASE;

        given(service.fetchServiceDetails(1L))
                .willReturn(getServiceResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(service).fetchServiceDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_SERVICE + DROPDOWN + DEPARTMENT_ID_PATH_VARIABLE_BASE;
        List<DropDownResponseDTO> dropDownInfo = dropDownResponseDTOS();


        given(service.fetchDropDownList(1L))
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL,1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchDropDownList(1L);
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_SERVICE + DROPDOWN + ACTIVE + DEPARTMENT_ID_PATH_VARIABLE_BASE;
        List<DropDownResponseDTO> dropDownInfo = dropDownResponseDTOS();


        given(service.fetchActiveDropDownList(1L))
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL,1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchActiveDropDownList(1L);
    }



}
