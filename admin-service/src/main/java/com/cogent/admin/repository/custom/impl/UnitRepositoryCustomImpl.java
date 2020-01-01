package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import com.cogent.admin.dto.response.unit.UnitMinimalResponseDTO;
import com.cogent.admin.dto.response.unit.UnitResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.UnitRepositoryCustom;
import com.cogent.persistence.model.Unit;
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
import static com.cogent.admin.query.UnitQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 10/13/19
 */
@Repository
@Transactional
@Slf4j
public class UnitRepositoryCustomImpl implements UnitRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<DropDownResponseDTO>> dropDownList() {
        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_UNIT);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> activeDropDownList() {
        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_UNIT);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public List<Object[]> fetchUnitByNameOrCode(String name, String code) {
        Query query = createQuery.apply(entityManager, FETCH_UNIT_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<Object[]> checkUnitNameAndCodeIfExist(Long id, String name, String code) {
        Query query = createQuery.apply(entityManager, CHECK_IF_UNIT_NAME_AND_CODE_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<UnitMinimalResponseDTO> searchUnit(UnitSearchRequestDTO searchRequestDTO, Pageable pageable) {


        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_UNIT.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<UnitMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                UnitMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Unit.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public UnitResponseDTO fetchUnitDetails(Long id) {
        Query query = createQuery.apply(entityManager, FETCH_UNIT_DETAILS)
                .setParameter(ID, id);

        try {
            UnitResponseDTO singleResult = transformQueryToSingleResult(query, UnitResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Unit.class, "id", id.toString());
        }
    }
}
