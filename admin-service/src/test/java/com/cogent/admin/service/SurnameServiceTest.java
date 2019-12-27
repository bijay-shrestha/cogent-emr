package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameUpdateRequestDTO;
import com.cogent.admin.dto.response.surname.SurnameMinimalResponseDTO;
import com.cogent.admin.dto.response.surname.SurnameResponseDTO;
import com.cogent.admin.dto.surname.SurnameResponseUtils;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.EthnicityRepository;
import com.cogent.admin.repository.SurnameRepository;
import com.cogent.admin.service.impl.SurnameServiceImpl;
import com.cogent.admin.utils.SurnameUtils;
import com.cogent.persistence.model.Ethnicity;
import com.cogent.persistence.model.Surname;
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
import static com.cogent.admin.dto.ethnicity.EthnicityTestUtils.getEthnicityInfo;
import static com.cogent.admin.dto.surname.SurnameRequestUtils.*;
import static com.cogent.admin.dto.surname.SurnameResponseUtils.*;
import static com.cogent.admin.utils.SurnameUtils.convertDTOToSurname;
import static com.cogent.admin.utils.SurnameUtils.convertToUpdatedSurname;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 2019-09-12
 */
public class SurnameServiceTest {

    @InjectMocks
    private SurnameServiceImpl surnameService;

    @Mock
    private SurnameRepository surnameRepository;

    @Mock
    private EthnicityRepository ethnicityRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {

        Should_Throw_Exception_When_Name_Duplicates();

        Should_Throw_Exception_When_EthnicityNotFound();

        Should_Successfully_Save_Surname();
    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_Surname_NotFound();

        Should_Throw_Exception_When_Name_Already_Exists();

        Should_Throw_Exception_When_EthnicityNotFound();

        Should_Successfully_Update_Surname();
    }

    @Test
    public void deleteSurnameTest() {

        Should_ThrowException_When_Surname_Is_Null();

        Should_SuccessFully_Delete_Surname();
    }

    @Test
    public void searchSurname() {

        Should_ThrowException_When_SurnameList_IsEmpty();

        Should_Successfully_Return_SurnameList();
    }

    @Test
    public void fetchSurnameForDropDown() {
        Should_ThrowException_When_No_SurnameFound();

        Should_Return_Surnames();
    }

    @Test
    public void fetchSurnameDetails() {
        Should_ThrowException_When_Surname_With_Given_ID_Is_Null();

        Should_Return_Surname_Details();
    }

    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        SurnameRequestDTO requestDTO = getSurnameRequestDTO();

