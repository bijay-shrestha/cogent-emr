package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationSearchRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationUpdateRequestDTO;
import com.cogent.admin.dto.response.qualification.QualificationMinimalResponseDTO;
import com.cogent.admin.dto.response.qualification.QualificationResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.QualificationRepository;
import com.cogent.admin.service.impl.QualificationServiceImpl;
import com.cogent.persistence.model.Country;
import com.cogent.persistence.model.Qualification;
import com.cogent.persistence.model.QualificationAlias;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.CommonRequestUtils.getDeleteRequestDTO;
import static com.cogent.admin.dto.country.CountryResponseUtils.fetchCountry;
import static com.cogent.admin.dto.request.qualification.QualificationRequestUtils.*;
import static com.cogent.admin.dto.request.qualification.QualificationResponseUtils.*;
import static com.cogent.admin.dto.qualificationAlias.QualificationAliasResponseUtils.fetchQualificationAlias;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 12/11/2019
 */
public class QualificationServiceTest {

    @InjectMocks
    private QualificationServiceImpl qualificationService;

    @Mock
    private QualificationRepository qualificationRepository;

    @Mock
    private CountryService countryService;

    @Mock
    private QualificationAliasService qualificationAliasService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveQualificationTest() {
        Should_Throw_Exception_When_CountryNotFound();
        Should_Throw_Exception_When_QualificationAliasNotFound();
        Should_Save_Qualification();
    }

    @Test
    public void updateQualificationTest() {
        updateQualification_Should_Throw_Exception_When_QualificationNotFound();
        updateQualification_Should_Throw_Exception_When_CountryNotFound();
        updateQualification_Should_Throw_Exception_When_QualificationAliasNotFound();
        Should_Update_Qualification();
    }

    @Test
    public void deleteQualificationTest() {
        deleteQualification_Should_Throw_Exception_When_QualificationNotFound();
        Should_Delete_Qualification();
    }

    @Test
    public void searchQualification() {
        Should_ThrowException_When_QualificationList_IsEmpty();
        Should_Successfully_Return_QualificationList();
    }

    @Test
    public void fetchQualificationForDropDown() {
        fetchQualificationForDropDown_Should_ThrowException_When_QualificationList_IsEmpty();
        Should_Return_QualificationListForDropdown();
    }

    @Test
    public void fetchQualificationDetails() {
        Should_ThrowException_When_Qualification_With_Given_ID_Is_Null();
        Should_Return_Qualification_Details();
    }

