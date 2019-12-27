package com.cogent.admin.dto.qualificationAlias;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.QualificationAlias;

import java.util.Arrays;
import java.util.List;

/**
 * @author smriti on 11/11/2019
 */
public class QualificationAliasResponseUtils {
    public static List<DropDownResponseDTO> fetchActiveQualificationAlias() {
        return Arrays.asList(
                DropDownResponseDTO.builder()
                        .value(1L)
                        .label("M.D")
                        .build(),
                DropDownResponseDTO.builder()
                        .value(2L)
                        .label("M.B.B.S")
                        .build());
    }

    public static QualificationAlias fetchQualificationAlias() {
        return new QualificationAlias(1L, "M.D", 'Y');
    }
}
