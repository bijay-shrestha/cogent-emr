package com.cogent.admin.customRepository;

import com.cogent.admin.dto.response.profile.ProfileDropdownDTO;
import com.cogent.admin.dto.response.profile.ProfileMenuResponseDTO;
import com.cogent.admin.dto.response.profile.ProfileMinimalResponseDTO;
import com.cogent.admin.dto.response.profile.ProfileResponseDTO;
import com.cogent.admin.repository.custom.impl.ProfileRepositoryCustomImpl;
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
import java.util.ArrayList;
import java.util.List;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.dto.request.profile.ProfileRequestUtils.getProfileSearchRequestDTO;
import static com.cogent.admin.query.ProfileQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author smriti on 7/11/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProfileRepositoryCustomTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ProfileRepositoryCustomImpl profileRepositoryCustom;

    @Test
    public void findProfileCountByName() {
        findProfileCountByName_Should_Return_0();

        findProfileCountByName_Should_Return_1();
    }

    @Test
    public void findProfileCountByIdAndName() {
        findProfileCountByIdAndName_Should_Return_0();

        findProfileCountByIdAndName_Should_Return_1();
    }

    @Test
    public void findProfileCountByName_Should_Return_0() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_PROFILE_COUNT_BY_NAME)
                .setParameter(NAME, "F1soft1");

        Long result = (Long) query.getSingleResult();
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void findProfileCountByName_Should_Return_1() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_PROFILE_COUNT_BY_NAME)
                .setParameter(NAME, "Super Admin");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void findProfileCountByIdAndName_Should_Return_0() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_PROFILE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, 1L)
                .setParameter(NAME, "f1soft1");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void findProfileCountByIdAndName_Should_Return_1() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_PROFILE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, 2L)
                .setParameter(NAME, "Super Admin");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void searchProfile() {

        Query query = getQuery.apply(testEntityManager,
                QUERY_TO_SEARCH_PROFILE.apply(getProfileSearchRequestDTO()));
        List results = QueryUtils.transformQueryToResultList(query, ProfileMinimalResponseDTO.class);

        assertFalse(results.isEmpty());
    }

    @Test
    public void fetchProfilesForDropdown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_PROFILES_FOR_DROPDOWN);

        List results = QueryUtils.transformQueryToResultList(query, ProfileDropdownDTO.class);

        assertFalse(results.isEmpty());
    }

    @Test
    public void fetchProfileDetails() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_PROFILE_DETAILS)
                .setParameter(ID, 1L);

        Object list = QueryUtils.transformQueryToSingleResult(query, ProfileResponseDTO.class);

        assertNotNull(list);
    }

    @Test
    public void fetchProfileMenuDetails() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_PROFILE_MENU_DETAILS)
                .setParameter(ID, 1L);

        List list = QueryUtils.transformQueryToResultList(query, ProfileMenuResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchDetailsWithGroupConcat() {

        String sql = "SELECT" +
                " group_concat((concat(pm.id, '-',pm.roleId, '-', pm.userMenuId)))," +      //[0]
                " p.description," +                                                         //[1]
                " p.subDepartment.id," +                                                    //[2]
                " p.remarks" +                                                             //[3]
                " FROM" +
                " ProfileMenu pm" +
                " LEFT JOIN Profile p ON pm.profile.id = p.id" +
                " WHERE" +
                " p.id=:id" +
                " AND pm.status='Y'" +
                " GROUP BY p.id";

        Query query = testEntityManager.getEntityManager().createQuery(sql).setParameter(ID, 1L);

        Object results = query.getResultList();

        Assert.assertTrue(new ReflectionEquals(results, new String("remarks"))
                .matches(getProfileDetailObject()));
    }

    public Object getProfileDetailObject() {
        List<Object> objects = new ArrayList<>();

        Object[] object = new Object[5];
        object[0] = "1-1-1,2-2-3";
        object[1] = "cogent profile";
        object[2] = 1L;
        objects.add(object);
        return objects;
    }
}