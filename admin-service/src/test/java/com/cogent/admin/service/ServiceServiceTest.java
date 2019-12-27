package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.service.ServiceRequestDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.request.service.ServiceUpdateRequestDTO;
import com.cogent.admin.dto.response.service.ServiceResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DepartmentRepository;
import com.cogent.admin.repository.ServiceRepository;
import com.cogent.admin.repository.ServiceTypeRepository;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.admin.service.impl.ServiceServiceImpl;
import com.cogent.persistence.model.Service;
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

import static com.cogent.admin.dto.department.DepartmentTestUtils.getDepartmentInfo;
import static com.cogent.admin.dto.service.ServiceTestUtils.*;
import static com.cogent.admin.dto.servicetype.ServiceTypeTestUtils.getServiceTypeInfo;
import static com.cogent.admin.dto.subdepartment.SubDepartmentTestUtlis.getSubDepartmentInfo;
import static com.cogent.admin.utils.ServiceUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ServiceServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    ServiceRepository repository;

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    SubDepartmentRepository subDepartmentRepository;

    @Mock
    ServiceTypeRepository serviceTypeRepository;

    @InjectMocks
    ServiceServiceImpl service;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createService_ShouldReturnNameAndCodeExists();
        createService_ShouldReturnNameExists();
        createService_ShouldReturnCodeExists();
        createService_ShouldCreateService();
    }

    @Test
    public void delete() {
        deleteService_ShouldReturnNoContentFound();
        deleteService_ShouldDelete();
    }


    @Test
    public void updateService() {
        updateService_ShouldReturnNoContentFound();
        updateService_ShouldReturnNameAlreadyExists();
        updateService_ShouldUpdate();
    }


    @Test(expected = NoContentFoundException.class)
    public void fetchDetailById() {
        fetchServiceDetails_ShouldReturnNoContentFound();
        fetchServiceDetails_ShouldReturnData();
    }


    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchService_ShouldReturnNoContentFound();
        searchService_ShouldReturnService();
    }

    @Test
    public void dropDownList() {
        dropDownList_ShouldReturnNoContentFound();
        dropDownList_ShouldData();
    }

    @Test
    public void activeDropDownList() {
        activeDropDownList_ShouldReturnNoContentFound();
        activeDropDownList_ShouldData();
    }

    @Test
    public void createService_ShouldReturnNameAndCodeExists() {

        thrown.expect(DataDuplicationException.class);

        ServiceRequestDTO requestDTO = getServiceRequestDTO();

        given(repository.fetchServiceByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getServiceObjectForNameAndCode());

        service.createService(requestDTO);

        verify(repository)
                .fetchServiceByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createService_ShouldReturnNameExists() {
        thrown.expect(DataDuplicationException.class);

        ServiceRequestDTO requestDTO = getServiceRequestDTO();

        given(repository.fetchServiceByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getServiceObjectForName());

        service.createService(requestDTO);

        verify(repository)
                .fetchServiceByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createService_ShouldReturnCodeExists() {
        thrown.expect(DataDuplicationException.class);

        ServiceRequestDTO requestDTO = getServiceRequestDTO();

        given(repository.fetchServiceByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(getServiceObjectForCode());

        service.createService(requestDTO);

        verify(repository)
                .fetchServiceByNameOrCode(requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void createService_ShouldCreateService() {

        ServiceRequestDTO requestDTO = getServiceRequestDTO();

        Service expected = parseToService(requestDTO,
                getDepartmentInfo(),
                getSubDepartmentInfo(),
                getServiceTypeInfo());

        given(repository.fetchServiceByNameOrCode(requestDTO.getName()
                , requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        given(departmentRepository.findActiveDepartmentById(requestDTO.getDepartmentId()))
                .willReturn(Optional.ofNullable(getDepartmentInfo()));

        given(subDepartmentRepository.findActiveSubDepartmentById(requestDTO.getSubDepartmentId()))
                .willReturn(Optional.of(getSubDepartmentInfo()));

        given(serviceTypeRepository.fetchActiveServiceTypeById(requestDTO.getServiceTypeId()))
                .willReturn(Optional.of(getServiceTypeInfo()));

        service.createService(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{
                "id",
                "department",
                "subDepartment",
                "serviceType"}).matches(getServiceInfo()));

        verify(repository)
                .fetchServiceByNameOrCode(requestDTO.getName(), requestDTO.getCode());

        verify(departmentRepository)
                .findActiveDepartmentById(requestDTO.getDepartmentId());

        verify(subDepartmentRepository)
                .findActiveSubDepartmentById(requestDTO.getSubDepartmentId());

        verify(serviceTypeRepository)
                .fetchActiveServiceTypeById(requestDTO.getServiceTypeId());

        verify(repository)
                .save(any(Service.class));
    }

    @Test
    public void deleteService_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchServiceById(1L))
                .willReturn(Optional.empty());

        service.deleteService(getDeleteRequestDTO());

        verify(repository)
                .fetchServiceById(1L);

    }

    @Test
    public void deleteService_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Service expected = deleteService
                .apply(getServiceInfo(), deleteRequestDTO);

        given(repository.fetchServiceById(1L))
                .willReturn(Optional.of(getServiceInfo()));

        service.deleteService(deleteRequestDTO);


        Assert.assertTrue(new ReflectionEquals(expected, new String[]{
                "department",
                "subDepartment",
                "serviceType"}).matches(getServiceToDelete()));

        verify(repository)
                .fetchServiceById(1L);
    }

    @Test
    public void updateService_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchServiceById(1L))
                .willReturn(Optional.empty());

        service.updateService(getServiceUpdateRequestDTO());

        verify(repository)
                .fetchServiceById(1L);
    }

    @Test
    public void updateService_ShouldReturnNameAlreadyExists() {

        ServiceUpdateRequestDTO requestDTO = getServiceUpdateRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(repository.fetchServiceById(requestDTO.getId()))
                .willReturn(Optional.of(getServiceInfo()));

        given(repository.checkIfServiceNameAndCodeExists(requestDTO.getId(),
                requestDTO.getName(), requestDTO.getCode()))
                .willReturn(getServiceObjectForNameAndCode());

        service.updateService(requestDTO);

        verify(repository)
                .fetchServiceById(requestDTO.getId());

        verify(repository)
                .checkIfServiceNameAndCodeExists(requestDTO.getId(), requestDTO.getName(), requestDTO.getCode());
    }

    @Test
    public void updateService_ShouldUpdate() {

        ServiceUpdateRequestDTO requestDTO = getServiceUpdateRequestDTO();

        Service expected = update(requestDTO,
                getServiceInfo(),
                getDepartmentInfo(),
                getSubDepartmentInfo(),
                getServiceTypeInfo());

        given(repository.fetchServiceById(requestDTO.getId()))
                .willReturn(Optional.of(getServiceInfo()));

        given(repository.checkIfServiceNameAndCodeExists(requestDTO.getId(),
                requestDTO.getName(), requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        given(departmentRepository.findActiveDepartmentById(requestDTO.getDepartmentId()))
                .willReturn(Optional.ofNullable(getDepartmentInfo()));

        given(subDepartmentRepository.findActiveSubDepartmentById(requestDTO.getSubDepartmentId()))
                .willReturn(Optional.of(getSubDepartmentInfo()));

        given(serviceTypeRepository.fetchActiveServiceTypeById(requestDTO.getServiceTypeId()))
                .willReturn(Optional.of(getServiceTypeInfo()));

        service.updateService(requestDTO);


        Assert.assertTrue(new ReflectionEquals(expected, new String[]{
                "department",
                "subDepartment",
                "serviceType"}).matches(getServiceInfoToUpdate()));

        verify(repository)
                .fetchServiceById(requestDTO.getId());

        verify(repository)
                .checkIfServiceNameAndCodeExists(requestDTO.getId(), requestDTO.getName(), requestDTO.getCode());

        verify(departmentRepository)
                .findActiveDepartmentById(requestDTO.getDepartmentId());

        verify(subDepartmentRepository)
                .findActiveSubDepartmentById(requestDTO.getSubDepartmentId());

        verify(serviceTypeRepository)
                .fetchActiveServiceTypeById(requestDTO.getServiceTypeId());


    }

    @Test(expected = NoContentFoundException.class)
    public void searchService_ShouldReturnNoContentFound() {

        ServiceSearchRequestDTO searchRequestDTO = getSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchService(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(Service.class));

        service.searchService(searchRequestDTO, pageable);

        verify(repository)
                .searchService(searchRequestDTO, pageable);
    }

    @Test
    public void searchService_ShouldReturnService() {

        ServiceSearchRequestDTO searchRequestDTO = getSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchService(searchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        service.searchService(searchRequestDTO, pageable);

        verify(repository)
                .searchService(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchServiceDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(repository.fetchServiceDetails(id))
                .willThrow(new NoContentFoundException(Service.class, "id", id.toString()));

        service.fetchServiceDetails(1L);

        verify(repository)
                .fetchServiceDetails(id);

    }

    @Test
    public void fetchServiceDetails_ShouldReturnData() {

        ServiceResponseDTO responseDTO = getServiceResponseDTO();

        given(repository.fetchServiceDetails(1L))
                .willReturn((responseDTO));

        service.fetchServiceDetails(1L);

        verify(repository)
                .fetchServiceDetails(1L);

    }


    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchDropDownList(1L))
                .willReturn(Optional.empty());

        service.fetchDropDownList(1L);

        verify(repository)
                .fetchDropDownList(1L);

    }

    @Test
    public void dropDownList_ShouldData() {
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(repository.fetchDropDownList(1L))
                .willReturn(Optional.of(dropDownResponseDTOS));

        service.fetchDropDownList(1L);

        verify(repository)
                .fetchDropDownList(1L);
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchActiveDropDownList(1L))
                .willReturn(Optional.empty());

        service.fetchActiveDropDownList(1L);

        verify(repository)
                .fetchActiveDropDownList(1L);
    }

    @Test
    public void activeDropDownList_ShouldData() {
        List<DropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(repository.fetchActiveDropDownList(1L))
                .willReturn(Optional.of(dropDownResponseDTOS));

        service.fetchActiveDropDownList(1L);

        verify(repository)
                .fetchActiveDropDownList(1L);
    }


}
