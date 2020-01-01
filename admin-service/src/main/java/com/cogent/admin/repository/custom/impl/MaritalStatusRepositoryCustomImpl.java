package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusDropdownDTO;
import com.cogent.admin.dto.response.maritalStatus.MaritalStatusResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.MaritalStatusRepositoryCustom;
import com.cogent.persistence.model.MaritalStatus;
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
import static com.cogent.admin.query.MaritalStatusQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.createQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class MaritalStatusRepositoryCustomImpl implements MaritalStatusRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchMaritalStatusByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_MARITAL_STATUS_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long findMaritalStatusByIdAndName(Long id, String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_MARITAL_STATUS_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public List<MaritalStatusResponseDTO> search(MaritalStatusSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_MARITAL_STATUS(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<MaritalStatusResponseDTO> results = transformQueryToResultList(
                query, MaritalStatusResponseDTO.class);

        if (results.isEmpty()) throw MARITAL_STATUS_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<MaritalStatusDropdownDTO> fetchMaritalStatusForDropDown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_MARITAL_STATUS_FOR_DROPDOWN);

        List<MaritalStatusDropdownDTO> results = transformQueryToResultList(query, MaritalStatusDropdownDTO.class);

        if (results.isEmpty()) throw MARITAL_STATUS_NOT_FOUND.get();
        else return results;
    }

    private Supplier<NoContentFoundException> MARITAL_STATUS_NOT_FOUND = () -> new NoContentFoundException(MaritalStatus.class);
}
