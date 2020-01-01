package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityDropdownDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityMinimalResponseDTO;
import com.cogent.admin.dto.response.municipality.MunicipalityResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.MunicipalityRepositoryCustom;
import com.cogent.persistence.model.Municipality;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.query.MunicipalityQuery.*;
import static com.cogent.admin.utils.MunicipalityUtils.parseObjectToMunicipality;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 2019-09-15
 */
@Repository
@Transactional(readOnly = true)
public class MunicipalityRepositoryCustomImpl implements MunicipalityRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchMunicipalityByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_MUNICIPALITY_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Municipality findMunicipality(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_MUNICIPALITY_ENTITY)
                .setParameter(ID, id);
        List<Object[]> objects= query.getResultList();
        Municipality municipality=parseObjectToMunicipality(objects);
        return municipality;
    }

    @Override
    public Long findMunicipalityByIdAndName(Long id, String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_MUNICIPALITY_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public List<MunicipalityMinimalResponseDTO> search(MunicipalitySearchRequestDTO searchRequestDTO,
                                                       Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_MUNICIPALITY(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<MunicipalityMinimalResponseDTO> results = transformQueryToResultList(
                query, MunicipalityMinimalResponseDTO.class);

        if (results.isEmpty()) throw MUNICIPALITY_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<MunicipalityDropdownDTO> fetchActiveMunicipalityForDropDown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_MUNICIPALITY_FOR_DROPDOWN);

        List<MunicipalityDropdownDTO> results = transformQueryToResultList(query, MunicipalityDropdownDTO.class);

        if (results.isEmpty()) throw MUNICIPALITY_NOT_FOUND.get();
        else return results;
    }

    @Override
    public MunicipalityResponseDTO fetchDetailsById(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_MUNICIPALITY_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, MunicipalityResponseDTO.class);
        } catch (NoResultException e) {
            throw MUNICIPALITY_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    @Override
    public Municipality fetchMunicipalityById(Long id) {
        try {
            Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_MUNICIPALITY)
                    .setParameter(ID, id);
            Municipality municipality = transformQueryToSingleResult(query, Municipality.class);
//            Provinces provinces = entityManager.createQuery(QUERY1, Provinces.class)
//                    .setParameter(ID, 1L)
//                    .getSingleResult();
//            municipality.getDistrict().setProvinces(provinces);
            return municipality;
        } catch (NoResultException e) {
            throw MUNICIPALITY_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    private Supplier<NoContentFoundException> MUNICIPALITY_NOT_FOUND = () ->
            new NoContentFoundException(Municipality.class);

    private Function<Long, NoContentFoundException> MUNICIPALITY_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Municipality.class, "id", id.toString());
    };

}
