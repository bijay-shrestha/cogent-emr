package com.cogent.admin.dto.hospital;
import com.cogent.admin.dto.response.hospital.HospitalDropDownResponseDTO;
import com.cogent.admin.dto.response.hospital.HospitalResponseDTO;
import com.cogent.persistence.model.Hospital;
import java.util.Arrays;
import java.util.List;

public class HospitalResponseUtils {


    public static Hospital getHospital() {
        return new Hospital(1l,"Nidan Hospital","NID-001","Pulchowk-32, Kathmandu","123456789101112","0111223442","image.jpeg",'Y',null);
    }

    public static Hospital getDeletedHospital() {
        return new Hospital(1l,"Nidan Hospital","NID-01","Pulchowk Kathmandu","0012122123243345345445","0111223442","image.jpeg",'D',"Yes, Delete it!!");
    }

    public static List<HospitalResponseDTO> fetchHospitalResponseDTO() {
        return Arrays.asList(HospitalResponseDTO.builder()
                        .id(1L)
                        .name("Nidan Hospital")
                        .status('Y')
                        .build(),
                HospitalResponseDTO.builder()
                        .id(2L)
                        .name("Nidan Hospital")
                        .status('Y')
                        .build()
        );
    }

    public static List<HospitalDropDownResponseDTO> fetchHospitalForDropDown() {

        return Arrays.asList(HospitalDropDownResponseDTO.builder()
                        .value(1L)
                        .label("Nidan Hospital")
                        .build(),
                HospitalDropDownResponseDTO.builder()
                        .value(2L)
                        .label("Mediciti Hospital")
                        .build()
        );
    }

}
