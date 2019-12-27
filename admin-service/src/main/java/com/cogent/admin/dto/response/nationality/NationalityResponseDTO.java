package com.cogent.admin.dto.response.nationality;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sauravi
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NationalityResponseDTO implements Serializable {

    private String name;

    private Character status;

    private String remarks;

}
