package com.cogent.admin.dto.request.servicecharge;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
public class ServiceChargeUpdateRequestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Long serviceId;

    @NotNull
    @Status
    private Character status;

    @NotNull
    private Double price;

    @NotNull
    private List<Long> billingModeId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String remarks;


}
