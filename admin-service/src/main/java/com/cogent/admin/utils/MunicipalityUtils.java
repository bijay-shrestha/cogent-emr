package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityRequestDTO;
import com.cogent.admin.dto.request.municipality.MunicipalityUpdateRequestDTO;
import com.cogent.persistence.model.District;
import com.cogent.persistence.model.Municipality;
import com.cogent.persistence.model.Provinces;

import java.util.List;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author smriti on 2019-09-15
 */
public class MunicipalityUtils {

    public static Municipality convertDTOToMunicipality(MunicipalityRequestDTO requestDTO,
                                                        District district) {

        Municipality municipality = new Municipality();
        municipality.setName(toUpperCase(requestDTO.getName()));
        municipality.setStatus(requestDTO.getStatus());
        municipality.setDistrict(district);
        return municipality;
    }

    public static Municipality convertToUpdatedMunicipality(MunicipalityUpdateRequestDTO updateRequestDTO,
                                                            Municipality municipality,
                                                            District district) {

        municipality.setName(toUpperCase(updateRequestDTO.getName()));
        municipality.setStatus(updateRequestDTO.getStatus());
        municipality.setRemarks(updateRequestDTO.getRemarks());
        municipality.setDistrict(district);
        return municipality;
    }

    public static Municipality convertToDeletedMunicipality(Municipality municipality,
                                                            DeleteRequestDTO deleteRequestDTO) {

        municipality.setStatus(deleteRequestDTO.getStatus());
        municipality.setRemarks(deleteRequestDTO.getRemarks());
        return municipality;
    }

    public static Municipality parseObjectToMunicipality(List<Object[]> objects) {

        final Integer ID=0;
        final Integer NAME=1;
        final Integer REMARKS=2;
        final Integer STATUS=3;
        final Integer DISTRICT=4;
        final Integer PROVINCES=5;

        Municipality municipality = new Municipality();
        for (Object[] object : objects) {
            municipality.setId(Long.parseLong(object[ID].toString()));
            municipality.setName(object[NAME].toString());
            municipality.setRemarks((object[REMARKS] == null) ? null : object[REMARKS].toString());
            municipality.setStatus(object[STATUS].toString().charAt(0));
            municipality.setDistrict((District) object[DISTRICT]);
            municipality.getDistrict().setProvinces((Provinces) object[PROVINCES]);
        }
        return municipality;
    }
}
