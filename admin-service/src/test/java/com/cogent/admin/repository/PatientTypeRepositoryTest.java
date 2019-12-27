package com.cogent.admin.repository;

import com.cogent.admin.dto.response.patientType.PatientTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeResponseDTO;
import com.cogent.admin.utils.QueryUtils;
import com.cogent.persistence.model.PatientType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.dto.request.patientType.PatientTypeRequestUtils.getPatientTypeSearchRequestDTO;
import static com.cogent.admin.dto.request.patientType.PatientTypeResponseUtils.getPatientType;
import static com.cogent.admin.query.PatientTypeQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToSingleResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author smriti on 2019-09-26
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class PatientTypeRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchPatientTypeById() {
        String sql = "SELECT a FROM PatientType a WHERE a.status!='D' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        PatientType expected = getPatientType();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));
    }

    @Test
    public void fetchActivePatientTypeById() {
        String sql = "SELECT a FROM PatientType a WHERE a.status!='D' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        assertNotNull(query);
    }

    @Test
    public void findPatientTypeCountByName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_PATIENT_TYPE_COUNT_BY_NAME)
                .setParameter(NAME, "Inpatient");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void findPatientTypeCountByIdAndName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_PATIENT_TYPE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, 1L)
                .setParameter(NAME, "Inpatient");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void searchPatientType() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_PATIENT_TYPE
                (getPatientTypeSearchRequestDTO()));

        List list = QueryUtils.transformQueryToResultList(query, PatientTypeMinimalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchPatientTypeForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_PATIENT_TYPE);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchPatientTypeDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_PATIENT_TYPE_DETAILS)
                .setParameter("id", 1L);

        Object result = transformQueryToSingleResult(query, PatientTypeResponseDTO.class);
        assertNotNull(result);
    }

}
