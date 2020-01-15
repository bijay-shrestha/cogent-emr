package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.request.forgotPassword.ForgotPasswordRequestDTO;
import com.cogent.admin.service.ForgotPasswordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.ForgotPasswordConstants.*;
import static com.cogent.admin.dto.forgotPassword.ForgotPasswordRequestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author smriti on 2019-09-20
 */
public class ForgotPasswordControllerTest {
    @InjectMocks
    private ForgotPasswordController forgotPasswordController;

    @Mock
    private ForgotPasswordService forgotPasswordService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(forgotPasswordController).build();
        BASE_URL = API_V1 + BASE_PASSWORD;
    }

    @Test
    public void forgotPasswordTest() throws Exception {
        String URL = BASE_URL + FORGOT;

        mockMvc.perform(post(URL)
                .param("username", getUsernameOrEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(forgotPasswordService, times(1)).forgotPassword(getUsernameOrEmail());
        verifyNoMoreInteractions(forgotPasswordService);
    }

    @Test
    public void verifyResetCodeTest() throws Exception {
        String URL = BASE_URL + VERIFY;

        mockMvc.perform(get(URL)
                .param("resetCode", getResetCode())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(forgotPasswordService, times(1)).verify(getResetCode());
        verifyNoMoreInteractions(forgotPasswordService);
    }

    @Test
    public void updatePassword() throws Exception {
        String URL = BASE_URL;

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeObjectToJson(getForgotPasswordRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        verify(forgotPasswordService, times(1))
                .updatePassword(any(ForgotPasswordRequestDTO.class));
        verifyNoMoreInteractions(forgotPasswordService);
    }
}
