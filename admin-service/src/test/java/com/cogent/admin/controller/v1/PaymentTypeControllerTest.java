package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.service.PaymentTypeService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.List;

import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.PaymentTypeConstants.BASE_PAYMENT_TYPE;
import static com.cogent.admin.dto.paymentmode.PaymentTypeTestUtils.getPaymentTypeDropDownInfo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi Thapa 12/11/19
 */
public class PaymentTypeControllerTest {
    @Mock
    private PaymentTypeService service;

    @InjectMocks
    private PaymentTypeController controller;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void Unit() throws Exception {
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_PAYMENT_TYPE + DROPDOWN;
        List<DropDownResponseDTO> dropDownInfo = getPaymentTypeDropDownInfo();


        given(service.fetchDropDownList())
                .willReturn(dropDownInfo);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownInfo.size())));

        verify(service).fetchDropDownList();
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_PAYMENT_TYPE + DROPDOWN + ACTIVE;
        List<DropDownResponseDTO> unitDropDownResponseDTO = getPaymentTypeDropDownInfo();


        given(service.fetchActiveDropDownList())
                .willReturn(unitDropDownResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(unitDropDownResponseDTO.size())));

        verify(service).fetchActiveDropDownList();
    }
}
