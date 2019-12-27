package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.response.service.ServiceMinimalResponseDTO;
import com.cogent.admin.dto.response.service.ServiceResponseDTO;
import com.cogent.admin.repository.ServiceRepository;
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
public class ServiceRepositoryCustomTest {
    @Autowired
    ServiceRepository repository;

    @Test
    public void fetchServiceByNameOrCode() {
        List<Object[]> objects = repository
                .fetchServiceByNameOrCode("Test", "TST");

        assertTrue(objects.size() == 0);
    }

    @Test
    public void checkIfServiceNameAndCodeExists() {
        List<Object[]> objects = repository
                .checkIfServiceNameAndCodeExists(1L,"Test", "TST");

        assertTrue(objects.size() == 0);
    }


    @Test
    public void testFetchServiceDetail() {
        ServiceResponseDTO responseDTO = repository
                .fetchServiceDetails(2L);

        assertNotNull(responseDTO);
    }

    @Test
    public void searchService() {
        Pageable pageable = PageRequest.of(1, 10);

        ServiceSearchRequestDTO serviceSearchRequestDTO = new ServiceSearchRequestDTO();

        List<ServiceMinimalResponseDTO> minimalResponseDTOS = repository
                .searchService(serviceSearchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }

        @Test
    public void fetchDropDownList() {
        Optional<List<DropDownResponseDTO>> dropDownList = repository
                .fetchDropDownList(2L);

        assertNotNull(dropDownList);
    }

    @Test
    public void fetchActiveDropDownList() {
        Optional<List<DropDownResponseDTO>> dropDownList = repository
                .fetchActiveDropDownList(2L);

        assertNotNull(dropDownList);
    }

}
