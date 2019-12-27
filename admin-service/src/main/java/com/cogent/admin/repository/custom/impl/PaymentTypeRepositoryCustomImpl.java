package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.repository.custom.PaymentTypeRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.query.PaymentTypeQuery.FETCH_ACTIVE_DROP_DOWN_LIST;
import static com.cogent.admin.query.PaymentTypeQuery.FETCH_DROP_DOWN_LIST;
import static com.cogent.admin.utils.QueryUtils.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

/**
 * @author Sauravi Thapa 12/11/19
 */

@Repository
@Transactional(readOnly = true)
public class PaymentTypeRepositoryCustomImpl implements PaymentTypeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<List<DropDownResponseDTO>> dropDownList() {
        Query query = getQuery.apply(entityManager, FETCH_DROP_DOWN_LIST);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> activeDropDownList() {
        Query query = getQuery.apply(entityManager, FETCH_ACTIVE_DROP_DOWN_LIST);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }
}
