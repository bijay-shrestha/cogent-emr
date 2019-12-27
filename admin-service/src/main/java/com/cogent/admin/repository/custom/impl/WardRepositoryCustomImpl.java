package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import com.cogent.admin.dto.response.ward.WardMinimalResponseDTO;
import com.cogent.admin.dto.response.ward.WardResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.WardRepositoryCustom;
import com.cogent.persistence.model.Ward;
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
import static com.cogent.admin.query.WardQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;
import static com.cogent.admin.utils.WardUtils.parseObjectListToWardMinimalResponseDTO;
import static com.cogent.admin.utils.WardUtils.parseObjectToWardResponseDTO;

/**
 * @author Sauravi Thapa 10/2/19
 */

@Repository
@Transactional
@Slf4j
public class WardRepositoryCustomImpl implements WardRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<DropDownResponseDTO>> dropDownList() {
        Query query = getQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_WARD);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> activeDropDownList() {
        Query query = getQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_WARD);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public List<Object[]> fetchWardByNameOrCode(String name, String code) {
        Query query = getQuery.apply(entityManager, FETCH_WARD_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<Object[]> checkIfWardNameAndCodeExists(Long id, String name, String code) {
        Query query=getQuery.apply(entityManager, CHECK_IF_WARD_NAME_AND_CODE_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME,name)
                .setParameter(CODE,code);

        return  query.getResultList();
    }

    @Override
    public List<WardMinimalResponseDTO> searchWard(WardSearchRequestDTO searchRequestDTO, Pageable pageable) {

        Query query = createNativeQuery.apply(entityManager, QUERY_TO_SEARCH_WARD.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<Object[]> objects = query.getResultList();
        List<WardMinimalResponseDTO> minimalResponseDTOS=parseObjectListToWardMinimalResponseDTO(objects);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Ward.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public WardResponseDTO fetchWardDetails(Long id) {
        Query query =  createNativeQuery.apply(entityManager,FETCH_WARD_DETAILS)
                .setParameter(ID, id);
        try {
            List<Object[]> singleResult = query.getResultList();
            return parseObjectToWardResponseDTO(singleResult);
        } catch (NoResultException e) {
            throw new NoContentFoundException(Ward.class, "id", id.toString());
        }
    }

}
