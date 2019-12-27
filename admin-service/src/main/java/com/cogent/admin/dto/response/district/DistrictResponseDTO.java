package com.cogent.admin.dto.response.district;

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
public class DistrictResponseDTO implements Serializable {

    private Long id;

    private String name;

    private Character status;

    private String province;

    private String remarks;

    private Date createdDate;

    private Date lastModifiedDate;

    private Long createdById;

    private Long modifiedById;
}
