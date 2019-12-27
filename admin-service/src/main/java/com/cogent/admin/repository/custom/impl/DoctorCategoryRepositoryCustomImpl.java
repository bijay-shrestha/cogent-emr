package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.doctorcategory.DoctorCategorySearchRequestDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.doctorcategory.DoctorCategoryResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.DoctorCategoryRepositoryCustom;
import com.cogent.persistence.model.DoctorCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.query.DoctorCategoryQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 11/21/19
 */
@Repository
public class DoctorCategoryRepositoryCustomImpl implements DoctorCategoryRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> fetchDoctorCategoryByNameOrCode(String name, String code) {
        Query query = getQuery.apply(entityManager, FETCH_DOCTOR_CATEGORY_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<Object[]> checkIfDoctorCategoryNameAndCodeExists(Long id, String name, String code) {
        Query query = getQuery.apply(entityManager, CHECK_IF_DOCTOR_CATEGORY_NAME_AND_CODE_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchDropDownList() {
        Query query = getQuery.apply(entityManager, FETCH_DROP_DOWN_LIST);

        List<DropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchActiveDropDownList() {
        Query query = getQuery.apply(entityManager, FETCH_ACTIVE_DROP_DOWN_LIST);

        List<DropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);
    }

    @Override
    public DoctorCategoryResponseDTO fetchDoctorCategoryDetails(Long id) {
        Query query = getQuery.apply(entityManager, FETCH_DOCTOR_CATEGORY_DETAILS)
                .setParameter(ID, id);

        try {
            return transformQueryToSingleResult(query, DoctorCategoryResponseDTO.class);
        } catch (NoResultException e) {
            throw new NoContentFoundException(DoctorCategory.class, "id", id.toString());
        }
    }

    @Override
    public List<DoctorCategoryMinimalResponseDTO> searchDoctorCategory(
            DoctorCategorySearchRequestDTO requestDTO, Pageable pageable) {

        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_DOCTOR_CATEGORY.apply(requestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<DoctorCategoryMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DoctorCategoryMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(DoctorCategory.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }
}
