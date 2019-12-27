package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import com.cogent.admin.dto.request.nationality.NationalityUpdateRequestDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.NationalityRepository;
import com.cogent.admin.service.impl.NationalityServiceImpl;
import com.cogent.admin.utils.NationalityUtils;
import com.cogent.persistence.model.Nationality;
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
import static com.cogent.admin.dto.nationality.NationalityTestUtils.*;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.NationalityUtils.updateNationality;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class NationalityServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    NationalityRepository nationalityRepository;

    @InjectMocks
    NationalityServiceImpl nationalityService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createNationality_ShouldReturnNameAlreadyExists();
        createNationality_ShouldCreate();
    }

    @Test
    public void delete() {
        deleteNationality_ShouldReturnNoContentFound();
        deleteNationality_ShouldDelete();
    }

    @Test
    public void update() {
        updateNationality_ShouldReturnNoContentFound();
        updateNationality_ShouldReturnNameAlreadyExists();
        updateNationality_ShouldUpdate();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchNationality_ShouldReturnNoContentFound();
        searchNationality_ShouldReturnNationality();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDetailById() {
        fetchNationalityDetails_ShouldReturnNoContentFound();
        fetchNationalityDetails_ShouldReturnData();
    }

    @Test
    public void fetchNatioanlity() {
        fetchNationality_ShouldReturnNoContentFound();
        fetchNationality_ShouldReturnData();
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
    public void createNationality_ShouldReturnNameAlreadyExists() {
        thrown.expect(DataDuplicationException.class);

        NationalityRequestDTO nationalityRequestDTO = getNationalityRequestDTO();

        given(nationalityRepository.fetchNationalityCountByName(nationalityRequestDTO.getName()))
                .willReturn(1L);

        nationalityService.createNationality(nationalityRequestDTO);

    }

    @Test
    public void createNationality_ShouldCreate() {

        NationalityRequestDTO nationalityRequestDTO = getNationalityRequestDTO();

        given(nationalityRepository.fetchNationalityCountByName(nationalityRequestDTO.getName()))
                .willReturn(0L);

        nationalityService.createNationality(nationalityRequestDTO);

        assertNotNull(NationalityUtils.convertToNationalityInfo(nationalityRequestDTO));

        verify(nationalityRepository).save(any(Nationality.class));

    }

    @Test
    public void deleteNationality_ShouldReturnNoContentFound() {

        thrown.expect(NoContentFoundException.class);

        given(nationalityRepository.fetchNationalityById(1L)).willReturn(Optional.empty());

        nationalityService.deleteNationality(getDeleteRequestDTO());
    }

    @Test
    public void deleteNationality_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Nationality nationality = updateNationality
                .apply(getNationalityInfo(), deleteRequestDTO);

        given(nationalityRepository.fetchNationalityById(1L))
                .willReturn(Optional.of(getNationalityInfo()));

        nationalityService.deleteNationality(getDeleteRequestDTO());

        assertTrue(nationality.getStatus() == deleteRequestDTO.getStatus()
                && nationality.getRemarks() == deleteRequestDTO.getRemarks());

        verify(nationalityRepository).save(any(Nationality.class));
    }

    @Test
    public void updateNationality_ShouldReturnNoContentFound() {

        thrown.expect(NoContentFoundException.class);

        given(nationalityRepository.checkNationalityNameIfExist(1L, "Nepali")).willReturn(1L);

        nationalityService.updateNationality(nationalityUpdateRequestDTO());
    }

    @Test
    public void updateNationality_ShouldReturnNameAlreadyExists() {

        NationalityUpdateRequestDTO updateRequestDTO = nationalityUpdateRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(nationalityRepository.fetchNationalityById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getNationalityInfo()));

        given(nationalityRepository.checkNationalityNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(1L);

        nationalityService.updateNationality(updateRequestDTO);
    }

    @Test
    public void updateNationality_ShouldUpdate() {

        NationalityUpdateRequestDTO updateRequestDTO = nationalityUpdateRequestDTO();

        Nationality nationalityUpdated = updateNationality.apply(getNationalityInfo(),
                ConvertToDeleteRequestDTO.apply(updateRequestDTO.getRemarks(),
                        updateRequestDTO.getStatus()));

        given(nationalityRepository.fetchNationalityById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getNationalityInfo()));

        given(nationalityRepository.checkNationalityNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(0L);


        nationalityService.updateNationality(updateRequestDTO);

        assertTrue(nationalityUpdated.getStatus() == updateRequestDTO.getStatus()
                && nationalityUpdated.getRemarks() == updateRequestDTO.getRemarks());

        verify(nationalityRepository).save(any(Nationality.class));
    }

    @Test(expected = NoContentFoundException.class)
    public void searchNationality_ShouldReturnNoContentFound() {

        NationalitySearchRequestDTO searchRequestDTO = getNationalitySeacrhRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(nationalityRepository.searchNationality(searchRequestDTO, pageable))
                .willThrow( new NoContentFoundException(Nationality.class));

        nationalityService.searchNationality(searchRequestDTO, pageable);
    }

    @Test
    public void searchNationality_ShouldReturnNationality() {

        NationalitySearchRequestDTO searchRequestDTO = getNationalitySeacrhRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(nationalityRepository.searchNationality(searchRequestDTO, pageable))
                .willReturn(Arrays.asList(getNationalityMinimalResponseDTO()));

        nationalityService.searchNationality(searchRequestDTO, pageable);

        verify(nationalityRepository).searchNationality(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchNationalityDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(nationalityRepository.fetchNationalityDetails(id))
                .willThrow(new NoContentFoundException(Nationality.class, "id", id.toString()));

        nationalityService.fetchNatioanlityDetails(id);

    }

    @Test
    public void fetchNationalityDetails_ShouldReturnData() {

        given(nationalityRepository.fetchNationalityDetails(1L))
                .willReturn((getNationalityResponseDTO()));

        nationalityService.fetchNatioanlityDetails(1L);

        verify(nationalityRepository).fetchNationalityDetails(1L);

    }

    @Test
    public void fetchNationality_ShouldReturnNoContentFound() {

        thrown.expect(NoContentFoundException.class);

        given(nationalityRepository.fetchNationalityById(1L))
                .willReturn(Optional.empty());

        nationalityService.fetchNatioanlity(1L);

    }

    @Test
    public void fetchNationality_ShouldReturnData() {

        given(nationalityRepository.fetchNationalityById(1L))
                .willReturn(Optional.ofNullable(getNationalityInfo()));

        nationalityService.fetchNatioanlity(1L);

        verify(nationalityRepository).fetchNationalityById(1L);

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {

        thrown.expect(NoContentFoundException.class);

        given(nationalityRepository.fetchDropDownList())
                .willReturn(Optional.empty());

        nationalityService.fetchDropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {

        given(nationalityRepository.fetchDropDownList())
                .willReturn(Optional.of(getDropDownList()));

        nationalityService.fetchDropDownList();

        verify(nationalityRepository).fetchDropDownList();
    }


    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(nationalityRepository.fetchActiveDropDownList())
                .willReturn(Optional.empty());

        nationalityService.fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {

        given(nationalityRepository.fetchActiveDropDownList())
                .willReturn(Optional.of(getDropDownList()));

        nationalityService.fetchActiveDropDownList();

        verify(nationalityRepository).fetchActiveDropDownList();
    }


}
