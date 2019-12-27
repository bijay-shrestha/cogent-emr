package com.cogent.admin.dto.response.category;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 10/24/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CategoryMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Character status;

    private Integer totalItems;
}
