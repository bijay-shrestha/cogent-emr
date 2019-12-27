package com.cogent.admin.repository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.response.specialization.SpecializationResponseDTO;
import com.cogent.admin.utils.QueryUtils;
import com.cogent.persistence.model.Specialization;
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
import static com.cogent.admin.dto.specialization.SpecializationRequestUtils.getSpecializationSearchRequestDTO;
import static com.cogent.admin.dto.specialization.SpecializationResponseUtils.getSpecialization;
import static com.cogent.admin.query.SpecializationQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;
import static com.cogent.admin.utils.QueryUtils.transformQueryToSingleResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author smriti on 2019-09-25
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class SpecializationRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchSpecializationById() {
        String sql = "SELECT s FROM Specialization s WHERE s.status!='D' AND s.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        Specialization expected = getSpecialization();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));
    }

    @Test
    public void findSpecializationCountByName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_SPECIALIZATION_COUNT_BY_NAME)
                .setParameter(NAME, "Physician");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void findSpecializationCountByIdAndName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_SPECIALIZATION_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, 1L)
                .setParameter(NAME, "Physician");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void searchSpecialization() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_SPECIALIZATION
                (getSpecializationSearchRequestDTO()));

        List list = QueryUtils.transformQueryToResultList(query, SpecializationResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchSpecializationForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_SPECIALIZATION_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }
    @Test
    public void fetchSpecializationDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_SPECIALIZATION_DETAILS)
                .setParameter("id", 1L);

        Object result = transformQueryToSingleResult(query, SpecializationResponseDTO.class);
        assertNotNull(result);
    }

    @Test
    public void fetchSpecializationByDoctorId() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_SPECIALIZATION_BY_DOCTOR_ID)
                .setParameter(ID, 2L);

        List result = transformQueryToResultList(query, DropDownResponseDTO.class);
        assertNotNull(result);
    }
}
