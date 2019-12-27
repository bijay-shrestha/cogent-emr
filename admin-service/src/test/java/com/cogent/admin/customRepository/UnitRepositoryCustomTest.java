package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.response.unit.UnitMinimalResponseDTO;
import com.cogent.admin.dto.response.unit.UnitResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.UnitRepository;
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
 * @author Sauravi  on 10/13/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UnitRepositoryCustomTest {

    @Autowired
    UnitRepository unitRepository;

    @Test
    public void fetchUnitByNameOrCode() {
        List<Object[]> objects = unitRepository
                .fetchUnitByNameOrCode("Test", "tst");
        assertTrue(objects.size() == 0);
    }

    @Test
    public void checkUnitNameAndCodeIfExist() {
        List<Object[]> objects = unitRepository
                .checkUnitNameAndCodeIfExist(0L,"Test", "tst");
        assertTrue(objects.size() == 0);
    }

    @Test
    public void searchUnit() {
        Pageable pageable = PageRequest.of(1, 10);

        UnitSearchRequestDTO searchRequestDTO = new UnitSearchRequestDTO();

       List<UnitMinimalResponseDTO> minimalResponseDTOS = unitRepository
                .searchUnit(searchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchUnitDetail_ThrowsException() {
        unitRepository.fetchUnitDetails(0L);
    }

    @Test
    public void fetchUnitDetail() {
        UnitResponseDTO responseDTO = unitRepository
                .fetchUnitDetails(2L);

        assertNotNull(responseDTO);
    }

    @Test
    public void activeDropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = unitRepository
                .activeDropDownList();

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void dropDownList() {
        Optional<List<DropDownResponseDTO>> minimalResponseDTOS = unitRepository
                .dropDownList();

        assertNotNull(minimalResponseDTOS);
    }


}
