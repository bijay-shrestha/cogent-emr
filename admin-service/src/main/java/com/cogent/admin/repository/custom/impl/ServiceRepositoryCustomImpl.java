package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.response.service.ServiceMinimalResponseDTO;
import com.cogent.admin.dto.response.service.ServiceResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.ServiceRepositoryCustom;
import com.cogent.persistence.model.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.query.ServiceQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 11/18/19
 */

@Repository
public class ServiceRepositoryCustomImpl implements ServiceRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> fetchServiceByNameOrCode(String name, String code) {
        Query query = createQuery.apply(entityManager, FETCH_SERVICE_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        List<Object[]> objects = query.getResultList();

        return objects;
    }

    @Override
    public List<Object[]> checkIfServiceNameAndCodeExists(Long id, String name, String code) {
        Query query = createQuery.apply(entityManager, CHECK_IF_SERVICE_NAME_AND_CODE_EXISTS)
                .setParameter(ID, id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        List<Object[]> objects = query.getResultList();

        return objects;
    }

    @Override
    public List<ServiceMinimalResponseDTO> searchService(ServiceSearchRequestDTO searchRequestDTO,
                                                         Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_SERVICE.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<ServiceMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                ServiceMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Service.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public ServiceResponseDTO fetchServiceDetails(Long id) {
        Query query = createQuery.apply(entityManager, FETCH_SERVICE_DETAILS)
                .setParameter(ID, id);
        try {
            ServiceResponseDTO singleResult = transformQueryToSingleResult(query, ServiceResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Service.class, "id", id.toString());
        }
    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchDropDownList(Long departmentId) {
        Query query = createQuery.apply(entityManager, FETCH_DROP_DOWN_LIST)
                .setParameter(DEPARTMENT_ID,departmentId);

        List<DropDownResponseDTO> dropDownDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchActiveDropDownList(Long departmentId) {
        Query query = createQuery.apply(entityManager, FETCH_ACTIVE_DROP_DOWN_LIST)
                .setParameter(DEPARTMENT_ID, departmentId);

        List<DropDownResponseDTO> dropDownDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }
}
