package com.cogent.admin.service;

import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.GenderRepository;
import com.cogent.admin.service.impl.GenderServiceImpl;
import com.cogent.persistence.model.Gender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.cogent.admin.dto.gender.GenderResponseUtils.fetchActiveGenders;
import static com.cogent.admin.dto.gender.GenderResponseUtils.fetchGender;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

/**
 * @author smriti on 08/11/2019
 */
public class GenderServiceTest {
    @InjectMocks
    private GenderServiceImpl genderService;

    @Mock
    private GenderRepository genderRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchActiveGender() {
        fetchActiveGender_ShouldThrowException();
        Should_Successfully_Return_ActiveGender();
    }

    @Test
    public void fetchGenderById() {
        fetchGenderById_ShouldThrowException();
        Should_Return_ActiveGenderById();
    }

    @Test
    public void fetchActiveGender_ShouldThrowException() {

        given(genderRepository.fetchActiveGender()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        genderService.fetchActiveGender();
    }

    @Test
    public void Should_Successfully_Return_ActiveGender() {
        given(genderRepository.fetchActiveGender()).willReturn(fetchActiveGenders());

        genderService.fetchActiveGender();

        assertThat(genderService.fetchActiveGender(), hasSize(fetchActiveGenders().size()));

        assertThat(genderService.fetchActiveGender(), samePropertyValuesAs(fetchActiveGenders()));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchGenderById_ShouldThrowException() {
        Long id = 1L;

        given(genderRepository.fetchActiveGenderById(id))
                .willThrow(NoContentFoundException.class);

        genderService.fetchGenderById(id);
    }

    @Test
    public void Should_Return_ActiveGenderById() {
        Long id = 1L;
        Gender gender = fetchGender();

        given(genderRepository.fetchActiveGenderById(id))
                .willReturn(Optional.of(gender));

        Assert.assertThat(genderService.fetchGenderById(id), samePropertyValuesAs(gender));
    }
}
