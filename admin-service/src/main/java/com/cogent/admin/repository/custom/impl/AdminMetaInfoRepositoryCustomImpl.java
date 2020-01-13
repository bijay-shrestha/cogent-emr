package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.response.admin.AdminMetaInfoResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.AdminMetaInfoRepositoryCustom;
import com.cogent.persistence.model.AdminMetaInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.query.AdminQuery.QUERY_TO_FETCH_ADMIN_META_INFO;
import static com.cogent.admin.utils.QueryUtils.createQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

/**
 * @author smriti ON 13/01/2020
 */
@Repository
@Transactional(readOnly = true)
public class AdminMetaInfoRepositoryCustomImpl implements AdminMetaInfoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AdminMetaInfoResponseDTO> fetchAdminMetaInfoResponseDTOS() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ADMIN_META_INFO);

        List<AdminMetaInfoResponseDTO> list = transformQueryToResultList(query, AdminMetaInfoResponseDTO.class);

        if (list.isEmpty()) throw new NoContentFoundException((AdminMetaInfo.class));

        return list;
    }
}
