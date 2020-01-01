package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import com.cogent.admin.dto.response.surname.SurnameDropdownDTO;
import com.cogent.admin.dto.response.surname.SurnameMinimalResponseDTO;
import com.cogent.admin.dto.response.surname.SurnameResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.SurnameRepositoryCustom;
import com.cogent.persistence.model.Surname;
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
import static com.cogent.admin.query.SurnameQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 2019-09-12
 */
@Repository
@Transactional(readOnly = true)
public class SurnameRepositoryCustomImpl implements SurnameRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchSurnameByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_SURNAME_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long findSurnameByIdAndName(Long id, String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_SURNAME_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public List<SurnameMinimalResponseDTO> search(SurnameSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_SURNAME(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<SurnameMinimalResponseDTO> results = transformQueryToResultList(
                query, SurnameMinimalResponseDTO.class);

        if (results.isEmpty()) throw SURNAME_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<SurnameDropdownDTO> fetchActiveSurnameForDropDown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_SURNAME_FOR_DROPDOWN);

        List<SurnameDropdownDTO> results = transformQueryToResultList(query, SurnameDropdownDTO.class);

        if (results.isEmpty()) throw SURNAME_NOT_FOUND.get();
        else return results;
    }

    @Override
    public SurnameResponseDTO fetchDetailsById(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_SURNAME_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, SurnameResponseDTO.class);
        } catch (NoResultException e) {
            throw SURNAME_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    @Override
    public Surname fetchSurname(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_SURNAME_ENTITY)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, Surname.class);
        } catch (NoResultException e) {
            throw SURNAME_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    private Supplier<NoContentFoundException> SURNAME_NOT_FOUND = () -> new NoContentFoundException(Surname.class);

    private Function<Long, NoContentFoundException> SURNAME_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Surname.class, "id", id.toString());
    };
}
