package com.cogent.admin.customRepository;

import com.cogent.admin.dto.response.religion.ReligionResponseDTO;
import com.cogent.admin.repository.custom.impl.ReligionRepositoryCustomImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.dto.religion.ReligionRequestUtils.getReligionSearchRequestDTO;
import static com.cogent.admin.query.ReligionQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ReligionRepositoryCustomTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    ReligionRepositoryCustomImpl religionRepositoryCustom;

    @Test
    public void findReligionByName() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_RELIGION_COUNT_BY_NAME)
                .setParameter(NAME, "Hindusm");

        Long result = (Long) query.getSingleResult();
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void searchReligionTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_RELIGION
                (getReligionSearchRequestDTO()));

        List list = transformQueryToResultList(query, ReligionResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchReligionForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_RELIGION_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }

}
