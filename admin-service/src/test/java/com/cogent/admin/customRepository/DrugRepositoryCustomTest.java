package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.response.drug.DrugMinimalResponseDTO;
import com.cogent.admin.dto.response.drug.DrugResponseDTO;
import com.cogent.admin.repository.DrugRepository;
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

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DrugRepositoryCustomTest {
    @Autowired
    DrugRepository repository;

    @Test
    public void testFetchDrugByNameOrCode() {
        List<Object[]> objects = repository
                .fetchDrugByNameOrCode("Test", "TST");

        assertTrue(objects.size() == 0);
    }

    @Test
    public void testFetchDropDownList() {
        Optional<List<DropDownResponseDTO>> dropDownList = repository
                .fetchDropDownList();

        assertNotNull(dropDownList);
    }

    @Test
    public void testFetchActiveDropDownList() {
        Optional<List<DropDownResponseDTO>> dropDownList = repository
                .fetchActiveDropDownList();

        assertNotNull(dropDownList);
    }

    @Test
    public void testFetchDrugDetail() {
        DrugResponseDTO responseDTO = repository
                .fetchDrugDetails(1L);

        assertNotNull(responseDTO);
    }

    @Test
    public void searchDrug() {
        Pageable pageable = PageRequest.of(1, 10);
        DrugSearchRequestDTO drugSearchRequestDTO = new DrugSearchRequestDTO();

        List<DrugMinimalResponseDTO> minimalResponseDTOS = repository
                .searchDrug(drugSearchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }
}
