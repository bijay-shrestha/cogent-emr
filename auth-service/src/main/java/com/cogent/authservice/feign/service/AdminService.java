package com.cogent.authservice.feign.service;

import com.cogent.authservice.feign.dto.response.AdminInfoByUsernameResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static com.cogent.authservice.feign.constants.MicroServiceConstants.API_V1;
import static com.cogent.authservice.feign.constants.MicroServiceConstants.AdminMicroservice.BASE_ADMIN;
import static com.cogent.authservice.feign.constants.MicroServiceConstants.AdminMicroservice.BASE_MODULE_NAME;
import static com.cogent.authservice.feign.constants.MicroServiceConstants.USERNAME_VARIABLE_BASE;

/**
 * @author smriti ON 02/01/2020
 */
@FeignClient(BASE_MODULE_NAME)
@RequestMapping(API_V1 + BASE_ADMIN)
public interface AdminService {

    @GetMapping(USERNAME_VARIABLE_BASE)
    Optional<AdminInfoByUsernameResponseDTO> fetchAdminInfoByUsernameResponseDTO(@PathVariable("username")String username);
}
