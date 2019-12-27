package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.category.CategoryRequestDTO;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.request.category.CategoryUpdateRequestDTO;
import com.cogent.admin.service.CategoryService;
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
import static com.cogent.admin.constants.WebResourceKeyConstants.CategoryConstants.BASE_CATEGORY;
import static com.cogent.admin.dto.category.CategoryTestUtils.*;
import static com.cogent.admin.utils.ObjectToJSONUtils.writeObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sauravi Thapa 10/24/19
 */

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;


    @InjectMocks
    private CategoryController categoryController;

    private static MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void ethnicity() throws Exception {
        create();
        delete();
        update();
        search();
        fetchCategory();
        fetchCategoryDetails();
        dropDownList();
        activeDropDownList();
    }

    @Test
    public void create() throws Exception {
        String URL = API_V1 + BASE_CATEGORY;

        CategoryRequestDTO categoryRequestDTO = getCategoryRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(categoryRequestDTO)))
                .andExpect(status().isCreated());

        verify(categoryService).createCategory(categoryRequestDTO);
    }

    @Test
    public void delete() throws Exception {
        String URL = API_V1 + BASE_CATEGORY;

        DeleteRequestDTO deleteRequestDTO = getDeleteReuqestDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(deleteRequestDTO)))
                .andExpect(status().isOk());


        verify(categoryService).deleteCategory(any(DeleteRequestDTO.class));
    }


    @Test
    public void update() throws Exception {
        String URL = API_V1 + BASE_CATEGORY;

        CategoryUpdateRequestDTO categoryUpdateRequestDTO = getCategoryUpdateRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(categoryUpdateRequestDTO)))
                .andExpect(status().isOk());


        verify(categoryService).updateCategory(any(CategoryUpdateRequestDTO.class));
    }

    @Test
    public void search() throws Exception {
        String URL = API_V1 + BASE_CATEGORY + SEARCH;
        Pageable pageable = PageRequest.of(1, 10);

        CategorySearchRequestDTO categorySearchRequestDTO = getCategorySearchRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "?page=1&size=10")
                .contentType(APPLICATION_JSON_UTF8)
                .content(writeObjectToJson(categorySearchRequestDTO)))
                .andExpect(status().isOk());


        verify(categoryService).searchCategory(categorySearchRequestDTO, pageable);
    }

    @Test
    public void fetchCategory() throws Exception {
        String URL = API_V1 + BASE_CATEGORY + ID_PATH_VARIABLE_BASE;

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());

        verify(categoryService).fetchActiveCategoryEntity(1L);
    }

    @Test
    public void fetchCategoryDetails() throws Exception {
        String URL = API_V1 + BASE_CATEGORY + DETAILS + ID_PATH_VARIABLE_BASE;

        given(categoryService.fetchCategoryDetails(1L))
                .willReturn((getCategoryResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders.get(URL, 1L))
                .andExpect(status().isOk());


        verify(categoryService).fetchCategoryDetails(1L);
    }

    @Test
    public void dropDownList() throws Exception {
        String URL = API_V1 + BASE_CATEGORY + DROPDOWN;
        List<DropDownResponseDTO> dropDownResponseDTOS = getDropDownInfo();


        given(categoryService.fetchDropDownList())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));


        verify(categoryService).fetchDropDownList();
    }


    @Test
    public void activeDropDownList() throws Exception {
        String URL = API_V1 + BASE_CATEGORY + DROPDOWN + ACTIVE;
        List<DropDownResponseDTO> dropDownResponseDTOS = getDropDownInfo();

        given(categoryService.fetchActiveDropDownList())
                .willReturn(dropDownResponseDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        IsCollectionWithSize.hasSize(dropDownResponseDTOS.size())));

        verify(categoryService).fetchActiveDropDownList();
    }


}
