package com.cogent.admin.service;


import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.district.DistrictTestUtils;
import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import com.cogent.admin.dto.request.district.DistrictUpdateRequestDTO;
import com.cogent.admin.dto.response.district.DistrictDropDownResponseDTO;
import com.cogent.admin.dto.response.district.DistrictMinimalResponseDTO;
import com.cogent.admin.dto.response.district.DistrictResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DistrictRepository;
import com.cogent.admin.repository.ProvincesRepository;
import com.cogent.admin.service.impl.DistrictServiceImpl;
import com.cogent.persistence.model.District;
import org.junit.Assert;
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

import static com.cogent.admin.dto.district.DistrictTestUtils.*;
import static com.cogent.admin.dto.provinces.ProvincesTestUtils.getProvincesInfo;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DistrictUtils.parseToDistrict;
import static com.cogent.admin.utils.DistrictUtils.updateDistrict;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class DistrictServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    DistrictRepository districtRepository;

    @Mock
    ProvincesRepository provincesRepository;

    @InjectMocks
    DistrictServiceImpl districtService;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void create() {
        createDistrict_ShouldReturnNameAlreadyExists();
        createDistrict_ShouldReturnNoContentFound();
        createDistrict_ShouldSave();
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

    @Test(expected = NoContentFoundException.class)
    public void fetchDetailById() {
        fetchDistrictDetails_ShouldReturnNoContentFound();
        fetchDistrictDetails_ShouldReturnData();
    }

    @Test
    public void delete() {
        deleteSubDepartment_ShouldReturnNoContentFoundException();
        deleteSubDepartment_ShouldDeleteDistrict();
    }

    @Test
    public void update() {
        updateDistrict_ShouldReturnNoContentFoundException();
        updateDistrict_ShouldReturnNameAlreadyExists();
        updateDistrict_ShouldReturnNoProvincesFound();
        updateDistrict_ShouldUpdateDistrict();
    }

    @Test(expected = NoContentFoundException.class)
    public void search() {
        searchDistrict_ShouldReturnNoContentFound();
        searchDistrict_ShouldReturnDistrict();
    }


    @Test
    public void createDistrict_ShouldReturnNameAlreadyExists() {
        thrown.expect(DataDuplicationException.class);

        given(districtRepository.fetchDistrictCountByName(getDistrictRequestDTO().getName()))
                .willReturn(1L);

        districtService.createDistrict(getDistrictRequestDTO());
    }

    @Test
    public void createDistrict_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(districtRepository.fetchDistrictCountByName(getDistrictRequestDTO().getName()))
                .willReturn(0L);
        given(provincesRepository.fetchprovincesById(1L))
                .willReturn(Optional.empty());

        districtService.createDistrict(getDistrictRequestDTO());
    }

    @Test
    public void createDistrict_ShouldSave() {
        thrown.expect(NoContentFoundException.class);
        District district = parseToDistrict.apply(
                getDistrictRequestDTO()
                , getProvincesInfo());

        given(districtRepository.fetchDistrictCountByName(getDistrictRequestDTO().getName()))
                .willReturn(0L);
        given(provincesRepository.fetchprovincesById(1L))
                .willReturn(Optional.ofNullable(getProvincesInfo()));

        districtService.createDistrict(getDistrictRequestDTO());

        Assert.assertNotNull(district);

        verify(districtRepository).save(any(District.class));
    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(districtRepository.fetchDropDownList())
                .willReturn(Optional.empty());

        districtService.districtDropdown();
    }

    @Test
    public void dropDownList_ShouldData() {
        List<DistrictDropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(districtRepository.fetchDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        districtService.districtDropdown();

        verify(districtRepository).fetchDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(districtRepository.fetchActiveDropDownList())
                .willReturn(Optional.empty());

        districtService.activeDistrictDropdown();
    }

    @Test
    public void activeDropDownList_ShouldData() {
        List<DistrictDropDownResponseDTO> dropDownResponseDTOS = dropDownResponseDTOS();

        given(districtRepository.fetchActiveDropDownList())
                .willReturn(Optional.of(dropDownResponseDTOS));

        districtService.activeDistrictDropdown();

        verify(districtRepository).fetchActiveDropDownList();
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDistrictDetails_ShouldReturnNoContentFound() {

        Long id = 1L;

        given(districtRepository.fetchDistrictDetails(id))
                .willThrow(new NoContentFoundException(District.class, "id", id.toString()));

        districtService.fetchDistrictDetails(1L);

    }

    @Test
    public void fetchDistrictDetails_ShouldReturnData() {
        DistrictResponseDTO responseDTO = districtResponseDTO();

        given(districtRepository.fetchDistrictDetails(1L))
                .willReturn((responseDTO));

        districtService.fetchDistrictDetails(1L);

        verify(districtRepository).fetchDistrictDetails(1L);

    }

    @Test(expected = NoContentFoundException.class)
    public void searchDistrict_ShouldReturnNoContentFound() {
        DistrictSearchRequestDTO searchRequestDTO = new DistrictSearchRequestDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(districtRepository.searchDistrict(searchRequestDTO, pageable))
                .willThrow(new NoContentFoundException(District.class));

        districtService.searchDistrict(searchRequestDTO, pageable);
    }

    @Test
    public void searchDistrict_ShouldReturnDistrict() {
        DistrictSearchRequestDTO searchRequestDTO = new DistrictSearchRequestDTO();
        List<DistrictMinimalResponseDTO> minimalResponseDTOS = getMinimalResponseDTOList();
        Pageable pageable = PageRequest.of(1, 10);

        given(districtRepository.searchDistrict(searchRequestDTO, pageable))
                .willReturn(minimalResponseDTOS);

        districtService.searchDistrict(searchRequestDTO, pageable);

        verify(districtRepository).searchDistrict(searchRequestDTO, pageable);
    }

    @Test
    public void deleteSubDepartment_ShouldReturnNoContentFoundException() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        thrown.expect(NoContentFoundException.class);

        given(districtRepository.fetchDistrictById(1L))
                .willReturn(Optional.ofNullable(null));

        districtService.deleteDistrict(deleteRequestDTO);
    }

    @Test
    public void deleteSubDepartment_ShouldDeleteDistrict() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();
        District district = DistrictTestUtils
                .getDistrictInfo();
        District districttoDelete = updateDistrict.apply(
                district, deleteRequestDTO);

        given(districtRepository.fetchDistrictById(1L))
                .willReturn(Optional.of(district));
        given(districtRepository.save(any(District.class)))
                .willReturn(districttoDelete);


        districtService.deleteDistrict(deleteRequestDTO);

        assertTrue(districttoDelete.getStatus() == 'D' &&
                districttoDelete.getRemarks() == deleteRequestDTO.getRemarks());

        verify(districtRepository).save(any(District.class));
    }

    @Test
    public void updateDistrict_ShouldReturnNoContentFoundException() {
        DistrictUpdateRequestDTO updateRequestDTO = getUpdateRequestDTO();
        thrown.expect(NoContentFoundException.class);

        given(districtRepository.fetchDistrictById(1L)).willReturn(Optional.ofNullable(null));

        districtService.updateDistrict(updateRequestDTO);
    }

    @Test
    public void updateDistrict_ShouldReturnNameAlreadyExists() {
        DistrictUpdateRequestDTO updateRequestDTO = getUpdateRequestDTO();

        thrown.expect(DataDuplicationException.class);

        given(districtRepository.fetchDistrictById(1L))
                .willReturn(Optional.of(getDistrictInfo()));
        given(districtRepository.checkDistrictNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(1L);

        districtService.updateDistrict(updateRequestDTO);
    }

    @Test
    public void updateDistrict_ShouldReturnNoProvincesFound() {
        thrown.expect(NoContentFoundException.class);

        DistrictUpdateRequestDTO updateRequestDTO = getUpdateRequestDTO();

        given(districtRepository.fetchDistrictById(1L))
                .willReturn(Optional.of(getDistrictInfo()));
        given(districtRepository.checkDistrictNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(0L);
        given(provincesRepository.findActiveProvincesById(updateRequestDTO.getProvincesId()))
                .willReturn(Optional.empty());


        districtService.updateDistrict(updateRequestDTO);
    }

    @Test
    public void updateDistrict_ShouldUpdateDistrict() {

        DistrictUpdateRequestDTO updateRequestDTO = getUpdateRequestDTO();

        District districtToSave = updateDistrict.apply(
                getDistrictInfo(),
                ConvertToDeleteRequestDTO
                        .apply(updateRequestDTO.getRemarks(),
                                updateRequestDTO.getStatus()));

        given(districtRepository.fetchDistrictById(1L))
                .willReturn(Optional.of(getDistrictInfo()));
        given(districtRepository.checkDistrictNameIfExist(updateRequestDTO.getId(),
                updateRequestDTO.getName()))
                .willReturn(0L);
        given(provincesRepository.findActiveProvincesById(updateRequestDTO.getProvincesId()))
                .willReturn(Optional.ofNullable(getProvincesInfo()));

        districtService.updateDistrict(updateRequestDTO);

        assertNotNull(districtToSave);

        verify(districtRepository).save(any(District.class));
    }


}
