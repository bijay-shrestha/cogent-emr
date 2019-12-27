package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.title.TitleSearchRequestDTO;
import com.cogent.admin.dto.response.title.TitleDropdownDTO;
import com.cogent.admin.dto.response.title.TitleResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.query.TitleQuery;
import com.cogent.admin.repository.custom.TitleRepositoryCustom;
import com.cogent.persistence.model.Title;
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
import static com.cogent.admin.query.TitleQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class TitleRepositoryCustomImpl implements TitleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchTitleByName(String name) {
        Query query = getQuery.apply(entityManager, QUERY_TO_FIND_TITLE_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long findTitleByIdAndName(Long id, String name) {
        Query query = getQuery.apply(entityManager, TitleQuery.QUERY_TO_FIND_TITLE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public List<TitleResponseDTO> search(TitleSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_TITLE(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<TitleResponseDTO> results = transformQueryToResultList(
                query, TitleResponseDTO.class);

        if (results.isEmpty()) throw TITLE_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<TitleDropdownDTO> fetchTitleForDropDown() {
        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_TITLE_FOR_DROPDOWN);

        List<TitleDropdownDTO> results = transformQueryToResultList(query, TitleDropdownDTO.class);

        if (results.isEmpty()) throw TITLE_NOT_FOUND.get();
        else return results;
    }

    private Supplier<NoContentFoundException> TITLE_NOT_FOUND = () -> new NoContentFoundException(Title.class);
}
