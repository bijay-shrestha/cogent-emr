package com.cogent.admin.dto.response.admin;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author smriti on 2019-08-29
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDetailResponseDTO implements Serializable {
    private AdminResponseDTO adminResponseDTO;

    private List<MacAddressInfoResponseDTO> macAddressInfoResponseDTOS;
}
