package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameRequestDTO;
import com.cogent.admin.dto.request.surname.SurnameUpdateRequestDTO;
import com.cogent.persistence.model.Ethnicity;
import com.cogent.persistence.model.Surname;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-09-12
 */
public class SurnameUtils {

    public static Surname convertDTOToSurname(SurnameRequestDTO surnameRequestDTO,
                                              Ethnicity ethnicity) {
        Surname surname = new Surname();
        surname.setName(toUpperCase(surnameRequestDTO.getName()));
        surname.setStatus(surnameRequestDTO.getStatus());
        surname.setEthnicity(ethnicity);
        return surname;
    }

    public static Surname convertToUpdatedSurname(SurnameUpdateRequestDTO updateRequestDTO,
                                                  Surname surname,
                                                  Ethnicity ethnicity) {

        surname.setName(toUpperCase(updateRequestDTO.getName()));
        surname.setStatus(updateRequestDTO.getStatus());
        surname.setRemarks(updateRequestDTO.getRemarks());
        surname.setEthnicity(ethnicity);
        return surname;
    }

    public static Surname convertToDeletedSurname(Surname surname,
                                                  DeleteRequestDTO deleteRequestDTO) {

        surname.setStatus(deleteRequestDTO.getStatus());
        surname.setRemarks(deleteRequestDTO.getRemarks());
        return surname;
    }
}
