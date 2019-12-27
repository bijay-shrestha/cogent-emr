package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeUpdateRequestDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.BillingModeRepository;
import com.cogent.admin.service.impl.BillingModeServiceImpl;
import com.cogent.persistence.model.BillingMode;
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
import static com.cogent.admin.dto.billingmode.BillingModeTestUtils.*;
import static com.cogent.admin.utils.BillingModeUtils.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 2019-10-22
 */
public class BillingModeServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private BillingModeRepository repository;

    @InjectMocks
    private BillingModeServiceImpl service;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void createBillingMode_ShouldReturnNameAndCodeExists() {

        thrown.expect(DataDuplicationException.class);

        BillingModeRequestDTO requestDTO = getBillingModeRequestDTO();

        given(repository.findBillingModeByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getBillingModeObjectForNameAndCode());

        service.createBillingMode(requestDTO);

        verify(repository)
                .findBillingModeByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createBillingMode_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        BillingModeRequestDTO requestDTO = getBillingModeRequestDTO();

        given(repository.findBillingModeByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getBillingModeObjectForName());

        service.createBillingMode(requestDTO);

        verify(repository).findBillingModeByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createBillingMode_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        BillingModeRequestDTO requestDTO = getBillingModeRequestDTO();

        given(repository.findBillingModeByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getBillingModeObjectForCode());

        service.createBillingMode(requestDTO);

        verify(repository)
                .findBillingModeByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }


    @Test
    public void createBillingMode_ShouldCreateBillingMode() {
        BillingModeRequestDTO requestDTO = getBillingModeRequestDTO();

        BillingMode expected = parseToBillingMode.apply(requestDTO);

        given(repository.findBillingModeByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        service.createBillingMode(requestDTO);

       assertNotNull(expected);

        verify(repository)
                .findBillingModeByNameOrCode(requestDTO.getName(), requestDTO.getCode());

        verify(repository).save(any(BillingMode.class));

    }

    @Test
    public void deleteBillingMode_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchBillingModeById(1L))
                .willReturn(Optional.empty());

        service.deleteBillingMode(getDeleteRequestDTO());

        verify(repository)
                .fetchBillingModeById(1L);
    }

    @Test
    public void deleteBillingMode_ShouldDelete() {

        BillingMode expected = deleteBillingMode.apply(getBillingModeInfo(), getDeleteRequestDTO());

        given(repository.fetchBillingModeById(1L))
                .willReturn(Optional.of(getBillingModeInfo()));


        service.deleteBillingMode(getDeleteRequestDTO());

        Assert.assertTrue(new ReflectionEquals(expected).matches(getDeletedBillingModeInfo()));

        verify(repository)
                .fetchBillingModeById(1L);
    }

    @Test
    public void updateBillingMode_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchBillingModeById(1L))
                .willReturn(Optional.empty());

        service.updateBillingMode(getUpdateBillingModeRequestDTO());

        verify(repository)
                .fetchBillingModeById(1L);
    }

    @Test
    public void updateBillingMode_ShouldReturnNameAlreadyExists() {

        BillingModeUpdateRequestDTO updateRequestDTO = getUpdateBillingModeRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(repository.fetchBillingModeById(1L))
                .willReturn(Optional.of(getBillingModeInfo()));

        given(repository.checkIfBillingModeNameAndCodeExists(updateRequestDTO.getId(),
                updateRequestDTO.getName(),updateRequestDTO.getCode()))
                .willReturn(getBillingModeObjectForNameAndCode());

        service.updateBillingMode(updateRequestDTO);

        verify(repository).fetchBillingModeById(1L);

        verify(repository)
                .checkIfBillingModeNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),updateRequestDTO.getCode());
    }

    @Test
    public void updateBillingMode_ShouldUpdate() {

        BillingModeUpdateRequestDTO updateRequestDTO = getUpdateBillingModeRequestDTO();

        BillingMode expected = updateBillingMode.apply(getBillingModeInfo(), updateRequestDTO);

        given(repository.fetchBillingModeById(1L))
                .willReturn(Optional.of(getBillingModeInfo()));

        given(repository.checkIfBillingModeNameAndCodeExists(updateRequestDTO.getId(),
                updateRequestDTO.getName(),updateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        service.updateBillingMode(updateRequestDTO);

       assertNotNull(expected);

        verify(repository)
                .fetchBillingModeById(1L);

        verify(repository)
                .checkIfBillingModeNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),updateRequestDTO.getCode());

    }

    @Test(expected = NoContentFoundException.class)
    public void searchBillingMode_ShouldReturnNoContentFound() {

        BillingModeSearchRequestDTO searchRequestDTO = new BillingModeSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchBillingMode(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(BillingMode.class));

        service.searchBillingMode(searchRequestDTO, pageable);

        verify(repository)
                .searchBillingMode(searchRequestDTO, pageable);
    }

    @Test
    public void searchBillingMode_ShouldReturnBillingMode() {

        BillingModeSearchRequestDTO searchRequestDTO = new BillingModeSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchBillingMode(searchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        service.searchBillingMode(searchRequestDTO, pageable);

        verify(repository)
                .searchBillingMode(searchRequestDTO, pageable);

    }

    @Test(expected = NoContentFoundException.class)
    public void fetchBillingModeDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(repository.fetchBillingModeDetails(id))
                .willThrow(new NoContentFoundException(BillingMode.class, "id", id.toString()));

        repository.fetchBillingModeDetails(id);

        verify(repository)
                .fetchBillingModeDetails(id);

    }

    @Test
    public void fetchBillingModeDetails_ShouldReturnData() {

        given(repository.fetchBillingModeDetails(1L))
                .willReturn(getBillingModeResponseDTO());

        service.fetchBillingModeDetails(1L);

        verify(repository).fetchBillingModeDetails(1L);


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
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

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
        List<DropDownResponseDTO> dropDownInfo = getDropDownInfo();

        given(repository.activeDropDownList())
                .willReturn(Optional.of(dropDownInfo));

        service.fetchActiveDropDownList();

        verify(repository).activeDropDownList();
    }



}
