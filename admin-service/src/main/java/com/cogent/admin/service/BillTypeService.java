package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.BillType;

import java.util.List;

/**
 * @author smriti on 2019-10-22
 */
public interface BillTypeService {

    List<DropDownResponseDTO> fetchActiveBillType();

    BillType fetchBillTypeById(Long id);
}
