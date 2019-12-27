package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.service.InsuranceCompanyService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.InsuranceCompanyConstants.BASE_INSURANCE_COMPANY;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.insurancecompany.InsuranceCompanyTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InsuranceCompanyControllerTest {
    @Mock
    private InsuranceCompanyService insuranceCompanyService;

    @InjectMocks
    private InsuranceCompanyController insuranceCompanyController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(insuranceCompanyController).build();
    }

    @Test
    public void provinces() throws Exception {
        create();
        delete();
        update();
        search();
        fetchInsuranceCompany();
        fetchInsuranceCompanyDetails();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_INSURANCE_COMPANY;

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getInsuranceCompanyRequestDTO())))
                .andExpect(status().isCreated());

        verify(insuranceCompanyService).createInsuranceCompany(getInsuranceCompanyRequestDTO());
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_INSURANCE_COMPANY;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getDeleteRequestDTO())))
                .andExpect(status().isOk());

        verify(insuranceCompanyService).deleteInsuranceCompany(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_INSURANCE_COMPANY;

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getInsuranceCompanyUpdateRequestDTO())))
                .andExpect(status().isOk());

        verify(insuranceCompanyService).updateInsuranceCompany(getInsuranceCompanyUpdateRequestDTO());
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_INSURANCE_COMPANY + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getInsuranceCompanySeacrhRequestDTO())))
                .andExpect(status().isOk());

        verify(insuranceCompanyService).searchInsuranceCompany(getInsuranceCompanySeacrhRequestDTO(), pageable);
    }

    @Test
    public void fetchInsuranceCompany() throws Exception {
        String URL = API_V1 + BASE_INSURANCE_COMPANY + ID_PATH_VARIABLE_BASE;

        given(insuranceCompanyService.fetchInsuranceCompany(1L))
                .willReturn(getInsuranceCompanyInfo());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(insuranceCompanyService).fetchInsuranceCompany(1L);
    }

    @Test
    public void fetchInsuranceCompanyDetails() throws Exception {
        String URL = API_V1 + BASE_INSURANCE_COMPANY + DETAILS + ID_PATH_VARIABLE_BASE;

        given(insuranceCompanyService.fetchInsuranceCompanyDetails(1L))
                .willReturn(getInsuranceCompanyResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(insuranceCompanyService).fetchInsuranceCompanyDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_INSURANCE_COMPANY + DROPDOWN;

        given(insuranceCompanyService.fetchDropDownList())
                .willReturn(getDropDownList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(getDropDownList().size())));

        verify(insuranceCompanyService).fetchDropDownList();
    }

    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_INSURANCE_COMPANY + DROPDOWN + ACTIVE;

        given(insuranceCompanyService.fetchActiveDropDownList())
                .willReturn(getDropDownList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(getDropDownList().size())));

        verify(insuranceCompanyService).fetchActiveDropDownList();
    }

}
