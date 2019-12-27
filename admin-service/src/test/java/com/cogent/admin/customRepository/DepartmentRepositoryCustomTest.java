package com.cogent.admin.customRepository;

import com.cogent.admin.dto.department.DepartmentTestUtils;
import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.response.department.DepartmentDropDownDTO;
import com.cogent.admin.dto.response.department.DepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.department.DepartmentResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DepartmentRepository;
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
public class DepartmentRepositoryCustomTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSearchDepartment_ShouldReturnAllDepartment() {
        Pageable pageable = PageRequest.of(1, 10);
        DepartmentSearchRequestDTO departmentSearchRequestDTO=new DepartmentSearchRequestDTO();

        List<DepartmentMinimalResponseDTO> departmentResponseDTOS = departmentRepository
                .searchDepartment(departmentSearchRequestDTO, pageable);

        assertNotNull(departmentResponseDTOS);
    }

    @Test
    public void testSearchDepartment_ShouldReturnselectedDepartment() {
        Pageable pageable = PageRequest.of(1, 10);
        DepartmentSearchRequestDTO departmentSearchRequestDTO= DepartmentTestUtils
                .getSearchDepartmentRequestDTO();

        List<DepartmentMinimalResponseDTO> departmentResponseDTOS = departmentRepository
                .searchDepartment(departmentSearchRequestDTO, pageable);

        assertNotNull(departmentResponseDTOS);
    }



    @Test
    public void testFetchDropDownList() {
        Optional<List<DepartmentDropDownDTO>> departmentDropDownDTOS = departmentRepository
                .fetchDropDownList();

        assertNotNull(departmentDropDownDTOS);
    }

    @Test
    public void testFetchActiveDropDownList() {
        Optional<List<DepartmentDropDownDTO>> departmentDropDownDTOS = departmentRepository
                .fetchActiveDropDownList();

        assertNotNull(departmentDropDownDTOS);
    }


    @Test
    public void testGetDepartment() {
        List<Object[]> departmentObject = departmentRepository.getDepartment();

        assertNotNull(departmentObject);
    }

    @Test
    public void testFetchDepartmentByNameOrCode() {
        List<Object[]> object = departmentRepository.fetchDepartmentByNameOrCode("Saurav","THPA");

        assertTrue(object.size() == 0);
    }

    @Test
    public void testFetchDepartmentDetail() {
        DepartmentResponseDTO responseDTO = departmentRepository
                .fetchDepartmentDetails(1L);

        assertNotNull(responseDTO);
    }

    @Test(expected = NoContentFoundException.class)
    public void testFetchEthnicityDetail_ThrowsException() {
        departmentRepository.fetchDepartmentDetails(0L);
    }


    @Test
    public void checkDepartment(){
        List<Object[]> object=departmentRepository.checkIfDepartmentNameAndCodeExists(1L,"Surgicaaal","LABaa");

        assertTrue(object.size() == 0);
    }
}
