package com.cogent.admin.dto.servicetype;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.servicetype.ServiceTypeRequestDTO;
import com.cogent.admin.dto.request.servicetype.ServiceTypeSearchRequestDTO;
import com.cogent.admin.dto.request.servicetype.ServiceTypeUpdateRequestDTO;
import com.cogent.admin.dto.response.servicetype.ServiceTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.servicetype.ServiceTypeResponseDTO;
import com.cogent.persistence.model.ServiceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sauravi Thapa 11/13/19
 */
public class ServiceTypeTestUtils {
    public static ServiceType getServiceTypeInfo() {
      ServiceType serviceType = new ServiceType();
      serviceType.setId(1L);
      serviceType.setName("TEST");
      serviceType.setCode("TST");
      serviceType.setStatus('Y');

      return serviceType;
    }
    public static ServiceTypeRequestDTO getServiceTypeRequestDTO() {
        return ServiceTypeRequestDTO.builder()
                .name("TEST")
                .code("TST")
                .status('Y')
                .build();
    }


    public static ServiceTypeUpdateRequestDTO getServiceTypeUpdateRequestDTO() {
        return ServiceTypeUpdateRequestDTO.builder()
                .id(1L)
                .name("TEST")
                .code("TST")
                .status('Y')
                .remarks("Update")
                .build();
    }

    public static ServiceTypeSearchRequestDTO getServiceTypeSearchRequestDTO() {
        return ServiceTypeSearchRequestDTO.builder()
                .id(1L)
                .name("TEST")
                .code("TST")
                .status('Y')
                .build();
    }

    public static ServiceTypeMinimalResponseDTO getServiceTypeMinimalResponseDTO() {
        return ServiceTypeMinimalResponseDTO.builder()
                .id(1L)
                .name("TEST")
                .code("TST")
                .status('Y')
                .build();
    }

    public static List<ServiceTypeMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getServiceTypeMinimalResponseDTO());
    }


    public static ServiceTypeResponseDTO getServiceTypeResponseDTO() {
        return ServiceTypeResponseDTO.builder()
                .remarks("Test")
                .build();
    }


    public static DropDownResponseDTO dropDownResponseDTO() {
        return DropDownResponseDTO.builder()
                .value(1L)
                .label("TEST")
                .build();
    }

    public static List<DropDownResponseDTO> dropDownResponseDTOS() {
        return Arrays.asList(dropDownResponseDTO());
    }

    public static DeleteRequestDTO getDeleteRequestDTO() {
        return DeleteRequestDTO.builder()
                .id(1L)
                .status('D')
                .remarks("delete service").build();
    }


    public static List<Object[]> getServiceTypeObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "TEST";
        object[1] = "TST";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getServiceTypeObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "TEST";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getServiceTypeObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "TST";
        objects.add(object);
        return objects;
    }
}
