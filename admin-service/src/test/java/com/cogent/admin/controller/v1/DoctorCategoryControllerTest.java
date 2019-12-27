package com.cogent.admin.controller.v1;


import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategoryUpdateRequestDTO;
import com.cogent.admin.service.impl.DoctorCategoryServiceImpl;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.DoctorCategoryConstants.BASE_DOCTOR_CATEGORY;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.doctorcategory.DoctorCategoryTestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoctorCategoryControllerTest {
    @Mock
    private DoctorCategoryServiceImpl service;

    @InjectMocks
    private DoctorCategoryController controller;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void doctorCategory() throws Exception {
        create();
        delete();
        update();
        search();
        fetchDoctorCategoryDetail();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_DOCTOR_CATEGORY;

        DoctorCategoryRequestDTO requestDTO = getDoctorCategoryRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(requestDTO)))
                .andExpect(status().isCreated());

        verify(service).createDoctorCategory(requestDTO);
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_DOCTOR_CATEGORY;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(service).deleteDoctorCategory(any(DeleteRequestDTO.class));
    }

    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_DOCTOR_CATEGORY;

        DoctorCategoryUpdateRequestDTO requestDTO = getDoctorCategoryUpdateRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(requestDTO)))
                .andExpect(status().isOk());


        verify(service).updateDoctorCategory(any(DoctorCategoryUpdateRequestDTO.class));

    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_DOCTOR_CATEGORY + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        DoctorCategorySearchRequestDTO requestDTO =getDoctorCategorySearchRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(ObjectToJSONUtils.writeObjectToJson(requestDTO)))
                .andExpect(status().isOk());


        verify(service).searchDoctorCategory(requestDTO, pageable);
    }

    @Test
    public void fetchDoctorCategoryDetail() throws Exception {
        String URL = API_V1 + BASE_DOCTOR_CATEGORY + DETAILS + ID_PATH_VARIABLE_BASE;


        given(service.fetchDoctorCategoryDetails(1L))
                .willReturn(getDoctorCategoryResponseDTO());


        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(service).fetchDoctorCategoryDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_DOCTOR_CATEGORY + DROPDOWN;
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();


        given(service.doctorCategoryDropdown())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(service).doctorCategoryDropdown();
    }

    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_DOCTOR_CATEGORY + DROPDOWN + ACTIVE;
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(service.activeDoctorCategoryDropdown())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(service).activeDoctorCategoryDropdown();
    }
}
