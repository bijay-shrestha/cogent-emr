package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.discountscheme.DiscountSchemeRequestDTO;
import com.cogent.admin.dto.request.discountscheme.DiscountSchemeUpdateRequestDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeMinimalResponseDTO;
import com.cogent.admin.dto.response.discountscheme.DiscountSchemeResponseDTO;
import com.cogent.admin.dto.response.discountscheme.discountschemedetails.DiscountResponseDTO;
import com.cogent.persistence.model.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.cogent.admin.constants.StringConstant.COMMA_SEPARATED;
import static com.cogent.admin.utils.StringUtil.toUpperCase;

/**
 * @author Sauravi Thapa 11/11/19
 */
public class DiscountSchemeUtils {

    public static Function<DiscountSchemeRequestDTO, DiscountScheme> parseToDiscountScheme = (requestDTO -> {
        DiscountScheme discountScheme = new DiscountScheme();
        discountScheme.setName(toUpperCase(requestDTO.getName()));
        discountScheme.setCode(toUpperCase(requestDTO.getCode()));
        discountScheme.setNetDiscount((requestDTO.getHas_netDiscount()) ? requestDTO.getNetDiscount() : null);
        discountScheme.setHas_netDiscount(requestDTO.getHas_netDiscount());
        discountScheme.setStatus(requestDTO.getStatus());

        return discountScheme;
    });

    public static BiFunction<DiscountScheme, DeleteRequestDTO, DiscountScheme>
            deleteDiscountScheme = (discountSchemeToDelete, deleteRequestDTO) -> {
        discountSchemeToDelete.setStatus(deleteRequestDTO.getStatus());
        discountSchemeToDelete.setRemarks(deleteRequestDTO.getRemarks());

        return discountSchemeToDelete;
    };

    public static BiFunction<DepartmentDiscount, DeleteRequestDTO, DepartmentDiscount>
            deleteDepartmentDiscount = (departmentDiscountToDelete, deleteRequestDTO) -> {
        departmentDiscountToDelete.setStatus(deleteRequestDTO.getStatus());
        departmentDiscountToDelete.setRemarks(deleteRequestDTO.getRemarks());

        return departmentDiscountToDelete;
    };

    public static BiFunction<ServiceDiscount, DeleteRequestDTO, ServiceDiscount>
            deleteServiceDiscount = (serviceDiscountToDelete, deleteRequestDTO) -> {
        serviceDiscountToDelete.setStatus(deleteRequestDTO.getStatus());
        serviceDiscountToDelete.setRemarks(deleteRequestDTO.getRemarks());

        return serviceDiscountToDelete;
    };

    public static BiFunction<DiscountScheme, DiscountSchemeUpdateRequestDTO, DiscountScheme>
            update = (discountSchemeToupdate, requestDTO) -> {

        discountSchemeToupdate.setName(requestDTO.getName());
        discountSchemeToupdate.setCode(requestDTO.getCode());
        discountSchemeToupdate.setStatus(requestDTO.getStatus());
        discountSchemeToupdate.setHas_netDiscount(requestDTO.getHas_netDiscount());
        discountSchemeToupdate.setNetDiscount((requestDTO.getHas_netDiscount()) ? requestDTO.getNetDiscount() : null);
        discountSchemeToupdate.setRemarks(requestDTO.getRemarks());

        return discountSchemeToupdate;
    };


    public static DepartmentDiscount parseToDepartmentDiscountScheme(Department department,
                                                                     Double departmentDiscountPercentage,
                                                                     Character status,
                                                                     DiscountScheme discountScheme) {
        DepartmentDiscount departmentDiscount = new DepartmentDiscount();
        departmentDiscount.setDiscountScheme(discountScheme);
        departmentDiscount.setDepartment(department);
        departmentDiscount.setDiscount(departmentDiscountPercentage);
        departmentDiscount.setStatus(status);

        return departmentDiscount;
    }

    public static DepartmentDiscount updateDepartmentDiscountDetails(DepartmentDiscount departmentDiscount,
                                                                     Double departmentDiscountPercentage,
                                                                     DiscountSchemeUpdateRequestDTO requestDTO) {

        departmentDiscount.setDiscount(departmentDiscountPercentage);
        departmentDiscount.setStatus(requestDTO.getStatus());
        departmentDiscount.setRemarks(requestDTO.getRemarks());

        return departmentDiscount;

    }

