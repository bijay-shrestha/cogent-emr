package com.cogent.admin.service;

import com.cogent.admin.dto.weekDays.WeekDaysResponseUtils;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.WeekDaysRepository;
import com.cogent.admin.service.impl.WeekDaysServiceImpl;
import com.cogent.persistence.model.WeekDays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.cogent.admin.dto.weekDays.WeekDaysResponseUtils.fetchWeekDays;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

/**
 * @author smriti on 25/11/2019
 */
public class WeekDaysServiceTest {

    @InjectMocks
    private WeekDaysServiceImpl weekDaysService;

    @Mock
    private WeekDaysRepository weekDaysRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchActiveWeekDays() {
        fetchActiveWeekDays_ShouldThrowException();
        Should_Successfully_Return_ActiveWeekDays();
    }

    @Test
    public void fetchWeekDaysById() {
        fetchWeekDaysById_ShouldThrowException();
        Should_Return_ActiveWeekDaysById();
    }

    @Test
    public void fetchActiveWeekDays_ShouldThrowException() {

        given(weekDaysRepository.fetchActiveWeekDays()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        weekDaysService.fetchActiveWeekDays();
    }

    @Test
    public void Should_Successfully_Return_ActiveWeekDays() {
        given(weekDaysRepository.fetchActiveWeekDays()).willReturn(WeekDaysResponseUtils.fetchActiveWeekDays());

        weekDaysService.fetchActiveWeekDays();

        assertThat(weekDaysService.fetchActiveWeekDays(),
                hasSize(WeekDaysResponseUtils.fetchActiveWeekDays().size()));

        assertThat(weekDaysService.fetchActiveWeekDays(),
                samePropertyValuesAs(WeekDaysResponseUtils.fetchActiveWeekDays()));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchWeekDaysById_ShouldThrowException() {
        Long id = 1L;

        given(weekDaysRepository.fetchActiveWeekDaysById(id))
                .willThrow(NoContentFoundException.class);

        weekDaysService.fetchWeekDaysById(id);
    }

    @Test
    public void Should_Return_ActiveWeekDaysById() {
        Long id = 1L;
        WeekDays weekDays = fetchWeekDays();

        given(weekDaysRepository.fetchActiveWeekDaysById(id))
                .willReturn(Optional.of(weekDays));

        Assert.assertThat(weekDaysService.fetchWeekDaysById(id), samePropertyValuesAs(weekDays));
    }
}
