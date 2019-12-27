package com.cogent.admin.customRepository;

import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityDropDownResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityMinimalResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.EthnicityRepository;
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

import static com.cogent.admin.dto.ethnicity.EthnicityTestUtils.getEthnicitySeacrhRequestDTO;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * @author Sauravi  on 2019-08-25
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class EthnicityRepositoryCustomTest {

    @Autowired
    EthnicityRepository ethnicityRepository;

    @Test(expected = NoContentFoundException.class)
    public void fetchEthnicityDetail_ThrowsException() {
        ethnicityRepository.fetchEthnicityDetails(0L);
    }

    @Test
    public void fetchEthnicityDetail() {
        EthnicityResponseDTO responseDTO = ethnicityRepository
                .fetchEthnicityDetails(1L);

        assertNotNull(responseDTO);
    }

    @Test
    public void activeDropDownList() {
        Optional<List<EthnicityDropDownResponseDTO>> minimalResponseDTOS = ethnicityRepository
                .activeDropDownList();

        assertTrue(!minimalResponseDTOS.equals(minimalResponseDTOS));
    }

    @Test
    public void dropDownList() {
        Optional<List<EthnicityDropDownResponseDTO>> minimalResponseDTOS = ethnicityRepository
                .dropDownList();

        assertTrue(!minimalResponseDTOS.equals(minimalResponseDTOS));
    }

    @Test
    public void fetchEthnicityByNameOrCode() {
        List<Object[]> objects = ethnicityRepository
                .fetchEthnicityByNameOrCode("Chhetrii", "Ckp");
        assertTrue(objects.size() == 0);
    }

    @Test
    public void checkEthnicityNameIfExist() {
        List<Object[]> objects = ethnicityRepository
                .checkEthnicityNameAndCodeIfExist(0L, "Chhetri","CHH");

        assertTrue(objects.size()==0);
    }

    @Test
    public void searchEthnicity() {
        Pageable pageable = PageRequest.of(1, 10);
        EthnicitySearchRequestDTO ethnicitySearchRequestDTO = getEthnicitySeacrhRequestDTO();

       List<EthnicityMinimalResponseDTO> minimalResponseDTOS = ethnicityRepository
                .searchEthnicity(ethnicitySearchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }

}
