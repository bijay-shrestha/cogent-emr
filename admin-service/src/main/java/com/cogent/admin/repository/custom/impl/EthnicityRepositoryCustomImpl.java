package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityDropDownResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityMinimalResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.EthnicityRepositoryCustom;
import com.cogent.persistence.model.Ethnicity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.query.EthnicityQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;


/**
 * @author Sauravi  on 2019-08-25
 */

@Repository
@Transactional
@Slf4j
public class EthnicityRepositoryCustomImpl implements EthnicityRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<EthnicityDropDownResponseDTO>> dropDownList() {

        Query query = getQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_ETHNICITY);

        List<EthnicityDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                EthnicityDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<EthnicityDropDownResponseDTO>> activeDropDownList() {

        Query query = getQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_ETHNICITY);

        List<EthnicityDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                EthnicityDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public List<EthnicityMinimalResponseDTO> searchEthnicity(
            EthnicitySearchRequestDTO ethnicitySearchRequestDTO, Pageable pageable) {

        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_ETHNICITY.apply(ethnicitySearchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<EthnicityMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                EthnicityMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Ethnicity.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public List<Object[]> fetchEthnicityByNameOrCode(String name, String code) {

        Query query = getQuery.apply(entityManager, FETCH_ETHNICITY_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<Object[]> checkEthnicityNameAndCodeIfExist(Long id, String name, String code) {
        Query query = getQuery.apply(entityManager, CHECK_IF_ETHNICITY_NAME_AND_CODE_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public EthnicityResponseDTO fetchEthnicityDetails(Long id) {

        Query query = getQuery.apply(entityManager, FETCH_ETHNICITY_DETAILS)
                .setParameter(ID, id);

        try {
            EthnicityResponseDTO singleResult = transformQueryToSingleResult(query, EthnicityResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Ethnicity.class, "id", id.toString());
        }
    }

}
