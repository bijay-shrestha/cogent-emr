package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileRequestUtils;
import com.cogent.admin.dto.request.profile.ProfileSearchRequestDTO;
import com.cogent.admin.dto.request.profile.ProfileUpdateRequestDTO;
import com.cogent.admin.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.ProfileSetupConstants.BASE_PROFILE;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.CommonRequestUtils.getInvalidInputForDelete;
import static com.cogent.admin.dto.adminCategory.AdminCategoryRequestUtils.getInvalidInputForUpdate;
import static com.cogent.admin.dto.profile.ProfileResponseUtils.*;
import static com.cogent.admin.dto.request.profile.ProfileRequestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author smriti on 7/2/19
 */
public class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
        BASE_URL = API_V1 + BASE_PROFILE;
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
        fetchDetailsById();
    }

    @Test
    public void activeDropDownTest() {
        activeDropDownTest();
    }

    @Test
    public void Should_Throw_Exception_When_InputIsInvalidForSave() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(ProfileRequestUtils.getInvalidInputForSave())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void save() throws Exception {
        String URL = BASE_URL;

        doNothing().when(profileService).save(any(ProfileRequestDTO.class));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getProfileRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        verify(profileService, times(1)).save(any(ProfileRequestDTO.class));
        verifyNoMoreInteractions(profileService);
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

        doNothing().when(profileService).update(any(ProfileUpdateRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .content(writeObjectToJson(getProfileRequestDTOForUpdate()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(profileService, times(1)).update(any(ProfileUpdateRequestDTO.class));
        verifyNoMoreInteractions(profileService);
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

        doNothing().when(profileService).delete(any(DeleteRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .content(writeObjectToJson(getDeleteRequestDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(profileService, times(1)).delete(any(DeleteRequestDTO.class));
        verifyNoMoreInteractions(profileService);
    }

    @Test
    public void search() throws Exception {
        String URL = BASE_URL + SEARCH;

        given(profileService.search(any(ProfileSearchRequestDTO.class), any(Pageable.class)))
                .willReturn(getProfileMinimalResponseList());

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getProfileDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(getProfileMinimalResponseList().size())))
                .andReturn();

        verify(profileService, times(1)).search
                (any(ProfileSearchRequestDTO.class), any(Pageable.class));
        verifyNoMoreInteractions(profileService);
    }

    @Test
    public void fetchDetailsById() throws Exception {
        String URL = BASE_URL + DETAILS + ID_PATH_VARIABLE_BASE;

        given(profileService.fetchDetailsById(1L)).willReturn(getProfileDetailResponse());

        mockMvc.perform(get(URL, 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(profileService, times(1)).fetchDetailsById(1L);
        verifyNoMoreInteractions(profileService);
    }

    @Test
    public void fetchProfileForDropdown() throws Exception {
        String URL = BASE_URL + DROPDOWN + ACTIVE;

        given(profileService.fetchActiveProfilesForDropdown()).willReturn(getProfilesForDropdown());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        hasSize(getProfilesForDropdown().size())))
                .andReturn();

        verify(profileService, times(1)).fetchActiveProfilesForDropdown();
        verifyNoMoreInteractions(profileService);
    }
}
