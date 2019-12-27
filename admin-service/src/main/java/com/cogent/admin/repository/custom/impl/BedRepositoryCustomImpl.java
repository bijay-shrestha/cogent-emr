package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.response.bed.BedMinimalResponseDTO;
import com.cogent.admin.dto.response.bed.BedResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.BedRepositoryCustom;
import com.cogent.persistence.model.Bed;
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
import static com.cogent.admin.query.BedQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 11/1/19
 */

@Repository
@Transactional
@Slf4j
public class BedRepositoryCustomImpl implements BedRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> findBedByNameOrCode(String name, String code) {
        Query query = getQuery.apply(entityManager, FETCH_BED_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<Object[]> checkBedNameAndCodeIfExist(Long id, String name, String code) {
        Query query = getQuery.apply(entityManager, CHECK_BED_NAME_AND_CODE_IF_EXIST)
                .setParameter(ID,id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<BedMinimalResponseDTO> searchBed(BedSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_BED.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<BedMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                BedMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Bed.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public Optional<List<DropDownResponseDTO>> dropDownList() {
        Query query = getQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_BED);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> activeDropDownList() {
        Query query = getQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_BED);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public BedResponseDTO fetchBedDetails(Long id) {
        Query query = getQuery.apply(entityManager, FETCH_BED_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query,
                    BedResponseDTO.class);
        } catch (NoResultException e) {
            throw new NoContentFoundException(Bed.class, "id", id.toString());
        }
    }
}
