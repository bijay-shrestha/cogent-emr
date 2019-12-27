package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyMinimalResponseDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.InsuranceCompanyRepository;
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
 * @author Sauravi  on 2019-09-20
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class InsuranceCompanyRepositoryCustomTest {

    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;

    @Test
    public void testSearchInsuranceCompany() {
        Pageable pageable = PageRequest.of(1, 10);

        List<InsuranceCompanyMinimalResponseDTO> minimalResponseDTOS = insuranceCompanyRepository
                .searchInsuranceCompany(new InsuranceCompanySearchRequestDTO(), pageable);

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void testActiveDropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = insuranceCompanyRepository
                .fetchActiveDropDownList();

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void testDropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = insuranceCompanyRepository
                .fetchDropDownList();

        assertNotNull(minimalResponseDTOS);
    }

    @Test(expected = NoContentFoundException.class)
    public void testFetchInsuranceCompanyDetail_InsuranceCompanyControllerThrowsException() {
        insuranceCompanyRepository.fetchInsuranceCompanyDetails(0L);
    }

    @Test
    public void testFetchInsuranceCompanyDetail() {
        InsuranceCompanyResponseDTO responseDTO = insuranceCompanyRepository
                .fetchInsuranceCompanyDetails(1L);

        assertNotNull(responseDTO);
    }
}
