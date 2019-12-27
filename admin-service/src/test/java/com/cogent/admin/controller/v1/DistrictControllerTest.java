package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.district.DistrictTestUtils;
import com.cogent.admin.dto.request.district.DistrictRequestDTO;
import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import com.cogent.admin.dto.request.district.DistrictUpdateRequestDTO;
import com.cogent.admin.dto.response.district.DistrictDropDownResponseDTO;
import com.cogent.admin.service.DistrictService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.DistrictConstants.BASE_DISTRICT;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DistrictControllerTest {
    @Mock
    private DistrictService districtService;

    @InjectMocks
    private DistrictController districtController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(districtController).build();
    }

    @Test
    public void district() throws Exception {
        create();
        dropDownList();
        activeDropDownList();
        search();
        fetchDistrictDetail();
        delete();
        update();

    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_DISTRICT;

        DistrictRequestDTO districtRequestDTO = DistrictTestUtils.getDistrictRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(districtRequestDTO)))
                .andExpect(status().isCreated());

        verify(districtService).createDistrict(districtRequestDTO);
    }

    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_DISTRICT + DROPDOWN + ACTIVE;
        List<DistrictDropDownResponseDTO> dropDownResponseDTOS = DistrictTestUtils
                .dropDownResponseDTOS();

        given(districtService.activeDistrictDropdown())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(districtService).activeDistrictDropdown();
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_DISTRICT + DROPDOWN;
        List<DistrictDropDownResponseDTO> dropDownResponseDTOS = DistrictTestUtils
                .dropDownResponseDTOS();

        given(districtService.districtDropdown())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(districtService).districtDropdown();
    }

    @Test
    public void fetchDistrictDetail() throws Exception {
        String URL = API_V1 + BASE_DISTRICT + DETAILS + ID_PATH_VARIABLE_BASE;

        given(districtService.fetchDistrictDetails(1L))
                .willReturn(DistrictTestUtils.districtResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(districtService).fetchDistrictDetails(1L);

    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_DISTRICT + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(new DistrictSearchRequestDTO())))
                .andExpect(status().isOk());


        verify(districtService).searchDistrict(new DistrictSearchRequestDTO(), pageable);
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_DISTRICT;

        DeleteRequestDTO deleteRequestDTO = DistrictTestUtils
                .getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(districtService).deleteDistrict(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_DISTRICT;

        DistrictUpdateRequestDTO updateRequestDTO = DistrictTestUtils
                .getUpdateRequestDTO();
        ;

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(updateRequestDTO)))
                .andExpect(status().isOk());


        verify(districtService).updateDistrict(any(DistrictUpdateRequestDTO.class));

    }
}
