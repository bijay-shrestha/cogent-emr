package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationRequestDTO;
import com.cogent.admin.dto.request.qualification.QualificationUpdateRequestDTO;
import com.cogent.persistence.model.Country;
import com.cogent.persistence.model.Qualification;
import com.cogent.persistence.model.QualificationAlias;

import static com.cogent.admin.constants.StatusConstants.ACTIVE;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 11/11/2019
 */
public class QualificationUtils {

    public static Qualification convertToQualification(QualificationRequestDTO requestDTO,
                                                       Country country,
                                                       QualificationAlias qualificationAlias) {
        Qualification qualification = new Qualification();
        qualification.setName(toUpperCase(requestDTO.getName()));
        qualification.setUniversity(requestDTO.getUniversity());
        qualification.setStatus(ACTIVE);
        parseToQualification(qualification, country, qualificationAlias);
        return qualification;
    }

    private static void parseToQualification(Qualification qualification,
                                             Country country,
                                             QualificationAlias qualificationAlias) {
        qualification.setCountry(country);
        qualification.setQualificationAlias(qualificationAlias);
    }

    public static void convertToUpdatedQualification(QualificationUpdateRequestDTO requestDTO,
                                                     Country country,
                                                     QualificationAlias qualificationAlias,
                                                     Qualification qualification) {
        qualification.setName(toUpperCase(requestDTO.getName()));
        qualification.setUniversity(requestDTO.getUniversity());
        qualification.setStatus(requestDTO.getStatus());
        qualification.setRemarks(requestDTO.getRemarks());
        parseToQualification(qualification, country, qualificationAlias);
    }

    public static void convertToDeletedQualification(Qualification qualification,
                                                     DeleteRequestDTO deleteRequestDTO) {
        qualification.setStatus(deleteRequestDTO.getStatus());
        qualification.setRemarks(deleteRequestDTO.getRemarks());
    }


}
