package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.discountscheme.DiscountSchemeSearchRequestDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeMinimalResponseDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeResponseDTO;
import com.cogent.admin.dto.response.discountscheme.discountschemedetails.DiscountResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DepartmentDiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.ServiceDiscountDropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.DiscountSchemeRepositoryCustom;
import com.cogent.persistence.model.DiscountScheme;
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
import static com.cogent.admin.query.DiscountSchemeQuery.*;
import static com.cogent.admin.utils.DiscountSchemeUtils.parseToDiscountSchemeMinimalResponseDTO;
import static com.cogent.admin.utils.DiscountSchemeUtils.parseToDiscountSchemeResponseDTO;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 11/11/19
 */


@Repository
@Transactional
@Slf4j
public class DiscountSchemeRepositoryCustomImpl implements DiscountSchemeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> fetchDiscountSchemeByNameOrCode(String name, String code) {
        Query query = createQuery.apply(entityManager, FETCH_DISCOUNT_SCHEME_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<Object[]> checkIfDiscountSchemeNameAndCodeExists(Long id, String name, String code) {
        Query query= createQuery.apply(entityManager,CHECK_IF_DISCOUNT_SCHEME_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME,name)
                .setParameter(CODE,code);

        return  query.getResultList();
    }

    @Override
    public List<DiscountSchemeMinimalResponseDTO> searchDiscountScheme(DiscountSchemeSearchRequestDTO searchRequestDTO,
                                                                       Pageable pageable) {
        Query query = createNativeQuery.apply(entityManager, QUERY_TO_SEARCH_DISCOUNT_SCHEME(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<DiscountResponseDTO> responseDTO = transformNativeQueryToResultList(
                query, DiscountResponseDTO.class);
        List<DiscountSchemeMinimalResponseDTO> results = parseToDiscountSchemeMinimalResponseDTO(responseDTO);

        if (results.isEmpty()) throw new NoContentFoundException(DiscountScheme.class);
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public Optional<List<DiscountDropDownResponseDTO>> dropDownListWithNetDiscount() {
        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_BY_DISCOUNT_SCHEME);

        List<DiscountDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DiscountDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DiscountDropDownResponseDTO>> activeDropDownListWithNetDiscount() {
        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_BY_DISCOUNT_SCHEME);

        List<DiscountDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DiscountDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DepartmentDiscountDropDownResponseDTO>> dropDownListByDepartmentId(Long departmentId) {
        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_BY_DEPARTMENT_ID)
                .setParameter(DEPARTMENT_ID,departmentId);

        List<DepartmentDiscountDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DepartmentDiscountDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DepartmentDiscountDropDownResponseDTO>> activeDropDownListByDepartmentId(Long departmentId) {
        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_BY_DEPARTMENT_ID)
                .setParameter(DEPARTMENT_ID,departmentId);

        List<DepartmentDiscountDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DepartmentDiscountDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<ServiceDiscountDropDownResponseDTO>> dropDownListByServiceId(Long serviceId) {
        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_BY_SERVICE_ID)
                .setParameter(SERVICE_ID, serviceId);

        List<ServiceDiscountDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                ServiceDiscountDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<ServiceDiscountDropDownResponseDTO>> activeDropDownListByServiceId(Long serviceId) {
        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_BY_SERVICE_ID)
                .setParameter(SERVICE_ID, serviceId);

        List<ServiceDiscountDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                ServiceDiscountDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public DiscountSchemeResponseDTO fetchDiscountSchemeDetails(Long id) {
        Query query = createNativeQuery.apply(entityManager, QUERY_TO_GET_DISCOUNT_DETAILS)
                .setParameter(ID, id);
        try {
            DiscountResponseDTO responseDTO = transformNativeQueryToSingleResult(
                    query, DiscountResponseDTO.class);

            return parseToDiscountSchemeResponseDTO(responseDTO);
        } catch (NoResultException e) {
            throw new NoContentFoundException(DiscountScheme.class, "id", id.toString());
        }
    }
}
