package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesUpdateRequestDTO;
import com.cogent.admin.dto.response.provinces.ProvincesDropDownResponseDTO;
import com.cogent.admin.service.ProvincesService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.ProvincesConstants.BASE_PROVINCES;
import static com.cogent.admin.dto.request.provinces.ProvincesTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProvincesControllerTest {
    @Mock
    private ProvincesService provincesService;

    @InjectMocks
    private ProvincesController provincesController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(provincesController).build();
    }

    @Test
    public void provinces() throws Exception {
        create();
        delete();
        update();
        search();
        fetchProvincesDetail();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_PROVINCES;

        ProvincesRequestDTO provincesRequestDTO = getRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(provincesRequestDTO)))
                .andExpect(status().isCreated());

        verify(provincesService).createProvinces(provincesRequestDTO);
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_PROVINCES;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(provincesService).deleteProvinces(any(DeleteRequestDTO.class));
    }


    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_PROVINCES;

        ProvincesUpdateRequestDTO updateRequestDTO = getProvincesUpdateRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(updateRequestDTO)))
                .andExpect(status().isOk());


        verify(provincesService).updateProvinces(updateRequestDTO);

    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_PROVINCES + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(new ProvincesSearchRequestDTO())))
                .andExpect(status().isOk());


        verify(provincesService).searchProvinces(new ProvincesSearchRequestDTO(), pageable);

    }

    @Test
    public void fetchProvincesDetail() throws Exception {
        String URL = API_V1 + BASE_PROVINCES + DETAILS + ID_PATH_VARIABLE_BASE;

        given(provincesService.fetchProvincesDetails(1L))
                .willReturn(provincesResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(provincesService).fetchProvincesDetails(1L);

    }


    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_PROVINCES + DROPDOWN;
        List<ProvincesDropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(provincesService.provincesDropdown())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(provincesService).provincesDropdown();
    }

    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_PROVINCES + DROPDOWN + ACTIVE;
        List<ProvincesDropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(provincesService.activeProvinceDropdown())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(provincesService).activeProvinceDropdown();
    }


}
