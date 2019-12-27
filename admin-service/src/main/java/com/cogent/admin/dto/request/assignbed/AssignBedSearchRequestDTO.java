package com.cogent.admin.dto.request.assignbed;

import com.cogent.admin.constraintvalidator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/6/19
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignBedSearchRequestDTO implements Serializable {


    private Long id;


    private Long wardId;


    private Long unitId;


    private Long bedId;


    private Character status;

}
