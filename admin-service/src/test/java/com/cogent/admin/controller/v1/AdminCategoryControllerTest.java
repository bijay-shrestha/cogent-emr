package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryUpdateRequestDTO;
import com.cogent.admin.service.AdminCategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.AdminCategoryConstants.BASE_ADMIN_CATEGORY;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.adminCategory.AdminCategoryRequestUtils.*;
import static com.cogent.admin.dto.adminCategory.AdminCategoryResponseUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author smriti on 2019-08-11
 */
public class AdminCategoryControllerTest {

    @InjectMocks
    private AdminCategoryController adminCategoryController;

    @Mock
    private AdminCategoryService adminCategoryService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminCategoryController).build();
        BASE_URL = API_V1 + BASE_ADMIN_CATEGORY;
    }

    @Test
    public void saveTest() throws Exception {
        Should_Throw_Exception_When_InputIsInvalidForSave();
        save();
    }

    @Test
    public void updateTest() throws Exception {
        Should_Throw_Exception_When_InputIsInvalidForUpdate();
        update();
    }

    @Test
    public void deleteTest() throws Exception {
        Should_Throw_Exception_When_InputIsInvalidForDelete();
        delete();
    }

    @Test
    public void searchTest() throws Exception {
        search();
    }

    @Test
    public void detailsTest() throws Exception {
        fetchDetails();
    }

    @Test
    public void excelTest() throws Exception {
        downloadExcel();
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForSave() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForSave())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(adminCategoryService).save(any(AdminCategoryRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getAdminCategoryRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(adminCategoryService, times(1)).save(any(AdminCategoryRequestDTO.class));
        verifyNoMoreInteractions(adminCategoryService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForUpdate() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForUpdate())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        doNothing().when(adminCategoryService).update(any(AdminCategoryUpdateRequestDTO.class));

        mockMvc.perform(put(URL)
                .content(writeObjectToJson(getAdminCategoryRequestDTOForUpdate()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(adminCategoryService, times(1)).update(any(AdminCategoryUpdateRequestDTO.class));
        verifyNoMoreInteractions(adminCategoryService);
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForDelete() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getInvalidInputForDelete())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        doNothing().when(adminCategoryService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(adminCategoryService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(adminCategoryService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(adminCategoryService.search(any(AdminCategorySearchRequestDTO.class), any(Pageable.class)))
                .willReturn(fetchMinimalAdminCategoryList());

        mockMvc.perform(put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getAdminCategoryRequestDTOForSearch())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchMinimalAdminCategoryList().size())))
                .andReturn();

        verify(adminCategoryService, times(1)).search
                (any(AdminCategorySearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(adminCategoryService);
    }

    @Test
    public void fetchAdminCategoryForDropdown() throws Exception {
        String URL = BASE_URL  + DROPDOWN + ACTIVE;

        given(adminCategoryService.fetchActiveAdminCategoryForDropDown()).willReturn(fetchAdminCategoryListForDropDown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(fetchAdminCategoryListForDropDown().size())))
                .andReturn();

        verify(adminCategoryService, times(1)).fetchActiveAdminCategoryForDropDown();
        verifyNoMoreInteractions(adminCategoryService);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS +ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(adminCategoryService.fetchDetailsById(id)).willReturn(fetchAdminCategoryDetailById());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(adminCategoryService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(adminCategoryService);
    }

    @Test
    public void downloadExcel() throws Exception {
        String URL = BASE_URL +  EXCEL;

        MockHttpServletResponse response = new MockHttpServletResponse();
        doNothing().when(adminCategoryService).createExcelInByteArray(any(response.getClass()));

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(adminCategoryService, times(1)).createExcelInByteArray(any(response.getClass()));
        verifyNoMoreInteractions(adminCategoryService);
    }
}
