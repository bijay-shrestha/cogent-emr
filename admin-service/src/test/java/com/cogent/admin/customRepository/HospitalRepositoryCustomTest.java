package com.cogent.admin.customRepository;

import com.cogent.admin.dto.response.hospital.HospitalResponseDTO;
import com.cogent.admin.repository.custom.impl.HospitalRepositoryCustomImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.dto.hospital.HospitalTestUtils.getHospitalSearchRequestDTO;
import static com.cogent.admin.log.constants.HospitalLog.HOSPITAL;
import static com.cogent.admin.query.HospitalQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

/**
 * @author Rupak
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class HospitalRepositoryCustomTest {


    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    HospitalRepositoryCustomImpl hospitalRepositoryCustom;

    @Test
    public void findHospitalByName() {

        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_HOSPITAL_COUNT_BY_NAME)
                .setParameter(HOSPITAL, "Mr.");

        Long result = (Long) query.getSingleResult();
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void searchHospitalTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_HOSPITAL
                (getHospitalSearchRequestDTO()));

        List list = transformQueryToResultList(query, HospitalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchHospitalForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_HOSPITAL_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }


}
