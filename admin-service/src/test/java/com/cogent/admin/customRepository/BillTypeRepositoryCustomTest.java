package com.cogent.admin.customRepository;

import com.cogent.admin.repository.custom.impl.BillTypeRepositoryCustomImpl;
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

import static com.cogent.admin.dto.billType.BillTypeResponseUtils.fetchActiveBillTypes;
import static com.cogent.admin.query.BillTypeQuery.QUERY_TO_FETCH_ACTIVE_BILLTYPE;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertFalse;

/**
 * @author smriti on 2019-10-22
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BillTypeRepositoryCustomTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    BillTypeRepositoryCustomImpl billTypeRepositoryCustom;

    @Test
    public void fetchActiveBillType() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_BILLTYPE);

        List results = query.getResultList();

        assertFalse(results.isEmpty());
        Assert.assertTrue(new ReflectionEquals(results).matches(fetchActiveBillTypes()));
    }

}
