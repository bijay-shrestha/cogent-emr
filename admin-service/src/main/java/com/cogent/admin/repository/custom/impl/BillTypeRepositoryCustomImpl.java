package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.BillTypeRepositoryCustom;
import com.cogent.persistence.model.BillType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Supplier;

import static com.cogent.admin.query.BillTypeQuery.QUERY_TO_FETCH_ACTIVE_BILLTYPE;
import static com.cogent.admin.utils.QueryUtils.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

/**
 * @author smriti on 2019-10-22
 */
@Repository
@Transactional(readOnly = true)
public class BillTypeRepositoryCustomImpl implements BillTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DropDownResponseDTO> fetchActiveBillTypes() {
        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_BILLTYPE);

        List<DropDownResponseDTO> results = transformQueryToResultList(query, DropDownResponseDTO.class);

        if (results.isEmpty()) throw BILL_TYPE_NOT_FOUND.get();
        else return results;
    }

    private Supplier<NoContentFoundException> BILL_TYPE_NOT_FOUND = ()
            -> new NoContentFoundException(BillType.class);
}
