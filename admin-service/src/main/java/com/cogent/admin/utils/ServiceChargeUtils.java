package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeUpdateRequestDTO;
import com.cogent.persistence.model.BillingMode;
import com.cogent.persistence.model.Service;
import com.cogent.persistence.model.ServiceCharge;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Sauravi Thapa 11/19/19
 */
public class ServiceChargeUtils {

    public static ServiceCharge parseToServiceCharge(ServiceChargeRequestDTO requestDTO,
                                                     Service service,
                                                     List<BillingMode> billingMode){
        ServiceCharge serviceCharge = new ServiceCharge();

        serviceCharge.setService(service);
        serviceCharge.setStatus(requestDTO.getStatus());
        serviceCharge.setPrice(requestDTO.getPrice());
        serviceCharge.setBillingModes(billingMode);
        return serviceCharge;

    }

    public static BiFunction<DeleteRequestDTO, ServiceCharge, ServiceCharge> deleteServiceCharge = (deleteRequestDTO,
                                                                                                    serviceCharge) -> {

        serviceCharge.setStatus(deleteRequestDTO.getStatus());
        serviceCharge.setRemarks(deleteRequestDTO.getRemarks());

        return serviceCharge;
    };

    public static ServiceCharge update(ServiceChargeUpdateRequestDTO requestDTO,
                                       ServiceCharge serviceChargeToUpdate,
                                       Service service,
                                       List<BillingMode> billingMode) {

        serviceChargeToUpdate.setService(service);
        serviceChargeToUpdate.setStatus(requestDTO.getStatus());
        serviceChargeToUpdate.setPrice(requestDTO.getPrice());
        serviceChargeToUpdate.setRemarks(requestDTO.getRemarks());
        serviceChargeToUpdate.setBillingModes(billingMode);
        return serviceChargeToUpdate;
    }
}
