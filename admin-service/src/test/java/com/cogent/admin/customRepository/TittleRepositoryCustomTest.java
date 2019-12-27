package com.cogent.admin.customRepository;

import com.cogent.admin.dto.response.title.TitleResponseDTO;
import com.cogent.admin.repository.custom.impl.TitleRepositoryCustomImpl;
import com.cogent.admin.utils.QueryUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.dto.title.TitleRequestUtils.getTitleSearchRequestDTO;
import static com.cogent.admin.log.constants.TitleLog.TITLE;
import static com.cogent.admin.query.TitleQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TittleRepositoryCustomTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    TitleRepositoryCustomImpl titleRepositoryCustom;

    @Test
    public void findTittleByName() {

        Query query = QueryUtil.getQuery.apply(testEntityManager, QUERY_TO_FIND_TITLE_COUNT_BY_NAME)
                .setParameter(TITLE, "Mr.");

        Long result = (Long) query.getSingleResult();
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void searchTittleTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_TITLE
                (getTitleSearchRequestDTO()));

        List list = transformQueryToResultList(query, TitleResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchSurnameForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_TITLE_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }


}
