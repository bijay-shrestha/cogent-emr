package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityUpdateRequestDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityMinimalResponseDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityResponseDTO;
import com.cogent.admin.dto.municipality.MunicipalityResponseUtils;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DistrictRepository;
import com.cogent.admin.repository.MunicipalityRepository;
import com.cogent.admin.service.impl.MunicipalityServiceImpl;
import com.cogent.admin.utils.MunicipalityUtils;
import com.cogent.persistence.model.Municipality;
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
import static com.cogent.admin.dto.district.DistrictTestUtils.getDistrictInfo;
import static com.cogent.admin.dto.municipality.MunicipalityResponseUtils.*;
import static com.cogent.admin.dto.municipality.MunicipalityTestUtils.*;
import static com.cogent.admin.utils.MunicipalityUtils.convertDTOToMunicipality;
import static com.cogent.admin.utils.MunicipalityUtils.convertToUpdatedMunicipality;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author smriti on 2019-09-15
 */
public class MunicipalityServiceTest {

    @InjectMocks
    private MunicipalityServiceImpl municipalityService;

    @Mock
    private MunicipalityRepository municipalityRepository;

    @Mock
    private DistrictRepository districtRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {

        Should_Throw_Exception_When_Name_Duplicates();

        Should_Throw_Exception_When_District_Not_Found();

        Should_Successfully_Save_Municipality();
    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_Municipality_NotFound();

        Should_Throw_Exception_When_Name_Already_Exists();

        Should_Throw_Exception_When_District_Not_Found();

        Should_Successfully_Update_Municipality();
    }

    @Test
    public void deleteMunicipalityTest() {

        Should_ThrowException_When_Municipality_Is_Null();

        Should_SuccessFully_Delete_Municipality();
    }

    @Test
    public void searchMunicipality() {

        Should_ThrowException_When_MunicipalityList_IsEmpty();

        Should_Successfully_Return_MunicipalityList();
    }

    @Test
    public void fetchMunicipalityForDropDown() {
        Should_ThrowException_When_No_MunicipalityFound();

        Should_Return_Municipalities();
    }

    @Test
    public void fetchMunicipalityDetails() {
        Should_ThrowException_When_Municipality_With_Given_Id_Is_Null();

        Should_Return_Municipality_Details();
    }

    @Test
    public void fetchMunicipality() {
        fetchMunicipalityById_ShouldThrowException();

        Should_Return_Municipality();
    }

    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        MunicipalityRequestDTO requestDTO = getMunicipalityRequestDTO();

