package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentDropDownDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentResponseDTO;
import com.cogent.admin.service.SubDepartmentService;
import com.cogent.admin.utils.ObjectToJSONUtils;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.List;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.SubDepartmentConstants.BASE_SUB_DEPARTMENT;
import static com.cogent.admin.dto.subdepartment.SubDepartmentTestUtlis.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SubDepartmentControllerTest {
    @Mock
    private SubDepartmentService subDepartmentService;


    @InjectMocks
    private SubDepartmentController subDepartmentController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subDepartmentController).build();
    }

    @Test
    public void testController() throws Exception {
        create();
        delete();
        update();
        search();
        fetchSubDepartmentDetails();
        dropDownList();
        activeDropDownList();
    }


    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_SUB_DEPARTMENT;
        System.out.println(URL);

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(getSubDepartmentRequestDTO())))
                .andExpect(status().isCreated());

        verify(subDepartmentService).createSubDepartment(any(SubDepartmentRequestDTO.class));

    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_SUB_DEPARTMENT;
        DeleteRequestDTO deleteSubDepartmentRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(deleteSubDepartmentRequestDTO)))
                .andExpect(status().isOk());

        verify(subDepartmentService).deleteSubDepartment(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_SUB_DEPARTMENT;

        SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO = getsubDepartmentUpdateRequestDTO();


        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(subDepartmentUpdateRequestDTO)))
                .andExpect(status().isOk());

        verify(subDepartmentService).updateSubDepartment(subDepartmentUpdateRequestDTO);

    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_SUB_DEPARTMENT + SEARCH;
        SubDepartmentSearchRequestDTO subDepartmentRequestDTO = new SubDepartmentSearchRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);
        List<SubDepartmentMinimalResponseDTO> subDepartmentMinimalResponseDTOS = SubDepartmentMinimalResponseDTO();

        given(subDepartmentService.searchSubDepartment(subDepartmentRequestDTO, pageable))
                .willReturn(subDepartmentMinimalResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=0&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(subDepartmentRequestDTO)))
                .andExpect(status().isOk());

        Assert.assertThat((subDepartmentService.searchSubDepartment(subDepartmentRequestDTO, pageable)),
                hasSize(subDepartmentMinimalResponseDTOS.size()));

        verify(subDepartmentService).searchSubDepartment(subDepartmentRequestDTO, pageable);
    }

    @Test
    public void fetchSubDepartmentDetails() throws Exception {
        String URL = API_V1 + BASE_SUB_DEPARTMENT + DETAILS + ID_PATH_VARIABLE_BASE;

        SubDepartmentResponseDTO subDepartmentResponseDTO = getSubDepartmentResponseDTO();

        given(subDepartmentService.fetchSubDepartmentDetailsById(1L))
                .willReturn(subDepartmentResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk())
                .andReturn();

        verify(subDepartmentService).fetchSubDepartmentDetailsById(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_SUB_DEPARTMENT + DROPDOWN;

        List<SubDepartmentDropDownDTO> dropDownDTOS = SubDepartmentDropDownDTO();

        given(subDepartmentService.dropDownList())
                .willReturn(dropDownDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andReturn();

        verify(subDepartmentService).dropDownList();
    }

    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_SUB_DEPARTMENT + DROPDOWN +DROPDOWN;

        List<SubDepartmentDropDownDTO> dropDownDTOS = SubDepartmentDropDownDTO();

        given(subDepartmentService.activeDropDownList())
                .willReturn(dropDownDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andReturn();

        verify(subDepartmentService).activeDropDownList();
    }


}
