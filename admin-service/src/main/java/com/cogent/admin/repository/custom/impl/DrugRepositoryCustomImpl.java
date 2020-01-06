package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import com.cogent.admin.dto.response.drug.DrugMinimalResponseDTO;
import com.cogent.admin.dto.response.drug.DrugResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.DrugRepositoryCustom;
import com.cogent.persistence.model.Drug;
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
import static com.cogent.admin.query.DrugQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;


@Repository
@Transactional
@Slf4j
public class DrugRepositoryCustomImpl implements DrugRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> fetchDrugByNameOrCode(String name, String code) {

        Query query = createQuery.apply(entityManager, FETCH_DRUG_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<Object[]> checkIfDrugNameAndCodeExists(Long id, String name, String code) {
        Query query = createQuery.apply(entityManager, CHECK_IF_DRUG_NAME_AND_CODE_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchDropDownList() {

        Query query = createQuery.apply(entityManager, FETCH_DROP_DOWN_LIST);

        List<DropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);

    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchActiveDropDownList() {

        Query query = createQuery.apply(entityManager, FETCH_ACTIVE_DROP_DOWN_LIST);

        List<DropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);
    }

    @Override
    public DrugResponseDTO fetchDrugDetails(Long id) {

        Query query = createQuery.apply(entityManager, FETCH_DRUG_DETAILS)
                .setParameter(ID, id);

        try {
            DrugResponseDTO singleResult = transformQueryToSingleResult(query, DrugResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Drug.class, "id", id.toString());
        }
    }

    @Override
    public List<DrugMinimalResponseDTO> searchDrug(
            DrugSearchRequestDTO drugSearchRequestDTO, Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_DRUG.apply(drugSearchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<DrugMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DrugMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Drug.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }
}
