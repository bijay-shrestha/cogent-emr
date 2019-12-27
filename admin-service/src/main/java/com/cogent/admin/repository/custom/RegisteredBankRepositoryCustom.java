package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankMinimalResponseDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 12/10/19
 */
@Repository
@Qualifier("registeredBankRepositoryCustom")
public interface RegisteredBankRepositoryCustom {
    List<Object[]> findRegisteredBankByNameOrCode(String name, String code);

    List<RegisteredBankMinimalResponseDTO> searchRegisteredBank(
            RegisteredBankSearchRequestDTO searchRequestDTO, Pageable pageable);

    Optional<List<DropDownResponseDTO>> dropDownList();

    Optional<List<DropDownResponseDTO>> activeDropDownList();

    RegisteredBankResponseDTO fetchRegisteredBankDetails(Long id);

    List<Object[]> checkIfRegisteredBankNameAndCodeExists(Long id, String name, String code);
}
