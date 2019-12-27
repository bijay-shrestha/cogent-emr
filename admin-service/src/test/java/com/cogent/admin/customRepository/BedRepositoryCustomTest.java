package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.response.bed.BedMinimalResponseDTO;
import com.cogent.admin.dto.response.bed.BedResponseDTO;
import com.cogent.admin.repository.BedRepository;
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
import static org.junit.Assert.assertTrue;

/**
 * @author Sauravi Thapa 11/1/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BedRepositoryCustomTest {

    @Autowired
    BedRepository bedRepository;

    @Test
    public void findBedByNameAndCode() {
        List<Object[]> objects = bedRepository.findBedByNameOrCode("Test", "Test");

        assertTrue(objects.size() == 0);
    }

    @Test
    public void checkBedNameAndCodeIfExist() {
        List<Object[]> objects = bedRepository
                .checkBedNameAndCodeIfExist(0L, "Test","Test");

        assertTrue(objects.size()==0);
    }

    @Test
    public void searchBed() {
        Pageable pageable = PageRequest.of(1, 10);

        BedSearchRequestDTO bedSearchRequestDTO = new BedSearchRequestDTO();

        List<BedMinimalResponseDTO> minimalResponseDTOs = bedRepository.searchBed(bedSearchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOs);
    }

    @Test
    public void activeDropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = bedRepository
                .activeDropDownList();

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void dropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = bedRepository
                .dropDownList();

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void fetchBedById() {
        BedResponseDTO bed = bedRepository
                .fetchBedDetails(1L);

        assertNotNull(bed);
    }

}
