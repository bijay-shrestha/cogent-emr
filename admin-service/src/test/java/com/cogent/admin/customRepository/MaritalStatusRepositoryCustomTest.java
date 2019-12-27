package com.cogent.admin.customRepository;

import com.cogent.admin.constants.QueryConstants;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusResponseDTO;
import com.cogent.admin.repository.custom.impl.MaritalStatusRepositoryCustomImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.dto.maritalStatus.MaritalStatusTestUtils.getMaritalStatusSearchRequestDTO;
import static com.cogent.admin.query.MaritalStatusQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class MaritalStatusRepositoryCustomTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    MaritalStatusRepositoryCustomImpl maritalStatusRepositoryCustom;

    @Test
    public void findMaritalStatusByName() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_MARITAL_STATUS_COUNT_BY_NAME)
                .setParameter(QueryConstants.NAME, "Married");

        Long result = (Long) query.getSingleResult();
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void searchMaritalStatusTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_MARITAL_STATUS
                (getMaritalStatusSearchRequestDTO()));

        List list = transformQueryToResultList(query, MaritalStatusResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchMaritalStatusForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_MARITAL_STATUS_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }


}
