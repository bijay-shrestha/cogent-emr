package com.cogent.admin.dto.qualification;

import com.cogent.admin.dto.response.qualification.QualificationDropdownDTO;
import com.cogent.admin.dto.response.qualification.QualificationMinimalResponseDTO;
import com.cogent.admin.dto.response.qualification.QualificationResponseDTO;
import com.cogent.persistence.model.Qualification;

import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.country.CountryResponseUtils.fetchCountry;
import static com.cogent.admin.dto.qualificationAlias.QualificationAliasResponseUtils.fetchQualificationAlias;

/**
 * @author smriti on 11/11/2019
 */
public class QualificationResponseUtils {

    public static Qualification fetchQualification() {
        return new Qualification(1L, "Masters", "Tribhuwan University",
                fetchCountry(), fetchQualificationAlias(), 'Y', null);
    }

    public static Qualification fetchUpdatedQualification() {
        return new Qualification(1L, "Masters", "Tribhuwan University",
                fetchCountry(), fetchQualificationAlias(), 'N', "inactive it");
    }

    public static Qualification fetchDeletedQualificationInfo() {
        return new Qualification(1L, "Masters", "Tribhuwan University",
                fetchCountry(), fetchQualificationAlias(), 'D', "delete it");
    }

    public static List<QualificationMinimalResponseDTO> fetchMinimalQualificationList() {
        return Arrays.asList(QualificationMinimalResponseDTO.builder()
                        .id(1L)
                        .name("Masters in Therapy")
                        .university("Tribhuwan University")
                        .countryName("Nepal")
                        .qualificationAliasName("M.D")
                        .status('Y')
                        .build(),
                QualificationMinimalResponseDTO.builder()
                        .id(2L)
                        .name("Masters in Orthopedics")
                        .university("Pokhara University")
                        .countryName("Nepal")
                        .qualificationAliasName("M.D")
                        .status('Y')
                        .build()
        );
    }

    public static List<QualificationDropdownDTO> fetchQualificationListForDropDown() {
        return Arrays.asList(QualificationDropdownDTO.builder()
                        .id(1L)
                        .name("Masters in Therapy")
                        .university("Tribhuwan University")
                        .countryName("Nepal")
                        .qualificationAliasName("M.D")
                        .build(),
                QualificationDropdownDTO.builder()
                        .id(2L)
                        .name("Masters in Orthopedics")
                        .university("Pokhara University")
                        .countryName("Nepal")
                        .qualificationAliasName("M.D")
                        .build()
        );
    }

    public static QualificationResponseDTO fetchQualificationDetailById() {
        return QualificationResponseDTO.builder()
                .name("Masters in Orthopedics")
                .university("Pokhara University")
                .countryId(1L)
                .countryName("Nepal")
                .qualificationAliasId(1L)
                .qualificationAliasName("M.D")
                .status('Y')
                .remarks(null).build();
    }

}
