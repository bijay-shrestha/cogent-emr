package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleSearchRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleUpdateRequestDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDetailResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleMinimalResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.ApplicationModuleRepository;
import com.cogent.admin.service.impl.ApplicationModuleServiceImpl;
import com.cogent.persistence.model.ApplicationModule;
import com.cogent.persistence.model.SubDepartment;
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

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.applicationModules.ApplicationModuleRequestUtils.*;
import static com.cogent.admin.dto.applicationModules.ApplicationModuleResponseUtils.*;
import static com.cogent.admin.dto.subdepartment.SubDepartmentTestUtlis.getSubDepartmentInfo;
import static com.cogent.admin.utils.ApplicationModuleUtils.parseToApplicationModule;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti ON 25/12/2019
 */

public class ApplicationModuleServiceTest {

    @InjectMocks
    private ApplicationModuleServiceImpl applicationModuleService;

    @Mock
    private ApplicationModuleRepository applicationModuleRepository;

    @Mock
    private SubDepartmentService subDepartmentService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveApplicationModuleTest() {

        Save_Should_Throw_Exception_When_Name_Duplicates();

        Should_Throw_Exception_When_SubDepartmentNotFound();

        Should_Successfully_Save_ApplicationModule();
    }

    @Test
    public void updateApplicationModuleTest() {
        Update_Should_Throw_Exception_When_ApplicationModule_NotFound();

        Update_Should_Throw_Exception_When_Name_Duplicates();

        Should_Successfully_Update_ApplicationModule();
    }

    @Test
    public void deleteApplicationModuleTest() {

        Delete_Should_ThrowException_When_ApplicationModule_NotFound();

        Should_SuccessFully_Delete_ApplicationModule();
    }

    @Test
    public void searchApplicationModule() {

        Search_Should_ThrowException_When_ApplicationModuleList_IsEmpty();

        Should_Successfully_Return_ApplicationModules();
    }

    @Test
    public void fetchApplicationModuleForDropDown() {
        fetchForDropDown_Should_ThrowException_When_ApplicationModuleList_IsEmpty();

        Should_Return_ApplicationModules();
    }

    @Test
    public void fetchApplicationModuleDetails() {
        Should_ThrowException_When_ApplicationModule_With_Given_ID_Is_Null();

        Should_Return_ApplicationModule_Details();
    }

