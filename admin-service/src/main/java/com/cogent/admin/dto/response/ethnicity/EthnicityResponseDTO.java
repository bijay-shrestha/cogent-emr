package com.cogent.admin.dto.response.ethnicity;

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
public class EthnicityResponseDTO implements Serializable {

    private String name;

    private String code;

    private Character status;

    private String remarks;

    private Date createdDate;

    private Date lastModifiedDate;

    private Long createdById;

    private Long modifiedById;
}
