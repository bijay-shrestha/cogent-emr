package com.cogent.admin.dto.request.servicecharge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/13/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceChargeSearchRequestDTO implements Serializable {

    private String serviceName;

    private String serviceCode;

    private Long serviceId;

    private Character status;

    private Long departmentId;

    private Long subDepartmentId;

    private Long serviceTypeId;

    private Long billModeId;

    private Double price;

}
