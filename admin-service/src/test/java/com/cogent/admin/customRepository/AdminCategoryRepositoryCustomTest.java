package com.cogent.admin.customRepository;

import com.cogent.admin.dto.adminCategory.AdminCategoryRequestUtils;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryMinimalResponseDTO;
import com.cogent.admin.utils.QueryUtils;
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

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.dto.adminCategory.AdminCategoryRequestUtils.getAdminCategoryObject;
import static com.cogent.admin.dto.adminCategory.AdminCategoryResponseUtils.fetchAdminCategoryDetailById;
import static com.cogent.admin.query.AdminCategoryQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertFalse;

/**
 * @author smriti on 2019-08-11
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AdminCategoryRepositoryCustomTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void findAdminCategoryCountByNameOrCode() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_ADMIN_CATEGORY_BY_NAME_OR_CODE)
                .setParameter(NAME, "Consultant")
                .setParameter(CODE, "CT");

        assertFalse(query.getResultList().isEmpty());
        Assert.assertTrue(new ReflectionEquals(query.getResultList()).matches(getAdminCategoryObject()));
    }

    @Test
    public void findAdminCategoryCountByIdAndNameOrCode() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_ADMIN_CATEGORY_BY_ID_AND_NAME_OR_CODE)
                .setParameter(ID, 1L)
                .setParameter(NAME, "Consultant")
                .setParameter(CODE, "CT");

        assertFalse(query.getResultList().isEmpty());
        Assert.assertTrue(new ReflectionEquals(query.getResultList()).matches(getAdminCategoryObject()));
    }

    @Test
    public void searchAdminCategoryTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_ADMIN_CATEGORY.apply
                (AdminCategoryRequestUtils.getAdminCategoryRequestDTOForSearch()));

        List list = QueryUtils.transformQueryToResultList(query, AdminCategoryMinimalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchAdminCategoryForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_ADMIN_CATEGORY_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchAdminCategoryDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ADMIN_CATEGORY_DETAILS)
                .setParameter("id", 1L);

        List<Object[]> results = query.getResultList();
        final String[] remarks = {""};

        results.forEach(result -> {
            remarks[0] = result[0].toString();
        });

        Assert.assertTrue(new ReflectionEquals(fetchAdminCategoryDetailById().getRemarks()).matches(remarks[0]));

        assertFalse(results.isEmpty());
    }
}
