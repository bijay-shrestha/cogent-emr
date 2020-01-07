package com.cogent.admin.dto.qualification;

import com.cogent.admin.dto.request.qualification.QualificationRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationSearchRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationUpdateRequestDTO;

/**
 * @author smriti on 11/11/2019
 */
public class QualificationRequestUtils {

    public static QualificationRequestDTO getQualificationRequestDTO() {
        return QualificationRequestDTO.builder()
                .name("Masters")
                .university("Tribhuwan University")
                .countryId(1L)
                .qualificationAliasId(1L)
                .build();
    }

    public static QualificationUpdateRequestDTO getQualificationUpdateRequestDTO() {
        return QualificationUpdateRequestDTO.builder()
                .id(1L)
                .name("Masters")
                .university("Tribhuwan University")
                .countryId(1L)
                .qualificationAliasId(1L)
                .status('N')
                .remarks("yes. Inactive it!")
                .build();
    }

    public static QualificationSearchRequestDTO getQualificationSearchRequestDTO() {
        return QualificationSearchRequestDTO.builder()
                .name("Masters")
                .university("Tribhuwan University")
                .build();
    }
}
