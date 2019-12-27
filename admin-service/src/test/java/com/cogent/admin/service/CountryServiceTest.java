package com.cogent.admin.service;

import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.CountryRepository;
import com.cogent.admin.service.impl.CountryServiceImpl;
import com.cogent.persistence.model.Country;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.cogent.admin.dto.country.CountryResponseUtils.fetchActiveCountries;
import static com.cogent.admin.dto.country.CountryResponseUtils.fetchCountry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

/**
 * @author smriti on 08/11/2019
 */
public class CountryServiceTest {
    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private CountryRepository countryRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchActiveCountry() {
        fetchActiveCountry_ShouldThrowException();
        Should_Successfully_Return_ActiveCountry();
    }

    @Test
    public void fetchCountryById() {
        fetchCountryById_ShouldThrowException();
        Should_Return_ActiveCountryById();
    }

    @Test
    public void fetchActiveCountry_ShouldThrowException() {

        given(countryRepository.fetchActiveCountry()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        countryService.fetchActiveCountry();
    }

    @Test
    public void Should_Successfully_Return_ActiveCountry() {
        given(countryRepository.fetchActiveCountry()).willReturn(fetchActiveCountries());

        countryService.fetchActiveCountry();

        assertThat(countryService.fetchActiveCountry(), hasSize(fetchActiveCountries().size()));

        assertThat(countryService.fetchActiveCountry(), samePropertyValuesAs(fetchActiveCountries()));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchCountryById_ShouldThrowException() {
        Long id = 1L;

        given(countryRepository.fetchActiveCountryById(id))
                .willThrow(NoContentFoundException.class);

        countryService.fetchCountryById(id);
    }

    @Test
    public void Should_Return_ActiveCountryById() {
        Long id = 1L;
        Country Country = fetchCountry();

        given(countryRepository.fetchActiveCountryById(id))
                .willReturn(Optional.of(Country));

        Assert.assertThat(countryService.fetchCountryById(id), samePropertyValuesAs(Country));
    }
}