        given(surnameRepository.fetchSurnameByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        surnameService.save(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_EthnicityNotFound() {
        SurnameRequestDTO requestDTO = getSurnameRequestDTO();
        given(surnameRepository.fetchSurnameByName(requestDTO.getName()))
                .willReturn(0L);

        given(ethnicityRepository.fetchActiveEthnicityById(requestDTO.getEthnicityId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        surnameService.save(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_Surname() {
        SurnameRequestDTO requestDTO = getSurnameRequestDTO();
        Ethnicity ethnicity = getEthnicityInfo();

        Surname expected = convertDTOToSurname(requestDTO, ethnicity);

        given(surnameRepository.fetchSurnameByName(requestDTO.getName()))
                .willReturn(0L);
        given(ethnicityRepository.fetchActiveEthnicityById(requestDTO.getEthnicityId()))
                .willReturn(Optional.of(ethnicity));

        surnameService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "ethnicity"}).matches(getSurname()));

        verify(surnameRepository, times(1)).save(any(Surname.class));
    }

    @Test
    public void Should_Throw_Exception_When_Surname_NotFound() {
        SurnameUpdateRequestDTO requestDTO = getSurnameUpdateRequestDTO();

        given(surnameRepository.findSurnameById(requestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        surnameService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        SurnameUpdateRequestDTO requestDTO = getSurnameUpdateRequestDTO();

        given(surnameRepository.findSurnameById(requestDTO.getId()))
                .willReturn(Optional.of(getSurname()));

        given(surnameRepository.findSurnameByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        surnameService.update(requestDTO);
    }

    @Test
    public void updateSurname_ShouldThrowExceptionWhenEthnicityNotFound() {
        SurnameUpdateRequestDTO requestDTO = getSurnameUpdateRequestDTO();

        given(surnameRepository.findSurnameById(requestDTO.getId()))
                .willReturn(Optional.of(getSurname()));

        given(surnameRepository.findSurnameByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(0L);

        given(ethnicityRepository.fetchActiveEthnicityById(requestDTO.getEthnicityId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        surnameService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_Update_Surname() {

        SurnameUpdateRequestDTO requestDTO = getSurnameUpdateRequestDTO();
        Surname expected = convertToUpdatedSurname(requestDTO, getSurname(), getEthnicityInfo());

        given(surnameRepository.findSurnameById(requestDTO.getId()))
                .willReturn(Optional.of(getSurname()));

        given(surnameRepository.findSurnameByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(0L);

        given(ethnicityRepository.fetchActiveEthnicityById(requestDTO.getEthnicityId()))
                .willReturn(Optional.of(getEthnicityInfo()));

        surnameService.update(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "ethnicity"}).matches(getUpdatedSurname()));

        verify(surnameRepository, times(1)).save(any(expected.getClass()));
    }

    @Test
    public void Should_ThrowException_When_Surname_Is_Null() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(surnameRepository.findSurnameById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        surnameService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_Surname() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Surname surname = getSurname();

        given(surnameRepository.findSurnameById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(surname));

        Surname expected = SurnameUtils.convertToDeletedSurname(surname, deleteRequestDTO);

        given(surnameRepository.save(expected)).willReturn(getDeletedSurname());

        surnameService.delete(deleteRequestDTO);

        assertTrue(surnameRepository.save(expected).getStatus().equals('D') &&
                surnameRepository.save(expected).getRemarks().equals(getDeletedSurname().getRemarks()));

        verify(surnameRepository, times(3)).save(any(expected.getClass()));
    }

    @Test
    public void Should_ThrowException_When_SurnameList_IsEmpty() {
        Pageable pageable = PageRequest.of(1, 10);
        SurnameSearchRequestDTO searchRequestDTO = getSurnameSearchRequestDTO();

        given(surnameRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        surnameService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_SurnameList() {
        SurnameSearchRequestDTO searchRequestDTO = getSurnameSearchRequestDTO();
        List<SurnameMinimalResponseDTO> expected = fetchSurnameMinimalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(surnameRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(surnameService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(surnameService.search(searchRequestDTO, pageable),
                hasSize(expected.size()));
    }

    @Test
    public void Should_ThrowException_When_No_SurnameFound() {

        given(surnameRepository.fetchActiveSurnameForDropDown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        surnameService.fetchActiveSurnameForDropDown();
    }

    @Test
    public void Should_Return_Surnames() {
        given(surnameRepository.fetchActiveSurnameForDropDown())
                .willReturn(SurnameResponseUtils.fetchSurnameForDropDown());

        surnameService.fetchActiveSurnameForDropDown();

        assertThat(surnameService.fetchActiveSurnameForDropDown(),
                hasSize(SurnameResponseUtils.fetchSurnameForDropDown().size()));

        assertThat(surnameService.fetchActiveSurnameForDropDown(),
                samePropertyValuesAs(SurnameResponseUtils.fetchSurnameForDropDown()));
    }

    @Test
    public void fetchSurname_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(surnameRepository.findSurnameById(1L))
                .willReturn(Optional.empty());

        surnameService.fetchSurname(1L);
    }

    @Test
    public void fetchSurname_ShouldReturnData() {

        given(surnameRepository.findSurnameById(1L))
                .willReturn(Optional.of((getSurname())));

        surnameService.fetchSurname(1L);

        verify(surnameRepository).findSurnameById(1L);
    }

    @Test
    public void Should_ThrowException_When_Surname_With_Given_ID_Is_Null() {
        Long id = 1L;

        given(surnameRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        surnameService.fetchSurnameDetails(id);
    }

    @Test
    public void Should_Return_Surname_Details() {
        Long id = 1L;
        SurnameResponseDTO expected = fetchSurnameResponseDTO();

        given(surnameRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(surnameService.fetchSurnameDetails(id), samePropertyValuesAs(expected));
    }

}
