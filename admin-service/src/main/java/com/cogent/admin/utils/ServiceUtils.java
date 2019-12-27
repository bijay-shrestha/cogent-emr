package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.service.ServiceRequestDTO;
import com.cogent.admin.dto.request.service.ServiceUpdateRequestDTO;
import com.cogent.persistence.model.Department;
import com.cogent.persistence.model.Service;
import com.cogent.persistence.model.ServiceType;
import com.cogent.persistence.model.SubDepartment;

import java.util.function.BiFunction;

import static com.cogent.admin.utils.StringUtil.toUpperCase;

public class ServiceUtils {
    public static Service parseToService(ServiceRequestDTO requestDTO,
                                         Department department,
                                         SubDepartment subDepartment,
                                         ServiceType serviceType) {
        Service service = new Service();
        service.setName(toUpperCase(requestDTO.getName()));
        service.setCode(toUpperCase(requestDTO.getCode()));
        service.setStatus(requestDTO.getStatus());
        service.setDepartment(department);
        service.setSubDepartment(subDepartment);
        service.setServiceType(serviceType);
        return service;
    }

    public static BiFunction<Service, DeleteRequestDTO, Service> deleteService = (ServiceToDelete
            , deleteRequestDTO) -> {
        ServiceToDelete.setStatus(deleteRequestDTO.getStatus());
        ServiceToDelete.setRemarks(deleteRequestDTO.getRemarks());
        return ServiceToDelete;
    };

    public static final Service update(ServiceUpdateRequestDTO requestDTO,
                                       Service serviceToUpdate,
                                       Department department,
                                       SubDepartment subDepartment,
                                       ServiceType serviceType) {
        serviceToUpdate.setName(toUpperCase(requestDTO.getName()));
        serviceToUpdate.setCode(toUpperCase(requestDTO.getCode()));
        serviceToUpdate.setStatus(requestDTO.getStatus());
        serviceToUpdate.setDepartment(department);
        serviceToUpdate.setSubDepartment(subDepartment);
        serviceToUpdate.setServiceType(serviceType);
        serviceToUpdate.setRemarks(requestDTO.getRemarks());
        return serviceToUpdate;
    }

}
