package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeUpdateRequestDTO;
import com.cogent.persistence.model.BillingMode;
import com.cogent.persistence.model.Service;
import com.cogent.persistence.model.ServiceCharge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Sauravi Thapa 11/19/19
 */
public class ServiceChargeUtils {

    private static final Integer ID = 0;
    private static final Integer PRICE = 1;
    private static final Integer STATUS = 3;
    private static final Integer REMARKS = 4;


    public static ServiceCharge parseToServiceCharge(ServiceChargeRequestDTO requestDTO,
                                                     Service service,
                                                     List<BillingMode> billingMode) {
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

    public static List<ServiceCharge> parseObjectToServiceCharge(List<Object[]> objects,List<Service> services) {

        List<ServiceCharge> serviceCharges=new ArrayList<>();
       ServiceCharge serviceCharge=new ServiceCharge();
        for (Object[] object : objects) {
            services.forEach(service -> {
                serviceCharge.setId(Long.valueOf(object[ID].toString()));
                serviceCharge.setPrice(Double.valueOf(object[PRICE].toString()));
                serviceCharge.setStatus(object[STATUS].toString().charAt(0));
                serviceCharge.setRemarks((object[REMARKS] == null) ? null : object[REMARKS].toString());
                serviceCharge.setService(service);
                serviceCharges.add(serviceCharge);
            });

        }

        return serviceCharges;
    }

}
