package com.cogent.admin.service;

import com.cogent.admin.dto.request.discountscheme.DiscountSchemeRequestDTO;
import com.cogent.admin.dto.request.discountscheme.DiscountSchemeSearchRequestDTO;
import com.cogent.admin.dto.request.discountscheme.DiscountSchemeUpdateRequestDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DepartmentDiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.ServiceDiscountDropDownResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.*;
import com.cogent.admin.service.impl.DiscountSchemeServiceImpl;
import com.cogent.persistence.model.AssignBed;
import com.cogent.persistence.model.DepartmentDiscount;
import com.cogent.persistence.model.DiscountScheme;
import com.cogent.persistence.model.ServiceDiscount;
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
import static com.cogent.admin.dto.department.DepartmentTestUtils.getDepartmentInfo;
import static com.cogent.admin.dto.service.ServiceTestUtils.getServiceInfo;
import static com.cogent.admin.dto.request.discountscheme.DiscountSchemeTestUtils.*;
import static com.cogent.admin.utils.DiscountSchemeUtils.*;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi Thapa 11/11/19
 */
public class DiscountSchemeServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    DiscountSchemeRepository repository;

    @Mock
    DepartmentDiscountSchemeRepository departmentDiscountSchemeRepository;

    @Mock
    ServiceDiscountSchemeRepository serviceDiscountSchemeRepository;

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    ServiceRepository serviceRepository;

    @InjectMocks
    DiscountSchemeServiceImpl discountSchemeService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        create_ShouldThrowDataDuplicationException();
        create_ShouldThrowNameAlreadyExists();
        create_ShouldThrowCodeAlreadyExists();
        create_ShouldThrowDataDuplicationException();
        create_ShouldSaveWithNetDiscount();
        create_ShouldSaveWithoutNetDiscount();
    }

    @Test
    public void delete() {
        deleteDiscountScheme_ShouldReturnNoContentFound();
        deleteDiscountScheme_ShouldDeleteWithNetDiscount();
        deleteDiscountScheme_ShouldDeleteWithoutNetDiscount();
    }

    @Test
    public void create_ShouldThrowDataDuplicationException() {
        thrown.expect(DataDuplicationException.class);

        DiscountSchemeRequestDTO requestDTO = getDiscountSchemeRequestDTO();

        given(repository.fetchDiscountSchemeByNameOrCode(requestDTO.getName(), requestDTO.getCode()))
                .willReturn(getDiscountSchemeObjectForNameAndCode());

        discountSchemeService.createDiscountScheme(requestDTO);
    }

    @Test
    public void create_ShouldThrowNameAlreadyExists() {
        thrown.expect(DataDuplicationException.class);

        DiscountSchemeRequestDTO requestDTO = getDiscountSchemeRequestDTO();

        given(repository.fetchDiscountSchemeByNameOrCode(requestDTO.getName(), requestDTO.getCode()))
                .willReturn(getDiscountSchemeObjectForName());

        discountSchemeService.createDiscountScheme(requestDTO);
    }

    @Test
    public void create_ShouldThrowCodeAlreadyExists() {
        thrown.expect(DataDuplicationException.class);

        DiscountSchemeRequestDTO requestDTO = getDiscountSchemeRequestDTO();

        given(repository.fetchDiscountSchemeByNameOrCode(requestDTO.getName(), requestDTO.getCode()))
                .willReturn(getDiscountSchemeObjectForCode());

        discountSchemeService.createDiscountScheme(requestDTO);
    }

    @Test
    public void create_ShouldSaveWithNetDiscount() {

        DiscountSchemeRequestDTO requestDTO = getDiscountSchemeRequestDTO();

        given(repository.fetchDiscountSchemeByNameOrCode(requestDTO.getName(), requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        given(repository.save(any(DiscountScheme.class)))
                .willReturn(getDiscountSchemeInfoWithNetDiscount());

        discountSchemeService.createDiscountScheme(requestDTO);

        verify(repository)
                .fetchDiscountSchemeByNameOrCode(requestDTO.getName(), requestDTO.getCode());

        verify(repository)
                .save(any(DiscountScheme.class));

    }

    @Test
    public void create_ShouldSaveWithoutNetDiscount() {

        DiscountSchemeRequestDTO requestDTO = getDiscountSchemeRequestDTO();

        given(repository.fetchDiscountSchemeByNameOrCode(requestDTO.getName(), requestDTO.getCode()))
                .willReturn(new ArrayList<>());

        given(repository.save(any(DiscountScheme.class)))
                .willReturn(getDiscountSchemeInfoWithoutNetDiscount());

        given(departmentRepository.findActiveDepartmentById(1L))
                .willReturn(Optional.of(getDepartmentInfo()));

        given(serviceRepository.fetchActiveServiceById(1L))
                .willReturn(Optional.of(getServiceInfo()));

        discountSchemeService.createDiscountScheme(requestDTO);

        verify(repository)
                .fetchDiscountSchemeByNameOrCode(requestDTO.getName(), requestDTO.getCode());

        verify(repository)
                .save(any(DiscountScheme.class));

        verify(departmentDiscountSchemeRepository)
                .save(any(DepartmentDiscount.class));

        verify(serviceDiscountSchemeRepository)
                .save(any(ServiceDiscount.class));


    }

    @Test
    public void deleteDiscountScheme_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchDiscountSchemeById(1L))
                .willReturn(Optional.empty());

        discountSchemeService.deleteDiscountScheme(getDeleteRequestDTO());

        verify(repository)
                .fetchDiscountSchemeById(1L);
    }

    @Test
    public void deleteDiscountScheme_ShouldDeleteWithNetDiscount() {

        given(repository.fetchDiscountSchemeById(1L))
                .willReturn(Optional.of(getDiscountSchemeInfoWithNetDiscount()));

        DiscountScheme expected = deleteDiscountScheme.apply(getDiscountSchemeInfoWithNetDiscount(),
                getDeleteRequestDTO());

        discountSchemeService.deleteDiscountScheme(getDeleteRequestDTO());

        assertTrue(new ReflectionEquals(expected).matches(getDiscountSchemeInfoWithNetDiscountToDelete()));

        verify(repository)
                .fetchDiscountSchemeById(1L);

    }

    @Test
    public void deleteDiscountScheme_ShouldDeleteWithoutNetDiscount() {

        given(repository.fetchDiscountSchemeById(1L))
                .willReturn(Optional.of(getDiscountSchemeInfoWithoutNetDiscount()));

        DiscountScheme expected = deleteDiscountScheme.apply(getDiscountSchemeInfoWithNetDiscount(),
                getDeleteRequestDTO());

        DepartmentDiscount expectedDepartmentDiscount = deleteDepartmentDiscount
                .apply(getDepartmentDiscountInfo(), getDeleteRequestDTO());

        ServiceDiscount expectedServiceDiscount = deleteServiceDiscount
                .apply(getServiceDiscountSchemeInfo(), getDeleteRequestDTO());

        discountSchemeService.deleteDiscountScheme(getDeleteRequestDTO());

        assertTrue(new ReflectionEquals(expected).matches(getDiscountSchemeInfoWithNetDiscountToDelete()));

        assertTrue(new ReflectionEquals(expectedDepartmentDiscount, new String[]{"id", "discountScheme", "department"})
                .matches(getDepartmentDiscountInfoToDelete()));

        assertTrue(new ReflectionEquals(expectedServiceDiscount, new String[]{"id", "discountScheme", "service"})
                .matches(getServiceDiscountSchemeInfoToDelete()));

        verify(repository)
                .fetchDiscountSchemeById(1L);

        verify(departmentDiscountSchemeRepository)
                .fetchByDiscountSchemeId(1L);

        verify(serviceDiscountSchemeRepository)
                .fetchByDiscountSchemeId(1L);

    }


    @Test
    public void updateDiscountScheme_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.fetchDiscountSchemeById(1L))
                .willReturn(Optional.empty());

        discountSchemeService.updateDiscountScheme(getDiscountSchemeUpdateRequestDTOWithNetDisocunt());

        verify(repository)
                .fetchDiscountSchemeById(1L);
    }

    @Test
    public void updateDiscountScheme_ShouldReturnDataDuplication() {
        thrown.expect(DataDuplicationException.class);

        DiscountSchemeUpdateRequestDTO updateRequestDTO = getDiscountSchemeUpdateRequestDTOWithNetDisocunt();

        given(repository.fetchDiscountSchemeById(1L))
                .willReturn(Optional.of(getDiscountSchemeInfoWithoutNetDiscount()));

        given(repository.checkIfDiscountSchemeNameAndCodeExists(updateRequestDTO.getId(),
                updateRequestDTO.getName(),
                updateRequestDTO.getCode()))
                .willReturn(getDiscountSchemeObjectForNameAndCode());

        discountSchemeService.updateDiscountScheme(getDiscountSchemeUpdateRequestDTOWithNetDisocunt());

        verify(repository)
                .checkIfDiscountSchemeNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),
                        updateRequestDTO.getCode());
    }

    @Test
    public void updateDiscountScheme_ShouldUpdateWithNetDiscount() {


        DiscountSchemeUpdateRequestDTO updateRequestDTO = getDiscountSchemeUpdateRequestDTOWithNetDisocunt();

        DiscountScheme expected = update.apply(getDiscountSchemeInfoWithoutNetDiscount(), updateRequestDTO);

        given(repository.fetchDiscountSchemeById(1L))
                .willReturn(Optional.of(getDiscountSchemeInfoWithoutNetDiscount()));

        given(repository.checkIfDiscountSchemeNameAndCodeExists(updateRequestDTO.getId(),
                updateRequestDTO.getName(),
                updateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        given(departmentDiscountSchemeRepository.fetchByDiscountSchemeId(updateRequestDTO.getId()))
                .willReturn(getDepartmentDiscountSchemeList());

        given(serviceDiscountSchemeRepository.fetchByDiscountSchemeId(updateRequestDTO.getId()))
                .willReturn(getServiceDiscountSchemeList());

        discountSchemeService.updateDiscountScheme(getDiscountSchemeUpdateRequestDTOWithNetDisocunt());

        assertTrue(new ReflectionEquals(expected)
                .matches(getDiscountSchemeInfoWithNetDiscountToUpdate()));

        verify(repository)
                .checkIfDiscountSchemeNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),
                        updateRequestDTO.getCode());

        verify(repository)
                .checkIfDiscountSchemeNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),
                        updateRequestDTO.getCode());

        verify(departmentDiscountSchemeRepository)
                .fetchByDiscountSchemeId(updateRequestDTO.getId());

        verify(serviceDiscountSchemeRepository)
                .fetchByDiscountSchemeId(updateRequestDTO.getId());
    }

    @Test
    public void updateDiscountScheme_ShouldUpdateWithoutNetDiscount() {


        DiscountSchemeUpdateRequestDTO updateRequestDTO = getDiscountSchemeUpdateRequestDTOWithoutNetDisocunt();


        given(repository.fetchDiscountSchemeById(1L))
                .willReturn(Optional.of(getDiscountSchemeInfoWithNetDiscount()));

        given(repository.checkIfDiscountSchemeNameAndCodeExists(updateRequestDTO.getId(),
                updateRequestDTO.getName(),
                updateRequestDTO.getCode()))
                .willReturn(new ArrayList<>());

        given(departmentDiscountSchemeRepository.fetchByDiscountSchemeId(updateRequestDTO.getId()))
                .willReturn(getDepartmentDiscountSchemeList());

        given(serviceDiscountSchemeRepository.fetchByDiscountSchemeId(updateRequestDTO.getId()))
                .willReturn(getServiceDiscountSchemeList());

        given(departmentDiscountSchemeRepository.fetchByDiscountSchemeAndDepartmentId(
                updateRequestDTO.getDepartmentDiscountList().get(0).getDepartmentId(),updateRequestDTO.getId()))
                .willReturn(null);

        given(serviceDiscountSchemeRepository.fetchByDiscountSchemeAndServiceId(
                updateRequestDTO.getServiceDiscountList().get(0).getServiceId(),updateRequestDTO.getId()))
                .willReturn(null);

        given(departmentRepository.findActiveDepartmentById(1L))
                .willReturn(Optional.of(getDepartmentInfo()));

        given(serviceRepository.fetchActiveServiceById(1L))
                .willReturn(Optional.of(getServiceInfo()));


        discountSchemeService.updateDiscountScheme(updateRequestDTO);



        verify(repository)
                .checkIfDiscountSchemeNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),
                        updateRequestDTO.getCode());

        verify(repository)
                .checkIfDiscountSchemeNameAndCodeExists(updateRequestDTO.getId(),
                        updateRequestDTO.getName(),
                        updateRequestDTO.getCode());

        verify(departmentDiscountSchemeRepository)
                .fetchByDiscountSchemeAndDepartmentId(updateRequestDTO.getDepartmentDiscountList().get(0)
                        .getDepartmentId(),updateRequestDTO.getId());

        verify(departmentDiscountSchemeRepository)
                .save(any(DepartmentDiscount.class));

        verify(serviceDiscountSchemeRepository)
                .save(any(ServiceDiscount.class));
    }



    @Test(expected = NoContentFoundException.class)
    public void searchDiscountScheme_ShouldReturnNoContentFound() {

        DiscountSchemeSearchRequestDTO searchRequestDTO = new DiscountSchemeSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchDiscountScheme(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(AssignBed.class));

        discountSchemeService.searchDiscountScheme(searchRequestDTO, pageable);

        verify(repository)
                .searchDiscountScheme(searchRequestDTO, pageable);
    }

    @Test
    public void searchDiscountScheme_ShouldReturnData() {

        DiscountSchemeSearchRequestDTO searchRequestDTO = new DiscountSchemeSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(repository.searchDiscountScheme(searchRequestDTO, pageable))
                .willReturn(discountSchemeMinimalResponseDTO());

        discountSchemeService.searchDiscountScheme(searchRequestDTO, pageable);

        verify(repository)
                .searchDiscountScheme(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDiscountSchemeDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(repository.fetchDiscountSchemeDetails(id))
                .willThrow(new NoContentFoundException(AssignBed.class, "id", id.toString()));

        discountSchemeService.fetchDiscountSchemeDetails(id);

        verify(repository)
                .fetchDiscountSchemeDetails(id);

    }

    @Test
    public void fetchDiscountSchemeDetails_ShouldReturnData() {

        given(repository.fetchDiscountSchemeDetails(1L))
                .willReturn(getDiscountSchemeResponseDTO());

        discountSchemeService.fetchDiscountSchemeDetails(1L);

        verify(repository)
                .fetchDiscountSchemeDetails(1L);

    }

    @Test
    public void dropDownListWithNetDiscount_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.dropDownListWithNetDiscount())
                .willReturn(Optional.empty());

        discountSchemeService.fetchDropDownListWithNetDiscount();

        verify(repository)
                .dropDownListWithNetDiscount();
    }

    @Test
    public void dropDownListWithNetDiscount_ShouldReturnData() {
        List<DiscountDropDownResponseDTO> dropDownInfo = getDropDownResponse();

        given(repository.dropDownListWithNetDiscount())
                .willReturn(Optional.of(dropDownInfo));

        discountSchemeService.fetchDropDownListWithNetDiscount();

        verify(repository)
                .dropDownListWithNetDiscount();

    }

    @Test
    public void activeDropDownListWithNetDiscount_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.activeDropDownListWithNetDiscount())
                .willReturn(Optional.empty());

        discountSchemeService.fetchActiveDropDownListWithNetDiscount();

        verify(repository)
                .activeDropDownListWithNetDiscount();
    }

    @Test
    public void activeDropDownListWithNetDiscount_ShouldReturnData() {
        List<DiscountDropDownResponseDTO> dropDownInfo = getDropDownResponse();

        given(repository.activeDropDownListWithNetDiscount())
                .willReturn(Optional.of(dropDownInfo));

        discountSchemeService.fetchActiveDropDownListWithNetDiscount();

        verify(repository)
                .activeDropDownListWithNetDiscount();
    }

    @Test
    public void dropDownListByDepartmentId_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.dropDownListByDepartmentId(1L))
                .willReturn(Optional.empty());

        discountSchemeService.fetchDropDownListByDepartmentId(1L);

        verify(repository)
                .dropDownListByDepartmentId(1L);
    }

    @Test
    public void dropDownListByDepartmentId_ShouldReturnData() {
        List<DepartmentDiscountDropDownResponseDTO> dropDownInfo = getDepartmentDiscountDropDownResponseDTOS();

        given(repository.dropDownListByDepartmentId(1L))
                .willReturn(Optional.of(dropDownInfo));

        discountSchemeService.fetchDropDownListByDepartmentId(1L);

        verify(repository)
                .dropDownListByDepartmentId(1L);
    }

    @Test
    public void activeDropDownListByDepartmentId_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.activeDropDownListByDepartmentId(1L))
                .willReturn(Optional.empty());

        discountSchemeService.fetchActiveDropDownListByDepartmentId(1L);

        verify(repository)
                .activeDropDownListByDepartmentId(1L);
    }

    @Test
    public void activeDropDownListByDepartmentId_ShouldReturnData() {
        List<DepartmentDiscountDropDownResponseDTO> dropDownInfo = getDepartmentDiscountDropDownResponseDTOS();

        given(repository.activeDropDownListByDepartmentId(1L))
                .willReturn(Optional.of(dropDownInfo));

        discountSchemeService.fetchActiveDropDownListByDepartmentId(1L);

        verify(repository)
                .activeDropDownListByDepartmentId(1L);
    }

    @Test
    public void dropDownListByServiceId_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.dropDownListByServiceId(1L))
                .willReturn(Optional.empty());

        discountSchemeService.fetchDropDownListByServiceId(1L);

        verify(repository)
                .dropDownListByServiceId(1L);
    }

    @Test
    public void dropDownListByServiceId_ShouldReturnData() {
        List<ServiceDiscountDropDownResponseDTO> dropDownInfo = getServiceDiscountDropDownResponseDTOS();

        given(repository.dropDownListByServiceId(1L))
                .willReturn(Optional.of(dropDownInfo));

        discountSchemeService.fetchDropDownListByServiceId(1L);

        verify(repository)
                .dropDownListByServiceId(1L);
    }

    @Test
    public void activeDropDownListByServiceId_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.activeDropDownListByServiceId(1L))
                .willReturn(Optional.empty());

        discountSchemeService.fetchActiveDropDownListByServiceId(1L);

        verify(repository)
                .activeDropDownListByServiceId(1L);
    }

    @Test
    public void activeDropDownListByServiceId_ShouldReturnData() {
        List<ServiceDiscountDropDownResponseDTO> dropDownInfo = getServiceDiscountDropDownResponseDTOS();

        given(repository.activeDropDownListByServiceId(1L))
                .willReturn(Optional.of(dropDownInfo));

        discountSchemeService.fetchActiveDropDownListByServiceId(1L);

        verify(repository)
                .activeDropDownListByServiceId(1L);
    }

    @Test
    public void deleteDepartmentDiscountScheme_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(departmentDiscountSchemeRepository.fetchById(1L))
                .willReturn(Optional.empty());

        discountSchemeService.deleteDepartmentDiscount(getDeleteRequestDTO());

        verify(departmentDiscountSchemeRepository)
                .fetchById(1L);
    }

    @Test
    public void deleteDepartmentDiscountScheme_ShouldDeleteWithNetDiscount() {

        given(departmentDiscountSchemeRepository.fetchById(1L))
                .willReturn(Optional.of(getDepartmentDiscountInfo()));

        DiscountScheme expected = deleteDiscountScheme.apply(getDiscountSchemeInfoWithNetDiscount(),
                getDeleteRequestDTO());

        discountSchemeService.deleteDepartmentDiscount(getDeleteRequestDTO());

        assertTrue(new ReflectionEquals(expected).matches(getDiscountSchemeInfoWithNetDiscountToDelete()));

        verify(departmentDiscountSchemeRepository)
                .fetchById(1L);

    }

    @Test
    public void deleteServiceDiscountScheme_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(serviceDiscountSchemeRepository.fetchById(1L))
                .willReturn(Optional.empty());

        discountSchemeService.deleteServiceDiscount(getDeleteRequestDTO());

        verify(serviceDiscountSchemeRepository)
                .fetchById(1L);
    }

    @Test
    public void deleteServiceDiscountScheme_ShouldDeleteWithNetDiscount() {

        given(serviceDiscountSchemeRepository.fetchById(1L))
                .willReturn(Optional.of(getServiceDiscountSchemeInfo()));

        DiscountScheme expected = deleteDiscountScheme.apply(getDiscountSchemeInfoWithNetDiscount(),
                getDeleteRequestDTO());

        discountSchemeService.deleteServiceDiscount(getDeleteRequestDTO());

        assertTrue(new ReflectionEquals(expected).matches(getDiscountSchemeInfoWithNetDiscountToDelete()));

        verify(serviceDiscountSchemeRepository)
                .fetchById(1L);

    }


}
