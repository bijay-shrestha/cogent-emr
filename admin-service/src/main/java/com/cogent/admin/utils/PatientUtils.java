package com.cogent.admin.utils;

import com.cogent.admin.dto.response.patient.ResponseDTO;
import com.cogent.persistence.model.*;

/**
 * @author Sauravi Thapa 9/23/19
 */
public class PatientUtils {

    public static ResponseDTO parseToPatientResponseDTO(Nationality nationality,
                                                        Municipality municipality,
                                                        Surname surname,
                                                        Religion religion,
                                                        MaritalStatus maritalStatus,
                                                        Title title,
                                                        Category category) {
        return ResponseDTO
                .builder()
                .nationality(nationality)
                .municipality(municipality)
                .surname(surname)
                .religion(religion)
                .maritalStatus(maritalStatus)
                .title(title)
                .category(category)
                .build();

    }
}
