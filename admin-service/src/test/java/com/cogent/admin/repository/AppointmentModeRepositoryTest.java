package com.cogent.admin.repository;

import com.cogent.admin.dto.response.appointmentMode.AppointmentModeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeResponseDTO;
import com.cogent.admin.utils.QueryUtils;
import com.cogent.persistence.model.AppointmentMode;
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
import static com.cogent.admin.dto.appointmentMode.AppointmentModeRequestUtils.getAppointmentModeSearchRequestDTO;
import static com.cogent.admin.dto.appointmentMode.AppointmentModeResponseUtils.getAppointmentMode;
import static com.cogent.admin.query.AppointmentModeQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToSingleResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author smriti on 2019-10-10
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AppointmentModeRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchAppointmentModeById() {
        String sql = "SELECT a FROM AppointmentMode a WHERE a.status!='D' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        AppointmentMode expected = getAppointmentMode();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query, "remarks").matches(expected));
    }

    @Test
    public void fetchActiveAppointmentModeById() {
        String sql = "SELECT a FROM AppointmentMode a WHERE a.status='Y' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter("id", 1L).getSingleResult();

        AppointmentMode expected = getAppointmentMode();

        assertNotNull(query);

        Assert.assertTrue(new ReflectionEquals(query).matches(expected));
    }

    @Test
    public void findAppointmentModeCountByName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_APPOINTMENT_MODE_COUNT_BY_NAME)
                .setParameter(NAME, "Department");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void findAppointmentModeCountByIdAndName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_APPOINTMENT_MODE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, 1L)
                .setParameter(NAME, "Department");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void searchAppointmentMode() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_APPOINTMENT_MODE
                (getAppointmentModeSearchRequestDTO()));

        List list = QueryUtils.transformQueryToResultList(query, AppointmentModeMinimalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchAppointmentModeForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_APPOINTMENT_MODE);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchAppointmentModeDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_APPOINTMENT_MODE_DETAILS)
                .setParameter("id", 1L);

        Object result = transformQueryToSingleResult(query, AppointmentModeResponseDTO.class);
        assertNotNull(result);
    }
}
