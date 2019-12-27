package com.cogent.admin.dto.response.nationality;

import lombok.*;

import java.io.Serializable;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NationalityMinimalResponseDTO implements Serializable {

    private Long id;

    private String name;

    private Character status;

    private Integer totalItems;
}
