package com.cogent.admin.controller.v1;

import com.cogent.admin.service.BillTypeService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.BillTypeConstants.BASE_BILL_TYPE;
import static com.cogent.admin.dto.billType.BillTypeResponseUtils.fetchActiveBillTypes;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author smriti on 2019-10-22
 */
public class BillTypeControllerTest {

    @InjectMocks
    private BillTypeController billTypeController;

    @Mock
    private BillTypeService billTypeService;

    private static MockMvc mockMvc;

    private String BASE_URL;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(billTypeController).build();
        BASE_URL = API_V1 + BASE_BILL_TYPE;
    }

    @Test
    public void fetchActiveBillType() throws Exception {
        String URL = BASE_URL ;

        given(billTypeService.fetchActiveBillType()).willReturn(fetchActiveBillTypes());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(billTypeService, times(1)).fetchActiveBillType();
        verifyNoMoreInteractions(billTypeService);
    }

}
