package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.request.forgotPassword.ForgotPasswordRequestDTO;
import com.cogent.admin.service.ForgotPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cogent.admin.constants.SwaggerConstants.ForgotPasswordConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.BASE_PASSWORD;
import static com.cogent.admin.constants.WebResourceKeyConstants.ForgotPasswordConstants.*;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-09-20
 */
@RestController
@RequestMapping(API_V1 + BASE_PASSWORD)
@Api(BASE_API_VALUE)
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping(FORGOT)
    @ApiOperation(FORGOT_PASSWORD_OPERATION)
    public ResponseEntity<?> forgotPassword(@RequestParam(name = "username") String username) {
        forgotPasswordService.forgotPassword(username);
        return ok().build();
    }

    @GetMapping(VERIFY)
    @ApiOperation(VERIFY_RESET_CODE)
    public ResponseEntity<?> verify(@RequestParam(name = "resetCode") String resetCode) {
        forgotPasswordService.verify(resetCode);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword(@Valid @RequestBody ForgotPasswordRequestDTO requestDTO) {
        forgotPasswordService.updatePassword(requestDTO);
        return ok().build();
    }

}
