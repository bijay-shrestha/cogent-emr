package com.cogent.admin.service;

import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DoctorTypeRepository;
import com.cogent.admin.service.impl.DoctorTypeServiceImpl;
import com.cogent.persistence.model.DoctorType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.cogent.admin.dto.doctorType.DoctorTypeResponseUtils.fetchActiveDoctorTypes;
import static com.cogent.admin.dto.doctorType.DoctorTypeResponseUtils.fetchDoctorType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

/**
 * @author smriti on 08/11/2019
 */
public class DoctorTypeServiceTest {

    @InjectMocks
    private DoctorTypeServiceImpl doctorTypeService;

    @Mock
    private DoctorTypeRepository doctorTypeRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchActiveDoctorType() {
        fetchActiveDoctorType_ShouldThrowException();
        Should_Successfully_Return_ActiveDoctorType();
    }

    @Test
    public void fetchDoctorTypeById() {
        fetchDoctorTypeById_ShouldThrowException();
        Should_Return_ActiveDoctorTypeById();
    }

    @Test
    public void fetchActiveDoctorType_ShouldThrowException() {

        given(doctorTypeRepository.fetchActiveDoctorType()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        doctorTypeService.fetchActiveDoctorType();
    }

    @Test
    public void Should_Successfully_Return_ActiveDoctorType() {
        given(doctorTypeRepository.fetchActiveDoctorType()).willReturn(fetchActiveDoctorTypes());

        doctorTypeService.fetchActiveDoctorType();

        assertThat(doctorTypeService.fetchActiveDoctorType(),
                hasSize(fetchActiveDoctorTypes().size()));

        assertThat(doctorTypeService.fetchActiveDoctorType(),
                samePropertyValuesAs(fetchActiveDoctorTypes()));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchDoctorTypeById_ShouldThrowException() {
        Long id = 1L;

        given(doctorTypeRepository.fetchActiveDoctorTypeById(id))
                .willThrow(NoContentFoundException.class);

        doctorTypeService.fetchDoctorTypeById(id);
    }

    @Test
    public void Should_Return_ActiveDoctorTypeById() {
        Long id = 1L;
        DoctorType doctorType = fetchDoctorType();

        given(doctorTypeRepository.fetchActiveDoctorTypeById(id))
                .willReturn(Optional.of(doctorType));

        Assert.assertThat(doctorTypeService.fetchDoctorTypeById(id),
                samePropertyValuesAs(doctorType));
    }
}
