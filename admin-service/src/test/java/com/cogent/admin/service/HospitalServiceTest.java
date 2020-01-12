package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.hospital.HospitalResponseUtils;
import com.cogent.admin.dto.request.hospital.HospitalRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalSearchRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalUpdateRequestDTO;
import com.cogent.admin.dto.response.hospital.HospitalResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.HospitalRepository;
import com.cogent.admin.service.impl.HospitalServiceImpl;
import com.cogent.admin.utils.HospitalUtils;
import com.cogent.persistence.model.Hospital;
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
import static com.cogent.admin.dto.hospital.HospitalResponseUtils.*;
import static com.cogent.admin.dto.hospital.HospitalResponseUtils.getHospital;
import static com.cogent.admin.dto.hospital.HospitalTestUtils.*;
import static com.cogent.admin.utils.HospitalUtils.convertDTOToHospital;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

/**
 * @author Rupak
 */
public class HospitalServiceTest {
    @InjectMocks
    private HospitalServiceImpl hospitalService;

    @Mock
    private HospitalRepository hospitalRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_Name_Duplicates();
        Should_Successfully_Save_Hospital();
    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_Hospital_NotFound();
        Should_Throw_Exception_When_Name_Already_Exists();
    }

    @Test
    public void deleteHospital() {

        Should_Throw_Exception_When_Hospital_Is_Null();
        Should_SuccessFully_Delete_Hospital();

    }

    @Test
    public void searchHospital() {
        Should_Throw_Exception_When_HospitalList_Empty();
        Should_Successfully_Return_HospitalList();
    }

    @Test
    public void fetchHospitalForDropDown() {
        Should_ThrowException_When_No_HospitalFound();
        Should_Return_Hospitals();
    }


    @Test
    public void Should_Throw_Exception_When_HospitalList_Empty() {

        Pageable pageable = PageRequest.of(1, 10);

        HospitalSearchRequestDTO searchRequestDTO = getHospitalSearchRequestDTO();
        given(hospitalRepository.search(searchRequestDTO, pageable)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        hospitalService.searchHospital(searchRequestDTO, pageable);

    }

    @Test
    public void Should_Successfully_Return_HospitalList() {

        HospitalSearchRequestDTO searchRequestDTO = getHospitalSearchRequestDTO();

        List<HospitalResponseDTO> expected = fetchHospitalResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(hospitalRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(hospitalService.searchHospital(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(hospitalService.searchHospital(searchRequestDTO, pageable),
                hasSize(expected.size()));


    }

    @Test
    public void Should_Throw_Exception_When_Hospital_Is_Null() {

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(hospitalRepository.findHospitalById(deleteRequestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        hospitalService.deleteHospital(deleteRequestDTO);


    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {

        HospitalUpdateRequestDTO requestDTO = getHospitalUpdateRequestDTO();

        given(hospitalRepository.findHospitalById(requestDTO.getId()))
                .willReturn(Optional.of(getHospital()));

        given(hospitalRepository.findHospitalByIdAndName(requestDTO.getId(), requestDTO.getName()))
                .willReturn(1L);

        given(hospitalRepository.findHospitalById(requestDTO.getId()))
                .willReturn(Optional.of(getHospital()));

        thrown.expect(DataDuplicationException.class);

        hospitalService.updateHospital(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Hospital_NotFound() {

        HospitalUpdateRequestDTO requestDTO = getHospitalUpdateRequestDTO();

        given(hospitalRepository.findHospitalById(requestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        hospitalService.updateHospital(requestDTO);
    }

    @Test
    public void Should_Successfully_Save_Hospital() {

        HospitalRequestDTO requestDTO = getHospitalRequestDTO();

        Hospital expected = convertDTOToHospital(requestDTO);

        given(hospitalRepository.fetchHospitalByName(requestDTO.getName()))
                .willReturn(0l);

        hospitalService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getHospital()));

    }

    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {

        HospitalRequestDTO requestDTO = getHospitalRequestDTO();

        given(hospitalRepository.fetchHospitalByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        hospitalService.save(requestDTO);

    }

    @Test
    public void Should_SuccessFully_Delete_Hospital() {

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Hospital hospital = getHospital();

        given(hospitalRepository.findHospitalById(deleteRequestDTO.getId())).willReturn(Optional.of(hospital));

        Hospital expected = HospitalUtils.convertToDeletedHospital(hospital, deleteRequestDTO);

        given(hospitalRepository.save(expected)).willReturn(getDeletedHospital());

    }

    @Test
    public void Should_ThrowException_When_No_HospitalFound() {

        given(hospitalRepository.fetchHospitalForDropDown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        hospitalService.hospitalDropdown();

    }

    @Test
    public void Should_Return_Hospitals() {

        given(hospitalRepository.fetchHospitalForDropDown())
                .willReturn(HospitalResponseUtils.fetchHospitalForDropDown());

        hospitalService.hospitalDropdown();

        assertThat(hospitalService.hospitalDropdown(),
                hasSize(HospitalResponseUtils.fetchHospitalForDropDown().size()));

        assertThat(hospitalService.hospitalDropdown(),
                samePropertyValuesAs(HospitalResponseUtils.fetchHospitalForDropDown()));

    }


}
