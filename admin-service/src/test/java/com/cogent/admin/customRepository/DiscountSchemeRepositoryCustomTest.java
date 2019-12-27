package com.cogent.admin.customRepository;

import com.cogent.admin.dto.request.discountscheme.DiscountSchemeSearchRequestDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeMinimalResponseDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DepartmentDiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.ServiceDiscountDropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DiscountSchemeRepository;
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
public class DiscountSchemeRepositoryCustomTest {
    @Autowired
    DiscountSchemeRepository repository;

    @Test
    public void fetchDiscountSchemeByNameOrCode() {
        List<Object[]> object = repository.fetchDiscountSchemeByNameOrCode("Test","tst");

        assertTrue(object.size() == 0);
    }

    @Test
    public void checkIfDiscountSchemeNameAndCodeExists() {
        List<Object[]> object = repository.checkIfDiscountSchemeNameAndCodeExists(1L,"Test","tst");

        assertTrue(object.size() == 0);
    }

    @Test
    public void searchDiscountScheme() {

        Pageable pageable = PageRequest.of(1, 10);

        List<DiscountSchemeMinimalResponseDTO> minimalResponseDTOS=repository
                .searchDiscountScheme(new DiscountSchemeSearchRequestDTO(),pageable);

        assertNotNull(minimalResponseDTOS);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDiscountSchemeDetails_ThrowsException() {
        repository.fetchDiscountSchemeDetails(0L);
    }

    @Test
    public void fetchDiscountSchemeDetails() {

        DiscountSchemeResponseDTO schemeResponseDTO=repository.fetchDiscountSchemeDetails(1L);

        assertNotNull(schemeResponseDTO);
    }

    @Test
    public void activeDropDownListWithNetDiscount() {
        Optional<List<DiscountDropDownResponseDTO>> minimalResponseDTOS = repository
                .activeDropDownListWithNetDiscount();

        Assert.assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void dropDownListWithNetDiscount() {
        Optional<List<DiscountDropDownResponseDTO>> minimalResponseDTOS = repository
                .dropDownListWithNetDiscount();

        Assert.assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void activeDropDownListByDepartmentId() {
        Optional<List<DepartmentDiscountDropDownResponseDTO>> minimalResponseDTOS = repository
                .activeDropDownListByDepartmentId(2L);

        Assert.assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void dropDownListByDepartmentId() {
        Optional<List<DepartmentDiscountDropDownResponseDTO>> minimalResponseDTOS = repository
                .dropDownListByDepartmentId(2L);

        Assert.assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void activeDropDownListByFacilityId() {
        Optional<List<ServiceDiscountDropDownResponseDTO>> minimalResponseDTOS = repository
                .activeDropDownListByServiceId(1L);

        Assert.assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void dropDownListByFacilityId() {
        Optional<List<ServiceDiscountDropDownResponseDTO>> minimalResponseDTOS = repository
                .dropDownListByServiceId(1L);

        Assert.assertNotNull(minimalResponseDTOS);
    }

}
