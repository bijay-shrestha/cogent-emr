package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedMinimalResponseDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AssignBedRepository;
import com.cogent.persistence.model.AssignBed;
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
 * @author Sauravi Thapa 11/10/19
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AssignBedRepositoryCustomTest {

    @Autowired
    AssignBedRepository assignBedRepository;

    @Test
    public void fetchAssignBedById() {
        AssignBed assignBed = assignBedRepository.fetchAssignBedById(1L);

        assertNotNull(assignBed);
    }

    @Test
    public void searchAssignedBed() {
        Pageable pageable = PageRequest.of(1, 10);

        AssignBedSearchRequestDTO searchRequestDTO = new AssignBedSearchRequestDTO();

        List<AssignBedMinimalResponseDTO> minimalResponseDTOS = assignBedRepository
                .searchAssignedBed(searchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }


    @Test(expected = NoContentFoundException.class)
    public void fetchWardDetail_ThrowsException() {
        assignBedRepository.fetchAssignBedDetails(0L);
    }

    @Test
    public void fetchAssignedBedDetail() {
        AssignBedResponseDTO responseDTO = assignBedRepository.fetchAssignBedDetails(1L);

        assertNotNull(responseDTO);
    }

    @Test
    public void activeDropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = assignBedRepository
                .activeDropDownList(3L);

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void dropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = assignBedRepository
                .dropDownList(3L);

        assertNotNull(minimalResponseDTOS);
    }
    @Test
    public void checkIfBedExixts(){
        Long count=assignBedRepository.checkIfBedExixts(10L,10L,10L);

        assertTrue(count == 0);
    }

}
