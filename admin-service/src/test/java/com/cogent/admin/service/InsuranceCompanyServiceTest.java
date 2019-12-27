package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanyUpdateRequestDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.InsuranceCompanyRepository;
import com.cogent.admin.service.impl.InsuranceCompanyServiceImpl;
import com.cogent.persistence.model.InsuranceCompany;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.insurancecompany.InsuranceCompanyTestUtils.*;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.InsuranceCompanyUtils.convertToInsuranceCompanyInfo;
import static com.cogent.admin.utils.InsuranceCompanyUtils.updateInsuranceCompany;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class InsuranceCompanyServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    InsuranceCompanyRepository insuranceCompanyRepository;

    @InjectMocks
    InsuranceCompanyServiceImpl insuranceCompanyService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createInsuranceCompany_ShouldReturnNameAlreadyExists();
        createInsuranceCompany_ShouldCreate();
    }

    @Test
    public void delete() {
        deleteInsuranceCompany_ShouldReturnNoContentFound();
        deleteInsuranceCompany_ShouldDelete();
    }

    @Test
    public void update() {
        updateInsuranceCompany_ShouldReturnNoContentFound();
        updateInsuranceCompany_ShouldReturnNameAlreadyExists();
        updateInsuranceCompany_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchInsuranceCompany_ShouldReturnNoContentFound();
        searchInsuranceCompany_ShouldReturnInsuranceCompany();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDetailById() {
        fetchInsuranceCompanyDetails_ShouldReturnNoContentFound();
        fetchInsuranceCompanyDetails_ShouldReturnData();
    }

    @Test
    public void fetchInsurance() {
        fetchInsuranceCompany_ShouldReturnNoContentFound();
        fetchInsuranceCompany_ShouldReturnData();
    }


    @Test
    public void dropDown() {
        dropDownList_ShouldReturnNoContentFound();
        dropDownList_ShouldReturnData();
    }

    @Test
    public void activeDropDown() {
        activeDropDownList_ShouldReturnNoContentFound();
        activeDropDownList_ShouldReturnData();
    }


    @Test
    public void createInsuranceCompany_ShouldReturnNameAlreadyExists() {

        thrown.expect(DataDuplicationException.class);

        InsuranceCompanyRequestDTO requestDTO = getInsuranceCompanyRequestDTO();

        given(insuranceCompanyRepository.fetchInsuranceCompanyCountByName(requestDTO.getName()))
                .willReturn(1L);

        insuranceCompanyService.createInsuranceCompany(requestDTO);

    }

    @Test
    public void createInsuranceCompany_ShouldCreate() {

        InsuranceCompanyRequestDTO requestDTO = getInsuranceCompanyRequestDTO();

        given(insuranceCompanyRepository.fetchInsuranceCompanyCountByName(requestDTO.getName()))
                .willReturn(0L);

        insuranceCompanyService.createInsuranceCompany(requestDTO);

        assertNotNull(convertToInsuranceCompanyInfo(requestDTO));

        verify(insuranceCompanyRepository).save(any(InsuranceCompany.class));

    }

    @Test
    public void deleteInsuranceCompany_ShouldReturnNoContentFound() {

        thrown.expect(NoContentFoundException.class);

        given(insuranceCompanyRepository.fetchInsuranceCompanyById(1L)).willReturn(Optional.empty());

        insuranceCompanyService.deleteInsuranceCompany(getDeleteRequestDTO());
    }

    @Test
    public void deleteInsuranceCompany_ShouldDelete() {

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        InsuranceCompany insuranceCompany = updateInsuranceCompany
                .apply(getInsuranceCompanyInfo(), deleteRequestDTO);

        given(insuranceCompanyRepository.fetchInsuranceCompanyById(1L))
                .willReturn(Optional.of(getInsuranceCompanyInfo()));

        insuranceCompanyService.deleteInsuranceCompany(getDeleteRequestDTO());

        assertTrue(insuranceCompany.getStatus() == deleteRequestDTO.getStatus()
                && insuranceCompany.getRemarks() == deleteRequestDTO.getRemarks());

        verify(insuranceCompanyRepository).save(any(InsuranceCompany.class));
    }

    @Test
    public void updateInsuranceCompany_ShouldReturnNoContentFound() {

        thrown.expect(NoContentFoundException.class);

        given(insuranceCompanyRepository.fetchInsuranceCompanyById(1L)).willReturn(Optional.empty());

        insuranceCompanyService.updateInsuranceCompany(getInsuranceCompanyUpdateRequestDTO());
    }

    @Test
    public void updateInsuranceCompany_ShouldReturnNameAlreadyExists() {

        InsuranceCompany insuranceCompany = getInsuranceCompanyInfo();

        InsuranceCompanyUpdateRequestDTO updateRequestDTO = getInsuranceCompanyUpdateRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(insuranceCompanyRepository.fetchInsuranceCompanyById(updateRequestDTO.getId()))
                .willReturn(Optional.of(insuranceCompany));

        given(insuranceCompanyRepository.checkInsuranceCompanyNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(1L);

        insuranceCompanyService.updateInsuranceCompany(updateRequestDTO);
    }

    @Test
    public void updateInsuranceCompany_ShouldUpdate() {

        InsuranceCompanyUpdateRequestDTO updateRequestDTO = getInsuranceCompanyUpdateRequestDTO();

        InsuranceCompany insuranceCompanyUpdated = updateInsuranceCompany.apply(getInsuranceCompanyInfo(),
                ConvertToDeleteRequestDTO.apply(updateRequestDTO.getRemarks(),
                        updateRequestDTO.getStatus()));

        given(insuranceCompanyRepository.fetchInsuranceCompanyById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getInsuranceCompanyInfo()));

        given(insuranceCompanyRepository.checkInsuranceCompanyNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(0L);

        insuranceCompanyService.updateInsuranceCompany(updateRequestDTO);

        assertTrue(insuranceCompanyUpdated.getStatus() == updateRequestDTO.getStatus()
                && insuranceCompanyUpdated.getRemarks() == updateRequestDTO.getRemarks());

        verify(insuranceCompanyRepository).save(any(InsuranceCompany.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void searchInsuranceCompany_ShouldReturnNoContentFound() {

        Pageable pageable = PageRequest.of(1, 10);

        given(insuranceCompanyRepository.searchInsuranceCompany(getInsuranceCompanySeacrhRequestDTO(), pageable))
                .willThrow(new NoContentFoundException(InsuranceCompany.class));

        insuranceCompanyService.searchInsuranceCompany(getInsuranceCompanySeacrhRequestDTO(), pageable);
    }

    @Test
    public void searchInsuranceCompany_ShouldReturnInsuranceCompany() {

        InsuranceCompanySearchRequestDTO searchRequestDTO = getInsuranceCompanySeacrhRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(insuranceCompanyRepository.searchInsuranceCompany(searchRequestDTO, pageable))
                .willReturn(Arrays.asList(getInsuranceCompanyMinimalResponseDTO()));

        insuranceCompanyService.searchInsuranceCompany(searchRequestDTO, pageable);

        verify(insuranceCompanyRepository).searchInsuranceCompany(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchInsuranceCompanyDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(insuranceCompanyRepository.fetchInsuranceCompanyDetails(id))
                .willThrow(new NoContentFoundException(InsuranceCompany.class, "id", id.toString()));

        insuranceCompanyService.fetchInsuranceCompanyDetails(id);

    }

    @Test
    public void fetchInsuranceCompanyDetails_ShouldReturnData() {

        InsuranceCompanyResponseDTO responseDTOS = getInsuranceCompanyResponseDTO();

        given(insuranceCompanyRepository.fetchInsuranceCompanyDetails(1L))
                .willReturn((responseDTOS));

        insuranceCompanyService.fetchInsuranceCompanyDetails(1L);

        verify(insuranceCompanyRepository).fetchInsuranceCompanyDetails(1L);

    }

    @Test
    public void fetchInsuranceCompany_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(insuranceCompanyRepository.fetchInsuranceCompanyById(1L))
                .willReturn(Optional.empty());

        insuranceCompanyService.fetchInsuranceCompany(1L);

    }

    @Test
    public void fetchInsuranceCompany_ShouldReturnData() {

        given(insuranceCompanyRepository.fetchInsuranceCompanyById(1L))
                .willReturn(Optional.ofNullable(getInsuranceCompanyInfo()));

        insuranceCompanyService.fetchInsuranceCompany(1L);

        verify(insuranceCompanyRepository).fetchInsuranceCompanyById(1L);

    }


    @Test
    public void dropDownList_ShouldReturnNoContentFound() {

        thrown.expect(NoContentFoundException.class);

        given(insuranceCompanyRepository.fetchDropDownList())
                .willReturn(Optional.empty());

        insuranceCompanyService.fetchDropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {

        given(insuranceCompanyRepository.fetchDropDownList())
                .willReturn(Optional.of(getDropDownList()));

        insuranceCompanyService.fetchDropDownList();

        verify(insuranceCompanyRepository).fetchDropDownList();
    }


    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {

        thrown.expect(NoContentFoundException.class);

        given(insuranceCompanyRepository.fetchActiveDropDownList())
                .willReturn(Optional.empty());

        insuranceCompanyService.fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {

        given(insuranceCompanyRepository.fetchActiveDropDownList())
                .willReturn(Optional.of(getDropDownList()));

        insuranceCompanyService.fetchActiveDropDownList();

        verify(insuranceCompanyRepository).fetchActiveDropDownList();
    }


}
