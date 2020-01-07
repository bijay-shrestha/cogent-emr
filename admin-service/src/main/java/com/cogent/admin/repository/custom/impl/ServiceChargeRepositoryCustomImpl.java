package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.response.servicecharge.BillingModeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeMinimalResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.ServiceChargeRepositoryCustom;
import com.cogent.persistence.model.ServiceCharge;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.query.ServiceChargeQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 11/18/19
 */

@Repository
public class ServiceChargeRepositoryCustomImpl implements ServiceChargeRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public BigInteger fetchServiceChargeCountByServiceIdAndBillingModeId(Long serviceId, Long billingModeId) {
        Query query = createNativeQuery.apply(entityManager, FETCH_SERVICE_CHARGE_COUNT_BY_SERVICE_ID_AND_BILLING_MODE_ID)
                .setParameter(SERVICE_ID, serviceId)
                .setParameter(BILLING_MODE_ID, billingModeId);


        return (BigInteger) query.getSingleResult();
    }

    @Override
    public BigInteger CheckIfServiceExists(Long id, Long serviceId, Long billingModeId) {
        Query query = createNativeQuery.apply(entityManager, CHECK_IF_SERVICE_ALREADY_EXISTS)
                .setParameter(ID, id)
                .setParameter(SERVICE_ID, serviceId)
                .setParameter(BILLING_MODE_ID, billingModeId);


        return (BigInteger) query.getSingleResult();
    }

    @Override
    public List<ServiceChargeMinimalResponseDTO> searchServiceCharge(ServiceChargeSearchRequestDTO requestDTO, Pageable pageable) {
        Query query = createNativeQuery.apply(entityManager, QUERY_TO_SEARCH_SERVICE_CHARGE.apply(requestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<ServiceChargeMinimalResponseDTO> minimalResponseDTOS = transformNativeQueryToResultList(query,
                ServiceChargeMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(ServiceCharge.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public ServiceChargeResponseDTO fetchServiceChargeDetailById(Long id) {
        Query query = createNativeQuery.apply(entityManager, FETCH_SERVICE_CHARGE_DETAILS)
                .setParameter(ID, id);
        try {
            return transformNativeQueryToSingleResult(query, ServiceChargeResponseDTO.class);
        } catch (NoResultException e) {
            throw new NoContentFoundException(ServiceCharge.class, "id", id.toString());
        }
    }

    @Override
    public Optional<List<ServiceChargeDropDownResponseDTO>> fetchDropDownList() {
        Query query = createNativeQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_SERVICE_CHARGE);

        List<ServiceChargeDropDownResponseDTO> dropDownDTOS = transformNativeQueryToResultList(query,
                ServiceChargeDropDownResponseDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }

    @Override
    public Optional<List<ServiceChargeDropDownResponseDTO>> fetchActiveDropDownList() {
        Query query = createNativeQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_SERVICE_CHARGE);

        List<ServiceChargeDropDownResponseDTO> dropDownDTOS = transformNativeQueryToResultList(query,
                ServiceChargeDropDownResponseDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }

    @Override
    public Optional<List<BillingModeDropDownResponseDTO>> fetchActiveDropDownListByBillingModeId(Long billingModeId) {
        Query query = createNativeQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_SERVICE_CHARGE_BY_BILLING_MODE_ID)
                .setParameter(BILLING_MODE_ID, billingModeId);

        List<BillingModeDropDownResponseDTO> dropDownDTOS = transformNativeQueryToResultList(query,
                BillingModeDropDownResponseDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchDropDownListBydepartmentId(Long departmentId) {
        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_SERVICE_CHARGE_BY_DEPARTMENT_ID)
                .setParameter(DEPARTMENT_ID, departmentId);

        List<DropDownResponseDTO> dropDownDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchActiveDropDownListBydepartmentId(Long departmentId) {
        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_SERVICE_CHARGE_BY_DEPARTMENT_ID)
                .setParameter(DEPARTMENT_ID, departmentId);

        List<DropDownResponseDTO> dropDownDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }

    @Override
    public List<ServiceCharge> fetchLisByBillingModeIdAndServiceId(Long billingModeId, Long serviceId) {

        List<ServiceCharge> serviceCharges = entityManager.createNativeQuery(
                QUERY_TO_FETCH_LIST_BY_BILLING_MODE_ID_AND_SERVICE_ID, ServiceCharge.class)
                .setParameter(BILLING_MODE_ID, billingModeId)
                .setParameter(SERVICE_ID, serviceId)
                .getResultList();

        return serviceCharges;
    }
}
