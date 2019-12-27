package com.cogent.admin.customRepository;

import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import com.cogent.admin.dto.response.district.DistrictDropDownResponseDTO;
import com.cogent.admin.dto.response.district.DistrictMinimalResponseDTO;
import com.cogent.admin.dto.response.district.DistrictResponseDTO;
import com.cogent.admin.repository.DistrictRepository;
import com.cogent.persistence.model.District;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DistrictRepositoryCustomTest {
    @Autowired
    DistrictRepository districtRepository;

    @Test
    public void testFectDistrictCountByName(){
        Long count=districtRepository
                .fetchDistrictCountByName("Test");

        assertTrue(count == 0L);
    }

    @Test
    public void testFetchDropDownList(){
        Optional<List<DistrictDropDownResponseDTO>> dropDownResponseDTO=districtRepository
                .fetchDropDownList();

        assertNotNull(dropDownResponseDTO);
    }

    @Test
    public void testFetchActiveDropDownList(){
        Optional<List<DistrictDropDownResponseDTO>> dropDownResponseDTO=districtRepository
                .fetchActiveDropDownList();

        assertNotNull(dropDownResponseDTO);
    }

    @Test
    public void testFetchServiceModeDetail() {
        DistrictResponseDTO responseDTO = districtRepository
                .fetchDistrictDetails(1L);

        Assert.assertNotNull(responseDTO);
    }

    @Test
    public void testSearchServiceMode() {
        Pageable pageable = PageRequest.of(1, 10);
       DistrictSearchRequestDTO searchRequestDTO = new DistrictSearchRequestDTO();

        List<DistrictMinimalResponseDTO> minimalResponseDTOS = districtRepository
                .searchDistrict(searchRequestDTO, pageable);

        Assert.assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void testFetchDistrictById() {
        Optional<District> district = districtRepository
                .fetchDistrictById(1L);

        Assert.assertNotNull(district);
    }




}
