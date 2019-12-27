package com.cogent.admin.customRepository;

import com.cogent.admin.dto.response.surname.SurnameResponseDTO;
import com.cogent.admin.repository.SurnameRepository;
import com.cogent.admin.utils.QueryUtils;
import com.cogent.persistence.model.Surname;
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
import static com.cogent.admin.dto.surname.SurnameRequestUtils.getSurnameSearchRequestDTO;
import static com.cogent.admin.query.SurnameQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author smriti on 2019-09-12
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class SurnameRepositoryCustomTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    SurnameRepository surnameRepository;

    @Test
    public void findSurnameCountByName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_SURNAME_COUNT_BY_NAME)
                .setParameter(NAME, "Nepalii");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void findSurnameCountByIdAndNameOrCode() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_SURNAME_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, 1L)
                .setParameter(NAME, "Nepal");


        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void searchSurnameTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_SURNAME
                (getSurnameSearchRequestDTO()));

        List list = QueryUtils.transformQueryToResultList(query, SurnameResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchSurnameForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_SURNAME_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchSurname() {
        Surname surname=surnameRepository.fetchSurname(1L);

        assertNotNull(surname);
    }
}
