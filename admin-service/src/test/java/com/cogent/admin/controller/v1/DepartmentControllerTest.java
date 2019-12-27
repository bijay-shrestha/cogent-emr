package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentRequestDTO;
import com.cogent.admin.dto.department.DepartmentTestUtils;
import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.department.DepartmentDropDownDTO;
import com.cogent.admin.dto.response.department.DepartmentMinimalResponseDTO;
import com.cogent.admin.service.DepartmentService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.DepartmentConstants.BASE_DEPARTMENT;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepartmentControllerTest {
    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private static MockMvc mockMvc;


    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }


    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void departmentCrud() throws Exception {
        save();
        delete();
        update();
        search();
        fetchDepartmentDetail();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void save() throws Exception {
        String URL = API_V1 + BASE_DEPARTMENT;

        DepartmentRequestDTO departmentRequestDtO = DepartmentTestUtils
                .getDepartmentRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(departmentRequestDtO)))
                .andExpect(status().isCreated());

        verify(departmentService).createDepartment(departmentRequestDtO);

    }


    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_DEPARTMENT;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(DepartmentTestUtils
                        .getDeleteRequestDTO())))
                .andExpect(status().isOk());

        verify(departmentService).deleteDepartment(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_DEPARTMENT;

        DepartmentUpdateRequestDTO updatedDepartmentRequestDTO = DepartmentTestUtils
                .getupdatedDepartmentRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(updatedDepartmentRequestDTO)))
                .andExpect(status().isOk());

        verify(departmentService).updateDepartment(any(DepartmentUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_DEPARTMENT + SEARCH;

        DepartmentSearchRequestDTO departmentSearchRequestDTO = new DepartmentSearchRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);
        List<DepartmentMinimalResponseDTO> departmentMinimalResponseDTOS = DepartmentTestUtils
                .departmentResponseDTOS();

        given(departmentService.searchDepartment(departmentSearchRequestDTO, pageable))
                .willReturn(departmentMinimalResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(departmentSearchRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertThat((departmentService.searchDepartment(departmentSearchRequestDTO, pageable)),
                hasSize(departmentMinimalResponseDTOS.size()));

        verify(departmentService, times(2)).searchDepartment(departmentSearchRequestDTO, pageable);
    }

    @Test
    public void fetchDepartmentDetail() throws Exception {
        String URL = API_V1 + BASE_DEPARTMENT + DETAILS + ID_PATH_VARIABLE_BASE;


        given(departmentService.fetchDepartmentDetails(1L))
                .willReturn(DepartmentTestUtils
                        .getDepartmentReponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(departmentService).fetchDepartmentDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_DEPARTMENT + DROPDOWN;

        List<DepartmentDropDownDTO> departmentDropDownDTOS = DepartmentTestUtils
                .departmentDropDownDTOS();

        given(departmentService.dropDownList())
                .willReturn(departmentDropDownDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andReturn();

        verify(departmentService).dropDownList();
    }

    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_DEPARTMENT + DROPDOWN + ACTIVE;

        List<DepartmentDropDownDTO> departmentDropDownDTOS = DepartmentTestUtils
                .departmentDropDownDTOS();

        given(departmentService.activeDropDownList())
                .willReturn(departmentDropDownDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andReturn();

        verify(departmentService).activeDropDownList();
    }


}
