package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.response.ward.WardMinimalResponseDTO;
import com.cogent.admin.dto.response.ward.WardResponseDTO;
import com.cogent.admin.repository.WardRepository;
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
 * @author Sauravi  on 10/2/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class WardRepositoryCustomTest {

    @Autowired
    WardRepository wardRepository;

    @Test
    public void fetchWardByNameOrCode() {
        List<Object[]> objects = wardRepository
                .fetchWardByNameOrCode("Test", "tst");
        assertTrue(objects.size() == 0);
    }

    @Test
    public void checkIfWardNameAndCodeExists() {
        List<Object[]> objects = wardRepository
                .checkIfWardNameAndCodeExists(0L,"Test", "tst");
        assertTrue(objects.size() == 0);
    }

    @Test
    public void searchWard() {
        Pageable pageable = PageRequest.of(1, 10);

        WardSearchRequestDTO wardSearchRequestDTO=new WardSearchRequestDTO();

        List<WardMinimalResponseDTO> minimalResponseDTOS = wardRepository
                .searchWard( wardSearchRequestDTO,pageable);

        assertNotNull(minimalResponseDTOS);
    }


    @Test
    public void fetchWardDetail() {
        WardResponseDTO responseDTO = wardRepository
                .fetchWardDetails(2L);

        assertNotNull(responseDTO);
    }

    @Test
    public void activeDropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = wardRepository
                .activeDropDownList();

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void dropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = wardRepository
                .dropDownList();

        assertNotNull(minimalResponseDTOS);
    }


}
