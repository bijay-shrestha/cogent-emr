package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryDropdownDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.AdminCategoryRepositoryCustom;
import com.cogent.persistence.model.AdminCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.query.AdminCategoryQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 2019-08-11
 */
@Service
@Transactional(readOnly = true)
public class AdminCategoryRepositoryCustomImpl implements AdminCategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List findAdminCategoryByNameOrCode(String name, String code) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_ADMIN_CATEGORY_BY_NAME_OR_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List findAdminCategoryByIdAndNameOrCode(Long id, String name, String code) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_ADMIN_CATEGORY_BY_ID_AND_NAME_OR_CODE)
                .setParameter(ID, id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<AdminCategoryMinimalResponseDTO> search
            (AdminCategorySearchRequestDTO searchRequestDTO, Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_ADMIN_CATEGORY.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<AdminCategoryMinimalResponseDTO> results = transformQueryToResultList(
                query, AdminCategoryMinimalResponseDTO.class);

        if (results.isEmpty()) throw ADMIN_CATEGORY_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<AdminCategoryDropdownDTO> fetchActiveAdminCategoryForDropDown() {

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_ADMIN_CATEGORY_FOR_DROPDOWN);

        List<AdminCategoryDropdownDTO> results = transformQueryToResultList(query, AdminCategoryDropdownDTO.class);

        if (results.isEmpty()) throw ADMIN_CATEGORY_NOT_FOUND.get();
        else return results;
    }

    @Override
    public AdminCategoryResponseDTO fetchDetailsById(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ADMIN_CATEGORY_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, AdminCategoryResponseDTO.class);
        } catch (NoResultException e) {
            throw ADMIN_CATEGORY_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    @Override
    public List getAdminCategoryForExcel() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ADMIN_CATEGORY_FOR_EXCEL);
        return query.getResultList();
    }

    private Supplier<NoContentFoundException> ADMIN_CATEGORY_NOT_FOUND = () ->
            new NoContentFoundException(AdminCategory.class);

    private Function<Long, NoContentFoundException> ADMIN_CATEGORY_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(AdminCategory.class, "id", id.toString());
    };
}

