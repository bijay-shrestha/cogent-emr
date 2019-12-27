package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.title.TitleRequestDTO;
import com.cogent.admin.dto.request.title.TitleSearchRequestDTO;
import com.cogent.admin.dto.request.title.TitleUpdateRequestDTO;
import com.cogent.admin.dto.surname.SurnameResponseUtils;
import com.cogent.admin.dto.response.title.TitleResponseDTO;
import com.cogent.admin.dto.title.TitleResponseUtils;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.TitleRepository;
import com.cogent.admin.service.impl.TitleServiceImpl;
import com.cogent.admin.utils.TitleUtils;
import com.cogent.persistence.model.Title;
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
import static com.cogent.admin.dto.title.TitleRequestUtils.*;
import static com.cogent.admin.dto.title.TitleResponseUtils.fetchTitleResponseDTO;
import static com.cogent.admin.dto.title.TitleResponseUtils.getDeletedTitle;
import static com.cogent.admin.utils.TitleUtils.convertDTOToTitle;
import static org.apache.xmlbeans.XmlBeans.getTitle;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

public class TitleServiceTest {

    @InjectMocks
    private TitleServiceImpl titleService;

    @Mock
    private TitleRepository titleRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        Should_Throw_Exception_When_Name_Duplicates();
        Should_Successfully_Save_Title();

    }

    @Test
    public void updateTest() {
        Should_Throw_Exception_When_Title_NotFound();
        Should_Throw_Exception_When_Name_Already_Exists();
    }

    @Test
    public void deleteTitle() {

        Should_Throw_Exception_When_Title_Is_Null();
        Should_SuccessFully_Delete_Title();

    }

    @Test
    public void searchTitle() {
        Should_Throw_Exception_When_TitleList_Empty();
        Should_Successfully_Return_TitleList();
    }

    @Test
    public void fetchTitleForDropDown() {
        Should_ThrowException_When_No_TitleFound();
        Should_Return_Titles();
    }


    @Test
    public void Should_SuccessFully_Delete_Title() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        Title title = TitleResponseUtils.getTitle();

        given(titleRepository.findTitleById(deleteRequestDTO.getId())).willReturn(Optional.of(title));

        Title expected = TitleUtils.convertToDeletedTitle(title, deleteRequestDTO);

        given(titleRepository.save(expected)).willReturn(getDeletedTitle());

    }

    @Test
    public void Should_Throw_Exception_When_Title_Is_Null() {
        DeleteRequestDTO deleteRequestDTO = getDeleteRequestDTO();

        given(titleRepository.findTitleById(deleteRequestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        titleService.delete(deleteRequestDTO);
    }


    @Test
    public void Should_Throw_Exception_When_Name_Duplicates() {
        TitleRequestDTO requestDTO = getTitleRequestDTO();

        given(titleRepository.fetchTitleByName(requestDTO.getName()))
                .willReturn(1l);

        thrown.expect(DataDuplicationException.class);

        titleService.save(requestDTO);

    }

    @Test
    public void Should_Successfully_Save_Title() {

        TitleRequestDTO requestDTO = getTitleRequestDTO();

        Title expected = convertDTOToTitle(requestDTO);

        given(titleRepository.fetchTitleByName(requestDTO.getName()))
                .willReturn(0l);

        titleService.save(requestDTO);

        Assert.assertTrue(new ReflectionEquals(expected, new String[]{"id"}).matches(getTitle()));

    }

    @Test
    public void Should_Throw_Exception_When_Title_NotFound() {
        TitleUpdateRequestDTO requestDTO = getTitleUpdateRequestDTO();

        given(titleRepository.findTitleById(requestDTO.getId())).willReturn(Optional.empty());

        thrown.expect(NoContentFoundException.class);

        titleService.update(requestDTO);
    }

    @Test
    public void Should_Throw_Exception_When_Name_Already_Exists() {
        TitleUpdateRequestDTO requestDTO = getTitleUpdateRequestDTO();

        given(titleRepository.findTitleById(requestDTO.getId())).willReturn(Optional.of(TitleResponseUtils.getTitle()));

        given(titleRepository.findTitleByIdAndName(requestDTO.getId(), requestDTO.getName())).willReturn(1L);

        thrown.expect(DataDuplicationException.class);

        titleService.update(requestDTO);

    }

    @Test
    public void Should_Throw_Exception_When_TitleList_Empty() {
        Pageable pageable = PageRequest.of(1, 10);

        TitleSearchRequestDTO searchRequestDTO = getTitleSearchRequestDTO();
        given(titleRepository.search(searchRequestDTO, pageable)).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);
        titleService.search(searchRequestDTO, pageable);

    }

    @Test
    public void Should_Successfully_Return_TitleList() {

        TitleSearchRequestDTO searchRequestDTO = getTitleSearchRequestDTO();

        List<TitleResponseDTO> expected = fetchTitleResponseDTO();

        Pageable pageable = PageRequest.of(1, 10);

        given(titleRepository.search(searchRequestDTO, pageable))
                .willReturn(expected);

        assertThat(titleService.search(searchRequestDTO, pageable),
                samePropertyValuesAs(expected));

        assertThat(titleService.search(searchRequestDTO, pageable),
                hasSize(expected.size()));


    }

    @Test
    public void Should_ThrowException_When_No_TitleFound() {

        given(titleRepository.fetchTitleForDropDown()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        titleService.fetchTitleForDropDown();

    }

    @Test
    public void Should_Return_Titles() {
        given(titleRepository.fetchTitleForDropDown())
                .willReturn(TitleResponseUtils.fetchTitleForDropDown());

        titleService.fetchTitleForDropDown();

        assertThat(titleService.fetchTitleForDropDown(),
                hasSize(TitleResponseUtils.fetchTitleForDropDown().size()));

        assertThat(titleService.fetchTitleForDropDown(),
                samePropertyValuesAs(SurnameResponseUtils.fetchSurnameForDropDown()));
    }

}
