package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.referrer.ReferrerResponseUtils;
import com.cogent.admin.dto.request.referrer.ReferrerRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerSearchRequestDTO;
import com.cogent.admin.dto.request.referrer.ReferrerUpdateRequestDTO;
import com.cogent.admin.dto.response.referrer.ReferrerResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.ReferrerRepository;
import com.cogent.admin.service.impl.ReferrerServiceImpl;
import com.cogent.admin.utils.ReferrerUtils;
import com.cogent.persistence.model.Referrer;
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
import static com.cogent.admin.dto.referrer.ReferrerResponseUtils.*;
import static com.cogent.admin.dto.referrer.ReferrerTestUtils.*;
import static com.cogent.admin.utils.ReferrerUtils.convertDTOToReferrer;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author Rupak
 */

public class ReferrerServiceTest {
    @InjectMocks
    private ReferrerServiceImpl referrerService;

    @Mock
    private ReferrerRepository referrerRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveReferrer() {
        Should_Throw_Exception_When_Name_Duplicates();
        Should_Successfully_Save_Referrer();

    }

    @Test
    public void deleteReferrer() {

        Should_Throw_Exception_When_Referrer_Is_Null();
        Should_SuccessFully_Delete_Referrer();

    }

    @Test
    public void searchReferrer() {
        Should_Throw_Exception_When_ReferrerList_Empty();
        Should_Successfully_Return_ReferrerList();
    }

    @Test
    public void Should_Successfully_Save_Referrer() {

        ReferrerRequestDTO requestDTO = getReferrerRequestDTO();

        Referrer expected = convertDTOToReferrer(requestDTO);

        given(referrerRepository.fetchReferrerByName(requestDTO.getName()))
                .willReturn(0l);

        referrerService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getReferrer()));

    }

    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {

        ReferrerRequestDTO requestDTO = getReferrerRequestDTO();

        given(referrerRepository.fetchReferrerByName(requestDTO.getName()))
                .willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        referrerService.save(requestDTO);

    }

    @Test
    public void updateReferrer() {
        Should_Throw_Exception_When_Referrer_NotFound();
        Should_Throw_Exception_When_Name_Already_Exists();
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {

        ReferrerUpdateRequestDTO requestDTO = getReferrerUpdateRequestDTO();

        given(referrerRepository.findReferrerStatusById(requestDTO.getId())).willReturn(Optional.of(ReferrerResponseUtils.getReferrer()));

        given(referrerRepository.findReferrerByIdAndName(requestDTO.getId(), requestDTO.getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        referrerService.update(requestDTO);

    }

    @Test
    public void Should_Throw_Exception_When_Referrer_NotFound() {

        ReferrerUpdateRequestDTO requestDTO = getReferrerUpdateRequestDTO();

        given(referrerRepository.findReferrerStatusById(requestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        referrerService.update(requestDTO);

    }

    @Test
    public void Should_Throw_Exception_When_ReferrerList_Empty() {

        Pageable pageable = PageRequest.of(1, 10);

        ReferrerSearchRequestDTO searchRequestDTO = getReferrerSearchRequestDTO();
        given(referrerRepository.search(searchRequestDTO, pageable)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        referrerService.search(searchRequestDTO, pageable);

    }

    @Test
    public void Should_Successfully_Return_ReferrerList() {

        ReferrerSearchRequestDTO searchRequestDTO = getReferrerSearchRequestDTO();

        List<ReferrerResponseDTO> expected = fetchReferrerResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(referrerRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(referrerService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(referrerService.search(searchRequestDTO, pageable),
                hasSize(expected.size()));

    }

    @Test
    public void Should_Throw_Exception_When_Referrer_Is_Null() {

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(referrerRepository.findReferrerStatusById(deleteRequestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        referrerService.delete(deleteRequestDTO);

    }

    @Test
    public void Should_SuccessFully_Delete_Referrer() {

        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Referrer referrer = ReferrerResponseUtils.getReferrer();

        given(referrerRepository.findReferrerStatusById(deleteRequestDTO.getId())).willReturn(Optional.of(referrer));

        Referrer expected = ReferrerUtils.convertToDeletedReferrer(referrer, deleteRequestDTO);

        given(referrerRepository.save(expected)).willReturn(getDeletedReferrer());

    }

}
