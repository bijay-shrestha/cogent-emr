package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.admin.*;
import com.cogent.admin.service.AdminService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.AdminConstants.*;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.admin.AdminRequestUtils.*;
import static com.cogent.admin.dto.admin.AdminResponseUtils.*;
import static com.cogent.admin.dto.files.MultipartFileUtils.getMockMultipartFile;
import static com.cogent.admin.utils.FileUtils.configureMultipartForPutMethod;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author smriti on 2019-08-19
 */
public class AdminControllerTest {
    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        BASE_URL = API_V1 + BASE_ADMIN;
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(adminService).save(any(AdminRequestDTO.class), any(), null);
        mockMvc.perform(MockMvcRequestBuilders.multipart(URL)
                .param("files", writeObjectToJson(getMockMultipartFile().getBytes()))
                .param("request", writeObjectToJson(getAdminRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(adminService, times(1)).save(any(AdminRequestDTO.class), any(), null);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void update() throws Exception {
        String URL = BASE_URL;

        MockMultipartHttpServletRequestBuilder builder = configureMultipartForPutMethod(URL);

        doNothing().when(adminService).update(any(AdminUpdateRequestDTO.class), any());
        mockMvc.perform(builder
                .file(getMockMultipartFile())
                .param("request", writeObjectToJson(getAdminUpdateRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(adminService, times(1)).update(any(AdminUpdateRequestDTO.class), any());
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void activeDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(adminService.fetchActiveAdminsForDropdown()).willReturn(getAdminForDropdown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(getAdminForDropdown().size())))
                .andReturn();

        verify(adminService, times(1)).fetchActiveAdminsForDropdown();
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(adminService.search(any(AdminSearchRequestDTO.class), any(Pageable.class)))
                .willReturn(getAdminMinimalResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getAdminSearchRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.hasSize(getAdminMinimalResponseDTO().size())))
                .andReturn();

        verify(adminService, times(1)).search
                (any(AdminSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void fetchDetails() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;
        Long id = 1L;

        given(adminService.fetchDetailsById(id)).willReturn(getAdminDetailResponseDTO());

        mockMvc.perform(get(URL, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(adminService, times(1)).fetchDetailsById(id);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void delete() throws Exception {
        String URL = BASE_URL;

        doNothing().when(adminService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(adminService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void changePassword() throws Exception {
        String URL = BASE_URL + CHANGE_PASSWORD;
        doNothing().when(adminService).changePassword(any(AdminChangePasswordRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getAdminPasswordRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(adminService, times(1)).changePassword
                (any(AdminChangePasswordRequestDTO.class));
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void updateAvatar() throws Exception {
        String URL = BASE_URL + AVATAR;

        MockMultipartHttpServletRequestBuilder builder = configureMultipartForPutMethod(URL);

        doNothing().when(adminService).updateAvatar(any(), anyLong());

        mockMvc.perform(builder
                .file(getMockMultipartFile())
                .param("adminId", writeObjectToJson(1L)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(adminService, times(1)).updateAvatar(any(), anyLong());
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void verifyConfirmationToken() throws Exception {
        String URL = BASE_URL + VERIFY;
        String confirmationToken = getConfirmationToken();

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .param("token", confirmationToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(adminService, times(1)).verifyConfirmationToken(confirmationToken);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void savePassword() throws Exception {
        String URL = BASE_URL + BASE_PASSWORD;

        doNothing().when(adminService).savePassword(any(AdminPasswordRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getPasswordRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(adminService, times(1)).savePassword(any(AdminPasswordRequestDTO.class));
        verifyNoMoreInteractions(adminService);
    }
}
