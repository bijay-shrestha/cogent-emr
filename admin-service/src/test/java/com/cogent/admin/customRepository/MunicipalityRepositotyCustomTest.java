package com.cogent.admin.customRepository;

import com.cogent.admin.dto.response.municipality.MunicipalityMinimalResponseDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityResponseDTO;
import com.cogent.admin.repository.MunicipalityRepository;
import com.cogent.admin.utils.QueryUtils;
import com.cogent.persistence.model.District;
import com.cogent.persistence.model.Municipality;
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

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.dto.municipality.MunicipalityTestUtils.getMunicipalitySearchRequestDTO;
import static com.cogent.admin.dto.municipality.MunicipalityResponseUtils.fetchMunicipalityResponseDTO;
import static com.cogent.admin.query.DistrictQuery.QUERY_TO_FETCH_DISTRICT;
import static com.cogent.admin.query.MunicipalityQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToSingleResult;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author smriti on 2019-09-15
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class MunicipalityRepositotyCustomTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    MunicipalityRepository municipalityRepository;

    @Test
    public void findMunicipalityCountByName() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_MUNICIPALITY_COUNT_BY_NAME)
                .setParameter(NAME, "Kathmandu");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void findMunicipalityCountByIdAndNameOrCode() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FIND_MUNICIPALITY_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, 1L)
                .setParameter(NAME, "Kathmandu");

        Long result = (Long) query.getSingleResult();

        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void searchMunicipalityTest() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_SEARCH_MUNICIPALITY
                (getMunicipalitySearchRequestDTO()));

        List list = QueryUtils.transformQueryToResultList(query, MunicipalityMinimalResponseDTO.class);

        assertFalse(list.isEmpty());
    }

    @Test
    public void fetchMunicipalityForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_ACTIVE_MUNICIPALITY_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchMunicipalityDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_MUNICIPALITY_DETAILS)
                .setParameter("id", 1L);

        MunicipalityResponseDTO responseDTO = transformQueryToSingleResult(query, MunicipalityResponseDTO.class);

        Assertions.assertThat(responseDTO).isEqualTo(fetchMunicipalityResponseDTO());
    }

    @Test
    public void fetchMunicipality() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_DISTRICT)
                .setParameter("id", 1L);


        District district=transformQueryToSingleResult(query, District.class);

        assertNotNull(district);
    }

    @Test
    public void Test() {
       Municipality objects=municipalityRepository.findMunicipality(1L);


        assertNotNull(objects);
    }



}
