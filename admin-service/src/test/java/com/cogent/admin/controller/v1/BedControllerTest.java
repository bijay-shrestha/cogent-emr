package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.request.bed.BedUpdateRequestDTO;
import com.cogent.admin.service.BedService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.BedConstants.BASE_BED;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.bed.BedTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi Thapa 11/1/19
 */
public class BedControllerTest {
    @Mock
    private BedService bedService;

    @InjectMocks
    private BedController bedController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bedController).build();
    }

    @Test
    public void bed() throws Exception {
        create();
        delete();
        update();
        search();
        fetchBedDetails();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_BED;

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getBedRequestDTO())))
                .andExpect(status().isCreated());

        verify(bedService).createBed(getBedRequestDTO());

    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_BED;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(bedService).deleteBed(any(DeleteRequestDTO.class));
    }


    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_BED;

        BedUpdateRequestDTO updateRequestDTO = getUpdateBedRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(updateRequestDTO)))
                .andExpect(status().isOk());


        verify(bedService).updateBed(any(BedUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_BED + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        BedSearchRequestDTO searchRequestDTO = getSearchBedRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(searchRequestDTO)))
                .andExpect(status().isOk());


        verify(bedService).searchBed(searchRequestDTO, pageable);
    }

    @Test
    public void fetchBedDetails() throws Exception {
        String URL = API_V1 + BASE_BED + DETAILS + ID_PATH_VARIABLE_BASE;

        given(bedService.fetchBedDetails(1L))
                .willReturn(getBedResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(bedService).fetchBedDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_BED + DROPDOWN;
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();


        given(bedService.fetchDropDownList())
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(bedService).fetchDropDownList();
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_BED + DROPDOWN + ACTIVE;
        List<DropDownResponseDTO> bedDropDownResponseDTO = getDropDownInfo();


        given(bedService.fetchActiveDropDownList())
                .willReturn(bedDropDownResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(bedDropDownResponseDTO.size())));


        verify(bedService).fetchActiveDropDownList();
    }

}
