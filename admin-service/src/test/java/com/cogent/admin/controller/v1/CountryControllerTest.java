package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.country.CountryResponseUtils;
import com.cogent.admin.service.CountryService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.CountryConstants.BASE_COUNTRY;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author smriti on 08/11/2019
 */
public class CountryControllerTest {
    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
        BASE_URL = API_V1 + BASE_COUNTRY;
    }

    @Test
    public void fetchActiveCountry() throws Exception {
        String URL = BASE_URL;

        given(countryService.fetchActiveCountry()).willReturn(CountryResponseUtils.fetchActiveCountries());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(countryService, times(1)).fetchActiveCountry();
        verifyNoMoreInteractions(countryService);
    }
}
