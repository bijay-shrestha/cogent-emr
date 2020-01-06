package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.response.category.CategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.category.CategoryResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.CategoryRepositoryCustom;
import com.cogent.persistence.model.Category;
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
import static com.cogent.admin.query.CategoryQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;


/**
 * @author Sauravi  on 2019-08-25
 */

@Repository
@Transactional
@Slf4j
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<DropDownResponseDTO>> dropDownList() {

        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_CATEGORY);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> activeDropDownList() {

        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_CATEGORY);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public List<CategoryMinimalResponseDTO> searchCategory(
            CategorySearchRequestDTO searchRequestDTO, Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_CATEGORY.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<CategoryMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                CategoryMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Category.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public List<Object[]> fetchCategoryByNameOrCode(String name, String code) {

        Query query = createQuery.apply(entityManager, FETCH_CATEGORY_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<Object[]> checkCategoryNameAndCodeIfExist(Long id, String name, String code) {
        Query query = createQuery.apply(entityManager, CHECK_CATEGORY_NAME_AND_CODE_IF_EXIST)
                .setParameter(ID,id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public CategoryResponseDTO fetchCategoryDetails(Long id) {

        Query query = createQuery.apply(entityManager, FETCH_CATEGORY_DETAILS)
                .setParameter(ID, id);

        try {
            CategoryResponseDTO singleResult = transformQueryToSingleResult(query, CategoryResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Category.class, "id", id.toString());
        }
    }

    @Override
    public Category fetchActiveCategoryEntityById(Long id) {
        Query query = entityManager
                .createQuery(FETCH_CATEGORY_ENTITY, Category.class).setParameter(ID, id);

        try {
            Category category = (Category) query.getSingleResult();
            return category;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Category.class);
        }
    }

}
