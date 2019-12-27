package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import com.cogent.admin.dto.response.provinces.ProvincesDropDownResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesMinimalResponseDTO;
import com.cogent.admin.dto.response.provinces.ProvincesResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.ProvincesRepositoryCustom;
import com.cogent.persistence.model.Provinces;
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

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.query.ProvincesQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

@Repository
@Transactional
@Slf4j
public class ProvincesRepositoryCustomImpl implements ProvincesRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchCountByName(String name) {

        Query query = getQuery.apply(entityManager, FETCH_PROVINCE_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Optional<List<ProvincesDropDownResponseDTO>> fetchDropDownList() {

        Query query = getQuery.apply(entityManager, FETCH_DROP_DOWN_LIST);

        List<ProvincesDropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                ProvincesDropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);
    }

    @Override
    public Optional<List<ProvincesDropDownResponseDTO>> fetchActiveDropDownList() {

        Query query = getQuery.apply(entityManager, FETCH_ACTIVE_DROP_DOWN_LIST);

        List<ProvincesDropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                ProvincesDropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);
    }

    @Override
    public List<ProvincesMinimalResponseDTO> searchProvinces(ProvincesSearchRequestDTO searchRequestDTO,
                                                             Pageable pageable) {

        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_PROVINCES.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<ProvincesMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                ProvincesMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Provinces.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public ProvincesResponseDTO fetchProvincesDetails(Long id) {

        Query query = getQuery.apply(entityManager, FETCH_DETAIL_PROVINCES_DATA)
                .setParameter(ID, id);

        try {
            ProvincesResponseDTO singleResult = transformQueryToSingleResult(query, ProvincesResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Provinces.class, "id", id.toString());
        }
    }
}
