package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankUpdateRequestDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.RegisteredBankRepository;
import com.cogent.admin.service.impl.RegisteredBankServiceImpl;
import com.cogent.persistence.model.RegisteredBank;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.registeredBank.RegisteredBankTestUtils.*;
import static com.cogent.admin.utils.RegisteredBankUtils.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 2019-10-22
 */
public class RegisteredBankServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private RegisteredBankRepository repository;

    @InjectMocks
    private RegisteredBankServiceImpl service;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void createRegisteredBank_ShouldReturnNameAndCodeExists() {

        thrown.expect(DataDuplicationException.class);

        RegisteredBankRequestDTO requestDTO = getRegisteredBankRequestDTO();

        given(repository.findRegisteredBankByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getRegisteredBankObjectForNameAndCode());

        service.createRegisteredBank(requestDTO);

        verify(repository)
                .findRegisteredBankByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createRegisteredBank_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        RegisteredBankRequestDTO requestDTO = getRegisteredBankRequestDTO();

        given(repository.findRegisteredBankByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getRegisteredBankObjectForName());

        service.createRegisteredBank(requestDTO);

        verify(repository).findRegisteredBankByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createRegisteredBank_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        RegisteredBankRequestDTO requestDTO = getRegisteredBankRequestDTO();

        given(repository.findRegisteredBankByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getRegisteredBankObjectForCode());

        service.createRegisteredBank(requestDTO);

        verify(repository)
                .findRegisteredBankByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }


    @Test
    public void createRegisteredBank_ShouldCreateRegisteredBank() {
        RegisteredBankRequestDTO requestDTO = getRegisteredBankRequestDTO();

        RegisteredBank expected = parseToRegisteredBank(requestDTO);

        given(repository.findRegisteredBankByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        service.createRegisteredBank(requestDTO);

        assertNotNull(expected);

        verify(repository)
                .findRegisteredBankByNameOrCode(requestDTO.getName(), requestDTO.getCode());

        verify(repository).save(any(RegisteredBank.class));

    }

    @Test
    public void deleteRegisteredBank_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchRegisteredBankById(1L))
                .willReturn(Optional.empty());

        service.deleteRegisteredBank(getDeleteRequestDTO());

        verify(repository)
                .fetchRegisteredBankById(1L);
    }

    @Test
    public void deleteRegisteredBank_ShouldDelete() {

        RegisteredBank expected = deleteRegisteredBank.apply(getRegisteredBankInfo(), getDeleteRequestDTO());

        given(repository.fetchRegisteredBankById(1L))
                .willReturn(Optional.of(getRegisteredBankInfo()));


        service.deleteRegisteredBank(getDeleteRequestDTO());

        Assert.assertTrue(new ReflectionEquals(expected).matches(getDeletedRegisteredBankInfo()));

        verify(repository)
                .fetchRegisteredBankById(1L);
    }

    @Test
    public void updateRegisteredBank_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchRegisteredBankById(1L))
                .willReturn(Optional.empty());

        service.updateRegisteredBank(getUpdateRegisteredBankRequestDTO());

        verify(repository)
                .fetchRegisteredBankById(1L);
    }

    @Test
    public void updateRegisteredBank_ShouldReturnNameAlreadyExists() {

        RegisteredBankUpdateRequestDTO updateRequestDTO = getUpdateRegisteredBankRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(repository.fetchRegisteredBankById(1L))
                .willReturn(Optional.of(getRegisteredBankInfo()));

        given(repository.checkIfRegisteredBankNameAndCodeExists(updateRequestDTO.getId(),
                updateRequestDTO.getName(), updateRequestDTO.getCode()))
                .willReturn(getRegisteredBankObjectForNameAndCode());

        service.updateRegisteredBank(updateRequestDTO);

        verify(repository).fetchRegisteredBankById(1L);

        verify(repository)
                .checkIfRegisteredBankNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(), updateRequestDTO.getCode());
    }

    @Test
    public void updateRegisteredBank_ShouldUpdate() {

        RegisteredBankUpdateRequestDTO updateRequestDTO = getUpdateRegisteredBankRequestDTO();

        RegisteredBank expected = updateRegisteredBank.apply(getRegisteredBankInfo(), updateRequestDTO);

        given(repository.fetchRegisteredBankById(1L))
                .willReturn(Optional.of(getRegisteredBankInfo()));

        given(repository.checkIfRegisteredBankNameAndCodeExists(updateRequestDTO.getId(),
                updateRequestDTO.getName(), updateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        service.updateRegisteredBank(updateRequestDTO);

        assertNotNull(expected);

        verify(repository)
                .fetchRegisteredBankById(1L);

        verify(repository)
                .checkIfRegisteredBankNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(), updateRequestDTO.getCode());

    }

    @Test(expected = NoContentFoundException.class)
    public void searchRegisteredBank_ShouldReturnNoContentFound() {

        RegisteredBankSearchRequestDTO searchRequestDTO = new RegisteredBankSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchRegisteredBank(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(RegisteredBank.class));

        service.searchRegisteredBank(searchRequestDTO, pageable);

        verify(repository)
                .searchRegisteredBank(searchRequestDTO, pageable);
    }

    @Test
    public void searchRegisteredBank_ShouldReturnRegisteredBank() {

        RegisteredBankSearchRequestDTO searchRequestDTO = new RegisteredBankSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchRegisteredBank(searchRequestDTO, pageable))
                .willReturn(getRegisteredBankMinimalResponseDTOList());

        service.searchRegisteredBank(searchRequestDTO, pageable);

        verify(repository)
                .searchRegisteredBank(searchRequestDTO, pageable);

    }

    @Test(expected = NoContentFoundException.class)
    public void fetchRegisteredBankDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(repository.fetchRegisteredBankDetails(id))
                .willThrow(new NoContentFoundException(RegisteredBank.class, "id", id.toString()));

        repository.fetchRegisteredBankDetails(id);

        verify(repository)
                .fetchRegisteredBankDetails(id);

    }

    @Test
    public void fetchRegisteredBankDetails_ShouldReturnData() {

        given(repository.fetchRegisteredBankDetails(1L))
                .willReturn(getRegisteredBankResponseDTO());

        service.fetchRegisteredBankDetails(1L);

        verify(repository).fetchRegisteredBankDetails(1L);


    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.dropDownList())
                .willReturn(Optional.empty());

        service.fetchDropDownList();

        verify(repository)
                .dropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getRegisteredBankDropDownInfo();

        given(repository.dropDownList())
                .willReturn(Optional.of(dropDownInfo));

        service.fetchDropDownList();

        verify(repository).dropDownList();

    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.activeDropDownList())
                .willReturn(Optional.empty());

        service.fetchActiveDropDownList();

        verify(repository).activeDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getRegisteredBankDropDownInfo();

        given(repository.activeDropDownList())
                .willReturn(Optional.of(dropDownInfo));

        service.fetchActiveDropDownList();

        verify(repository).activeDropDownList();
    }


}
