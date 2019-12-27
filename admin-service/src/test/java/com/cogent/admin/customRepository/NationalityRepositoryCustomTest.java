package com.cogent.admin.customRepository;

import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import com.cogent.admin.dto.response.nationality.NationalityDropDownResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityMinimalResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.NationalityRepository;
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

import static org.junit.Assert.assertNotNull;


/**
 * @author Sauravi  on 2019-08-25
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class NationalityRepositoryCustomTest {

    @Autowired
    NationalityRepository nationalityRepository;

    @Test
    public void testSearchNationality() {
        Pageable pageable = PageRequest.of(1, 10);
        NationalitySearchRequestDTO nationalitySearchRequestDTO = new NationalitySearchRequestDTO();

        List<NationalityMinimalResponseDTO> minimalResponseDTOS = nationalityRepository
                .searchNationality(nationalitySearchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void testActiveDropDownList() {
        Optional<List<NationalityDropDownResponseDTO>> minimalResponseDTOS = nationalityRepository
                .fetchActiveDropDownList();

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void testDropDownList() {
        Optional<List<NationalityDropDownResponseDTO>> minimalResponseDTOS = nationalityRepository
                .fetchDropDownList();

        assertNotNull(minimalResponseDTOS);
    }

    @Test(expected = NoContentFoundException.class)
    public void testFetchNationalityDetail_nationalityControllerThrowsException() {
        nationalityRepository.fetchNationalityDetails(0L);
    }

    @Test
    public void testFetchNationalityDetail() {
        NationalityResponseDTO responseDTO = nationalityRepository
                .fetchNationalityDetails(1L);

        assertNotNull(responseDTO);
    }


}
