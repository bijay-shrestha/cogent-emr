package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sauravi Thapa 12/11/19
 */

@Repository
@Qualifier("paymentTypeRepositoryCustom")
public interface PaymentTypeRepositoryCustom {

    Optional<List<DropDownResponseDTO>> dropDownList();

    Optional<List<DropDownResponseDTO>> activeDropDownList();
}
