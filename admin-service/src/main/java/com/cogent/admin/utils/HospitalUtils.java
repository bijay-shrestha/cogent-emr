package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalUpdateRequestDTO;
import com.cogent.persistence.model.Hospital;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Rupak
 */
public class HospitalUtils {

    public static Hospital convertDTOToHospital(HospitalRequestDTO hospitalRequestDTO) {
        hospitalRequestDTO.setName(toUpperCase(hospitalRequestDTO.getName()));
        return MapperUtility.map(hospitalRequestDTO, Hospital.class);
    }

    public static Hospital convertToUpdatedHospital(HospitalUpdateRequestDTO updateRequestDTO,
                                                    Hospital hospital) {

        hospital.setName(toUpperCase(updateRequestDTO.getName()));
        hospital.setStatus(updateRequestDTO.getStatus());
        hospital.setHospitalAddress(updateRequestDTO.getHospitalAddress());
        hospital.setRemarks(updateRequestDTO.getRemarks());
        hospital.setCode(updateRequestDTO.getHospitalCode());
        hospital.setHospitalPanNumber(updateRequestDTO.getHospitalPanNumber());
        hospital.setHospitalLogo(updateRequestDTO.getHospitalLogo());
        hospital.setHospitalPhone(updateRequestDTO.getHospitalPhone());


        return hospital;
    }

    public static Hospital convertToDeletedHospital(Hospital hospital, DeleteRequestDTO deleteRequestDTO) {
        hospital.setStatus(deleteRequestDTO.getStatus());
        hospital.setRemarks(deleteRequestDTO.getRemarks());
        return hospital;

    }

}
