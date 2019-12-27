package com.cogent.admin.dto.ethnicity;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import com.cogent.admin.dto.request.ethnicity.EthnicityUpdateRequestDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityDropDownResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityMinimalResponseDTO;
import com.cogent.admin.dto.response.ethnicity.EthnicityResponseDTO;
import com.cogent.persistence.model.Ethnicity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sauravi on 2019-08-25
 */

public class EthnicityTestUtils {
    public static EthnicityRequestDTO getEthnicityRequestDTO() {
        return EthnicityRequestDTO.builder()
                .name("Chhetri")
                .code("CHH")
                .status('Y')
                .build();
    }

    public static EthnicityUpdateRequestDTO getUpdateEthnicityRequestDTO() {
        return EthnicityUpdateRequestDTO.builder()
                .id(1L)
                .name("Chhetri")
                .code("CHH")
                .status('Y')
                .remarks("I want to update re")
                .build();
    }

    public static EthnicityUpdateRequestDTO getUpdateEthnicityRequestDTOForBadRequest() {
        return EthnicityUpdateRequestDTO.builder()
                .id(1L)
                .name("Chhetri")
                .code("CHH")
                .status('D')
                .remarks("I want to update re")
                .build();
    }

    public static EthnicitySearchRequestDTO getEthnicitySeacrhRequestDTO() {
        return EthnicitySearchRequestDTO.builder()
                .id(1L)
                .name("Chhetri")
                .code("CHH")
                .status('Y')
                .build();
    }

    public static List<Object[]> getEthnicityObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Chhetri";
        object[1] = "CHH";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getEthnicityObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "CHH";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getEthnicityObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Chhetri";
        objects.add(object);
        return objects;
    }


    public static EthnicityMinimalResponseDTO getEthnicityMinimalResponseDTO() {
        return EthnicityMinimalResponseDTO.builder()
                .id(1L)
                .name("Chhetri")
                .code("CHH")
                .status('Y')
                .build();
    }

    public static EthnicityResponseDTO getEthnicityResponseDTO() {
        return EthnicityResponseDTO.builder()
                .remarks("test")
                .build();
    }

    public static Ethnicity getEthnicityInfo() {
        Ethnicity ethnicity = new Ethnicity();
        ethnicity.setId(1L);
        ethnicity.setName("Chhetri");
        ethnicity.setCode("CHH");
        ethnicity.setStatus('Y');

        return ethnicity;
    }

    public static List<EthnicityDropDownResponseDTO> getDropDownInfo() {
        return Arrays.asList(EthnicityDropDownResponseDTO
                .builder()
                .value(1L)
                .label("Chhteri")
                .build());

    }

    public static List<EthnicityMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getEthnicityMinimalResponseDTO());
    }

    public static DeleteRequestDTO getDeleteReuqestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("deleteDrug").build();
    }
}
