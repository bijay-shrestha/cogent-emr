package com.cogent.admin.repository;

import com.cogent.admin.constants.QueryConstants;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeResponseDTO;
import com.cogent.admin.utils.QueryUtils;
import com.cogent.persistence.model.AppointmentType;
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

import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.dto.appointmentType.AppointmentTypeRequestUtils.getAppointmentTypeSearchRequestDTO;
import static com.cogent.admin.dto.appointmentType.AppointmentTypeResponseUtils.getAppointmentType;
import static com.cogent.admin.query.AppointmentTypeQuery.*;
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
public class AppointmentTypeRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchAppointmentTypeById() {
        String sql = "SELECT a FROM AppointmentType a WHERE a.status!='D' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        AppointmentType expected = getAppointmentType();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));
    }

    @Test
    public void fetchActiveAppointmentTypeById() {
        String sql = "SELECT a FROM AppointmentType a WHERE a.status='Y' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        AppointmentType expected = getAppointmentType();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query).matches(expected));
    }

    @Test
    public void findAppointmentTypeCountByName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_APPOINTMENT_TYPE_COUNT_BY_NAME)
                .setParameter(NAME, "Online");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void findAppointmentTypeCountByIdAndName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_APPOINTMENT_TYPE_COUNT_BY_ID_AND_NAME)
                .setParameter(QueryConstants.ID, 1L)
                .setParameter(NAME, "Online");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void searchAppointmentType() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_APPOINTMENT_TYPE
                (getAppointmentTypeSearchRequestDTO()));

        List list = QueryUtils.transformQueryToResultList(query, AppointmentTypeMinimalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchAppointmentTypeForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_APPOINTMENT_TYPE);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchAppointmentTypeDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_APPOINTMENT_TYPE_DETAILS)
                .setParameter("id", 1L);

        Object result = transformQueryToSingleResult(query, AppointmentTypeResponseDTO.class);
        assertNotNull(result);
    }
}
