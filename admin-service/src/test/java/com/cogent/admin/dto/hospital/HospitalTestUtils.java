package com.cogent.admin.dto.hospital;

import com.cogent.admin.dto.request.hospital.HospitalRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalSearchRequestDTO;
import com.cogent.admin.dto.request.hospital.HospitalUpdateRequestDTO;

/**
 * @author Rupak
 */
public class HospitalTestUtils {

    public static HospitalRequestDTO getInvalidInputForSave() {
        return HospitalRequestDTO.builder()
                .name("@")
                .status('M')
                .code("NID-00#$$#!@#@!1")
                .hospitalAddress("#@#$$$$")
                .hospitalPanNumber("qwqwqw000")
                .hospitalPhone("112323233$$")
                .build();
    }

    public static HospitalRequestDTO getHospitalRequestDTO() {
        return HospitalRequestDTO.builder()
                .name("Nidan Hospital")
                .hospitalAddress("Pulchowk-32, Kathmandu")
                .code("NID-001")
                .hospitalPanNumber("123456789101112")
                .hospitalPhone("0111223442")
                .hospitalLogo("image.jpeg")
                .status('Y')
                .remarks(null)
                .build();
    }


    public static HospitalUpdateRequestDTO getInvalidInputForUpdate() {
        return HospitalUpdateRequestDTO.builder()
                .id(null)
                .name("")
                .status('M')
                .hospitalAddress("")
                .hospitalCode("NID-001")
                .hospitalPanNumber("")
                .hospitalPhone("")
                .build();
    }

    public static HospitalUpdateRequestDTO getHospitalUpdateRequestDTO() {
        return HospitalUpdateRequestDTO.builder()
                .id(1L)
                .name("Nidan New Hospital")
                .status('N')
                .hospitalAddress("Pulchowk-32, Kathmandu")
                .hospitalCode("NID-001")
                .hospitalPanNumber("123456789101112")
                .hospitalPhone("0111111111")
                .remarks("Ok. Inactive it!")
                .hospitalLogo("Logo")
                .build();
    }

    public static HospitalSearchRequestDTO getHospitalSearchRequestDTO() {
        return HospitalSearchRequestDTO.builder()
                .name("Nidan Hospital")
                .status('Y')
                .build();
    }

}
