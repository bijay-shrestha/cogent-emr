package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedUpdateRequestDTO;
import com.cogent.admin.service.AssignBedService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.AssignBedConstants.BASE_ASSIGN_BED;
import static com.cogent.admin.constants.WebResourceKeyConstants.WardConstants.WARD_ID_PATH_VARIABLE_BASE;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.assignbed.AssignBedTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi Thapa 11/7/19
 */
public class AssignBedControllerTest {

    @Mock
    private AssignBedService assignBedService;

    @InjectMocks
    private AssignBedController assignBedController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assignBedController).build();
    }

    @Test
    public void bed() throws Exception {
        create();
        delete();
        update();
        search();
        fetchAssignBedDetails();
        dropDownList();
        activeDropDownList();
    }


    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_ASSIGN_BED;

        AssignBedRequestDTO requestDTO = getAssignBedRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(requestDTO)))
                .andExpect(status().isCreated());

        verify(assignBedService).createAssignBed(requestDTO);

    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_ASSIGN_BED;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(assignBedService).deleteAssignedBed(any(DeleteRequestDTO.class));
    }


    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_ASSIGN_BED;

        AssignBedUpdateRequestDTO requestDTO = getAssignBedUpdateRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(requestDTO)))
                .andExpect(status().isOk());


        verify(assignBedService).updateAssignedBed(any(AssignBedUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_ASSIGN_BED + SEARCH;

        Pageable pageable = PageRequest.of(1, 10);

        AssignBedSearchRequestDTO searchRequestDTO = new AssignBedSearchRequestDTO();


        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(searchRequestDTO)))
                .andExpect(status().isOk());


        verify(assignBedService).searchAssignedBed(searchRequestDTO, pageable);
    }

    @Test
    public void fetchAssignBedDetails() throws Exception {
        String URL = API_V1 + BASE_ASSIGN_BED + DETAILS + ID_PATH_VARIABLE_BASE;

        given(assignBedService.fetchAssignedBedDetails(1L))
                .willReturn(getAssignBedResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(assignBedService).fetchAssignedBedDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_ASSIGN_BED + DROPDOWN + WARD_ID_PATH_VARIABLE_BASE;
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();


        given(assignBedService.fetchDropDownList(1L))
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL,1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(assignBedService).fetchDropDownList(1L);
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_ASSIGN_BED + DROPDOWN + ACTIVE + WARD_ID_PATH_VARIABLE_BASE;
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();


        given(assignBedService.fetchActiveDropDownList(1L))
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL,1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));


        verify(assignBedService).fetchActiveDropDownList(1L);
    }


}
