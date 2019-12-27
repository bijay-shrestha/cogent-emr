package com.cogent.admin.dto.request.servicecharge;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sauravi Thapa 11/13/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceChargeRequestDTO implements Serializable {

    @NotNull
    private Long serviceId;

    @NotNull
    @Status
    private Character status;

    private Double price;

    private List<Long> billingModeId;
}
