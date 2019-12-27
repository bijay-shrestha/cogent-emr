package com.cogent.admin.dto.billingmode;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import com.cogent.admin.dto.request.billingmode.BillingModeUpdateRequestDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeMinimalResponseDTO;
import com.cogent.admin.dto.response.billingmode.BillingModeResponseDTO;
import com.cogent.persistence.model.BillingMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sauravi Thapa 11/4/19
 */
public class BillingModeTestUtils {

    public static BillingModeRequestDTO getBillingModeRequestDTO() {
        return BillingModeRequestDTO.builder()
                .name("BillingMode-1")
                .code("B-1")
                .status('Y')
                .description("test billing mode")
                .build();
    }

    public static BillingMode getBillingModeInfo() {
        BillingMode billingMode = new BillingMode();
        billingMode.setId(1L);
        billingMode.setName("BillingMode-1");
        billingMode.setCode("B-1");
        billingMode.setStatus('Y');
        billingMode.setDescription("test billing mode");

        return billingMode;
    }

    public static List<BillingMode> getBillingModes(){
        return Arrays.asList(getBillingModeInfo());
    }

    public static BillingModeUpdateRequestDTO getUpdateBillingModeRequestDTO() {
        return BillingModeUpdateRequestDTO.builder()
                .id(1L)
                .name("BillingMode-2")
                .code("B-2")
                .status('Y')
                .description("test billing mode")
                .remarks("I want to update re")
                .build();
    }

    public static BillingMode getUpdatedBillingModeInfo() {
        BillingMode billingMode = new BillingMode();
        billingMode.setId(1L);
        billingMode.setName("BillingMode-2");
        billingMode.setCode("B-2");
        billingMode.setStatus('Y');
        billingMode.setRemarks("I want to update re");
        billingMode.setDescription("test billing mode");

        return billingMode;
    }

    public static BillingMode getDeletedBillingModeInfo() {
        BillingMode billingMode = new BillingMode();
        billingMode.setId(1L);
        billingMode.setName("BillingMode-1");
        billingMode.setCode("B-1");
        billingMode.setStatus('D');
        billingMode.setDescription("test billing mode");
        billingMode.setRemarks("Yes.. Delete it");

        return billingMode;
    }

    public static List<Object[]> getBillingModeObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "BillingMode-1";
        object[1] = "B-1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getBillingModeObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "B-1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getBillingModeObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "BillingMode-1";
        objects.add(object);
        return objects;
    }



    public static BillingModeSearchRequestDTO getSearchBillingModeRequestDTO() {
        return BillingModeSearchRequestDTO.builder()
                .id(1L)
                .name("BillingMode-1")
                .code("B-1")
                .status('Y')
                .build();
    }

    public static List<BillingModeMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(BillingModeMinimalResponseDTO
                .builder()
                .name("BillingMode-1")
                .code("B-1")
                .status('Y')
                .build());
    }

    public static List<DropDownResponseDTO> getDropDownInfo() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("BillingMode-2")
                .build());

    }

    public static BillingModeResponseDTO getBillingModeResponseDTO() {
        return BillingModeResponseDTO.builder()
                .name("BillingMode-1")
                .code("B-1")
                .status('Y')
                .remarks("test")
                .build();
    }





}
