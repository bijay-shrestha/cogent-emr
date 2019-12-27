package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankMinimalResponseDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankResponseDTO;
import com.cogent.admin.repository.RegisteredBankRepository;
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
 * @author smriti on 2019-10-22
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class RegisteredBankRepositoryCustomTest {

    @Autowired
    RegisteredBankRepository repository;

    @Test
    public void findRegisteredBankByNameAndCode() {
        List<Object[]> objects = repository.findRegisteredBankByNameOrCode("Test", "Test");

        assertTrue(objects.size() == 0);
    }

    @Test
    public void searchRegisteredBank() {
        Pageable pageable = PageRequest.of(1, 10);

        RegisteredBankSearchRequestDTO searchRequestDTO = new RegisteredBankSearchRequestDTO();

        List<RegisteredBankMinimalResponseDTO> minimalResponseDTOs = repository.searchRegisteredBank(searchRequestDTO, pageable);

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
    public void fetchRegisteredBankById() {
        RegisteredBankResponseDTO responseDTO = repository.fetchRegisteredBankDetails(1L);

        assertNotNull(responseDTO);
    }

}
