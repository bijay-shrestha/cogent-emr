package com.cogent.admin.dto.billType;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.BillType;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 2019-10-22
 */
public class BillTypeResponseUtils {

    public static List<DropDownResponseDTO> fetchActiveBillTypes() {
        return Arrays.asList(
                DropDownResponseDTO.builder()
                        .value(1L)
                        .label("PAYABLE")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("NON-PAYABLE")
                        .build()
        );
    }

    public static BillType getBillType() {
        BillType billType = new BillType();
        billType.setId(1L);
        billType.setName("PAYABLE");
        billType.setStatus('Y');
        return billType;
    }
}