    public static ServiceDiscount parseToServiceDiscountScheme(Service service,
                                                               Double serviceDiscountPercentage,
                                                               Character staus,
                                                               DiscountScheme discountScheme) {
        ServiceDiscount serviceDiscount = new ServiceDiscount();
        serviceDiscount.setDiscountScheme(discountScheme);
        serviceDiscount.setService(service);
        serviceDiscount.setDiscount(serviceDiscountPercentage);
        serviceDiscount.setStatus(staus);

        return serviceDiscount;
    }

    public static ServiceDiscount updateServiceDiscountDetails(ServiceDiscount serviceDiscount,
                                                               Double serviceDiscountPercentage,
                                                               DiscountSchemeUpdateRequestDTO requestDTO) {

        serviceDiscount.setDiscount(serviceDiscountPercentage);
        serviceDiscount.setStatus(requestDTO.getStatus());
        serviceDiscount.setRemarks(requestDTO.getRemarks());

        return serviceDiscount;

    }

    public static List<DiscountSchemeMinimalResponseDTO> parseToDiscountSchemeMinimalResponseDTO(List<DiscountResponseDTO>
                                                                                                         responseDTOS) {
        List<DiscountSchemeMinimalResponseDTO> minimalResponseDTOS = new ArrayList<>();
        responseDTOS.forEach(responseDTO -> {
            DiscountSchemeMinimalResponseDTO minimalResponseDTO = new DiscountSchemeMinimalResponseDTO();
            minimalResponseDTO.setName(responseDTO.getName());
            minimalResponseDTO.setCode(responseDTO.getCode());
            minimalResponseDTO.setStatus(responseDTO.getStatus());
            minimalResponseDTO.setNetDiscount((responseDTO.getNetDiscount() == null) ? null :
                    responseDTO.getNetDiscount());
            minimalResponseDTO.setDepartmentDiscount((responseDTO.getDepartmentDiscount() == null) ? null :
                    convertToDepartmentDiscountList(responseDTO));
            minimalResponseDTO.setServiceDiscount((responseDTO.getServiceDiscount() == null) ? null :
                    convertToServiceDiscountList(responseDTO));
            minimalResponseDTOS.add(minimalResponseDTO);

        });
        return minimalResponseDTOS;
    }

    public static DiscountSchemeResponseDTO parseToDiscountSchemeResponseDTO(DiscountResponseDTO responseDTO) {
        DiscountSchemeResponseDTO schemeResponseDTO = new DiscountSchemeResponseDTO();
        schemeResponseDTO.setName(responseDTO.getName());
        schemeResponseDTO.setCode(responseDTO.getCode());
        schemeResponseDTO.setStatus(responseDTO.getStatus());
        schemeResponseDTO.setNetDiscount((responseDTO.getNetDiscount() == null) ? null :
                responseDTO.getNetDiscount());
        schemeResponseDTO.setDepartmentDiscount((responseDTO.getDepartmentDiscount() == null) ? null :
                convertToDepartmentDiscountList(responseDTO));
        schemeResponseDTO.setServiceDiscount((responseDTO.getServiceDiscount() == null) ? null :
                convertToServiceDiscountList(responseDTO));

        return schemeResponseDTO;

    }


    public static Map<String, String> convertToDepartmentDiscountList(DiscountResponseDTO responseDTO) {
        Map<String, String> departmentDiscountList = new HashMap<>();
        if (!Objects.isNull(responseDTO.getDepartmentDiscount())) {
            String[] departmentList = responseDTO.getDepartmentName().split(COMMA_SEPARATED);
            String[] departmentDiscount = responseDTO.getDepartmentDiscount().split(COMMA_SEPARATED);
            for (int i = 0; i < departmentList.length; i++) {
                departmentDiscountList.put(departmentList[i], departmentDiscount[i]);
            }
        }
        return departmentDiscountList;
    }

    public static Map<String, String> convertToServiceDiscountList(DiscountResponseDTO responseDTO) {
        Map<String, String> serviceDiscountList = new HashMap<>();
        if (!Objects.isNull(responseDTO.getServiceDiscount())) {
            String[] serviceList = responseDTO.getServiceName().split(COMMA_SEPARATED);
            String[] serviceDiscount = responseDTO.getServiceDiscount().split(COMMA_SEPARATED);
            for (int i = 0; i < serviceList.length; i++) {
                serviceDiscountList.put(serviceList[i], serviceDiscount[i]);
            }
        }
        return serviceDiscountList;
    }


}
