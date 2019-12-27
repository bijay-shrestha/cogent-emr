package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeUpdateRequestDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.BillingModeRepository;
import com.cogent.admin.repository.ServiceChargeRepository;
import com.cogent.admin.repository.ServiceRepository;
import com.cogent.admin.service.impl.ServiceChargeServiceImpl;
import com.cogent.persistence.model.Service;
import com.cogent.persistence.model.ServiceCharge;
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

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.billingmode.BillingModeTestUtils.getBillingModeInfo;
import static com.cogent.admin.dto.billingmode.BillingModeTestUtils.getBillingModes;
import static com.cogent.admin.dto.service.ServiceTestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.service.ServiceTestUtils.getServiceInfo;
import static com.cogent.admin.dto.servicecharge.ServiceChargeTestUtils.*;
import static com.cogent.admin.utils.ServiceChargeUtils.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi Thapa 11/18/19
 */
public class ServiceChargeServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    ServiceChargeRepository repository;

    @Mock
    ServiceRepository serviceRepository;

    @Mock
    BillingModeRepository billingModeRepository;

    @InjectMocks
    ServiceChargeServiceImpl service;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void create() {
        createServiceCharge_ShouldThrowNoContentFound();
        createServiceCharge_ShouldSave();
    }

    @Test
    public void delete() {
        deleteServiceCharge_ShouldThrowNoContentFound();
        deleteServiceCharge_ShouldDelete();
    }

    @Test
    public void updateServiceCharge() {
        updateServiceCharge_ShouldThrowNoContentFound();
        updateServiceCharge_ShouldThrowDataDuplication();
        updateServiceCharge_ShouldThrowServiceNotFound();
        updateServiceCharge_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchServiceCharge_ShouldReturnNoContentFound();
        searchServiceCharge_ShouldReturnData();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchServiceChargeById() {
        fetchServiceChargeDetails_ShouldReturnNoContentFound();
        fetchServiceChargeDetails_ShouldReturnData();
    }

    @Test
    public void createServiceCharge_ShouldThrowDataDuplication() {
        thrown.expect(DataDuplicationException.class);

        ServiceChargeRequestDTO requestDTO = getServiceChargeRequestDTO();

        given(repository.fetchServiceChargeCountByServiceIdAndBillingModeId(requestDTO.getServiceId(),
                requestDTO.getBillingModeId().get(0)))
                .willReturn(BigInteger.valueOf(1));

        service.createServiceCharge(requestDTO);

        verify(repository)
                .fetchServiceChargeCountByServiceIdAndBillingModeId(requestDTO.getServiceId(),
                        requestDTO.getBillingModeId().get(0));

    }

    @Test
    public void createServiceCharge_ShouldThrowNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        ServiceChargeRequestDTO requestDTO = getServiceChargeRequestDTO();

        given(repository.fetchServiceChargeCountByServiceIdAndBillingModeId(requestDTO.getServiceId(),
                requestDTO.getBillingModeId().get(0)))
                .willReturn(BigInteger.valueOf(0));

        given(serviceRepository.fetchActiveServiceById(1L))
                .willReturn(Optional.empty());

        service.createServiceCharge(requestDTO);

        verify(repository)
                .fetchServiceChargeCountByServiceIdAndBillingModeId(requestDTO.getServiceId(),
                        requestDTO.getBillingModeId().get(0));

        verify(serviceRepository)
                .fetchActiveServiceById(1L);

    }

    @Test
    public void createServiceCharge_ShouldSave() {

        ServiceChargeRequestDTO requestDTO = getServiceChargeRequestDTO();

        ServiceCharge expected = parseToServiceCharge(requestDTO, getServiceInfo(), getBillingModes());

        given(repository.fetchServiceChargeCountByServiceIdAndBillingModeId(requestDTO.getServiceId(),
                requestDTO.getBillingModeId().get(0)))
                .willReturn(BigInteger.valueOf(0));

        given(serviceRepository.fetchActiveServiceById(1L))
                .willReturn(Optional.of(getServiceInfo()));

        given(billingModeRepository.fetchActiveBillingModeById(1L))
                .willReturn(getBillingModeInfo());

        service.createServiceCharge(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{
                "id",
                "service",
                "billingModes"}).matches(getServiceChargeInfo()));

        verify(repository)
                .fetchServiceChargeCountByServiceIdAndBillingModeId(requestDTO.getServiceId(),
                        requestDTO.getBillingModeId().get(0));

        verify(serviceRepository)
                .fetchActiveServiceById(1L);

        verify(billingModeRepository)
                .fetchActiveBillingModeById(1L);
    }

    @Test
    public void deleteServiceCharge_ShouldThrowNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(repository.fetchServiceChargeById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        service.deleteServiceCharge(deleteRequestDTO);

        verify(repository)
                .fetchServiceChargeById(deleteRequestDTO.getId());
    }

    @Test
    public void deleteServiceCharge_ShouldDelete() {

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        ServiceCharge expected = deleteServiceCharge.apply(deleteRequestDTO, getServiceChargeInfo());

        given(repository.fetchServiceChargeById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(getServiceChargeInfo()));

        doNothing().when(repository).deleteChildDataById(deleteRequestDTO.getId());

        service.deleteServiceCharge(deleteRequestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{
                "service"})
                .matches(getServiceChargeInfoToDelete()));

        verify(repository)
                .fetchServiceChargeById(deleteRequestDTO.getId());

        verify(repository)
                .deleteChildDataById(deleteRequestDTO.getId());
    }

    @Test
    public void updateServiceCharge_ShouldThrowNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        ServiceChargeUpdateRequestDTO requestDTO = getServiceChargeUpdateRequestDTO();

        given(repository.fetchServiceChargeById(requestDTO.getId()))
                .willReturn(Optional.empty());

        service.updateServiceCharge(requestDTO);

        verify(repository)
                .fetchServiceChargeById(requestDTO.getId());
    }

    @Test
    public void updateServiceCharge_ShouldThrowDataDuplication() {
        thrown.expect(DataDuplicationException.class);

        ServiceChargeUpdateRequestDTO requestDTO = getServiceChargeUpdateRequestDTO();

        given(repository.fetchServiceChargeById(requestDTO.getId()))
                .willReturn(Optional.of(getServiceChargeInfo()));

        given(repository.CheckIfServiceExists(requestDTO.getId(),
                requestDTO.getServiceId(),
                requestDTO.getBillingModeId().get(0)))
                .willReturn(BigInteger.valueOf(1));

        service.updateServiceCharge(requestDTO);

        verify(repository)
                .fetchServiceChargeById(requestDTO.getId());

    }

    @Test
    public void updateServiceCharge_ShouldThrowServiceNotFound() {
        thrown.expect(NoContentFoundException.class);

        ServiceChargeUpdateRequestDTO requestDTO = getServiceChargeUpdateRequestDTO();

        given(repository.fetchServiceChargeById(requestDTO.getId()))
                .willReturn(Optional.of(getServiceChargeInfo()));

        given(repository.CheckIfServiceExists(requestDTO.getId(),
                requestDTO.getServiceId(),
                requestDTO.getBillingModeId().get(0)))
                .willReturn(BigInteger.valueOf(0));

        given(serviceRepository.fetchActiveServiceById(1L))
                .willReturn(Optional.empty());

        service.updateServiceCharge(requestDTO);

        verify(repository)
                .fetchServiceChargeById(requestDTO.getId());

        verify(serviceRepository)
                .fetchActiveServiceById(requestDTO.getServiceId());
    }

    @Test
    public void updateServiceCharge_ShouldUpdate() {

        ServiceChargeUpdateRequestDTO requestDTO = getServiceChargeUpdateRequestDTO();

        ServiceCharge expected = update(requestDTO, getServiceChargeInfo(), getServiceInfo(), getBillingModes());

        given(repository.fetchServiceChargeById(requestDTO.getId()))
                .willReturn(Optional.of(getServiceChargeInfo()));

        given(repository.CheckIfServiceExists(requestDTO.getId(),
                requestDTO.getServiceId(),
                requestDTO.getBillingModeId().get(0)))
                .willReturn(BigInteger.valueOf(0));

        doNothing().when(repository).deleteChildDataById(requestDTO.getId());

        given(serviceRepository.fetchActiveServiceById(1L))
                .willReturn(Optional.of(getServiceInfo()));

        given(billingModeRepository.fetchActiveBillingModeById(1L))
                .willReturn(getBillingModeInfo());

        service.updateServiceCharge(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{
                "service",
                "billingModes"})
                .matches(getServiceChargeInfoToUpdate()));

        verify(repository)
                .fetchServiceChargeById(requestDTO.getId());

        verify(repository)
                .CheckIfServiceExists(requestDTO.getId(),
                        requestDTO.getServiceId(),
                        requestDTO.getBillingModeId().get(0));

        verify(repository)
                .deleteChildDataById(requestDTO.getId());

        verify(serviceRepository)
                .fetchActiveServiceById(requestDTO.getServiceId());

        verify(billingModeRepository)
                .fetchActiveBillingModeById(1L);
    }

    @Test(expected = NoContentFoundException.class)
    public void searchServiceCharge_ShouldReturnNoContentFound() {

        ServiceChargeSearchRequestDTO searchRequestDTO = getSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchServiceCharge(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(ServiceCharge.class));

        service.searchServiceCharge(searchRequestDTO, pageable);

        verify(repository)
                .searchServiceCharge(searchRequestDTO, pageable);
    }

    @Test
    public void searchServiceCharge_ShouldReturnData() {

        ServiceChargeSearchRequestDTO searchRequestDTO = getSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchServiceCharge(searchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        service.searchServiceCharge(searchRequestDTO, pageable);

        verify(repository)
                .searchServiceCharge(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchServiceChargeDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(repository.fetchServiceChargeDetailById(id))
                .willThrow(new NoContentFoundException(Service.class, "id", id.toString()));

        service.fetchServiceChargeDetailsById(1L);

        verify(repository)
                .fetchServiceChargeDetailById(id);

    }

    @Test
    public void fetchServiceChargeDetails_ShouldReturnData() {

        ServiceChargeResponseDTO responseDTO = getServiceChargeResponseDTO();

        given(repository.fetchServiceChargeDetailById(1L))
                .willReturn((responseDTO));

        service.fetchServiceChargeDetailsById(1L);

        verify(repository)
                .fetchServiceChargeDetailById(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchDropDownList())
                .willReturn(Optional.empty());

        service.fetchDropDownList();

        verify(repository)
                .fetchDropDownList();

    }

    @Test
    public void dropDownList_ShouldData() {
        List<ServiceChargeDropDownResponseDTO> dropDownResponseDTOS = getDropDownResponseDTOS();

        given(repository.fetchDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        service.fetchDropDownList();

        verify(repository)
                .fetchDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchActiveDropDownList())
                .willReturn(Optional.empty());

        service.fetchActiveDropDownList();

        verify(repository)
                .fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldData() {
        List<ServiceChargeDropDownResponseDTO> dropDownResponseDTOS = getDropDownResponseDTOS();

        given(repository.fetchActiveDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        service.fetchActiveDropDownList();

        verify(repository)
                .fetchActiveDropDownList();
    }

}
