package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeMinimalResponseDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeResponseDTO;
import com.cogent.admin.repository.BillingModeRepository;
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

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author smriti on 2019-10-22
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BillingModeRepositoryCustomTest {

    @Autowired
    BillingModeRepository repository;

    @Test
    public void findBillingModeByNameAndCode() {
        List<Object[]> objects = repository.findBillingModeByNameOrCode("Test", "Test");

        assertTrue(objects.size() == 0);
    }

    @Test
    public void searchBillingMode() {
        Pageable pageable = PageRequest.of(1, 10);

        BillingModeSearchRequestDTO searchRequestDTO = new BillingModeSearchRequestDTO();

        List<BillingModeMinimalResponseDTO> minimalResponseDTOs = repository.searchBillingMode(searchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOs);
    }

    @Test
    public void activeDropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = repository
                .activeDropDownList();

        assertTrue(!minimalResponseDTOS.equals(Optional.empty()));
    }

    @Test
    public void dropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = repository
                .dropDownList();

        assertTrue(!minimalResponseDTOS.equals(Optional.empty()));
    }

    @Test
    public void fetchBillingModeById() {
        BillingModeResponseDTO responseDTO = repository.fetchBillingModeDetails(1L);

        assertNotNull(responseDTO);
    }

}
