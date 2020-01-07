package com.cogent.admin.dto.discountscheme;

import com.cogent.admin.dto.discountscheme.discountschemedetails.DepartmentDiscountRequestDTO;
import com.cogent.admin.dto.discountscheme.discountschemedetails.ServiceDiscountRequestDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeMinimalResponseDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DepartmentDiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.DiscountDropDownResponseDTO;
import com.cogent.admin.dto.response.discountscheme.dropdown.ServiceDiscountDropDownResponseDTO;
import com.cogent.persistence.model.DepartmentDiscount;
import com.cogent.persistence.model.DiscountScheme;
import com.cogent.persistence.model.ServiceDiscount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cogent.admin.dto.department.DepartmentTestUtils.getDepartmentInfo;
import static com.cogent.admin.dto.service.ServiceTestUtils.getServiceInfo;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Sauravi Thapa 11/11/19
 */
public class DiscountSchemeTestUtils {
    public static DiscountSchemeRequestDTO getDiscountSchemeRequestDTO() {
        return DiscountSchemeRequestDTO.builder()
                .name("Ncell")
                .code("ncell")
                .status('Y')
                .has_netDiscount(FALSE)
                .departmentDiscountList(getDepartmentDiscountRequestDTOList())
                .serviceDiscountList(getServiceDiscountReuqestDTO())
                .build();
    }

    public static DiscountSchemeUpdateRequestDTO getDiscountSchemeUpdateRequestDTOWithNetDisocunt() {
        return DiscountSchemeUpdateRequestDTO.builder()
                .id(1L)
                .name("Ncell")
                .code("ncell")
                .status('Y')
                .has_netDiscount(TRUE)
                .netDiscount(50D)
                .remarks("update discount scheme")
                .build();
    }

    public static DiscountSchemeUpdateRequestDTO getDiscountSchemeUpdateRequestDTOWithoutNetDisocunt() {
        return DiscountSchemeUpdateRequestDTO.builder()
                .id(1L)
                .name("Ncell")
                .code("ncell")
                .status('Y')
                .has_netDiscount(FALSE)
                .netDiscount(null)
                .departmentDiscountList(getDepartmentDiscountRequestDTOList())
                .serviceDiscountList(getServiceDiscountRequestDTOList())
                .remarks("update discount scheme")
                .build();
    }


    public static DiscountScheme getDiscountSchemeInfoWithoutNetDiscount() {
        DiscountScheme discountScheme = new DiscountScheme();
        discountScheme.setId(1L);
        discountScheme.setName("Ncell");
        discountScheme.setCode("ncell");
        discountScheme.setStatus('Y');
        discountScheme.setNetDiscount(null);
        discountScheme.setHas_netDiscount(FALSE);
        discountScheme.setRemarks(null);
        return discountScheme;
    }

    public static DiscountScheme getDiscountSchemeInfoWithNetDiscountToUpdate() {
        DiscountScheme discountScheme = new DiscountScheme();
        discountScheme.setId(1L);
        discountScheme.setName("Ncell");
        discountScheme.setCode("ncell");
        discountScheme.setStatus('Y');
        discountScheme.setNetDiscount(50D);
        discountScheme.setHas_netDiscount(TRUE);
        discountScheme.setRemarks("update discount scheme");
        return discountScheme;
    }

    public static DiscountScheme getDiscountSchemeInfoWithNetDiscount() {
        DiscountScheme discountScheme = new DiscountScheme();
        discountScheme.setId(1L);
        discountScheme.setName("Ncell");
        discountScheme.setCode("ncell");
        discountScheme.setStatus('Y');
        discountScheme.setNetDiscount(100D);
        discountScheme.setHas_netDiscount(TRUE);
        discountScheme.setRemarks(null);
        return discountScheme;
    }

    public static DiscountScheme getDiscountSchemeInfoWithNetDiscountToDelete() {
        DiscountScheme discountScheme = new DiscountScheme();
        discountScheme.setId(1L);
        discountScheme.setName("Ncell");
        discountScheme.setCode("ncell");
        discountScheme.setStatus('D');
        discountScheme.setNetDiscount(100D);
        discountScheme.setHas_netDiscount(TRUE);
        discountScheme.setRemarks("Yes.. Delete it");
        return discountScheme;
    }


    public static List<DepartmentDiscountRequestDTO> getDepartmentDiscountRequestDTOList() {
        return Arrays.asList(DepartmentDiscountRequestDTO.builder()
                .departmentDiscount(15D)
                .departmentId(1L)
                .build());
    }

    public static List<ServiceDiscountRequestDTO> getServiceDiscountRequestDTOList() {
        return Arrays.asList(ServiceDiscountRequestDTO.builder()
                .serviceDiscount(15D)
                .serviceId(1L)
                .build());
    }

    public static List<DepartmentDiscount> getDepartmentDiscountSchemeList() {
        return Arrays.asList(getDepartmentDiscountInfo());
    }