        given(municipalityRepository.fetchMunicipalityByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        municipalityService.save(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_District_Not_Found(){
        MunicipalityRequestDTO requestDTO = getMunicipalityRequestDTO();

        given(municipalityRepository.fetchMunicipalityByName(requestDTO.getName()))
                .willReturn(0L);

        given(districtRepository.findActiveDistrictById(requestDTO.getDistrictId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        municipalityService.save(requestDTO);

    }

    @Test
    public void Should_Successfully_Save_Municipality() {
        MunicipalityRequestDTO requestDTO = getMunicipalityRequestDTO();

        Municipality expected = convertDTOToMunicipality(requestDTO, getDistrictInfo());

        given(municipalityRepository.fetchMunicipalityByName(requestDTO.getName()))
                .willReturn(0L);

        given(districtRepository.findActiveDistrictById(requestDTO.getDistrictId()))
                .willReturn(Optional.of(getDistrictInfo()));

        municipalityService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "district"}).matches(getMunicipality()));

        verify(municipalityRepository, times(1)).save(any(Municipality.class));
    }

    @Test
    public void Should_Throw_Exception_When_Municipality_NotFound() {
        MunicipalityUpdateRequestDTO requestDTO = getMunicipalityUpdateRequestDTO();

        given(municipalityRepository.findMunicipalityById(requestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        municipalityService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        MunicipalityUpdateRequestDTO requestDTO = getMunicipalityUpdateRequestDTO();

        given(municipalityRepository.findMunicipalityById(requestDTO.getId()))
                .willReturn(Optional.of(getMunicipality()));

        given(municipalityRepository.findMunicipalityByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        municipalityService.update(requestDTO);
    }

    @Test
    public void Should_Successfully_Update_Municipality() {

        MunicipalityUpdateRequestDTO requestDTO = getMunicipalityUpdateRequestDTO();

        Municipality expected = convertToUpdatedMunicipality(requestDTO, getMunicipality(), getDistrictInfo());

        given(municipalityRepository.findMunicipalityById(requestDTO.getId()))
                .willReturn(Optional.of(getMunicipality()));

        given(municipalityRepository.findMunicipalityByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(0L);

        given(districtRepository.findActiveDistrictById(requestDTO.getDistrictId()))
                .willReturn(Optional.of(getDistrictInfo()));

        municipalityService.update(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id", "district"}).matches(getUpdatedMunicipality()));

        verify(municipalityRepository, times(1)).save(any(expected.getClass()));
    }

    @Test
    public void Should_ThrowException_When_Municipality_Is_Null() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(municipalityRepository.findMunicipalityById(deleteRequestDTO.getId()))
                .willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        municipalityService.delete(deleteRequestDTO);
    }

    @Test
    public void Should_SuccessFully_Delete_Municipality() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Municipality Municipality = getMunicipality();

        given(municipalityRepository.findMunicipalityById(deleteRequestDTO.getId()))
                .willReturn(Optional.of(Municipality));

        Municipality expected = MunicipalityUtils.convertToDeletedMunicipality(Municipality, deleteRequestDTO);

        given(municipalityRepository.save(expected)).willReturn(getDeletedMunicipality());

        municipalityService.delete(deleteRequestDTO);

        assertTrue(municipalityRepository.save(expected).getStatus().equals('D') &&
                municipalityRepository.save(expected).getRemarks().equals(getDeletedMunicipality().getRemarks()));

        verify(municipalityRepository, times(3)).save(any(expected.getClass()));
    }

    @Test
    public void Should_ThrowException_When_MunicipalityList_IsEmpty() {
        Pageable pageable = PageRequest.of(1, 10);
        MunicipalitySearchRequestDTO searchRequestDTO = getMunicipalitySearchRequestDTO();

        given(municipalityRepository.search(searchRequestDTO, pageable))
                .willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        municipalityService.search(searchRequestDTO, pageable);
    }

    @Test
    public void Should_Successfully_Return_MunicipalityList() {
        MunicipalitySearchRequestDTO searchRequestDTO = getMunicipalitySearchRequestDTO();
        List<MunicipalityMinimalResponseDTO> expected = fetchMunicipalityMinimalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(municipalityRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(municipalityService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(municipalityService.search(searchRequestDTO, pageable),
                hasSize(expected.size()));
    }

    @Test
    public void Should_ThrowException_When_No_MunicipalityFound() {

        given(municipalityRepository.fetchActiveMunicipalityForDropDown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        municipalityService.fetchActiveMunicipalityForDropDown();
    }

    @Test
    public void Should_Return_Municipalities() {
        given(municipalityRepository.fetchActiveMunicipalityForDropDown())
                .willReturn(MunicipalityResponseUtils.fetchMunicipalityForDropDown());

        municipalityService.fetchActiveMunicipalityForDropDown();

        assertThat(municipalityService.fetchActiveMunicipalityForDropDown(),
                hasSize(MunicipalityResponseUtils.fetchMunicipalityForDropDown().size()));

        assertThat(municipalityService.fetchActiveMunicipalityForDropDown(),
                samePropertyValuesAs(MunicipalityResponseUtils.fetchMunicipalityForDropDown()));
    }

    @Test
    public void Should_ThrowException_When_Municipality_With_Given_Id_Is_Null() {
        Long id = 1L;

        given(municipalityRepository.fetchDetailsById(id)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        municipalityService.fetchDetailsById(id);
    }

    @Test
    public void Should_Return_Municipality_Details(){
        Long id = 1L;
        MunicipalityResponseDTO expected = fetchMunicipalityResponseDTO();

        given(municipalityRepository.fetchDetailsById(id)).willReturn(expected);

        assertThat(municipalityService.fetchDetailsById(id), samePropertyValuesAs(expected));
    }

    @Test
    public void fetchMunicipalityById_ShouldThrowException() {
        thrown.expect(NoContentFoundException.class);
        Long id = 1L;

        given(municipalityRepository.findMunicipalityById(id)).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        municipalityService.fetchMunicipalityById(id);
    }

    @Test
    public void Should_Return_Municipality(){
        Long id = 1L;

        given(municipalityRepository.findMunicipalityById(id)).willReturn(Optional.of(getMunicipality()));

        municipalityService.fetchMunicipalityById(id);

        verify(municipalityRepository).findMunicipalityById(id);
    }
}
