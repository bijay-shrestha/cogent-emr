package com.cogent.admin.dto.paymentmode;

import com.cogent.admin.dto.commons.DropDownResponseDTO;

import java.util.Arrays;
import java.util.List;

/**
 * @author Sauravi Thapa 10/13/19
 */
public class PaymentTypeTestUtils {
    public static List<DropDownResponseDTO> getPaymentTypeDropDownInfo() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("Unit-1")
                .build());

    }

}