    public static List<ServiceDiscount> getServiceDiscountSchemeList() {
        return Arrays.asList(getServiceDiscountSchemeInfo());
    }


    public static DepartmentDiscount getDepartmentDiscountInfo() {
        DepartmentDiscount departmentDiscount = new DepartmentDiscount();
        departmentDiscount.setId(1L);
        departmentDiscount.setDiscountScheme(getDiscountSchemeInfoWithoutNetDiscount());
        departmentDiscount.setDepartment(getDepartmentInfo());
        departmentDiscount.setDiscount(100D);
        departmentDiscount.setStatus('Y');

        return departmentDiscount;
    }

    public static DepartmentDiscount getDepartmentDiscountInfoToDelete() {
        DepartmentDiscount departmentDiscount = new DepartmentDiscount();
        departmentDiscount.setId(1L);
        departmentDiscount.setDiscountScheme(getDiscountSchemeInfoWithoutNetDiscount());
        departmentDiscount.setDepartment(getDepartmentInfo());
        departmentDiscount.setDiscount(100D);
        departmentDiscount.setStatus('D');
        departmentDiscount.setRemarks("Yes.. Delete it");

        return departmentDiscount;
    }

    public static ServiceDiscount getServiceDiscountSchemeInfo() {
        ServiceDiscount serviceDiscount = new ServiceDiscount();
        serviceDiscount.setId(1L);
        serviceDiscount.setDiscountScheme(getDiscountSchemeInfoWithoutNetDiscount());
        serviceDiscount.setService(getServiceInfo());
        serviceDiscount.setDiscount(100D);
        serviceDiscount.setStatus('Y');

        return serviceDiscount;
    }

    public static ServiceDiscount getServiceDiscountSchemeInfoToDelete() {
        ServiceDiscount serviceDiscount = new ServiceDiscount();
        serviceDiscount.setId(1L);
        serviceDiscount.setDiscountScheme(getDiscountSchemeInfoWithoutNetDiscount());
        serviceDiscount.setService(getServiceInfo());
        serviceDiscount.setDiscount(100D);
        serviceDiscount.setStatus('D');
        serviceDiscount.setRemarks("Yes.. Delete it");

        return serviceDiscount;
    }

    public static List<ServiceDiscountRequestDTO> getServiceDiscountReuqestDTO() {
        return Arrays.asList(ServiceDiscountRequestDTO.builder()
                .serviceDiscount(15D)
                .serviceId(1L)
                .build());
    }


    public static DiscountSchemeMinimalResponseDTO getDiscountSchemeMinimalResponseDTO() {
        return DiscountSchemeMinimalResponseDTO.builder()
                .name("Ncell")
                .code("ncell")
                .status('Y')
                .build();
    }

    public static DiscountSchemeResponseDTO getDiscountSchemeResponseDTO() {
        return DiscountSchemeResponseDTO.builder()
                .remarks("Test")
                .build();
    }

    public static DiscountDropDownResponseDTO getDropDownResponseDTO() {
        return DiscountDropDownResponseDTO.builder()
                .value(1L)
                .label("Ncell")
                .build();
    }


    public static DepartmentDiscountDropDownResponseDTO getDepartmentDiscountDropDownResponseDTO() {
        return DepartmentDiscountDropDownResponseDTO.builder()
                .value(1L)
                .label("Ncell")
                .build();
    }

    public static ServiceDiscountDropDownResponseDTO getServiceDiscountDropDownResponseDTO() {
        return ServiceDiscountDropDownResponseDTO.builder()
                .value(1L)
                .label("Ncell")
                .build();
    }

    public static List<DiscountSchemeMinimalResponseDTO> discountSchemeMinimalResponseDTO() {
        List<DiscountSchemeMinimalResponseDTO> minimalResponseDTOS = Arrays
                .asList(getDiscountSchemeMinimalResponseDTO());
        return minimalResponseDTOS;
    }

    public static List<DiscountDropDownResponseDTO> getDropDownResponse() {
        List<DiscountDropDownResponseDTO> downResponseDTOS = Arrays
                .asList(getDropDownResponseDTO());
        return downResponseDTOS;
    }

    public static List<DepartmentDiscountDropDownResponseDTO> getDepartmentDiscountDropDownResponseDTOS() {
        List<DepartmentDiscountDropDownResponseDTO> downResponseDTOS = Arrays
                .asList(getDepartmentDiscountDropDownResponseDTO());
        return downResponseDTOS;
    }

    public static List<ServiceDiscountDropDownResponseDTO> getServiceDiscountDropDownResponseDTOS() {
        List<ServiceDiscountDropDownResponseDTO> downResponseDTOS = Arrays
                .asList(getServiceDiscountDropDownResponseDTO());
        return downResponseDTOS;
    }

    public static List<Object[]> getDiscountSchemeObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Ncell";
        object[1] = "ncell";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getDiscountSchemeObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "ncell";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getDiscountSchemeObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "Ncell";
        objects.add(object);
        return objects;
    }
}
