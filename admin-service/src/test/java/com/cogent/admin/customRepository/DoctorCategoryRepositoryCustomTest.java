package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryResponseDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryMinimalResponseDTO;
import com.cogent.admin.repository.DoctorCategoryRepository;
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
public class DoctorCategoryRepositoryCustomTest {
    @Autowired
    DoctorCategoryRepository repository;

    @Test
    public void testFetchDrugByNameOrCode() {
        List<Object[]> objects = repository
                .fetchDoctorCategoryByNameOrCode("Test", "TST");

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
        DoctorCategoryResponseDTO responseDTO = repository
                .fetchDoctorCategoryDetails(1L);

        assertNotNull(responseDTO);
    }

    @Test
    public void searchDrug() {
        Pageable pageable = PageRequest.of(1, 10);
        DoctorCategorySearchRequestDTO  requestDTO=new DoctorCategorySearchRequestDTO();

        List<DoctorCategoryMinimalResponseDTO> minimalResponseDTOS = repository
                .searchDoctorCategory(requestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void testCheckDrugNameIfExist() {
        List<Object[]> count = repository
                .checkIfDoctorCategoryNameAndCodeExists(1L, "TEST","TST");

        assertTrue(count.size() == 0L);
    }
}
