package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankUpdateRequestDTO;
import com.cogent.admin.service.impl.RegisteredBankServiceImpl;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.RegisterdBankConstants.BASE_REGISTERED_BANK;
import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;

import static com.cogent.admin.dto.registeredBank.RegisteredBankTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author smriti on 2019-10-22
 */
public class RegisteredBankControllerTest {

    @Mock
    private RegisteredBankServiceImpl service;

    @InjectMocks
    private RegisteredBankController controller;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void RegisteredBank() throws Exception {
        create();
        delete();
        update();
        search();
        fetchRegisteredBankDetails();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_REGISTERED_BANK;


        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(getRegisteredBankRequestDTO())))
                .andExpect(status().isCreated());

        verify(service).createRegisteredBank(any(RegisteredBankRequestDTO.class));

    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_REGISTERED_BANK;

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(service).deleteRegisteredBank(any(DeleteRequestDTO.class));
    }


    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_REGISTERED_BANK;

        RegisteredBankUpdateRequestDTO updateRequestDTO = getUpdateRegisteredBankRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(updateRequestDTO)))
                .andExpect(status().isOk());


        verify(service).updateRegisteredBank(any(RegisteredBankUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_REGISTERED_BANK + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        RegisteredBankSearchRequestDTO searchRequestDTO = getSearchRegisteredBankRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(searchRequestDTO)))
                .andExpect(status().isOk());


        verify(service).searchRegisteredBank(any(RegisteredBankSearchRequestDTO.class), eq(pageable));

    }

    @Test
    public void fetchRegisteredBankDetails() throws Exception {
        String URL = API_V1 + BASE_REGISTERED_BANK + DETAILS + ID_PATH_VARIABLE_BASE;

        given(service.fetchRegisteredBankDetails(1L))
                .willReturn(getRegisteredBankResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(service).fetchRegisteredBankDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_REGISTERED_BANK + DROPDOWN;
        List<DropDownResponseDTO> dropDownInfo = getRegisteredBankDropDownInfo();


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
        String URL = API_V1 + BASE_REGISTERED_BANK + DROPDOWN + ACTIVE;

        given(service.fetchActiveDropDownList())
                .willReturn(getRegisteredBankDropDownInfo());

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(getRegisteredBankDropDownInfo().size())));


        verify(service).fetchActiveDropDownList();
    }

}
