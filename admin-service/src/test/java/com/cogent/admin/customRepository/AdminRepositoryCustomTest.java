package com.cogent.admin.customRepository;

import com.cogent.admin.dto.response.admin.AdminDetailResponseDTO;
import com.cogent.admin.dto.response.admin.AdminDropdownDTO;
import com.cogent.admin.dto.response.admin.AdminMinimalResponseDTO;
import com.cogent.admin.dto.response.admin.MacAddressInfoResponseDTO;
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
import static com.cogent.admin.dto.admin.AdminRequestUtils.getAdminSearchRequestDTO;
import static com.cogent.admin.dto.admin.AdminResponseUtils.getAdminObjectWithDuplicateUsername;
import static com.cogent.admin.query.AdminQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author smriti on 2019-08-26
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AdminRepositoryCustomTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void fetchAdminForValidation() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_ADMIN_FOR_VALIDATION)
                .setParameter(USERNAME, "smriti")
                .setParameter(EMAIL, "smriti.mool@f1soft.com");
        List<Object[]> expected = getAdminObjectWithDuplicateUsername();

        Assert.assertTrue(new ReflectionEquals(query.getResultList()).matches(expected));
    }

    @Test
    public void fetchAdminForDropdown() {
        String sql = "SELECT a.id as id, a.username as username FROM Admin a WHERE a.status= 'Y'";

        Query query = getQuery.apply(testEntityManager, sql);

        List results = QueryUtils.transformQueryToResultList(query, AdminDropdownDTO.class);

        assertFalse(results.isEmpty());
    }

    @Test
    public void searchAdmin() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_ADMIN(getAdminSearchRequestDTO()));

        List list = QueryUtils.transformQueryToResultList(query, AdminMinimalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchAdminDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ADMIN_DETAIL)
                .setParameter(ID, 3L);

        List<AdminDetailResponseDTO> result = QueryUtils.transformQueryToResultList(query, AdminDetailResponseDTO.class);

        assertFalse(result.isEmpty());
    }

    @Test
    public void fetchMacAddressInfo() {
        Query query = getQuery.apply(testEntityManager, QUERY_FO_FETCH_MAC_ADDRESS_INFO)
                .setParameter(ID, 3L);

        List result = QueryUtils.transformQueryToResultList(query, MacAddressInfoResponseDTO.class);

        System.out.println(result);

        assertFalse(result.isEmpty());
    }

    @Test
    public void fetchAdminByUsernameOrEmail(){
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ADMIN_BY_USERNAME_OR_EMAIL)
                .setParameter(USERNAME, "smriti")
                .setParameter(EMAIL, "smriti.mool@f1soft.com");

        Object result = query.getSingleResult();
        assertNotNull(result);
    }




}
