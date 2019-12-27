package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;

import java.util.List;

/**
 * @author Sauravi Thapa 12/11/19
 */
public interface PaymentTypeService {

    List<DropDownResponseDTO> fetchActiveDropDownList();

    List<DropDownResponseDTO> fetchDropDownList();
}
