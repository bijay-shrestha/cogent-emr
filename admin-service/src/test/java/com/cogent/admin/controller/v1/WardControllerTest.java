package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.request.ward.WardUnitDeleteRequestDTO;
import com.cogent.admin.dto.request.ward.WardUpdateRequestDTO;
import com.cogent.admin.service.WardService;
import com.cogent.admin.service.WardUnitService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.WardConstants.BASE_WARD;
import static com.cogent.admin.constants.WebResourceKeyConstants.WardUnitConstants.UNIT;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.ward.WardTestUtils.*;
import static com.cogent.admin.dto.ward.WardUnitTestUtils.getWardUnitDeleteRequestDTO;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi  on 10/2/19
 */

public class WardControllerTest {
    @Mock
    private WardService wardService;

    @Mock
    private WardUnitService wardUnitService;

    @InjectMocks
    private WardController wardController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wardController).build();
    }

    @Test
    public void ward() throws Exception {
        create();
        delete();
        update();
        search();
        fetchWardDetails();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_WARD;

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getWardRequestDTO())))
                .andExpect(status().isCreated());

        verify(wardService).createWard(getWardRequestDTO());
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_WARD;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(wardService).deleteWard(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_WARD;

        WardUpdateRequestDTO updateRequestDTO = getUpdateWardRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(updateRequestDTO)))
                .andExpect(status().isOk());


        verify(wardService).updateWard(any(WardUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_WARD + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        WardSearchRequestDTO searchRequestDTO = getSearchWardRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(searchRequestDTO)))
                .andExpect(status().isOk());


        verify(wardService).searchWard(searchRequestDTO, pageable);
    }

    @Test
    public void fetchWardDetails() throws Exception {
        String URL = API_V1 + BASE_WARD + DETAILS + ID_PATH_VARIABLE_BASE;

        given(wardService.fetchWardDetails(1L))
                .willReturn(getWardResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(wardService).fetchWardDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_WARD + DROPDOWN;
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();


        given(wardService.fetchDropDownList())
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(wardService).fetchDropDownList();
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_WARD + DROPDOWN + ACTIVE;
        List<DropDownResponseDTO> wardDropDownResponseDTO = getDropDownInfo();


        given(wardService.fetchActiveDropDownList())
                .willReturn(wardDropDownResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(wardDropDownResponseDTO.size())));


        verify(wardService).fetchActiveDropDownList();
    }

    @Test
    public void deleteUnit() throws Exception {
        String URL = API_V1 + BASE_WARD + UNIT;

        WardUnitDeleteRequestDTO wardUnitDeleteRequestDTO = getWardUnitDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(wardUnitDeleteRequestDTO)))
                .andExpect(status().isOk());


        verify(wardUnitService).delete(any(WardUnitDeleteRequestDTO.class));
    }
}
