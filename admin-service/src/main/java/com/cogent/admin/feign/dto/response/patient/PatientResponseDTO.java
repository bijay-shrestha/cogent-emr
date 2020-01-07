package com.cogent.admin.feign.dto.response.patient;

import com.cogent.persistence.model.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sauravi Thapa 9/29/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class PatientResponseDTO implements Serializable {

    private Nationality nationality;

    private Municipality municipality;

    private Surname surname;

    private  Religion religion;

    private MaritalStatus maritalStatus;

    private Title title;

    private Category category;
}
