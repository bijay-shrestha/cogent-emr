package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import com.cogent.admin.dto.request.provinces.ProvincesUpdateRequestDTO;
import com.cogent.admin.dto.response.provinces.ProvincesDropDownResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DistrictRepository;
import com.cogent.admin.repository.ProvincesRepository;
import com.cogent.admin.service.impl.DistrictServiceImpl;
import com.cogent.admin.service.impl.ProvincesServiceImpl;
import com.cogent.persistence.model.Provinces;
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

import static com.cogent.admin.dto.request.provinces.ProvincesTestUtils.*;
import static com.cogent.admin.utils.ProvincesUtils.convertToProvincesInfo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class ProvincesServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    ProvincesRepository provincesRepository;

    @Mock
    DistrictRepository districtRepository;

    @Mock
    DistrictServiceImpl districtService;

    @InjectMocks
    ProvincesServiceImpl provincesService;


    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        createProvinces_ShouldReturnNameAlreadyExists();
        createProvinces_ShouldCreate();
    }

    @Test
    public void dropDown() {
        dropDownList_ShouldReturnNoContentFound();
        dropDownList_ShouldData();
    }

    @Test
    public void activeDropDown() {
        activeDropDownList_ShouldReturnNoContentFound();
        activeDropDownList_ShouldData();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchProvinces_ShouldReturnNoContentFound();
        searchProvinces_ShouldReturnProvinces();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDetailById() {
        fetchServiceModeDetails_ShouldReturnNoContentFound();
        fetchServiceModeDetails_ShouldReturnData();
    }

    @Test
    public void delete() {
        deleteProvinces_ShouldReturnNoContentFound();
        deleteProvinces_ShouldDelete();
    }

    @Test
    public void update() {
        updateProvinces_ShouldReturnNoContentFound();
        updateProvinces_ShouldReturnNameAlreadyExists();
        updateProvinces_ShouldUpdate();
    }


    @Test
    public void createProvinces_ShouldReturnNameAlreadyExists() {
        thrown.expect(DataDuplicationException.class);

        ProvincesRequestDTO provincesRequestDTO = getRequestDTO();

        given(provincesRepository.fetchCountByName(provincesRequestDTO.getName()))
                .willReturn(1L);

        provincesService.createProvinces(provincesRequestDTO);

    }

    @Test
    public void createProvinces_ShouldCreate() {

        ProvincesRequestDTO provincesRequestDTO = getRequestDTO();

        given(provincesRepository.fetchCountByName(provincesRequestDTO.getName()))
                .willReturn(0L);

        provincesService.createProvinces(provincesRequestDTO);

        assertNotNull(convertToProvincesInfo(provincesRequestDTO));

        verify(provincesRepository).save(any(Provinces.class));

    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(provincesRepository.fetchDropDownList())
                .willReturn(Optional.empty());

        provincesService.provincesDropdown();
    }

    @Test
    public void dropDownList_ShouldData() {
        List<ProvincesDropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(provincesRepository.fetchDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        provincesService.provincesDropdown();

        verify(provincesRepository).fetchDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(provincesRepository.fetchActiveDropDownList())
                .willReturn(Optional.empty());

        provincesService.activeProvinceDropdown();
    }

    @Test
    public void activeDropDownList_ShouldData() {
        List<ProvincesDropDownResponseDTO> dropDownResponseDTOS =dropDownResponseDTOS();

        given(provincesRepository.fetchActiveDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        provincesService.activeProvinceDropdown();

        verify(provincesRepository).fetchActiveDropDownList();
    }

    @Test(expected = NoContentFoundException.class)
    public void searchProvinces_ShouldReturnNoContentFound() {
        ProvincesSearchRequestDTO searchRequestDTO = new ProvincesSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(provincesRepository.searchProvinces(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException((Provinces.class)));

        provincesService.searchProvinces(searchRequestDTO, pageable);
    }

    @Test
    public void searchProvinces_ShouldReturnProvinces() {
        ProvincesSearchRequestDTO searchRequestDTO = new ProvincesSearchRequestDTO();
        Pageable pageable = PageRequest.of(1, 10);

        given(provincesRepository.searchProvinces(searchRequestDTO, pageable))
                .willReturn(getMinimalResponseDTOList());

        provincesService.searchProvinces(searchRequestDTO, pageable);

        verify(provincesRepository).searchProvinces(searchRequestDTO, pageable);
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchServiceModeDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(provincesRepository.fetchProvincesDetails(id))
                .willThrow(new NoContentFoundException(Provinces.class, "id", id.toString()));

        provincesService.fetchProvincesDetails(1L);

    }

    @Test
    public void fetchServiceModeDetails_ShouldReturnData() {
        ProvincesResponseDTO serviceModeResponseDTO = provincesResponseDTO();

        given(provincesRepository.fetchProvincesDetails(1L))
                .willReturn((serviceModeResponseDTO));

        provincesService.fetchProvincesDetails(1L);

        verify(provincesRepository).fetchProvincesDetails(1L);

    }

    @Test
    public void deleteProvinces_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(provincesRepository.fetchprovincesById(1L))
                .willReturn(Optional.empty());

        provincesService.deleteProvinces(getDeleteRequestDTO());

    }

    @Test
    public void deleteProvinces_ShouldDelete() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(provincesRepository.fetchprovincesById(1L))
                .willReturn(Optional.of(getProvincesInfo()));

        given(districtRepository.fetchDistrictToDeleteByProvinceId(1L))
                .willReturn(getDeleteRequestDTOS());

        provincesService.deleteProvinces(deleteRequestDTO);

//        verify(provincesUtils).updateProvinces.apply(getProvincesInfo(), deleteRequestDTO);

        verify(districtRepository).fetchDistrictToDeleteByProvinceId(1L);

        verify(provincesRepository).save(any(Provinces.class));
    }

    @Test
    public void updateProvinces_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(provincesRepository.fetchprovincesById(1L)).willReturn(Optional.empty());

        provincesService.updateProvinces(getProvincesUpdateRequestDTO());
    }

    @Test
    public void updateProvinces_ShouldReturnNameAlreadyExists() {
        Provinces provinces = getProvincesInfo();
        ProvincesUpdateRequestDTO updateRequestDTO =getProvincesUpdateRequestDTO();
        thrown.expect(DataDuplicationException.class);

        given(provincesRepository.fetchprovincesById(updateRequestDTO.getId()))
                .willReturn(Optional.of(provinces));
        given(provincesRepository.checkProvincesNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(1L);

        provincesService.updateProvinces(updateRequestDTO);
    }

    @Test
    public void updateProvinces_ShouldUpdate() {
        ProvincesUpdateRequestDTO updateRequestDTO =getProvincesUpdateRequestDTO();

        given(provincesRepository.fetchprovincesById(updateRequestDTO.getId()))
                .willReturn(Optional.of(getProvincesInfo()));
        given(provincesRepository.checkProvincesNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(0L);
        given(districtRepository.saveAll(districtList()))
                .willReturn(districtList());

        provincesService.updateProvinces(updateRequestDTO);

//        verify(provincesUtils).updateProvinces.apply(getProvincesInfo(),ConvertToDeleteRequestDTO
//                        .apply(updateRequestDTO.getRemarks(), updateRequestDTO.getStatus()));

        verify(districtRepository).fetchDistrictByProvincesId(1L);

        verify(districtRepository).saveAll(anyIterable());

        verify(provincesRepository).save(any(Provinces.class));

    }


}
