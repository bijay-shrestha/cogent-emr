package com.cogent.admin.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 10/24/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySearchRequestDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;
}
