package com.cogent.admin.dto.response.referrer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Rupak
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferrerResponseDTO implements Serializable {

    private Long id;

    private String name;

    private Character status;

    private String remarks;

    private int totalItems;
}
