package com.cogent.admin.service;

import com.cogent.admin.dto.qualificationAlias.QualificationAliasResponseUtils;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.QualificationAliasRepository;
import com.cogent.admin.service.impl.QualificationAliasServiceImpl;
import com.cogent.persistence.model.QualificationAlias;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.cogent.admin.dto.qualificationAlias.QualificationAliasResponseUtils.fetchQualificationAlias;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

/**
 * @author smriti on 10/11/2019
 */
public class QualificationAliasServiceTest {

    @InjectMocks
    private QualificationAliasServiceImpl qualificationAliasService;

    @Mock
    private QualificationAliasRepository qualificationAliasRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchActiveQualificationAlias() {
        fetchActiveQualificationAlias_ShouldThrowException();
        Should_Successfully_Return_ActiveQualificationAlias();
    }

    @Test
    public void fetchQualificationAliasById() {
        fetchQualificationAliasById_ShouldThrowException();
        Should_Return_ActiveQualificationAliasById();
    }

    @Test
    public void fetchActiveQualificationAlias_ShouldThrowException() {

        given(qualificationAliasRepository.fetchActiveQualificationAlias()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        qualificationAliasService.fetchActiveQualificationAlias();
    }

    @Test
    public void Should_Successfully_Return_ActiveQualificationAlias() {
        given(qualificationAliasRepository.fetchActiveQualificationAlias())
                .willReturn(QualificationAliasResponseUtils.fetchActiveQualificationAlias());

        qualificationAliasService.fetchActiveQualificationAlias();

        assertThat(qualificationAliasService.fetchActiveQualificationAlias(),
                hasSize(QualificationAliasResponseUtils.fetchActiveQualificationAlias().size()));

        assertThat(qualificationAliasService.fetchActiveQualificationAlias(),
                samePropertyValuesAs(QualificationAliasResponseUtils.fetchActiveQualificationAlias()));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchQualificationAliasById_ShouldThrowException() {
        Long id = 1L;

        given(qualificationAliasRepository.fetchActiveQualificationAliasById(id))
                .willThrow(NoContentFoundException.class);

        qualificationAliasService.fetchQualificationAliasById(id);
    }

    @Test
    public void Should_Return_ActiveQualificationAliasById() {
        Long id = 1L;
        QualificationAlias qualificationAlias = fetchQualificationAlias();

        given(qualificationAliasRepository.fetchActiveQualificationAliasById(id))
                .willReturn(Optional.of(qualificationAlias));

        Assert.assertThat(qualificationAliasService.fetchQualificationAliasById(id),
                samePropertyValuesAs(qualificationAlias));
    }
}
