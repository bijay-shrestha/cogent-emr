package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeMinimalResponseDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.BillingModeRepositoryCustom;
import com.cogent.persistence.model.BillingMode;
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
import static com.cogent.admin.query.BillingModeQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 12/4/19
 */

@Repository
@Transactional(readOnly = true)
public class BillingModeRepositoryCustomImpl implements BillingModeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> findBillingModeByNameOrCode(String name, String code) {
        Query query = getQuery.apply(entityManager, FETCH_BILLING_MODE_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<BillingModeMinimalResponseDTO> searchBillingMode(BillingModeSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_BILLING_MODE.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<BillingModeMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                BillingModeMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(BillingMode.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public Optional<List<DropDownResponseDTO>> dropDownList() {
        Query query = getQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_BILLING_MODE);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> activeDropDownList() {
        Query query = getQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_BILLING_MODE);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public BillingModeResponseDTO fetchBillingModeDetails(Long id) {
        Query query = getQuery.apply(entityManager, FETCH_BILLING_MODE_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query,
                    BillingModeResponseDTO.class);
        } catch (NoResultException e) {
            throw new NoContentFoundException(BillingMode.class, "id", id.toString());
        }
    }

    @Override
    public List<Object[]> checkIfBillingModeNameAndCodeExists(Long id, String name, String code) {
        Query query=getQuery.apply(entityManager, CHECK_IF_BILLING_MODE_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME,name)
                .setParameter(CODE,code);

        return  query.getResultList();
    }
}
