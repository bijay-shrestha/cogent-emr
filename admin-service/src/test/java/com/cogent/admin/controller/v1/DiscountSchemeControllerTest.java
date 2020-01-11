package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.discountscheme.DiscountSchemeRequestDTO;
import com.cogent.admin.dto.request.discountscheme.DiscountSchemeSearchRequestDTO;
import com.cogent.admin.dto.request.discountscheme.DiscountSchemeUpdateRequestDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DepartmentDiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.ServiceDiscountDropDownResponseDTO;
import com.cogent.admin.service.DiscountSchemeService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.DiscountSchemeConstants.*;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.request.discountscheme.DiscountSchemeTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi Thapa 11/11/19
 */
public class DiscountSchemeControllerTest {

    @Mock
    private DiscountSchemeService service;

    @InjectMocks
    private DiscountSchemeController controller;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void bed() throws Exception {
        create();
        delete();
        search();
        fetchDiscountSchemeDetails();
    }


    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME;

        DiscountSchemeRequestDTO requestDTO = getDiscountSchemeRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(requestDTO)))
                .andExpect(status().isCreated());

        verify(service).createDiscountScheme(requestDTO);

    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(service).deleteDiscountScheme(any(DeleteRequestDTO.class));
    }

    @Test
    public void deleteDepartmentDiscounr() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + DEPARTMENT_DISCOUNT;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(service).deleteDepartmentDiscount(any(DeleteRequestDTO.class));
    }

    @Test
    public void deleteServiceDiscount() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + SERVICE_DISCOUNT;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(service).deleteServiceDiscount(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME;

        DiscountSchemeUpdateRequestDTO requestDTO = getDiscountSchemeUpdateRequestDTOWithNetDisocunt();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(requestDTO)))
                .andExpect(status().isOk());


        verify(service).updateDiscountScheme(any(DiscountSchemeUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + SEARCH;

        Pageable pageable = PageRequest.of(1, 10);

        DiscountSchemeSearchRequestDTO searchRequestDTO = new DiscountSchemeSearchRequestDTO();


        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(searchRequestDTO)))
                .andExpect(status().isOk());


        verify(service)
                .searchDiscountScheme(searchRequestDTO, pageable);
    }

    @Test
    public void fetchDiscountSchemeDetails() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + DETAILS + ID_PATH_VARIABLE_BASE;

        given(service.fetchDiscountSchemeDetails(1L))
                .willReturn(getDiscountSchemeResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(service)
                .fetchDiscountSchemeDetails(1L);
    }

    @Test
    public void dropDownListWithNetDiscount() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + DROPDOWN;

        List<DiscountDropDownResponseDTO> dropDownInfo = getDropDownResponse();

        given(service.fetchDropDownListWithNetDiscount())
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchDropDownListWithNetDiscount();
    }


    @Test
    public void activeDropDownListWithNetDiscount() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + DROPDOWN + ACTIVE;

        List<DiscountDropDownResponseDTO> dropDownInfo = getDropDownResponse();


        given(service.fetchActiveDropDownListWithNetDiscount())
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchActiveDropDownListWithNetDiscount();
    }

    @Test
    public void dropDownListByDepartmentId() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + DEPARTMENT_DISCOUNT + DROPDOWN + DEPARTMENT_ID_PATH_VARIABLE_BASE;

        List<DepartmentDiscountDropDownResponseDTO> dropDownInfo = getDepartmentDiscountDropDownResponseDTOS();

        given(service.fetchDropDownListByDepartmentId(1L))
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchDropDownListByDepartmentId(1L);
    }


    @Test
    public void activeDropDownListByDepartmentId() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + DEPARTMENT_DISCOUNT + DROPDOWN + ACTIVE
                + DEPARTMENT_ID_PATH_VARIABLE_BASE;

        List<DepartmentDiscountDropDownResponseDTO> dropDownInfo = getDepartmentDiscountDropDownResponseDTOS();


        given(service.fetchActiveDropDownListByDepartmentId(1L))
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchActiveDropDownListByDepartmentId(1L);
    }

    @Test
    public void dropDownListByServiceId() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + SERVICE_DISCOUNT + DROPDOWN + SERVICE_ID_PATH_VARIABLE_BASE;

        List<ServiceDiscountDropDownResponseDTO> dropDownInfo = getServiceDiscountDropDownResponseDTOS();

        given(service.fetchDropDownListByServiceId(1L))
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchDropDownListByServiceId(1L);
    }


    @Test
    public void activeDropDownListByServiceId() throws Exception {
        String URL = API_V1 + BASE_DISCOUNT_SCHEME + SERVICE_DISCOUNT + DROPDOWN + ACTIVE
                + SERVICE_ID_PATH_VARIABLE_BASE;

        List<ServiceDiscountDropDownResponseDTO> dropDownInfo = getServiceDiscountDropDownResponseDTOS();


        given(service.fetchActiveDropDownListByServiceId(1L))
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(service).fetchActiveDropDownListByServiceId(1L);
    }
}
