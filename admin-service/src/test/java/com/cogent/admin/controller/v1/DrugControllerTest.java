package com.cogent.admin.controller.v1;


import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.drug.DrugRequestDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.drug.DrugTestUtils;
import com.cogent.admin.dto.request.drug.DrugUpdateRequestDTO;
import com.cogent.admin.service.impl.DrugServiceImpl;
import com.cogent.admin.utils.ObjectToJSONUtils;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.DrugConstants.BASE_DRUG;
import static com.cogent.admin.dto.drug.DrugTestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DrugControllerTest {
    @Mock
    private DrugServiceImpl drugService;

    @InjectMocks
    private DrugController drugController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(drugController).build();
    }

    @Test
    public void drug() throws Exception {
        create();
        delete();
        update();
        search();
        fetchDrugDetail();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_DRUG;


        DrugRequestDTO drugRequestDTO = getDrugRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(drugRequestDTO)))
                .andExpect(status().isCreated());

        verify(drugService).createDrug(drugRequestDTO);
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_DRUG;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(drugService).deleteDrug(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_DRUG;

        DrugUpdateRequestDTO drugUpdateRequestDTO = getDrugUpdateRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(drugUpdateRequestDTO)))
                .andExpect(status().isOk());


        verify(drugService).updateDrug(any(DrugUpdateRequestDTO.class));

    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_DRUG + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        DrugSearchRequestDTO drugSearchRequestDTO = DrugTestUtils
                .getSearchRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(drugSearchRequestDTO)))
                .andExpect(status().isOk());


        verify(drugService).searchDrug(drugSearchRequestDTO, pageable);
    }

    @Test
    public void fetchDrugDetail() throws Exception {
        String URL = API_V1 + BASE_DRUG + DETAILS + ID_PATH_VARIABLE_BASE;


        given(drugService.fetchDrugDetails(1L))
                .willReturn(getDrugResponseDTO());


        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(drugService).fetchDrugDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_DRUG + DROPDOWN;
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();


        given(drugService.drugDropdown())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(drugService).drugDropdown();
    }

    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_DRUG + DROPDOWN + ACTIVE;
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(drugService.activeDrugDropdown())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(drugService).activeDrugDropdown();
    }
}
