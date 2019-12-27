package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.weekDays.WeekDaysResponseUtils;
import com.cogent.admin.service.WeekDaysService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.WeekDaysConstants.BASE_WEEK_DAYS;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author smriti on 25/11/2019
 */
public class WeekDaysControllerTest {

    @InjectMocks
    private WeekDaysController weekDaysController;

    @Mock
    private WeekDaysService weekDaysService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weekDaysController).build();
        BASE_URL = API_V1 + BASE_WEEK_DAYS;
    }

    @Test
    public void fetchActiveWeekDays() throws Exception {
        String URL = BASE_URL;

        given(weekDaysService.fetchActiveWeekDays()).willReturn(WeekDaysResponseUtils.fetchActiveWeekDays());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(weekDaysService, times(1)).fetchActiveWeekDays();
        verifyNoMoreInteractions(weekDaysService);
    }
}
