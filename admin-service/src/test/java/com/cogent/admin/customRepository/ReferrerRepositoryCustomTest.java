package com.cogent.admin.customRepository;

import com.cogent.admin.dto.response.referrer.ReferrerResponseDTO;
import com.cogent.admin.repository.custom.impl.ReferrerRepositoryCustomImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;

import java.util.List;

import static com.cogent.admin.dto.referrer.ReferrerTestUtils.getReferrerSearchRequestDTO;
import static com.cogent.admin.log.constants.ReferrerLog.REFERRER;
import static com.cogent.admin.query.ReferrerQuery.QUERY_TO_FETCH_REFERRER_FOR_DROPDOWN;
import static com.cogent.admin.query.ReferrerQuery.QUERY_TO_SEARCH_REFERRER;
import static com.cogent.admin.query.TitleQuery.QUERY_TO_FIND_TITLE_COUNT_BY_NAME;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;
import static org.junit.Assert.assertFalse;

/**
 * @author Rupak
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ReferrerRepositoryCustomTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    ReferrerRepositoryCustomImpl titleRepositoryCustom;

    @Test
    public void findReferrerByName() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_TITLE_COUNT_BY_NAME)
                .setParameter(REFERRER, "Dr.");

        Long result = (Long) query.getSingleResult();
        Assertions.assertThat(result).isEqualTo(0L);
    }

    @Test
    public void searchReferrerTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_REFERRER
                (getReferrerSearchRequestDTO()));

        List list = transformQueryToResultList(query, ReferrerResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchSurnameForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_REFERRER_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }



}
