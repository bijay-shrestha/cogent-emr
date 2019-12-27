package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeUpdateRequestDTO;
import com.cogent.persistence.model.BillingMode;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Sauravi Thapa 11/1/19
 */
public class BillingModeUtils {
    public static Function<BillingModeRequestDTO, BillingMode> parseToBillingMode = (requestDTO) -> {
        BillingMode billingMode = new BillingMode();
        billingMode.setName(toUpperCase(requestDTO.getName()));
        billingMode.setCode(toUpperCase(requestDTO.getCode()));
        billingMode.setStatus(requestDTO.getStatus());
        billingMode.setDescription(requestDTO.getDescription());
        return billingMode;
    };

    public static BiFunction<BillingMode, DeleteRequestDTO, BillingMode> deleteBillingMode =
            (billingModeToDelete, deleteRequestDTO) -> {

                billingModeToDelete.setStatus(deleteRequestDTO.getStatus());
                billingModeToDelete.setRemarks(deleteRequestDTO.getRemarks());

                return billingModeToDelete;
            };


    public static BiFunction<BillingMode, BillingModeUpdateRequestDTO, BillingMode> updateBillingMode =
            (billingModeToUpdate, updateRequestDTO) -> {

                billingModeToUpdate.setName(toUpperCase(updateRequestDTO.getName()));
                billingModeToUpdate.setCode(updateRequestDTO.getCode());
                billingModeToUpdate.setStatus(updateRequestDTO.getStatus());
                billingModeToUpdate.setDescription(updateRequestDTO.getDescription());
                billingModeToUpdate.setRemarks(updateRequestDTO.getRemarks());

                return billingModeToUpdate;
            };


}