    @Test
    public void Should_Throw_Exception_When_CountryNotFound() {
        QualificationRequestDTO requestDTO = getQualificationRequestDTO();

        given(countryService.fetchCountryById(requestDTO.getCountryId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        qualificationService.save(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_QualificationAliasNotFound() {
        QualificationRequestDTO requestDTO = getQualificationRequestDTO();

        given(qualificationAliasService.fetchQualificationAliasById(requestDTO.getQualificationAliasId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        qualificationService.save(requestDTO);
    }

    @Test
    public void Should_Save_Qualification() {
        QualificationRequestDTO requestDTO = getQualificationRequestDTO();
        Country country = fetchCountry();
        QualificationAlias qualificationAlias = fetchQualificationAlias();

        given(countryService.fetchCountryById(requestDTO.getCountryId()))
                .willReturn(country);

        given(qualificationAliasService.fetchQualificationAliasById(requestDTO.getQualificationAliasId()))
                .willReturn(qualificationAlias);

        qualificationService.save(requestDTO);

        verify(qualificationRepository, times(1)).save(any(Qualification.class));
    }

    @Test
    public void updateQualification_Should_Throw_Exception_When_QualificationNotFound() {

        QualificationUpdateRequestDTO requestDTO = getQualificationUpdateRequestDTO();

        given(qualificationRepository.findQualificationById(requestDTO.getId()))
                .willReturn(Optional.empty());

        qualificationService.update(requestDTO);

        thrown.expect(NoContentFoundException.class);
    }

    @Test
    public void updateQualification_Should_Throw_Exception_When_CountryNotFound() {
        QualificationUpdateRequestDTO requestDTO = getQualificationUpdateRequestDTO();

        given(qualificationRepository.findQualificationById(requestDTO.getId()))
                .willReturn(Optional.of(fetchQualification()));

        given(countryService.fetchCountryById(requestDTO.getCountryId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        qualificationService.update(requestDTO);
    }

    @Test
    public void updateQualification_Should_Throw_Exception_When_QualificationAliasNotFound() {
        QualificationUpdateRequestDTO requestDTO = getQualificationUpdateRequestDTO();

        given(qualificationRepository.findQualificationById(requestDTO.getId()))
                .willReturn(Optional.of(fetchQualification()));

        given(countryService.fetchCountryById(requestDTO.getCountryId()))
                .willReturn(fetchCountry());

        given(qualificationAliasService.fetchQualificationAliasById(requestDTO.getQualificationAliasId()))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        qualificationService.update(requestDTO);
    }

    @Test
    public void Should_Update_Qualification() {
        QualificationUpdateRequestDTO requestDTO = getQualificationUpdateRequestDTO();

        given(qualificationRepository.findQualificationById(requestDTO.getId()))
                .willReturn(Optional.of(fetchQualification()));

        given(countryService.fetchCountryById(requestDTO.getCountryId()))
                .willReturn(fetchCountry());

        given(qualificationAliasService.fetchQualificationAliasById(requestDTO.getQualificationAliasId()))
                .willReturn(fetchQualificationAlias());

        qualificationService.update(requestDTO);
    }

    @Test
    public void deleteQualification_Should_Throw_Exception_When_QualificationNotFound() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();
        given(qualificationRepository.findQualificationById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        qualificationService.delete(deleteRequestDTO);

        thrown.expect(NoContentFoundException.class);
    }

    @Test
    public void Should_Delete_Qualification() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();
        given(qualificationRepository.findQualificationById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(fetchQualification()));

        qualificationService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_ThrowException_When_QualificationList_IsEmpty() {

        Pageable pageable = PageRequest.of(1, 10);
        QualificationSearchRequestDTO searchRequestDTO = getQualificationSearchRequestDTO();

        given(qualificationRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        qualificationService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_QualificationList() {
        Pageable pageable = PageRequest.of(1, 10);
        QualificationSearchRequestDTO searchRequestDTO = getQualificationSearchRequestDTO();
        List<QualificationMinimalResponseDTO> expected = fetchMinimalQualificationList();

        given(qualificationRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        given(qualificationRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(qualificationService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(qualificationRepository.search(searchRequestDTO, pageable),
                hasSize(expected.size()));
    }

    @Test
    public void fetchQualificationForDropDown_Should_ThrowException_When_QualificationList_IsEmpty() {

        given(qualificationRepository.fetchActiveQualificationForDropDown())
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        qualificationService.fetchActiveQualificationForDropDown();
    }

    @Test
    public void Should_Return_QualificationListForDropdown() {
        given(qualificationRepository.fetchActiveQualificationForDropDown())
                .willReturn(fetchQualificationListForDropDown());

        qualificationService.fetchActiveQualificationForDropDown();

        assertThat(qualificationService.fetchActiveQualificationForDropDown(),
                hasSize(fetchQualificationListForDropDown().size()));

        assertThat(qualificationService.fetchActiveQualificationForDropDown(),
                samePropertyValuesAs(fetchQualificationListForDropDown()));
    }

    @Test
    public void Should_ThrowException_When_Qualification_With_Given_ID_Is_Null() {
        Long id = 1L;

        given(qualificationRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        qualificationService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_Qualification_Details() {
        Long id = 1L;
        QualificationResponseDTO expected = fetchQualificationDetailById();

        given(qualificationRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(qualificationService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

}
