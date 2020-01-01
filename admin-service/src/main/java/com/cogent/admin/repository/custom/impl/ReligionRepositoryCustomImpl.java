package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.religion.ReligionSearchRequestDTO;
import com.cogent.admin.dto.response.religion.ReligionDropdownDTO;
import com.cogent.admin.dto.response.religion.ReligionResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.ReligionRepositoryCustom;
import com.cogent.persistence.model.Religion;
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
import static com.cogent.admin.query.ReligionQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.createQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class ReligionRepositoryCustomImpl implements ReligionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchReligionByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_RELIGION_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long findReligionByIdAndName(Long id, String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_RELIGION_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();

    }

    @Override
    public List<ReligionResponseDTO> search(ReligionSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_RELIGION(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<ReligionResponseDTO> results = transformQueryToResultList(
                query, ReligionResponseDTO.class);

        if (results.isEmpty()) throw RELIGION_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<ReligionDropdownDTO> fetchReligionForDropDown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_RELIGION_FOR_DROPDOWN);

        List<ReligionDropdownDTO> results = transformQueryToResultList(query, ReligionDropdownDTO.class);

        if (results.isEmpty()) throw RELIGION_NOT_FOUND.get();
        else return results;
    }

    private Supplier<NoContentFoundException> RELIGION_NOT_FOUND = () -> new NoContentFoundException(Religion.class);
}
