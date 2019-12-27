package com.cogent.admin.repository;

import com.cogent.admin.dto.response.applicationModules.ApplicationModuleMinimalResponseDTO;
import com.cogent.admin.utils.QueryUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.dto.applicationModules.ApplicationModuleRequestUtils.getApplicationModuleRequestDTOForSearch;
import static com.cogent.admin.query.ApplicationModuleQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author smriti ON 25/12/2019
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ApplicationModuleRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void fetchApplicationModuleById() {
        String sql = "SELECT a FROM ApplicationModule a WHERE a.status!='D' AND a.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter(ID, 1L).getSingleResult();

        assertNotNull(query);
    }

    @Test
    public void fetchApplicationModuleCountByIdAndName() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_APPOINTMENT_MODULE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, 1L)
                .setParameter(NAME, "ADMIN MODULE");

        Long result = (Long) query.getSingleResult();
        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void search() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_APPLICATION_MODULE
                (getApplicationModuleRequestDTOForSearch()));

        List list = QueryUtils.transformQueryToResultList(query, ApplicationModuleMinimalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchApplicationModuleForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_APPLICATION_MODULE_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchApplicationModuleDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_APPLICATION_MODULE_DETAILS)
                .setParameter("id", 1L);

        Object result = query.getSingleResult();

        System.out.println(result);
    }
}
