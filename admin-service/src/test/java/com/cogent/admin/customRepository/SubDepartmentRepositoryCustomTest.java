package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentDropDownDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentResponseDTO;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.persistence.model.SubDepartment;
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
 * @author Sauravi
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class SubDepartmentRepositoryCustomTest {
    @Autowired
    private SubDepartmentRepository subDepartmentRepository;

    @Test
    public void fetchSubDepartmentByDepartmentId() {
        List<SubDepartment> subDepartmentList = subDepartmentRepository
                .fetchSubDepartmentByDepartmentId(1L);

        assertTrue(subDepartmentList.size() > 0);
    }

    @Test
    public void fetchSubDepartmentDTOByDepartmentId() {
        List<DeleteRequestDTO> subDepartmentList = subDepartmentRepository
                .fetchSubDepartmentToDeleteByDepartmentId(1L);

        assertTrue(subDepartmentList.size() > 0);
    }


    @Test
    public void searchSubDepartment_ShouldReturnAllSubDepartment() {
        Pageable pageable = PageRequest.of(1, 10);
        SubDepartmentSearchRequestDTO subDepartmentRequestDTO = new SubDepartmentSearchRequestDTO();

        List<SubDepartmentMinimalResponseDTO> subDepartmentResponseDTOS = subDepartmentRepository
                .searchSubDepartment(subDepartmentRequestDTO, pageable);

        assertNotNull(subDepartmentResponseDTOS);
    }

    @Test
    public void searchSubDepartment_ShouldSubDepartment() {
        Pageable pageable = PageRequest.of(1, 10);
        SubDepartmentSearchRequestDTO subDepartmentRequestDTO = SubDepartmentSearchRequestDTO.builder().id(1L).build();

        List<SubDepartmentMinimalResponseDTO> subDepartmentResponseDTOS = subDepartmentRepository
                .searchSubDepartment(subDepartmentRequestDTO, pageable);

        assertNotNull(subDepartmentResponseDTOS);
    }

    @Test
    public void fetchSubDepartmentById() {
        Optional<SubDepartment> subDepartment = subDepartmentRepository
                .fetchSubDepartmentById(1L);

        assertNotNull(subDepartment);
    }

    @Test
    public void testFetchDropDownList() {
        Optional<List<SubDepartmentDropDownDTO>> dropDownDTOS = subDepartmentRepository
                .fetchDropDownList();

        assertNotNull(dropDownDTOS);
    }

    @Test
    public void fetchActiveDropDownList() {
        Optional<List<SubDepartmentDropDownDTO>> dropDownDTOS = subDepartmentRepository
                .fetchActiveDropDownList();

        assertNotNull(dropDownDTOS);
    }


    @Test
    public void getSubDepartment() {
        List<Object[]> subDepartmentList = subDepartmentRepository
                .getSubDepartment();

        assertNotNull(subDepartmentList);
    }

    @Test
    public void fetchSubDepartmentDetailsById() {
        Optional<SubDepartmentResponseDTO> responseDTO = subDepartmentRepository
                .fetchSubDepartmentDetail(1L);

        assertNotNull(responseDTO);
    }


    @Test
    public void fetchSubDepartmentByNameAndCode() {
        List<Object[]> object = subDepartmentRepository
                .fetchSubDepartmentByNameAndCode("Sample", "smp");

        assertTrue(object.size() == 0);
    }

    @Test
    public void checkSubDepartmentNameAndCodeIfExist() {
        List<Object[]> object = subDepartmentRepository
                .checkSubDepartmentNameAndCodeIfExist(0L,"Sample", "smp");

        assertTrue(object.size() == 0);
    }

    @Test
    public void fetchActiveSubDepartmentByIdAndDepartmentId(){
        Optional<SubDepartment> subDepartment=subDepartmentRepository
                .fetchActiveSubDepartmentByIdAndDepartmentId(1L,1L);

        assertNotNull(subDepartment);
    }
}
