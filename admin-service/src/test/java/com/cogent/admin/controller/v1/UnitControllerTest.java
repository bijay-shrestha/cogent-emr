package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.request.unit.UnitUpdateRequestDTO;
import com.cogent.admin.service.UnitService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.UnitConstants.BASE_UNIT;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.unit.UnitTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi  on 10/13/19
 */

public class UnitControllerTest {
    @Mock
    private UnitService unitService;

    @InjectMocks
    private UnitController unitController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(unitController).build();
    }

    @Test
    public void Unit() throws Exception {
        create();
        delete();
        update();
        search();
        fetchUnitDetails();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_UNIT;

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getUnitRequestDTO())))
                .andExpect(status().isCreated());

        verify(unitService).createUnit(getUnitRequestDTO());
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_UNIT;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());

        verify(unitService).deleteUnit(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_UNIT;

        UnitUpdateRequestDTO updateRequestDTO = getUpdateUnitRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(updateRequestDTO)))
                .andExpect(status().isOk());

        verify(unitService).updateUnit(any(UnitUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_UNIT + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        UnitSearchRequestDTO searchRequestDTO = getSearchUnitRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(searchRequestDTO)))
                .andExpect(status().isOk());

        verify(unitService).searchUnit(searchRequestDTO, pageable);
    }

    @Test
    public void fetchUnitDetails() throws Exception {
        String URL = API_V1 + BASE_UNIT + DETAILS + ID_PATH_VARIABLE_BASE;

        given(unitService.fetchUnitDetails(1L))
                .willReturn(getUnitResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(unitService).fetchUnitDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_UNIT + DROPDOWN;
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();


        given(unitService.fetchDropDownList())
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));

        verify(unitService).fetchDropDownList();
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_UNIT + DROPDOWN + ACTIVE;
        List<DropDownResponseDTO> unitDropDownResponseDTO = getDropDownInfo();


        given(unitService.fetchActiveDropDownList())
                .willReturn(unitDropDownResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(unitDropDownResponseDTO.size())));

        verify(unitService).fetchActiveDropDownList();
    }
}
