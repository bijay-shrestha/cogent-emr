package com.cogent.admin.dto.bed;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.bed.BedRequestDTO;
import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import com.cogent.admin.dto.request.bed.BedUpdateRequestDTO;
import com.cogent.admin.dto.response.bed.BedMinimalResponseDTO;
import com.cogent.admin.dto.response.bed.BedResponseDTO;
import com.cogent.persistence.model.Bed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sauravi Thapa 11/4/19
 */
public class BedTestUtils {

    public static BedRequestDTO getBedRequestDTO() {
        return BedRequestDTO.builder()
                .name("Bed-1")
                .code("B-1")
                .status('Y')
                .build();
    }

    public static List<Object[]> getBedObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Bed-1";
        object[1] = "B-1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getBedObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "B-1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getBedObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Bed-1";
        objects.add(object);
        return objects;
    }

    public static Bed getBedInfo() {
        Bed bed = new Bed();
        bed.setId(1L);
        bed.setName("BED-1");
        bed.setCode("B-1");
        bed.setStatus('Y');

        return bed;
    }

    public static Bed getDeletedBedInfo() {
        Bed bed = new Bed();
        bed.setId(1L);
        bed.setName("BED-1");
        bed.setCode("B-1");
        bed.setStatus('D');
        bed.setRemarks("Yes.. Delete it");

        return bed;
    }

    public static BedSearchRequestDTO getSearchBedRequestDTO() {
        return BedSearchRequestDTO.builder()
                .id(1L)
                .name("BED-1")
                .code("B-1")
                .status('Y')
                .build();
    }

    public static List<BedMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(BedMinimalResponseDTO
                .builder()
                .name("BED-1")
                .code("B-1")
                .status('Y')
                .build());
    }

    public static List<DropDownResponseDTO> getDropDownInfo() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("Ward-2")
                .build());

    }

    public static BedResponseDTO getBedResponseDTO() {
        return BedResponseDTO.builder()
                .name("BED-1")
                .code("B-1")
                .status('Y')
                .remarks("test")
                .build();
    }

    public static BedUpdateRequestDTO getUpdateBedRequestDTO() {
        return BedUpdateRequestDTO.builder()
                .id(1L)
                .name("BED-1")
                .code("B-1")
                .status('Y')
                .remarks("I want to update re")
                .build();
    }

    public static Bed getUpdatedBedInfo() {
        Bed bed = new Bed();
        bed.setId(1L);
        bed.setName("BED-1");
        bed.setCode("B-1");
        bed.setStatus('Y');
        bed.setRemarks("I want to update re");

        return bed;
    }

}
