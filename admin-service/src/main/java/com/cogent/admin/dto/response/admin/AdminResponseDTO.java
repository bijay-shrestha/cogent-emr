package com.cogent.admin.dto.response.admin;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseDTO implements Serializable {

    private Long id;

    private String fullName;

    private String username;

    private String mobileNumber;

    private String email;

    private Character status;

    private Long adminCategoryId;

    private String adminCategoryName;

    private Character hasMacBinding;

    private String fileUri;

    private Character isDefaultImage;

    private String remarks;

    private Long hospitalId;

    private String hospitalName;

    private List<AdminProfileResponseDTO> adminProfileResponseDTOS;

    private List<AdminApplicationModuleResponseDTO> adminApplicationModuleResponseDTOS;
}
