package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedMinimalResponseDTO;
import com.cogent.admin.dto.response.assignbed.AssignBedResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.AssignBedRepositoryCustom;
import com.cogent.persistence.model.AssignBed;
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
import static com.cogent.admin.query.AssignBedQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 11/1/19
 */

@Repository
@Transactional
@Slf4j
public class AssignBedRepositoryCustomImpl implements AssignBedRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public AssignBed fetchAssignBedById(Long id) {
        try {
            AssignBed assignBed = entityManager.createQuery(FETCH_ASSIGN_BED_BY_ID, AssignBed.class)
                    .setParameter(ID, id)
                    .getSingleResult();

            return assignBed;
        } catch (NoResultException e) {
            throw new NoContentFoundException(AssignBed.class);
        }
    }

    @Override
    public List<AssignBedMinimalResponseDTO> searchAssignedBed(AssignBedSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_ASSIGNED_BED.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<AssignBedMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query, AssignBedMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(AssignBed.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public AssignBedResponseDTO fetchAssignBedDetails(Long id) {
        Query query = createQuery.apply(entityManager, FETCH_ASSIGNED_BED_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, AssignBedResponseDTO.class);
        } catch (NoResultException e) {
            throw new NoContentFoundException(AssignBed.class, "id", id.toString());
        }
    }

    @Override
    public Optional<List<DropDownResponseDTO>> dropDownList(Long wardId) {
        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_ASSIGNED_BED_BY_WARD_ID)
                .setParameter(WARD_ID,wardId);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> activeDropDownList(Long wardId) {
        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_ASSIGNED_BED_BY_WARD_ID)
                .setParameter(WARD_ID,wardId);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Long checkIfBedExixts(Long bedId, Long wardId, Long unitId) {
        Query query = createQuery.apply(entityManager, CHECK_IF_BEDS_EXISTS)
                .setParameter(BED_ID, bedId)
                .setParameter(WARD_ID, wardId)
                .setParameter(UNIT_ID, unitId);

        Object count = query.getSingleResult();

        return (Long) count;
    }
}
