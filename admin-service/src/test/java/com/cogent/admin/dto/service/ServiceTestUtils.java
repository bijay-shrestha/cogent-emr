package com.cogent.admin.dto.service;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.service.ServiceRequestDTO;
import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import com.cogent.admin.dto.request.service.ServiceUpdateRequestDTO;
import com.cogent.admin.dto.response.service.ServiceMinimalResponseDTO;
import com.cogent.admin.dto.response.service.ServiceResponseDTO;
import com.cogent.persistence.model.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.department.DepartmentTestUtils.getDepartmentInfo;
import static com.cogent.admin.dto.servicetype.ServiceTypeTestUtils.getServiceTypeInfo;
import static com.cogent.admin.dto.subdepartment.SubDepartmentTestUtlis.getSubDepartmentInfo;

public class ServiceTestUtils {
    public static ServiceRequestDTO getServiceRequestDTO() {
        return ServiceRequestDTO.builder()
                .name("TEST")
                .code("TST")
                .status('Y')
                .departmentId(1L)
                .subDepartmentId(1L)
                .serviceTypeId(1L)
                .build();
    }


    public static ServiceUpdateRequestDTO getServiceUpdateRequestDTO() {
        return ServiceUpdateRequestDTO.builder()
                .id(1L)
                .name("TEST")
                .code("TST")
                .status('Y')
                .departmentId(1L)
                .subDepartmentId(1L)
                .serviceTypeId(1L)
                .remarks("Update")
                .build();
    }

    public static Service getServiceInfo() {
        Service service = new Service();
        service.setId(1L);
        service.setName("TEST");
        service.setCode("TST");
        service.setDepartment(getDepartmentInfo());
        service.setSubDepartment(getSubDepartmentInfo());
        service.setServiceType(getServiceTypeInfo());
        service.setStatus('Y');

        return service;
    }

    public static Service getServiceToDelete() {
        Service service = new Service();
        service.setId(1L);
        service.setName("TEST");
        service.setCode("TST");
        service.setDepartment(getDepartmentInfo());
        service.setSubDepartment(getSubDepartmentInfo());
        service.setServiceType(getServiceTypeInfo());
        service.setStatus('D');
        service.setRemarks("delete service");

        return service;
    }

    public static Service getServiceInfoToUpdate() {
        Service service = new Service();
        service.setId(1L);
        service.setName("TEST");
        service.setCode("TST");
        service.setDepartment(getDepartmentInfo());
        service.setSubDepartment(getSubDepartmentInfo());
        service.setServiceType(getServiceTypeInfo());
        service.setStatus('Y');
        service.setRemarks("Update");

        return service;
    }

    public static ServiceSearchRequestDTO getSearchRequestDTO() {
        return ServiceSearchRequestDTO.builder()
                .id(1L)
                .name("TEST")
                .code("TST")
                .status('Y')
                .build();
    }

    public static ServiceMinimalResponseDTO getMinimalResponseDTO() {
        return ServiceMinimalResponseDTO.builder()
                .id(1L)
                .name("TEST")
                .code("TST")
                .status('Y')
                .build();
    }

    public static List<ServiceMinimalResponseDTO> getMinimalResponseDTOList() {
        return Arrays.asList(getMinimalResponseDTO());
    }


    public static ServiceResponseDTO getServiceResponseDTO() {
        return ServiceResponseDTO.builder()
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


    public static List<Object[]> getServiceObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "TEST";
        object[1] = "TST";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getServiceObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "TEST";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getServiceObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "TST";
        objects.add(object);
        return objects;
    }
}
