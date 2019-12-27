package com.cogent.admin.customRepository;

import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import com.cogent.admin.dto.response.provinces.ProvincesDropDownResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesMinimalResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesResponseDTO;
import com.cogent.admin.repository.ProvincesRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProvincesRepositoryCustomTest {

    @Autowired
    private ProvincesRepository provincesRepository;


    @Test
    public void testFetchProvinceByName(){
        Long count=provincesRepository
                .fetchCountByName("Sauravi");

        assertTrue(count == 0L);
    }

    @Test
    public void testFetchDropDownList(){
        Optional<List<ProvincesDropDownResponseDTO>> dropDownResponseDTO=provincesRepository
                .fetchDropDownList();

        assertNotNull(dropDownResponseDTO);
    }

    @Test
    public void testFetchActiveDropDownList(){
        Optional<List<ProvincesDropDownResponseDTO>> dropDownResponseDTO=provincesRepository
                .fetchActiveDropDownList();

        assertNotNull(dropDownResponseDTO);
    }

    @Test
    public void testSearchServiceMode() {
        Pageable pageable = PageRequest.of(1, 10);
        ProvincesSearchRequestDTO searchRequestDTO = new ProvincesSearchRequestDTO();

        List<ProvincesMinimalResponseDTO> minimalResponseDTOS = provincesRepository
                .searchProvinces(searchRequestDTO, pageable);

        Assert.assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void testFetchServiceModeDetail() {
        ProvincesResponseDTO responseDTO = provincesRepository
                .fetchProvincesDetails(1L);

        Assert.assertNotNull(responseDTO);
    }
}
