package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.referrer.ReferrerSearchRequestDTO;
import com.cogent.admin.dto.response.referrer.ReferrerDropdownDTO;
import com.cogent.admin.dto.response.referrer.ReferrerResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.query.ReferrerQuery;
import com.cogent.admin.repository.custom.ReferrerRepositoryCustom;
import com.cogent.persistence.model.Referrer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Supplier;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.query.ReferrerQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.createQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

/**
 * @author Rupak
 */
@Repository
@Transactional(readOnly = true)
@Slf4j
public class ReferrerRepositoryCustomImpl implements ReferrerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchReferrerByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_REFERRER_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long findReferrerByIdAndName(Long id, String name) {

        Query query = createQuery.apply(entityManager, ReferrerQuery.QUERY_TO_FIND_REFERRER_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();

    }

    @Override
    public List<ReferrerResponseDTO> search(ReferrerSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_REFERRER(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<ReferrerResponseDTO> results = transformQueryToResultList(
                query, ReferrerResponseDTO.class);

        if (results.isEmpty()) throw REFERRER_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<ReferrerDropdownDTO> fetchReferrerForDropDown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_REFERRER_FOR_DROPDOWN);

        List<ReferrerDropdownDTO> results = transformQueryToResultList(query, ReferrerDropdownDTO.class);

        if (results.isEmpty()) throw REFERRER_NOT_FOUND.get();
        else return results;
    }

    private Supplier<NoContentFoundException> REFERRER_NOT_FOUND = () -> new NoContentFoundException(Referrer.class);
}