    @Test
    public void Save_Should_Throw_Exception_When_Name_Duplicates() {
        ApplicationModuleRequestDTO requestDTO = getApplicationModuleRequestDTO();

        given(applicationModuleRepository.fetchApplicationModuleByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        applicationModuleService.save(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_SubDepartmentNotFound() {

        ApplicationModuleRequestDTO requestDTO = getApplicationModuleRequestDTO();

        given(applicationModuleRepository.fetchApplicationModuleByName(requestDTO.getName()))
                .willReturn(0L);

        given(subDepartmentService.findById(requestDTO.getSubDepartmentId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        applicationModuleService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_ApplicationModule() {
        ApplicationModuleRequestDTO requestDTO = getApplicationModuleRequestDTO();
        SubDepartment subDepartment = getSubDepartmentInfo();

        ApplicationModule expected = parseToApplicationModule(requestDTO, subDepartment);

        given(applicationModuleRepository.fetchApplicationModuleByName(requestDTO.getName()))
                .willReturn(0L);

        given(subDepartmentService.findById(requestDTO.getSubDepartmentId()))
                .willReturn(subDepartment);

        applicationModuleService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "subDepartmentId"})
                .matches(getApplicationModule()));

        Assert.assertTrue(new ReflectionEquals(expected.getSubDepartmentId().getId())
                .matches(getApplicationModule().getSubDepartmentId().getId()));

        verify(applicationModuleRepository, times(1)).save(any(ApplicationModule.class));
    }

    @Test
    public void Update_Should_Throw_Exception_When_ApplicationModule_NotFound() {
        ApplicationModuleUpdateRequestDTO requestDTO = getApplicationModuleRequestDTOForUpdate();

        given(applicationModuleRepository.findApplicationModuleById(requestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        applicationModuleService.update(requestDTO);
    }

    @Test
    public void Update_Should_Throw_Exception_When_Name_Duplicates() {
        ApplicationModuleUpdateRequestDTO requestDTO = getApplicationModuleRequestDTOForUpdate();

        given(applicationModuleRepository.findApplicationModuleById(requestDTO.getId())).willReturn
                (Optional.of(getApplicationModule()));

        given(applicationModuleRepository.fetchApplicationModuleByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        applicationModuleService.update(requestDTO);
    }

    @Test
    public void Update_Should_Throw_Exception_When_SubDepartmentNotFound() {

        ApplicationModuleUpdateRequestDTO requestDTO = getApplicationModuleRequestDTOForUpdate();

        given(applicationModuleRepository.fetchApplicationModuleByName(requestDTO.getName()))
                .willReturn(0L);

        given(subDepartmentService.findById(requestDTO.getSubDepartmentId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        applicationModuleService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_Update_ApplicationModule() {

        ApplicationModuleUpdateRequestDTO requestDTO = getApplicationModuleRequestDTOForUpdate();
        SubDepartment subDepartment = getSubDepartmentInfo();

        given(applicationModuleRepository.findApplicationModuleById(requestDTO.getId())).willReturn
                (Optional.of(getApplicationModule()));

        given(applicationModuleRepository.fetchApplicationModuleByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(0L);

        given(subDepartmentService.findById(requestDTO.getSubDepartmentId()))
                .willReturn(getSubDepartmentInfo());

        applicationModuleService.update(requestDTO);
    }

    @Test
    public void Delete_Should_ThrowException_When_ApplicationModule_NotFound() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(applicationModuleRepository.findApplicationModuleById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        applicationModuleService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_ApplicationModule() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(applicationModuleRepository.findApplicationModuleById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(getApplicationModule()));

        applicationModuleService.delete(deleteRequestDTO);
    }

    @Test
    public void Search_Should_ThrowException_When_ApplicationModuleList_IsEmpty() {
        Pageable pageable = PageRequest.of(1, 10);
        ApplicationModuleSearchRequestDTO searchRequestDTO = getApplicationModuleRequestDTOForSearch();

        given(applicationModuleRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        applicationModuleService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_ApplicationModules() {
        ApplicationModuleSearchRequestDTO searchRequestDTO = getApplicationModuleRequestDTOForSearch();
        List<ApplicationModuleMinimalResponseDTO> expected = fetchMinimalApplicationModuleList();

        Pageable pageable = PageRequest.of(1, 10);

        given(applicationModuleRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(applicationModuleService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(applicationModuleService.search(searchRequestDTO, pageable),
                hasSize(expected.size()));
    }

    @Test
    public void fetchForDropDown_Should_ThrowException_When_ApplicationModuleList_IsEmpty() {

        given(applicationModuleRepository.fetchActiveApplicationModuleForDropDown())
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        applicationModuleService.fetchActiveApplicationModuleForDropDown();
    }

    @Test
    public void Should_Return_ApplicationModules() {
        given(applicationModuleRepository.fetchActiveApplicationModuleForDropDown())
                .willReturn(fetchApplicationModuleListForDropDown());

        applicationModuleService.fetchActiveApplicationModuleForDropDown();

        assertThat(applicationModuleService.fetchActiveApplicationModuleForDropDown(),
                hasSize(fetchApplicationModuleListForDropDown().size()));

        assertThat(applicationModuleService.fetchActiveApplicationModuleForDropDown(),
                samePropertyValuesAs(fetchApplicationModuleListForDropDown()));
    }

    @Test
    public void Should_ThrowException_When_ApplicationModule_With_Given_ID_Is_Null() {
        Long id = 1L;

        given(applicationModuleRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        applicationModuleService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_ApplicationModule_Details() {
        Long id = 1L;
        ApplicationModuleDetailResponseDTO expected = fetchApplicationModuleDetailById();

        given(applicationModuleRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(applicationModuleService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

}
