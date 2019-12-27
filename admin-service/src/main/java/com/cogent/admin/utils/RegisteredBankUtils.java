package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankUpdateRequestDTO;
import com.cogent.persistence.model.RegisteredBank;

import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Sauravi Thapa 12/10/19
 */
public class RegisteredBankUtils {

    public static RegisteredBank parseToRegisteredBank(RegisteredBankRequestDTO requestDTO) {
        requestDTO.setName(toUpperCase(requestDTO.getName()));
        requestDTO.setCode(toUpperCase(requestDTO.getCode()));
        return MapperUtility.map(requestDTO, RegisteredBank.class);
    }

    public static BiFunction<RegisteredBank, DeleteRequestDTO, RegisteredBank> deleteRegisteredBank =
            (unitToDelete, deleteRequestDTO) -> {
        unitToDelete.setStatus(deleteRequestDTO.getStatus());
        unitToDelete.setRemarks(deleteRequestDTO.getRemarks());

        return unitToDelete;
    };

    public static BiFunction<RegisteredBank, RegisteredBankUpdateRequestDTO, RegisteredBank> updateRegisteredBank =
            (registeredBankToUpdate, updateRequestDTO) -> {
        registeredBankToUpdate.setName(updateRequestDTO.getName());
        registeredBankToUpdate.setCode(updateRequestDTO.getCode());
        registeredBankToUpdate.setAddress(updateRequestDTO.getAddress());
        registeredBankToUpdate.setContact(updateRequestDTO.getContact());
        registeredBankToUpdate.setSwiftCode(updateRequestDTO.getSwiftCode());
        registeredBankToUpdate.setStatus(updateRequestDTO.getStatus());
        registeredBankToUpdate.setRemarks(updateRequestDTO.getRemarks());

        return registeredBankToUpdate;
    };


}
