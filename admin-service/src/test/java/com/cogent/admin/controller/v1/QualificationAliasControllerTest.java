package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.qualificationAlias.QualificationAliasResponseUtils;
import com.cogent.admin.service.QualificationAliasService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.QualificationAliasConstants.BASE_QUALIFICATION_ALIAS;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author smriti on 11/11/2019
 */
public class QualificationAliasControllerTest {

    @InjectMocks
    private QualificationAliasController qualificationAliasController;

    @Mock
    private QualificationAliasService qualificationAliasService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(qualificationAliasController).build();
        BASE_URL = API_V1 + BASE_QUALIFICATION_ALIAS;
    }
    @Test
    public void fetchActiveQualificationAlias() throws Exception {
        String URL = BASE_URL;

        given(qualificationAliasService.fetchActiveQualificationAlias())
                .willReturn(QualificationAliasResponseUtils.fetchActiveQualificationAlias());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(qualificationAliasService, times(1)).fetchActiveQualificationAlias();
        verifyNoMoreInteractions(qualificationAliasService);
    }
}
