package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.response.servicecharge.BillingModeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeMinimalResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 11/18/19
 */

@Repository
@Qualifier("serviceChargeRepositoryCustom")
public interface ServiceChargeRepositoryCustom {

    BigInteger fetchServiceChargeCountByServiceIdAndBillingModeId(Long serviceId, Long billingModeId);

    BigInteger CheckIfServiceExists(Long id, Long serviceId, Long billingModeId);

    List<ServiceChargeMinimalResponseDTO> searchServiceCharge(ServiceChargeSearchRequestDTO requestDTO,
                                                              Pageable pageable);

    ServiceChargeResponseDTO fetchServiceChargeDetailById(Long id);


    Optional<List<ServiceChargeDropDownResponseDTO>> fetchDropDownList();

    Optional<List<ServiceChargeDropDownResponseDTO>> fetchActiveDropDownList();

    Optional<List<BillingModeDropDownResponseDTO>> fetchActiveDropDownListByBillingModeId(Long BillingModeId);


    Optional<List<DropDownResponseDTO>> fetchDropDownListBydepartmentId(Long departmentId);

    Optional<List<DropDownResponseDTO>> fetchActiveDropDownListBydepartmentId(Long departmentId);
}
