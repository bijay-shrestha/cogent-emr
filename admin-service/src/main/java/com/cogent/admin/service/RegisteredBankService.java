package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankUpdateRequestDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankMinimalResponseDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sauravi Thapa 12/10/19
 */
public interface RegisteredBankService {
    void createRegisteredBank(RegisteredBankRequestDTO requestDTO);

    void deleteRegisteredBank(DeleteRequestDTO deleteRequestDTO);

    void updateRegisteredBank(RegisteredBankUpdateRequestDTO updateRequestDTO);

    List<RegisteredBankMinimalResponseDTO> searchRegisteredBank(
            RegisteredBankSearchRequestDTO searchRequestDTO,
            Pageable pageable);

    RegisteredBankResponseDTO fetchRegisteredBankDetails(Long id);

    List<DropDownResponseDTO> fetchActiveDropDownList();

    List<DropDownResponseDTO> fetchDropDownList();
}
