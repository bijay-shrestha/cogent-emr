package com.cogent.admin.dto.servicecharge;



import com.cogent.admin.dto.request.servicecharge.ServiceChargeRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeUpdateRequestDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeMinimalResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeResponseDTO;
import com.cogent.persistence.model.ServiceCharge;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.service.ServiceTestUtils.getServiceInfo;

/**
 * @author Sauravi Thapa 11/13/19
 */
public class ServiceChargeTestUtils {
    public static ServiceChargeRequestDTO getServiceChargeRequestDTO() {
        return ServiceChargeRequestDTO.builder()
                .serviceId(1L)
                .price(Double.valueOf(150))
                .status('Y')
                .billingModeId(Arrays.asList(1L))
                .build();
    }


    public static ServiceChargeUpdateRequestDTO getServiceChargeUpdateRequestDTO() {
        return ServiceChargeUpdateRequestDTO.builder()
                .id(1L)
                .serviceId(1L)
                .billingModeId(Arrays.asList(1L))
                .price(Double.valueOf(150))
                .status('Y')
                .remarks("Update")
                .build();
    }

    public static ServiceCharge getServiceChargeInfoToUpdate() {
        ServiceCharge serviceCharge = new ServiceCharge();
        serviceCharge.setId(1L);
        serviceCharge.setService(getServiceInfo());
        serviceCharge.setPrice(Double.valueOf(150));
        serviceCharge.setStatus('Y');
        serviceCharge.setRemarks("Update");

        return serviceCharge;
    }

    public static ServiceCharge getServiceChargeInfo() {
        ServiceCharge serviceCharge = new ServiceCharge();
        serviceCharge.setId(1L);
        serviceCharge.setService(getServiceInfo());
        serviceCharge.setPrice(Double.valueOf(150));
        serviceCharge.setStatus('Y');

        return serviceCharge;
    }

    public static ServiceCharge getServiceChargeInfoToDelete() {
        ServiceCharge serviceCharge = new ServiceCharge();
        serviceCharge.setId(1L);
        serviceCharge.setService(getServiceInfo());
        serviceCharge.setPrice(Double.valueOf(150));
        serviceCharge.setStatus('D');
        serviceCharge.setRemarks("delete service");

        return serviceCharge;
    }

    public static ServiceChargeSearchRequestDTO getSearchRequestDTO() {
        return ServiceChargeSearchRequestDTO.builder()
                .serviceId(1L)
                .departmentId(1L)
                .subDepartmentId(1L)
                .serviceTypeId(1L)
                .status('Y')
                .build();
    }

    public static ServiceChargeMinimalResponseDTO getMinimalResponseDTO() {
        return ServiceChargeMinimalResponseDTO.builder()
                .name("TEST")
                .code("TST")
                .status('Y')
                .build();
    }

    public static List<ServiceChargeMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getMinimalResponseDTO());
    }


    public static ServiceChargeResponseDTO getServiceChargeResponseDTO() {
        return ServiceChargeResponseDTO.builder()
                .remarks("Test")
                .build();
    }


    public static ServiceChargeDropDownResponseDTO dropDownResponseDTO() {
        return ServiceChargeDropDownResponseDTO.builder()
                .value(BigInteger.valueOf(1))
                .label("Test")
                .price(100D)
                .billingMode("TEST")
                .build();
    }

    public static List<ServiceChargeDropDownResponseDTO> getDropDownResponseDTOS() {
        return Arrays.asList(dropDownResponseDTO());
    }

}
